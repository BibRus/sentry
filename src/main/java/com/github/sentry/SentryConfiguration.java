package com.github.sentry;

import atlantafx.base.theme.Theme;
import ch.qos.logback.core.util.FileSize;
import com.github.sentry.theme.ThemeProvider;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SentryConfiguration  {

    private static final Properties CONFIGURATION = new Properties();
    private static final Logger LOGGER = LoggerFactory.getLogger("sentry");

    private static final File SENTRY_CONFIGURATION_FILE = new File(System.getProperty("sentry.configuration"));

    public static final Property<Theme> THEME = new SimpleObjectProperty<>();

    private static void loadConfiguration() {
        try (var stream = new FileInputStream(SENTRY_CONFIGURATION_FILE)) {
            CONFIGURATION.load(stream);
        } catch (IOException exception) {
            LOGGER.error("SentryConfiguration is not found file", exception);
            throw new ExceptionInInitializerError(exception);
        }
    }

    private static void writeConfiguration() {
        try (var stream = new FileOutputStream(SENTRY_CONFIGURATION_FILE)) {
            CONFIGURATION.store(stream, null);
        } catch (IOException exception) {
            LOGGER.error("SentryConfiguration is not found file", exception);
            throw new ExceptionInInitializerError(exception);
        }
    }

    private static void initializeParameters() {
        THEME.setValue(ThemeProvider.getThemes().get(CONFIGURATION.getProperty("sentry.configuration.theme")));
        THEME.addListener((observable, currentTheme, newTheme) -> {
            CONFIGURATION.setProperty("sentry.configuration.theme", newTheme.getName());
            writeConfiguration();
        });
    }

    static {
        loadConfiguration();
        initializeParameters();
    }

}
