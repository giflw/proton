package com.itquasr.multiverse.proton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class LogListenner implements FileWatcherListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogListenner.class);

    @Override
    public void accept(WatchEvent<Path> pathWatchEvent) {
        LOGGER.info("Watch event on {}: {}", pathWatchEvent.context(), pathWatchEvent.kind());
    }
}
