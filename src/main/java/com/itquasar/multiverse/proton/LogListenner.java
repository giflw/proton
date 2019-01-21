package com.itquasar.multiverse.proton;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

@Slf4j
public class LogListenner implements FileWatcherListener {

    @Override
    public void accept(WatchEvent<Path> pathWatchEvent) {
        LOGGER.info("Watch event on {}: {}", pathWatchEvent.context(), pathWatchEvent.kind());
    }
}
