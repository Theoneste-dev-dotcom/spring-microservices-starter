package com.example.order_service.service;

import com.example.order_service.dto.InventoryResponse;
import com.example.order_service.dto.OrderLineItemsDto;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderLineItems;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    WebClient.Builder webClientBuilder;


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLIneItemsList(orderLineItems);


        List<String> skuCodes = order.getOrderLIneItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();


        for (String skuCode : skuCodes) {
            System.out.println(skuCode);
        }
        ;
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
//        InventoryResponse[] inventoryResponses;

        assert inventoryResponses != null;
        System.out.println("Inventory response length: " + inventoryResponses.length);

        if (inventoryResponses.length == 0) {
            System.out.println("No inventory data received.");
            throw new RuntimeException("Inventory service returned an empty response.");
        }


// Check stock availability only if the response is not empty
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            System.out.println("All products are in stock.");
            orderRepository.save(order); // Save order if all products are in stock
        } else {
            System.out.println("Some products are not in stock.");
            throw new IllegalArgumentException("Some products are not in stock. Please try again later.");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
