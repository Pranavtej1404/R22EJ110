package com.example.Backend.Models; 

import java.util.Date;

public class ShortUrl {
    private String shortcode;
    private String originalUrl;
    private Date expiry;
    private int clicks;

    public ShortUrl(String shortcode, String originalUrl, Date expiry) {
        this.shortcode = shortcode;
        this.originalUrl = originalUrl;
        this.expiry = expiry;
        this.clicks = 0;
    }

  
    public String getShortcode() { return shortcode; }
    public void setShortcode(String shortcode) { this.shortcode = shortcode; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public Date getExpiry() { return expiry; }
    public void setExpiry(Date expiry) { this.expiry = expiry; }

    public int getClicks() { return clicks; }
    public void setClicks(int clicks) { this.clicks = clicks; }

    public void incrementClicks() { this.clicks++; }
}
