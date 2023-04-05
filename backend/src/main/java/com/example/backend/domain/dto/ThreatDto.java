package com.example.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreatDto {
    private Long id;
    private String name;
    private String severity;
    private String source;
    private String deviceType;
    private Float potentialImpact;
}
