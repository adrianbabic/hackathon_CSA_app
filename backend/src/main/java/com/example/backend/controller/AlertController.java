package com.example.backend.controller;

import com.example.backend.domain.Alert;
import com.example.backend.request.CreateAlertRequest;
import com.example.backend.result.ActionResult;
import com.example.backend.result.DataResult;
import com.example.backend.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alert")
public class AlertController {

    private final AlertService alertService;


    @DeleteMapping
    public ResponseEntity<ActionResult> deleteAlert(@RequestParam Long id) {
        return alertService.deleteAlert(id).intoResponseEntity();
    }

    @PostMapping
    public ResponseEntity<ActionResult> addAlert(@RequestBody CreateAlertRequest request) {
        return alertService.addAlert(request).intoResponseEntity();
    }

    @GetMapping
    public ResponseEntity<DataResult<List<Alert>>> getAlerts() {
        return alertService.getAlerts().intoResponseEntity();
    }

}
