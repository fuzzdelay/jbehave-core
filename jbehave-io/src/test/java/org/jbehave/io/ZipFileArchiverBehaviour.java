package org.jbehave.io;

import static java.util.Arrays.asList;
import static org.apache.commons.io.FilenameUtils.separatorsToUnix;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class ZipFileArchiverBehaviour {

    private File dir;
    private FileArchiver archiver = new ZipFileArchiver();

    @Before
    public void setup() throws IOException {
        dir = new File("target/dir");
        dir.mkdirs();
    }

    @Test
    public void canArchiveZip() throws IOException {
        File zip = createFile("archive.zip");
        archiver.archive(zip, dir);
    }

    private File createFile(String path) throws IOException {
        File parent = new File("target");
        parent.mkdirs();
        File file = new File(parent, path);
        file.createNewFile();
        return file;
    }

    @Test
    public void canResolveFileRelativeToDirectoryUsingUnixSeparators() {
        assertRelativeFileUsesUnixSeparators("target/dir", "archive/file.txt");
        assertRelativeFileUsesUnixSeparators("/tmp/dir", "archive/file.txt");
        assertRelativeFileUsesUnixSeparators("\\\\UNC\\share\\dir", "archive\\file.txt");
        assertRelativeFileUsesUnixSeparators("C:\\Documents and Settings\\user\\dir", "archive\\file.txt");
        assertRelativeFileUsesUnixSeparators(System.getProperty("java.io.tmpdir"), "archive\\file.txt");
        assertRelativeFileUsesUnixSeparators(System.getProperty("java.io.tmpdir"), "archive/file.txt");
    }

    private void assertRelativeFileUsesUnixSeparators(String directoryPath, String relativePath) {
        String unixPath = separatorsToUnix(relativePath);
        File directory = new File(directoryPath);
        File file = new File(directoryPath + "/" + relativePath);
        assertEquals(new File(unixPath), archiver.relativeTo(file, directory));
    }

    @Test
    public void canUnarchiveZip() throws IOException {
        File zip = resourceFile("archive.zip");
        assertTrue(archiver.isArchive(zip));
        clearDir(dir);
        archiver.unarchive(zip, dir);
        assertFilesUnarchived(asList("archive", "archive/file1.txt", "archive/subdir1", "archive/subdir1/subfile1.txt"));
    }

    @Test
    public void canListFileContentOfUnarchiveZip() throws IOException {
        File zip = resourceFile("archive.zip");
        assertTrue(archiver.isArchive(zip));
        archiver.unarchive(zip, dir);
        List<File> content = archiver.listContent(new File(dir, "archive"));
        assertThatPathsMatch(content, Arrays.asList("archive", "archive/file1.txt", "archive/subdir1",
                "archive/subdir1/subfile1.txt"));
    }

    private void assertThatPathsMatch(List<File> content, List<String> expectedPaths) {
        List<String> paths = content.stream()
                .map(file -> file.getPath())
                .collect(Collectors.toList());
        Collections.sort(paths);
        List<String> expected = expectedPaths.stream()
                .map(path -> new File(dir, path).getPath())
                .collect(Collectors.toList());
        Collections.sort(expected);
        assertThat(paths, equalTo(expected));
    }

    private void assertFilesUnarchived(List<String> paths) {
        ResourceFinder finder = new ResourceFinder("");
        for (String path : paths) {
            File file = new File(dir, path);
            assertTrue(file.exists());
            if (!file.isDirectory()) {
                assertTrue(finder.resourceAsString(file.getPath()).length() > 0);
            }
        }
    }

    private void clearDir(File dir) {
        dir.delete();
        dir.mkdir();
    }

    private File resourceFile(String path) {
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }

}