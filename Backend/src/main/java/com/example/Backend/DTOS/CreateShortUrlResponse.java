package com.example.Backend.DTOS;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@Data
@AllArgsConstructor
public class CreateShortUrlResponse {
    private String shortcode;
    private String originalUrl;
    private Instant expiresAt;
}
