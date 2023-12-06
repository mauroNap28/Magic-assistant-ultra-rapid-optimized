package com.mauro.magicassistantultrarapidoptimized.controller;

import com.mauro.magicassistantultrarapidoptimized.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsController {

    @Autowired
    CardsService cardsService;

    @GetMapping("/compare-lists")
    public ResponseEntity<HashMap<String, Float>> compareStores(@RequestParam List<String> request) {
        return ResponseEntity.ok(cardsService.compareStores(request));
    }

    @GetMapping("/string-decrypter")
    public ResponseEntity<HashMap<String, Float>> stringDecrypter(@RequestParam String request) {
        return ResponseEntity.ok(cardsService.offersDecrypter(request));
    }

}
