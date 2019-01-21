package com.itquasr.multiverse.proton;

import io.javalin.Javalin;

public abstract class Server {

    private final Javalin javalin = Javalin.create();

    public Server() {
        this.config(this.javalin);
    }

    protected abstract void config(Javalin javalin);


    public Server start() {
        javalin.start();
        return this;
    }

    public Server stop() {
        javalin.stop();
        return this;
    }
}
