package com.github.sentry.ui;

import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class CpuLoadPane extends LoggablePane implements SentryPane {

    private static final Logger LOGGER = LoggerFactory.getLogger("cpuLoad");

    private final DecimalFormat formatter;

    public CpuLoadPane(Stage stage, SystemInfo systemInfo) {
        super(stage, systemInfo);
        this.formatter = new DecimalFormat("##.##%");
    }

        @Override
        protected void updateMonitorText() {
            String cpuLoad = this.getCpuLoad();
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss"));
            monitorText.append(String.format("[ %s ] %s\n", time, cpuLoad));
            LOGGER.info(String.format("%s", cpuLoad));
        }

        private String getCpuLoad() {
            return this.formatter.format(this.systemInfo
                .getHardware()
                .getProcessor()
                .getSystemCpuLoad(this.getPeriodInMilliseconds())
        );
    }

    private long getPeriodInMilliseconds() {
        return TimeUnit.SECONDS.toMillis(this.getPeriodInSeconds());
    }

}
