package com.itquasr.multiverse.proton;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;

@Slf4j
@AllArgsConstructor
public class Project {

    @Getter
    private final Path projectDir;

    public Project init() {
        File file = this.projectDir.toFile();
        if (!file.exists()) {
            LOGGER.warn("Creating folder {} as project root directory", this.projectDir);
            file.mkdirs();
        } else {
            LOGGER.warn("Using folder {} as project root directory", this.projectDir);
        }
        for (Folder folder : Folder.values()) {
            LOGGER.warn("Creating folder {}", folder);
            folder.getPath(this.projectDir).toFile().mkdir();
        }
        return this;
    }

    public enum Folder {
        API, ASSETS, PREVIEW, SRC, TEMPLATES;

        public String getFolderName() {
            return name().toLowerCase();
        }

        public Path getPath(Path projectDir) {
            return projectDir.resolve(this.getFolderName());
        }
    }
}
