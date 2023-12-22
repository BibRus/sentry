package com.github.sentry;

import com.github.sentry.system.SystemScheduler;
import com.github.sentry.ui.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public final class SentryLauncher {

    public static class SentryApplication extends Application {

        public static final short PREFERRED_WIDTH = 800;
        public static final short PREFERRED_HEIGHT = 450;

        public static final String TITLE = "Sentry";
        public static final Image ICON = new Image(Objects.requireNonNull(
                                                                SentryApplication.class
                                                                        .getClassLoader()
                                                                        .getResource("sentry.png"))
                                                                        .toExternalForm());

        @Override
        public void start(Stage stage) {
            this.configureTheme();
            this.configureStage(stage);
            this.loadScene(stage);
            this.showStage(stage);
            this.onCloseStageEventRegister(stage);
        }

        private void configureTheme() {
            Application.setUserAgentStylesheet(SentryConfiguration.THEME.getValue().getUserAgentStylesheetBSS());
        }

        private void configureStage(Stage stage) {
            stage.setTitle(TITLE);
            stage.getIcons().add(ICON);
        }

        private void loadScene(Stage stage) {
            stage.setScene(new Scene(new MainPane(stage), PREFERRED_WIDTH, PREFERRED_HEIGHT));
        }

        private void showStage(Stage stage) {
            stage.show();
        }

        private void onCloseStageEventRegister(Stage stage) {
            stage.setOnCloseRequest(event -> SystemScheduler.shutdown());
        }

    }

    public static void main(String[] args) {
        Application.launch(SentryApplication.class, args);
    }

}
