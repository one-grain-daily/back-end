package com.hackathon.Config.jwt;

public interface JwtProperties {
    String SECRET = "cos";
    int EXPIRATION_TIME = 6000 * 120;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
