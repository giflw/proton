package com.itquasar.multiverse.proton;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.function.Consumer;

public interface FileWatcherListener extends Consumer<WatchEvent<Path>> {

}
