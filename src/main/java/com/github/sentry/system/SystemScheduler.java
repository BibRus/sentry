package com.github.sentry.system;

import com.github.sentry.util.Loggable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class SystemScheduler {

    private static final int DEFAULT_DELAY = 0;
    private static final int MAX_NUMBER_OF_TASKS = 10;

    public static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(MAX_NUMBER_OF_TASKS);

    public static ScheduledFuture<?> execute(Loggable task, int periodInSeconds) {
        return EXECUTOR_SERVICE.scheduleAtFixedRate(task::handle, DEFAULT_DELAY, periodInSeconds, TimeUnit.SECONDS);
    }

    public static void shutdown() {
       EXECUTOR_SERVICE.shutdownNow();
    }

    private SystemScheduler() {

    }

}
