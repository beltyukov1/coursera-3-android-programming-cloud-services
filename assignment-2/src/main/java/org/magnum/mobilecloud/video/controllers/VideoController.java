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
    public @ResponseBody Video getVideoById(@PathVariable("id") long id, HttpResponse response) {
        Video video = videoRepository.findOne(id);

        if (video == null) {
            response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
        }

        return video;
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}/like", method=RequestMethod.POST)
    public void likeVideo(@PathVariable("id") long id, HttpResponse response, Principal principal) {
        Video video = videoRepository.findOne(id);

        if (video == null) {
            response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        boolean isLikeSuccessful = video.likeVideo(principal.getName());
        if (isLikeSuccessful) {
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}/unlike", method=RequestMethod.POST)
    public void unlikeVideo(@PathVariable("id") long id, HttpResponse response, Principal principal) {
        Video video = videoRepository.findOne(id);

        if (video == null) {
            response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        boolean isUnlikeSuccessful = video.unlikeVideo(principal.getName());
        if (isUnlikeSuccessful) {
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}/likedby", method=RequestMethod.GET)
    public @ResponseBody List<String> getUsersWhoLikedVideo(@PathVariable("id") long id, HttpResponse response) {
        Video video = videoRepository.findOne(id);

        if (video == null) {
            response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return video.getUsersWhoLikedVideo();
    }
}
