package com.abhi.microservices.inventory.service;

import com.abhi.microservices.inventory.repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Boolean getNonExistingSkewCodes(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }


}
