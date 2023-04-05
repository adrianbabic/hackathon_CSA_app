package com.example.backend.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateAlertRequest {

    private String field;
    private String description;
    private String data;
    private String name;
    private boolean minorAlert;

}
