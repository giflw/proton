package com.itquasr.multiverse.proton;

import io.javalin.Javalin;
import io.javalin.staticfiles.Location;

public class EditorServer extends Server {

    public static void main(String[] args) {
        new EditorServer().start();
    }

    @Override
    protected void config(Javalin javalin) {
        javalin.enableWebJars();
        javalin.enableStaticFiles("proton-editor", Location.CLASSPATH);
    }
}
