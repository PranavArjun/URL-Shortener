package com.app.url_shortener.dto;

import com.app.url_shortener.model.Role;

public record UserResponseDto (Long id, String name, String email,String role){
}
