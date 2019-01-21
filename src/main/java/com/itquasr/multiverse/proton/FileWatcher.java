package com.itquasr.multiverse.proton;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

// FIXME start/stop methods
public class FileWatcher implements Runnable {

    private final List<FileWatcherListener> listeners = new LinkedList<>();

    private final Path target;

    public FileWatcher(Path target) {
        this(target, new LogListenner());
    }

    public FileWatcher(Path target, FileWatcherListener... listeners) {
        this(target, Arrays.asList(listeners));
    }


    public FileWatcher(Path target, List<FileWatcherListener> listeners) {
        this.target = target;
        if (listeners != null) {
            this.listeners.addAll(listeners);
        }
    }

    public List<FileWatcherListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    @Override
    public void run() {
        WatchService watchService;
        try {
            watchService = target.getFileSystem().newWatchService();
            registerRecursive(target, watchService);
            while (true) {
                WatchKey watchKey = watchService.take(); // This call is blocking until events are present

                for (WatchEvent watchEvent : watchKey.pollEvents()) {
                    WatchEvent<Path> event = (WatchEvent<Path>) watchEvent;
                    for (FileWatcherListener listener : this.listeners) {
                        try {
                            listener.accept(event);
                        } catch (Exception ex) {
                            // just to be sure that a listener doesn't break another
                            ex.printStackTrace();
                        }
                    }
                }

                if (!watchKey.reset()) {
                    // FIXME logger
                    System.out.println("Path deleted");
                    watchKey.cancel();
                    watchService.close();
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void registerRecursive(Path root, WatchService watchService) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (dir.toFile().canWrite()) {
                    dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                if (exc instanceof AccessDeniedException) {
                    return FileVisitResult.CONTINUE;
                }
                return FileVisitResult.SKIP_SUBTREE;
            }
        });
    }

}
