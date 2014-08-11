package org.magnum.dataup.service;

import org.magnum.dataup.dao.VideoDao;
import org.magnum.dataup.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VideoService {

    @Autowired
    private VideoDao videoDao;
    private final AtomicLong ATOMIC_LONG = new AtomicLong(0L);

    public void setVideoId(Video video) {
        video.setId(ATOMIC_LONG.incrementAndGet());
    }

    public void setVideoUrl(Video video) {
        video.setDataUrl(getUrlBaseForLocalServer() + "/video" + video.getId() + "/data");
    }

    public void addVideo(Video video) {
        videoDao.addVideo(video);
    }

    public Collection<Video> getVideoList() {
        return videoDao.getVideoList();
    }

    public Video getVideo(long id) {
        Video foundVideo = null;
        for (Video video : videoDao.getVideoList()) {
            if (video.getId() == id) {
                foundVideo = video;
                break;
            }
        }

        return foundVideo;
    }

    private String getUrlBaseForLocalServer() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return "http://" + request.getServerName() + ((request.getServerPort() != 80) ? ":" + request.getServerPort() : "");
    }
}
