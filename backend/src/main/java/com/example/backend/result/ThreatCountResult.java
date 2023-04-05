package com.example.backend.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThreatCountResult {
    private Map<String, Integer> threatsBySeverity;
    private Date timestamp;
}
