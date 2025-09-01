package com.example.Backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Backend.Models.ShortUrl;
import com.example.Backend.Service.UrlShortenerService;

import java.util.Collection;
import java.util.Date;
import java.util.Map;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UrlController {

    private final UrlShortenerService service;

    public UrlController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrl> shorten(@RequestBody Map<String, String> body) {
        String shortcode = body.get("shortcode");
        String url = body.get("url");
        Integer validity = (body.get("validity") != null) ? Integer.parseInt(body.get("validity")) : null;
        return ResponseEntity.ok(service.createShortUrl(shortcode, url, validity));
    }

    @GetMapping("/{shortcode}")
public ResponseEntity<Void> resolve(@PathVariable String shortcode) {
    ShortUrl shortUrl = service.getByShortcode(shortcode);

    if (shortUrl == null) {
        return ResponseEntity.notFound().build();
    }

    // Check expiry
    if (shortUrl.getExpiry().before(new Date())) {
        return ResponseEntity.status(410).build(); 
    }

    return ResponseEntity
            .status(302) 
            .header("Location", shortUrl.getOriginalUrl())
            .build();
}


    @GetMapping("/all")
    public ResponseEntity<Collection<ShortUrl>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
