package com.robindrew.trading.util.io;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * File handling utilities.
 */
public class Files {

    /**
     * Returns a byte source that reads from the given file.
     */
    public static ByteSource asByteSource(File file) {
        return new FileByteSource(file);
    }

    /**
     * Returns a byte sink that writes to the given file.
     */
    public static ByteSink asByteSink(File file) {
        return new FileByteSink(file);
    }

    /**
     * Returns the immediate contents (files and directories) of the given directory.
     */
    public static List<File> listContents(File directory) {
        return listContents(directory, null);
    }

    /**
     * Returns the immediate contents of the given directory matching the given filter.
     */
    public static List<File> listContents(File directory, FilenameFilter filter) {
        checkDirectory(directory);
        File[] contents = filter == null ? directory.listFiles() : directory.listFiles(filter);
        if (contents == null) {
            throw new IllegalStateException("Unable to list directory: '" + directory + "'");
        }
        Arrays.sort(contents);
        return ImmutableList.copyOf(contents);
    }

    /**
     * Returns the contents of the given directory, descending into sub-directories when recursive.
     */
    public static List<File> listFiles(File directory, boolean recursive) {
        if (!recursive) {
            return listContents(directory);
        }
        List<File> files = new ArrayList<>();
        listFiles(directory, files);
        return ImmutableList.copyOf(files);
    }

    private static void listFiles(File directory, List<File> files) {
        for (File file : listContents(directory)) {
            files.add(file);
            if (file.isDirectory()) {
                listFiles(file, files);
            }
        }
    }

    private static void checkDirectory(File directory) {
        if (directory == null) {
            throw new NullPointerException("directory");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: '" + directory + "'");
        }
    }

    private Files() {}
}
