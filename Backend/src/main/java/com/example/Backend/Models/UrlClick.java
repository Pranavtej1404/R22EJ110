package com.example.Backend.Models;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlClick {
    private Instant time;
    private String referrer;
    private String ip;
    private String location;
}
