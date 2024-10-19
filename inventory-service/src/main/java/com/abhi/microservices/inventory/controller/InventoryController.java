package com.abhi.microservices.inventory.controller;

import com.abhi.microservices.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public Boolean isInStock(@RequestParam("skuCode") String skuCode) {
        log.info("SkuCode : {}", skuCode);
        return inventoryService.getNonExistingSkewCodes(skuCode);
    }
}
