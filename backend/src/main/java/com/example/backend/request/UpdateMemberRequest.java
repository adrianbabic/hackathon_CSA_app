package com.example.backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;
    private String role;
    private String phoneNumber;
    private boolean minorAlert;

}
