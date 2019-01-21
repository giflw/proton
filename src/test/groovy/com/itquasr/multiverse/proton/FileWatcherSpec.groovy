package com.itquasr.multiverse.proton

import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.WatchEvent

class FileWatcherSpec extends Specification {

    class ToArrayFileWatcherListener implements FileWatcherListener {

        def msg = []

        @Override
        void accept(WatchEvent<Path> pathWatchEvent) {
            msg.add(pathWatchEvent)
        }

    }

    def 'file watcher must call listenners'() {

        Path target = Paths.get(System.getProperty("java.io.tmpdir"))

        def listener = new ToArrayFileWatcherListener()
        def watcher = new FileWatcher(target, listener)

        def thread = new Thread(watcher)

        when:
        thread.start()
        target.resolve("tst_" + UUID.randomUUID()).toFile().mkdir()

        then:
        thread.join(1000)
        listener.msg.size() > 0
    }

}
