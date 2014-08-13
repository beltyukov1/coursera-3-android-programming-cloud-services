package org.magnum.dataup

import org.magnum.dataup.model.Video
import org.magnum.dataup.model.VideoStatus
import retrofit.client.Response
import retrofit.http.Body
import retrofit.http.GET
import retrofit.http.Multipart
import retrofit.http.POST
import retrofit.http.Part
import retrofit.http.Path
import retrofit.http.Streaming
import retrofit.mime.TypedFile

interface VideoSvcApi {

    static final String DATA_PARAMETER = 'data'
    static final String ID_PARAMETER = 'id'
    static final String VIDEO_SVC_PATH = '/video'
    static final String VIDEO_DATA_PATH = "$VIDEO_SVC_PATH/{id}/data"

    @GET('/video')
    Collection<Video> getVideoList()

    @POST('/video')
    Video addVideo(@Body Video v)

    @Multipart
    @POST('/video/{id}/data')
    VideoStatus setVideoData(@Path('id') long id, @Part('data') TypedFile videoData)

    @Streaming
    @GET('/video/{id}/data')
    Response getData(@Path('id') long id)
}