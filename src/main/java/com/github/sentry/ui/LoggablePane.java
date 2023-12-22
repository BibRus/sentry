package com.github.sentry.ui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import oshi.SystemInfo;
import java.util.concurrent.ScheduledFuture;

import com.github.sentry.system.SystemScheduler;
import com.github.sentry.util.Loggable;

public abstract class LoggablePane extends VBox implements SentryPane, Loggable {

    private final String monitorOnInfo;
    private final String monitorOffInfo;

    private int periodInSeconds;
    private ScheduledFuture<?> task;
    private String taskExecutePeriodInfo;

    protected TextArea monitor;
    protected StringBuilder monitorText;

    protected Button pauseMonitorAction;
    protected Button resumeMonitorAction;
    protected Button saveChangeLogTaskExecutePeriodAction;
    protected Slider changeLogTaskExecutePeriodAction;

    protected Label monitorState;
    protected Label changeLogTaskExecutePeriodState;

    protected Stage stage;
    protected SystemInfo systemInfo;

    public LoggablePane(Stage stage, SystemInfo systemInfo) {
        this.monitorOnInfo = "Идёт запись в журнал";
        this.monitorOffInfo = "Запись в журнал приостановлена";
        this.periodInSeconds = 1;
        this.taskExecutePeriodInfo = this.getFormattedTaskExecutePeriodInfo(this.periodInSeconds);

        this.initializeWithSystemDataAccess(stage, systemInfo);
    }

    private void initializeWithSystemDataAccess(Stage stage, SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
        this.initialize(stage);
        this.onEventRegister();
    }

    @Override
    public void initialize(Stage stage) {
        this.monitor = new TextArea();
        this.monitor.setEditable(false);
        this.monitorText = new StringBuilder();

        this.pauseMonitorAction = new Button("Приостановить введение журнала");
        this.resumeMonitorAction = new Button("Продолжить введение журнала");
        this.saveChangeLogTaskExecutePeriodAction = new Button("Сохранить изменения");
        this.saveChangeLogTaskExecutePeriodAction.setVisible(false);
        this.monitorState = new Label(this.monitorOnInfo);
        this.changeLogTaskExecutePeriodState = new Label(this.taskExecutePeriodInfo);
        this.stage = stage;
        this.configureChangeLogTaskExecutePeriodAction();
        this.setSpacing(20.0);
        this.setPadding(new Insets(20.0, 0.0, 20.0, 0.0));
        this.getChildren().addAll(
            monitor,
            new HBox(20.0, pauseMonitorAction, resumeMonitorAction, saveChangeLogTaskExecutePeriodAction),
            changeLogTaskExecutePeriodAction,
            new HBox(20.0, monitorState, changeLogTaskExecutePeriodState)


        );

        this.executeLogTask();
    }

    @Override
    public void onEventRegister() {
        this.pauseMonitorAction.setOnAction(event -> this.pauseLogTask());
        this.resumeMonitorAction.setOnAction(event -> this.resumeMonitor());
        this.changeLogTaskExecutePeriodAction.valueProperty().addListener((observable, oldPeriod, newPeriod) -> this.changeLogTaskExecutePeriod(newPeriod.intValue()));
        this.changeLogTaskExecutePeriodAction.focusedProperty().addListener((observable, oldValue, newValue) -> this.saveChangeLogTaskExecutePeriodAction.setVisible(newValue));
        this.saveChangeLogTaskExecutePeriodAction.setOnAction(event -> this.saveChangeLogTaskExecutePeriod());
    }

    private void saveChangeLogTaskExecutePeriod() {
        this.resumeMonitor();
        this.saveChangeLogTaskExecutePeriodAction.setVisible(false);
    }

    private void resumeMonitor() {
        if (this.task.isCancelled()) {
            this.executeLogTask();
            this.monitorState.setText(monitorOnInfo);
        }
    }

    private void pauseLogTask() {
        if (!this.task.isDone()) {
            this.task.cancel(true);
            this.monitorState.setText(monitorOffInfo);
        }
    }

    private void executeLogTask() {
        this.task = SystemScheduler.execute(this, periodInSeconds);
    }

    private void changeLogTaskExecutePeriod(int periodInSeconds) {
        this.pauseLogTask();
        this.periodInSeconds = periodInSeconds;
        this.taskExecutePeriodInfo = this.getFormattedTaskExecutePeriodInfo(this.periodInSeconds);
        this.changeLogTaskExecutePeriodState.setText(this.taskExecutePeriodInfo);
    }
    
    private void configureChangeLogTaskExecutePeriodAction() {
        this.changeLogTaskExecutePeriodAction = new Slider(1, 60 * 60 * 24, 1);
        this.changeLogTaskExecutePeriodAction.setShowTickLabels(true);
        this.changeLogTaskExecutePeriodAction.setShowTickMarks(true);
        this.changeLogTaskExecutePeriodAction.setBlockIncrement(1.0);
        this.changeLogTaskExecutePeriodAction.setMinorTickCount(60);
        this.changeLogTaskExecutePeriodAction.setMajorTickUnit(3600);
        this.changeLogTaskExecutePeriodAction.setLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Double object) {
                return String.valueOf(object.intValue() / 3600);
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });
    }

    private String getFormattedTaskExecutePeriodInfo(int periodInSeconds) {
        int hours = periodInSeconds / 3600;
        int minutes = (periodInSeconds % 3600) / 60;
        int seconds = periodInSeconds % 60;
        return String.format("Период между записями равен %02d:%02d:%02d", hours, minutes, seconds);
    }

    protected final void updateMonitor() {
        this.monitor.setText(monitorText.toString());
        this.monitor.positionCaret(monitorText.length());
    }

    protected abstract void updateMonitorText();

    @Override
    public void handle() {
        this.updateMonitorText();
        this.updateMonitor();
    }

    protected int getPeriodInSeconds() {
        return this.periodInSeconds;
    }

}
