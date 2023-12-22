package com.github.sentry.ui;

import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CpuTemperaturePane extends LoggablePane {

    private static final Logger LOGGER = LoggerFactory.getLogger("cpuTemperature");

    public CpuTemperaturePane(Stage stage, SystemInfo systemInfo) {
        super(stage, systemInfo);
    }

    @Override
    protected void updateMonitorText() {
        double cpuTemperature = this.getCpuTemperature();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss"));
        monitorText.append(String.format("[ %s ] %.2f \n", time, cpuTemperature));
        LOGGER.info(String.format("%.2f", cpuTemperature));
    }

    private double getCpuTemperature() {
        return this.systemInfo.getHardware().getSensors().getCpuTemperature();
    }

}
