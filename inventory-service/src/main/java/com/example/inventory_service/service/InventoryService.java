package com.example.inventory_service.service;

import com.example.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class InventoryService { @Autowired
InventoryRepository inventoryRepository;

@Transactional(readOnly = true)
public boolean isInStock(String skuCode) {
  return inventoryRepository.findBySkuCode(skuCode).isPresent();
}
}
