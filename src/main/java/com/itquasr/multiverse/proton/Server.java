package com.itquasr.multiverse.proton;

import io.javalin.Javalin;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PROTECTED)
public abstract class Server {


    private final Javalin javalin = Javalin.create();
    private final Project project;

    public Server(Project project) {
        this.project = project;
        this.config(this.javalin, this.project);
    }

    protected abstract void config(Javalin javalin, Project  project);


    public Server start() {
        javalin.start();
        return this;
    }

    public Server stop() {
        javalin.stop();
        return this;
    }
}
