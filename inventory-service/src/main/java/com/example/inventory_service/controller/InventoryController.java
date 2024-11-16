package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
InventoryService inventoryService;

    //http://localhost:8002/api/inventory/skuCode=iphnone&skuCode=ipohone
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
       return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "We are testing our inventory apis";
    }


}
