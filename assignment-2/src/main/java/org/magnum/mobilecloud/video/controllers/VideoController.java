package org.magnum.mobilecloud.video.controllers;

import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @RequestMapping(value= VideoSvcApi.VIDEO_SVC_PATH, method= RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideoList() {
        return Lists.newArrayList(videoRepository.findAll());
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody Video addVideo(@RequestBody Video v) {
        videoRepository.save(v);
        return v;
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}", method=RequestMethod.GET)
    public @ResponseBody Video getVideoById(@PathVariable long id, HttpServletResponse response) {
        Video video = videoRepository.findOne(id);

        if (video == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return video;
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}/like", method=RequestMethod.POST)
    public void likeVideo(@PathVariable long id, HttpServletResponse response, Principal principal) {
        Video video = videoRepository.findOne(id);

        if (video == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        boolean isLikeSuccessful = video.likeVideo(principal.getName());
        if (isLikeSuccessful) {
            videoRepository.save(video);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}/unlike", method=RequestMethod.POST)
    public void unlikeVideo(@PathVariable long id, HttpServletResponse response, Principal principal) {
        Video video = videoRepository.findOne(id);

        if (video == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        boolean isUnlikeSuccessful = video.unlikeVideo(principal.getName());
        if (isUnlikeSuccessful) {
            videoRepository.save(video);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}/likedby", method=RequestMethod.GET)
    public @ResponseBody List<String> getUsersWhoLikedVideo(@PathVariable long id, HttpServletResponse response) {
        Video video = videoRepository.findOne(id);

        if (video == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return video.getUsersWhoLikedVideo();
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_TITLE_SEARCH_PATH, method=RequestMethod.GET)
    public @ResponseBody Collection<Video> findByTitle(@RequestParam(VideoSvcApi.TITLE_PARAMETER) String title) {
        return videoRepository.findByName(title);
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_DURATION_SEARCH_PATH, method=RequestMethod.GET)
    public @ResponseBody Collection<Video> findByDurationLessThan(@RequestParam(VideoSvcApi.DURATION_PARAMETER) long duration) {
        return videoRepository.findByDurationLessThan(duration);
    }
}
