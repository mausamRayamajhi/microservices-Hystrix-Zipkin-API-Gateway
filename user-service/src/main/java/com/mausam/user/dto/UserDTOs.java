package com.mausam.user.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserDTOs {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private int status;

}
