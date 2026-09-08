package com.springAlura.springAlura.configs.security;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {
}
