package com.github.sentry.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import oshi.SystemInfo;

public class MainPane extends TabPane {

    public MainPane(Stage stage) {
        final SystemInfo systemInfo = new SystemInfo();
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.setPadding(new Insets(0.0, 20.0, 0.0, 20.0));
        this.getTabs().addAll(
            new Tab("Температура процессора", new CpuTemperaturePane(stage, systemInfo)),
            new Tab("Нагрузка на процессор", new CpuLoadPane(stage, systemInfo)),
            new Tab("Напряжение процессора", new CpuVoltagePane(stage, systemInfo)),
            new Tab("Параметры", new ConfigurationPane(stage))
        );
    }

}
