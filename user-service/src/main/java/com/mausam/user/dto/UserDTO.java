package com.mausam.user.dto;

import lombok.*;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private int status;

}
