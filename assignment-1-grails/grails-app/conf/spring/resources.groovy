import org.magnum.dataup.VideoFileManager
import org.magnum.dataup.dao.VideoDao

// Place your Spring DSL code here
beans = {
    videoDao(VideoDao)
    videoFileManager(VideoFileManager)
}
