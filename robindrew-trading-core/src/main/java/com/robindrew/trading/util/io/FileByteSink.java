package com.robindrew.trading.util.io;

import com.google.common.io.ByteSink;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A {@link ByteSink} that writes to a file, creating any missing parent directories.
 */
public class FileByteSink extends ByteSink {

    private final File file;
    private final boolean append;

    public FileByteSink(File file) {
        this(file, false);
    }

    public FileByteSink(File file, boolean append) {
        if (file == null) {
            throw new NullPointerException("file");
        }
        this.file = file;
        this.append = append;
    }

    public File getFile() {
        return file;
    }

    @Override
    public OutputStream openStream() throws IOException {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs() && !parent.exists()) {
            throw new IOException("Unable to create directory: '" + parent + "'");
        }
        return new FileOutputStream(file, append);
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
