package com.robindrew.trading.util.io;

import com.google.common.io.ByteSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A {@link ByteSource} that reads from a file.
 */
public class FileByteSource extends ByteSource {

    private final File file;

    public FileByteSource(File file) {
        if (file == null) {
            throw new NullPointerException("file");
        }
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public InputStream openStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public com.google.common.base.Optional<Long> sizeIfKnown() {
        if (file.isFile()) {
            return com.google.common.base.Optional.of(file.length());
        }
        return com.google.common.base.Optional.absent();
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
