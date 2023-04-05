package com.example.backend.service;

import com.example.backend.domain.Alert;
import com.example.backend.domain.Record;
import com.example.backend.domain.Threat;
import com.example.backend.domain.dto.RecordDto;
import com.example.backend.exception.BadRequestException;
import com.example.backend.mapper.Mapper;
import com.example.backend.repository.AlertRepository;
import com.example.backend.repository.RecordRepository;
import com.example.backend.repository.ThreatRepository;
import com.example.backend.result.ActionResult;
import com.example.backend.result.DataResult;
import com.example.backend.result.ThreatCountResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final ThreatRepository threatRepository;
    private final RecordRepository recordRepository;
    private final Mapper mapper;
    private final EmailService emailService;
    private final AlertRepository alertRepository;
    private static final Logger logger = LogManager.getLogger(RecordService.class);

    @Scheduled(fixedRate = 300000)
    public DataResult<RecordDto> scan() {
        int random = getRandomNumber(0, 10);
        List<Threat> threats = threatRepository.findRandom(random);
        Record record = Record.builder()
                .timestamp(new Date())
                .threats(threats)
                .build();

        recordRepository.save(record);

        checkAlerts(threats);

        RecordDto recordDto = mapper.recordToRecordDto(record);
        logger.info("Successfully scanned");
        return new DataResult<>(true, "Successfully scanned", recordDto);
    }

    public ActionResult scanCount() {
        int random = getRandomNumber(0, 10);
        List<Threat> threats = threatRepository.findRandom(random);
        Record record = Record.builder()
                .timestamp(new Date())
                .threats(threats)
                .build();

        recordRepository.save(record);
        Map<String, Integer> threatsCount = threats.stream().collect(Collectors.groupingBy(Threat::getSeverity, Collectors.summingInt(e -> 1)));
        ThreatCountResult result = new ThreatCountResult(threatsCount, new Date());
        logger.info("Successfully retrieved threats count");
        return new DataResult<>(true, "Successfully retrieved threats count", result);
    }


    public DataResult<List<RecordDto>> getRecords() {
        List<Record> records = recordRepository.findAll();
        List<RecordDto> recordDtos = new ArrayList<>();
        for(Record r : records){
            recordDtos.add(mapper.recordToRecordDto(r));
        }
        logger.info("Successfully retrieved records");
        return new DataResult<>(true, "Successfully retrieved records", recordDtos);
    }

    public DataResult<?> getThreatsCount() {
        List<Record> records = recordRepository.findAll();
        List<Threat> threats = records.stream().flatMap(record -> record.getThreats().stream()).toList();
        Map<String, Integer> threatsCount = threats.stream().collect(Collectors.groupingBy(Threat::getSeverity, Collectors.summingInt(e -> 1)));
        ThreatCountResult result = new ThreatCountResult(threatsCount, new Date());
        logger.info("Successfully retrieved threats count");
        return new DataResult<>(true, "Successfully retrieved threats count", result);
    }


    public ActionResult deleteRecord(Long id) {
        Optional<Record> record = recordRepository.findById(id);
        if(record.isEmpty()) {
            logger.error("Record with id " + id + " not found");
            return new ActionResult(false, "Record with id " + id + " not found");
        }
        recordRepository.deleteById(id);
        logger.info("Successfully deleted record");
        return new ActionResult(true, "Successfully deleted record");
    }

    public DataResult<?> getRecord(Long id) {
        Record record = recordRepository.findById(id).
                orElseThrow(() -> {
                    logger.error("Record with id " + id + " not found");
                    return new BadRequestException("Record with id " + id + " not found");
                });
        RecordDto recordDto = mapper.recordToRecordDto(record);
        logger.info("Successfully retrieved record");
        return new DataResult<>(true, "Successfully retrieved record", recordDto);
    }

    @SneakyThrows
    public ActionResult addRecordFromFile(MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        Record record = objectMapper.readValue(file.getInputStream(), Record.class);
        recordRepository.save(record);
        logger.info("Successfully added record");
        return new ActionResult(true, "Successfully added record");
    }


    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void checkAlerts(List<Threat> threats) {
        List<Alert> alerts = alertRepository.findAll();

        if(!alerts.isEmpty()){
            for(Alert alert : alerts){
                List<Threat> threatList = new ArrayList<>();
                for(Threat threat : threats){
                    if(threat.getName().equals(alert.getData()) && threat.getSource().equalsIgnoreCase(alert.getData())){
                        threatList.add(threat);
                    }
                    else if(alert.getField().equals("potentialImpact")){
                        if(threat.getPotentialImpact() > Float.parseFloat(alert.getData())){
                            threatList.add(threat);
                        }
                    }
                    if(!threatList.isEmpty()){
                        emailService.sendAlert(alert, threatList);
                    }
                }
            }
        }
    }

    public DataResult<List<RecordDto>> getTodaysRecords() {
        List<Record> records = recordRepository.findAll();
        List<RecordDto> recordDtos = new ArrayList<>();
        for(Record r : records){
            if(r.getTimestamp().getDay() == new Date().getDay()){
                recordDtos.add(mapper.recordToRecordDto(r));
            }
        }
        logger.info("Successfully retrieved records");
        return new DataResult<>(true, "Successfully retrieved records", recordDtos);
    }
}

