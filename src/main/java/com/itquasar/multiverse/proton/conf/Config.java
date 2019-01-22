package com.itquasar.multiverse.proton.conf;

import io.vavr.control.Try;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

@Slf4j
@Data
public class Config {

    public static final String CONFIG_FILENAME = "proton.yaml";
    private Editor editor = new Editor();
    private Preview preview = new Preview();

    public static Config load(Path projectDir) {
        return load(
                StringUtils.join(
                        Try.of(
                                () -> Files.readAllLines(projectDir.resolve(CONFIG_FILENAME), StandardCharsets.UTF_8)
                        ).onFailure(
                                ex -> LOGGER.error("Config file '{}' not found on project directory '{}'. Loading defaults.", CONFIG_FILENAME, projectDir, ex)
                        ).getOrElse(
                                Collections.emptyList()
                        ),
                        "\n"
                )
        );
    }

    public static Config load(String yaml) {
        Config loadedConfig = new Yaml().loadAs(yaml, Config.class);
        return loadedConfig == null ? new Config() : loadedConfig;
    }

}
