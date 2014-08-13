package org.magnum.dataup.model

class Video {

    long id
    String title
    long duration
    String location
    String subject
    String contentType
    String dataUrl

    @Override
    int hashCode() {
        Objects.hash(title, duration)
    }

    @Override
    boolean equals(Object obj) {
        obj instanceof Video &&
                Objects.equals(title, (obj as Video).title) &&
                duration == (obj as Video).duration
    }
}
