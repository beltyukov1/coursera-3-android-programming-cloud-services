package org.magnum.dataup.model

class VideoStatus {

    enum VideoState {
        READY, PROCESSING
    }

    VideoState state
}
