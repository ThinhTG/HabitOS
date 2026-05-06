package com.habitos.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không hợp lệ")
        @Size(max = 255, message = "Email tối đa 255 ký tự")
        String email,

        @NotBlank(message = "Password không được để trống")
        @Size(min = 8, max = 72, message = "Password phải từ 8 đến 72 ký tự")
        String password,

        @NotBlank(message = "Firstname không được để trống")
        @Size(max = 100, message = "Firstname tối đa 100 ký tự")
        String firstname,

        @NotBlank(message = "Lastname không được để trống")
        @Size(max = 100, message = "Lastname tối đa 100 ký tự")
        String lastname
) {}
