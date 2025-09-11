package com.devapp.kafkaredis.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDataRequest {
    @NotBlank(message = "ID is required")
    private String id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
}
