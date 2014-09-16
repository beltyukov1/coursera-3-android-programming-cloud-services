package org.magnum.mobilecloud.video.controllers;

import com.google.common.collect.Lists;
import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @RequestMapping(value= VideoSvcApi.VIDEO_SVC_PATH, method= RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideoList() {
        return Lists.newArrayList(videoRepository.findAll());
    }
}
