package com.itquasr.multiverse.proton;

import lombok.Getter;
import picocli.CommandLine;

import java.io.File;

@Getter
public class ApplicationArgs {

    private static final String null__ = "__null__";

    @CommandLine.Parameters(
            arity = "1..1", converter = FileConverter.class, defaultValue = null__,
            description = "Project directory to use. Uses actual directory if none provided."
    )
    private File projectDirectory;

    public ApplicationArgs(String[] args) {
        CommandLine.populateCommand(this, args);
    }

    public static class FileConverter implements CommandLine.ITypeConverter<File> {
        @Override
        public File convert(String value) throws Exception {
            return value == null || null__.equals(value) ? new File(System.getProperty("user.dir")) : new File(value);
        }
    }
}
