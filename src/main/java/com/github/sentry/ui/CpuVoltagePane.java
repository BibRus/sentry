package com.github.sentry.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CpuVoltagePane extends LoggablePane {

    private static final Logger LOGGER = LoggerFactory.getLogger("cpuVoltage");

    public CpuVoltagePane(Stage stage, SystemInfo systemInfo) {
        super(stage, systemInfo);
    }

    @Override
    protected void updateMonitorText() {
        double cpuVoltage = this.getCpuVoltage();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss"));
        monitorText.append(String.format("[ %s ] %.2f \n", time, cpuVoltage));
        LOGGER.info(String.format("%.2f", cpuVoltage));
    }

    private double getCpuVoltage() {
        return this.systemInfo.getHardware().getSensors().getCpuVoltage();
    }

}
