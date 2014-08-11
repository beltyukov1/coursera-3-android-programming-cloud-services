package org.magnum.dataup.dao;

import org.magnum.dataup.model.Video;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class VideoDao {

    private Collection<Video> videos = new CopyOnWriteArrayList<Video>();

    public Collection<Video> getVideoList() {
        return videos;
    }

    public void addVideo(Video video) {
        videos.add(video);
    }

}
