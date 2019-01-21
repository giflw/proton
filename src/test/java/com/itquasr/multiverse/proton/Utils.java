package com.itquasr.multiverse.proton;

import java.nio.file.Paths;
import java.util.UUID;

public class Utils {

    public static String TMP_DIR = System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID();

    static {
        Paths.get(TMP_DIR).toFile().mkdirs();
    }
}
