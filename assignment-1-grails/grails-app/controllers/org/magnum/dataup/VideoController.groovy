package org.magnum.dataup

import grails.converters.JSON
import org.magnum.dataup.model.VideoStatus
import org.springframework.web.multipart.MultipartFile

class VideoController {

    def videoService
    def videoFileManager

    def getVideoList() {
        render videoService.getVideoList() as JSON
    }

    def setVideoData() {
        def video = videoService.getVideo(params.id)
        MultipartFile data = request.getFile('data')
        if (data.empty) {
            response.sendError(404)
            return
        }

        videoFileManager.saveVideoData(video, data.inputStream)

        render new VideoStatus(state: VideoStatus.VideoState.READY) as JSON
    }

    def getData() {
        def video = videoService.getVideo(params.id)
        if (!video) {
            response.sendError(404)
        }

        videoFileManager.hasVideoData(video) ? videoFileManager.copyVideoData(video, response.outputStream) : response.sendError(404)

        render status: 200
    }
}
