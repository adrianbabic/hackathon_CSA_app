package com.example.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Threat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String severity;
    private String source;
    private Float potentialImpact;
    private String deviceType;
    @ManyToMany(mappedBy = "threats")
    private List<Record> records;

    public Threat(String name, String severity, String source, Float potentialImpact, String deviceType) {
        this.name = name;
        this.severity = severity;
        this.source = source;
        this.potentialImpact = potentialImpact;
        this.deviceType = deviceType;
    }
}
