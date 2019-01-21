package com.itquasr.multiverse.proton;

import io.vavr.control.Try;
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
            Try
                    .of(() -> file.mkdirs())
                    .onFailure((ex) -> LOGGER.error("Error creating directory {}", file, ex));
        } else {
            LOGGER.warn("Using folder {} as project root directory", this.projectDir);
        }
        for (Folder folder : Folder.values()) {
            File folderPath = folder.getPath(this.projectDir).toFile();
            LOGGER.warn("Creating folder {}", folderPath);
            Try
                    .of(() -> folderPath.mkdir())
                    .onFailure((ex) -> LOGGER.error("Error creating directory for {}: {}", folder, folderPath, ex));
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
