package com.itquasr.multiverse.proton;

import java.nio.file.Path;
import java.util.UUID;

public class Utils {

    public static String TMP_DIR = System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID();

    static {
        Path.of(TMP_DIR).toFile().mkdirs();
    }
}
