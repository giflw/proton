package com.itquasr.multiverse.proton;

import java.util.concurrent.atomic.AtomicBoolean;

class Application {

    private final AtomicBoolean running = new AtomicBoolean(false);

    public Application() {
        System.out.println("INIT");
    }

    public static void main(String[] args) {
        new Application().start();
    }

    public final void start() {
        if (this.running.compareAndSet(false, true)) {
            System.out.println("START");
        }
    }

    public final boolean isRunning() {
        return running.get();
    }

    public final void stop() {
        if (this.running.compareAndSet(true, false)) {
            System.out.println("STOP");
        }
    }
}
