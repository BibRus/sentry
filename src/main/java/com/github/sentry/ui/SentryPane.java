package com.github.sentry.ui;

import javafx.stage.Stage;

public interface SentryPane {

    void initialize(Stage stage);

    void onEventRegister();

}
