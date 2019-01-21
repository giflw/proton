package com.itquasr.multiverse.proton;

import io.javalin.Javalin;
import io.javalin.staticfiles.Location;

public class EditorServer extends Server {

    public EditorServer(Project project) {
        super(project);
    }

    @Override
    protected void config(Javalin javalin, Project project) {
        javalin.enableWebJars();
        javalin.enableStaticFiles("proton-editor", Location.CLASSPATH);
    }
}
