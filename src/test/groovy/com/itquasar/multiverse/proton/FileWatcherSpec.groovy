package com.itquasar.multiverse.proton

import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class FileWatcherSpec extends Specification {

    def "file watcher must call listenners"() {

        given:
        Path target = Paths.get(Utils.TMP_DIR)

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
