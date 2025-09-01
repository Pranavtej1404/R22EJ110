package com.example.Backend.DTOS;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateShortUrlRequest {
    @NotBlank
    private String originalUrl;
    
    @Min(1)
    private int validityMinutes = 30;

    private String customShortcode;
}
