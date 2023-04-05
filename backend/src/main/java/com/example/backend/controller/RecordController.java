package com.example.backend.controller;

import com.example.backend.domain.dto.RecordDto;
import com.example.backend.result.ActionResult;
import com.example.backend.result.DataResult;
import com.example.backend.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/scan")
    public ResponseEntity<DataResult<RecordDto>> scan() {
        return recordService.scan().intoResponseEntity();
    }

    @GetMapping("/scan/count")
    public ResponseEntity<DataResult<?>> scanCount() {
        return recordService.scanCount().intoResponseEntity();
    }

    @GetMapping("/records")
    public ResponseEntity<DataResult<List<RecordDto>>> getRecords() {
        return recordService.getRecords().intoResponseEntity();
    }

    @GetMapping("/todaysRecords")
    public ResponseEntity<DataResult<List<RecordDto>>> getTodaysRecords() {
        return recordService.getTodaysRecords().intoResponseEntity();
    }
    @GetMapping("/records/{id}")
    public ResponseEntity<DataResult<RecordDto>> getRecord(@PathVariable("id") Long id) {
        return recordService.getRecord(id).intoResponseEntity();
    }

    @DeleteMapping("/records/{id}")
    public ResponseEntity<ActionResult> deleteRecord(@PathVariable("id") Long id) {
        return recordService.deleteRecord(id).intoResponseEntity();
    }

    @GetMapping("/threats/count")
    public ResponseEntity<DataResult<?>> getThreatsCount() {
        return recordService.getThreatsCount().intoResponseEntity();
    }

    @PostMapping("/records/file")
    public ResponseEntity<ActionResult> addRecordFromFile(@RequestParam("file")MultipartFile file) {
        return recordService.addRecordFromFile(file).intoResponseEntity();
    }
}
