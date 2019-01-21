package com.itquasr.multiverse.proton;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
class Application {

    private final AtomicReference<Status> status = new AtomicReference<>();

    @Getter
    private final ApplicationArgs args;

    private Project project;

    public Application(String... args) {
        this.args = new ApplicationArgs(args);
        this.status.set(Status.STOPPED);
    }

    public static void main(String... args) {
        new Application(args).start();
    }

    public void init() {
        if (this.status.compareAndSet(Status.STOPPED, Status.INITIALIZED)) {
            this.project = new Project(args.getProjectDirectory().toPath());
        }
    }

    public final void start() {
        if (this.status.compareAndSet(Status.INITIALIZED, Status.STARTED)) {
            System.out.println("START");
        }
    }

    public final Status getStatus() {
        return status.get();
    }

    public final void stop() {
        if (this.status.compareAndSet(Status.STARTED, Status.STOPPED)) {
            System.out.println("STOP");
        }
    }

    public enum Status {
        INITIALIZED, STARTED, STOPPED
    }

}
