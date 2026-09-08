package com.springAlura.springAlura.configs;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {
}
