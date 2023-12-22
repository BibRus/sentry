package com.github.sentry.ui;

import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SystemInfoPane implements SentryPane {

    private Label operatingSystem;
    private Label processor;
    private Label displays;
    private Label hardware;


    @Override
    public void initialize(Stage stage) {

    }

    @Override
    public void onEventRegister() {

    }

}
