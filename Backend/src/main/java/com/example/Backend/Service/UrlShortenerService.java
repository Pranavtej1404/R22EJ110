package com.example.Backend.Service;

import org.springframework.stereotype.Service;

import com.example.Backend.Models.ShortUrl;

import java.util.*;

@Service
public class UrlShortenerService {

    private final Map<String, ShortUrl> store = new HashMap<>();

    public ShortUrl createShortUrl(String shortcode, String url, Integer validityMinutes) {
        if (shortcode == null || shortcode.isEmpty()) {
            shortcode = generateRandomShortcode();
        }
        if (validityMinutes == null) {
            validityMinutes = 30;
        }
        Date expiry = new Date(System.currentTimeMillis() + validityMinutes * 60 * 1000L);
        ShortUrl shortUrl = new ShortUrl(shortcode, url, expiry);
        store.put(shortcode, shortUrl);
        return shortUrl;
    }

    public ShortUrl getByShortcode(String shortcode) {
        return store.get(shortcode);
    }

    public Collection<ShortUrl> getAll() {
        return store.values();
    }

    private String generateRandomShortcode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
