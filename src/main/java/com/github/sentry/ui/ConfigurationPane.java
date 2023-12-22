package com.github.sentry.ui;

import com.github.sentry.SentryConfiguration;
import com.github.sentry.theme.ThemeProvider;

import atlantafx.base.theme.Theme;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ConfigurationPane extends VBox implements SentryPane {

    private ComboBox<Theme> themes;

    public ConfigurationPane(Stage stage) {
        this.initialize(stage);
        this.onEventRegister();
    }

    @Override
    public void initialize(Stage stage) {
        this.configureThemes();
        this.setSpacing(20.0);
        this.setPadding(new Insets(20.0, 0.0, 20.0, 0.0));
        this.getChildren().addAll(
            new VBox(10.0, new Label("Доступные темы"), themes)
        );
    }

    @Override
    public void onEventRegister() {
        this.themes.setOnAction(event -> this.changeTheme());
    }

    private void configureThemes() {
        this.themes = new ComboBox<>(getThemeNames());
        this.themes.setCellFactory(this.getThemesCellFactory());
        this.themes.setButtonCell(this.getThemesCellFactory().call(null));
        this.themes.setValue(SentryConfiguration.THEME.getValue());
    }

    private void changeTheme() {
        Application.setUserAgentStylesheet(this.themes.getValue().getUserAgentStylesheetBSS());
        SentryConfiguration.THEME.setValue(this.themes.getValue());
    }

    private Callback<ListView<Theme>, ListCell<Theme>> getThemesCellFactory() {
        return callback -> new ListCell<>() {
            @Override
            protected void updateItem(Theme theme, boolean empty) {
                super.updateItem(theme, empty);
                this.setText(empty ? "" : theme.getName());
            }
        };
    }

    private ObservableList<Theme> getThemeNames() {
        return FXCollections.observableList(ThemeProvider.getThemes().values().stream().toList());
    }

}
