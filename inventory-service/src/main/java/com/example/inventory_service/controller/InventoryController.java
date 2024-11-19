package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")

public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    //http://localhost:8002/api/inventory/skuCode=iphnone&skuCode=iphone
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) throws InterruptedException {

        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "We are testing our inventory apis";
    }


}
