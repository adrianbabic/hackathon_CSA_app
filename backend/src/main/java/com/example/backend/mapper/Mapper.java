package com.example.backend.mapper;

import com.example.backend.domain.Member;
import com.example.backend.domain.Record;
import com.example.backend.domain.Threat;
import com.example.backend.domain.dto.MemberDto;
import com.example.backend.domain.dto.RecordDto;
import com.example.backend.domain.dto.ThreatDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class Mapper {

    public ThreatDto threatToThreatDto(Threat threat) {
        return ThreatDto.builder()
                .id(threat.getId())
                .name(threat.getName())
                .potentialImpact(round(threat.getPotentialImpact(),2))
                .source(threat.getSource())
                .severity(threat.getSeverity())
                .deviceType(threat.getDeviceType())
                .build();
    }

    public List<ThreatDto> threatsToThreatDtos(List<Threat> threats) {
        return threats.stream().map(this::threatToThreatDto).toList();
    }

    public MemberDto memberToMemberDto(Member member) {
        return MemberDto.builder()
                .email(member.getEmail())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .minorAlert(member.isMinorAlert())
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole())
                .build();
    }

    @SneakyThrows
    public RecordDto recordToRecordDto(Record record) {
        Float potentialImpact = (float) record.getThreats().stream().mapToDouble(Threat::getPotentialImpact).average().orElse(0.0);
        return RecordDto.builder()
                .id(record.getId())
                .timestamp(record.getTimestamp())
                .threats(threatsToThreatDtos(record.getThreats()))
                .potentialImpact(round(potentialImpact,2))
                .build();
    }

    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
