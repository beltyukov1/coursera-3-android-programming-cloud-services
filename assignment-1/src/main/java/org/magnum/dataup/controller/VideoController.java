package org.magnum.dataup.controller;

import org.magnum.dataup.VideoFileManager;
import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import retrofit.client.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class VideoController {

    @Autowired
    VideoService videoService;

    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideoList() {
        return videoService.getVideoList();
    }

    @RequestMapping(value = "/video", method = RequestMethod.POST)
    public @ResponseBody Video addVideo(@RequestBody Video video) {
        videoService.setVideoId(video);
        videoService.setVideoUrl(video);
        videoService.addVideo(video);
        return video;
    }

    @RequestMapping(value = "/video/{id}/data", method = RequestMethod.POST)
    public @ResponseBody VideoStatus setVideoData(@PathVariable("id") long videoId, @RequestParam("data") MultipartFile videoData, HttpServletResponse response) {
        Video video = videoService.getVideo(videoId);
        if (video != null) {
            try {
                VideoFileManager.get().saveVideoData(video, videoData.getInputStream());
            } catch (IOException e) {
                // Error saving file
                response.setStatus(500);
                return null;
            }
        } else {
            response.setStatus(404);
            return null;
        }

        return new VideoStatus(VideoStatus.VideoState.READY);
    }

    @RequestMapping(value = "/video/{id}/data", method = RequestMethod.GET)
    public void getData(@PathVariable("id") long id, HttpServletResponse response) {
        Video video = videoService.getVideo(id);
        if (video != null) {
            response.setContentType("video/mpeg");
            try {
                if (VideoFileManager.get().hasVideoData(video)) {
                    VideoFileManager.get().copyVideoData(video, response.getOutputStream());
                } else {
                    response.setStatus(404);
                }
            } catch (IOException e) {
                // could not retrieve data
            }
        } else {
            response.setStatus(404);
        }

    }
}
