package org.magnum.dataup

import org.magnum.dataup.model.Video

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class VideoFileManager {

    Path targetDir_ = Paths.get('videos')

    /**
     * This static factory method creates and returns a
     * VideoFileManager object to the caller. Feel free to customize
     * this method to take parameters, etc. if you want.
     *
     * @return
     * @throws IOException
     */
    static VideoFileManager get() throws IOException {
        new VideoFileManager()
    }

    // The VideoFileManager.get() method should be used
    // to obtain an instance
    private VideoFileManager() throws IOException {
        if(!Files.exists(targetDir_)){
            Files.createDirectories(targetDir_)
        }
    }

    // Private helper method for resolving video file paths
    private Path getVideoPath(Video v) {
        assert v != null

        targetDir_.resolve("video ${v.id}.mpg")
    }

    /**
     * This method returns true if the specified Video has binary
     * data stored on the file system.
     *
     * @param v
     * @return
     */
    boolean hasVideoData(Video v) {
        Files.exists getVideoPath(v)
    }

    /**
     * This method copies the binary data for the given video to
     * the provided output stream. The caller is responsible for
     * ensuring that the specified Video has binary data associated
     * with it. If not, this method will throw a FileNotFoundException.
     *
     * @param v
     * @param out
     * @throws IOException
     */
    void copyVideoData(Video v, OutputStream out) throws IOException {
        def source = getVideoPath v
        if(!Files.exists(source)){
            throw new FileNotFoundException("Unable to find the referenced video file for videoId: ${v.id}")
        }

        Files.copy(source, out)
    }

    /**
     * This method reads all of the data in the provided InputStream and stores
     * it on the file system. The data is associated with the Video object that
     * is provided by the caller.
     *
     * @param v
     * @param videoData
     * @throws IOException
     */
    void saveVideoData(Video v, InputStream videoData) throws IOException {
        assert videoData != null

        Files.copy(videoData, getVideoPath(v), StandardCopyOption.REPLACE_EXISTING)
    }
}
