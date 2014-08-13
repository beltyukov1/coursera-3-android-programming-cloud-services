package org.magnum.dataup

import org.magnum.dataup.model.Video
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import java.util.concurrent.atomic.AtomicLong

class VideoService {

    def videoDao
    private final AtomicLong ATOMIC_LONG = new AtomicLong(0L)

    void setVideoId(Video video) {
        video.id = ATOMIC_LONG.incrementAndGet()
    }

    void setVideoUrl(Video video) {
        video.dataUrl = "${getUrlBaseForLocalServer()}/video/${video.id}/data"
    }

    void addVideo(Video video) {
        videoDao.addVideo(video)
    }

    List getVideoList() {
        videoDao.getVideoList()
    }

    Video getVideo(long id) {
        videoDao.getVideoList().find { it.id == id }
    }

    private static String getUrlBaseForLocalServer() {
        def request = (RequestContextHolder.requestAttributes as ServletRequestAttributes).request
        def port = request.serverPort != 80 ? ":${request.serverPort}" : ''
        "http://${request.serverName}$port"
    }
}
