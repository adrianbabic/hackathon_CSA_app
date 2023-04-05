package com.example.backend.service;

import com.example.backend.domain.Alert;
import com.example.backend.repository.AlertRepository;
import com.example.backend.request.CreateAlertRequest;
import com.example.backend.result.ActionResult;
import com.example.backend.result.DataResult;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private static final Logger logger = LogManager.getLogger(RecordService.class);

    public ActionResult addAlert(CreateAlertRequest request) {
        Alert alert = Alert
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .data(request.getData())
                .field(request.getField())
                .build();
        alertRepository.save(alert);
        logger.info("Alert added successfully");
        return new ActionResult(true, "Alert added successfully");
    }

    public ActionResult deleteAlert(Long id) {
        Optional<Alert> alert = alertRepository.findById(id);
        if(alert.isEmpty()) {
            logger.error("Alert with id: '" + id + "' not found");
            return new ActionResult(false, "Alert with id: '" + id + "' not found");
        }
        alertRepository.deleteById(id);
        logger.info("Alert deleted successfully");
        return new ActionResult(true, "Alert deleted successfully");
    }

        public DataResult<List<Alert>> getAlerts() {
        logger.info("Alerts fetched successfully");
        return new DataResult<>(true, "Alerts fetched successfully", alertRepository.findAll());
    }

}
