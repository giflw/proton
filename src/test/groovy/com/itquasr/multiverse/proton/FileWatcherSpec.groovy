package com.itquasr.multiverse.proton

import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class FileWatcherSpec extends Specification {

    def 'file watcher must call listenners'() {

        Path target = Paths.get(System.getProperty("java.io.tmpdir"))

        def msg = []
        def watcher = new FileWatcher(target, { evt -> msg.add(evt) } as FileWatcherListener)
        def thread = new Thread(watcher)

        when:
        thread.start()
        target.resolve("tst_" + UUID.randomUUID()).toFile().mkdir()

        then:
        thread.join(1000)
        msg.size() > 0
    }

}
