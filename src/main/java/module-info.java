module com.github.sentry {
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;
    requires com.github.oshi;
    requires atlantafx.base;
    requires javafx.controls;

    exports com.github.sentry;
}