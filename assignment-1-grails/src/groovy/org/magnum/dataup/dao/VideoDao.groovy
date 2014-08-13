package org.magnum.dataup.dao

import org.magnum.dataup.model.Video

import java.util.concurrent.CopyOnWriteArrayList

class VideoDao {

    private List videos = new CopyOnWriteArrayList<>()

    List getVideoList() {
        videos
    }

    void addVideo(Video video) {
        videos << video
    }
}
