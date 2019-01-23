package com.itquasar.multiverse.proton.conf;

import io.vavr.control.Try;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

@Slf4j
@Data
public class Config {


    public static final String CONFIG_FILENAME = "proton.yaml";
    private static final Config DEFAULT_CONFIG = new Config();
    private Editor editor = new Editor();
    private Preview preview = new Preview();

    public static Config load(Path projectDir, String filename) {
        return load(
                StringUtils.join(
                        Try.of(
                                () -> Files.readAllLines(projectDir.resolve(filename), StandardCharsets.UTF_8)
                        ).onFailure(
                                ex -> LOGGER.error("Config file '{}' not found on project directory '{}'. Loading defaults.", filename, projectDir, ex)
                        ).getOrElse(
                                Collections.emptyList()
                        ),
                        "\n"
                )
        );
    }

    public static Config load(Path projectDir) {
        return load(projectDir, CONFIG_FILENAME);
    }

    @NonNull
    public static Path configFilePath(@NonNull Path projectDir) {
        return projectDir.resolve(CONFIG_FILENAME);
    }

    public static Config load(String yaml) {
        Config loadedConfig = new Yaml().loadAs(yaml, Config.class);
        return loadedConfig == null ? DEFAULT_CONFIG : loadedConfig;
    }

    public static void createIfNotExists(Path projectDir) {
        File configFile = configFilePath(projectDir).toFile();
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            save(DEFAULT_CONFIG, configFile.toPath());
        }
    }

    public static void save(Config config, Path path) {
        Try
                .of(() -> write(config, path))
                .onFailure((ex) -> LOGGER.error("Error saving config to {}", path, ex));
    }

    private static boolean write(Config config, Path path) throws IOException {
        File parentFile = path.toFile().getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        Writer writer = Files.newBufferedWriter(path);
        writer.write(
                new Yaml().dumpAs(config, Tag.MAP, DumperOptions.FlowStyle.BLOCK)
        );
        writer.close();
        return true;
    }

}
