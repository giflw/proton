package com.itquasr.multiverse.proton;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.function.Consumer;

public interface FileWatcherListener extends Consumer<WatchEvent<Path>> {

    @Override
    default void accept(WatchEvent<Path> pathWatchEvent) {
        System.out.println("!!! " + pathWatchEvent.context());
    }
}
