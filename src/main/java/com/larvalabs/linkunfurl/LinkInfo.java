package com.larvalabs.linkunfurl;

/**
 * Created by matt on 5/9/16.
 */
public class LinkInfo {

    private String url;
    private String title;
    private String description;
    private String imageUrl;
    private int imageWidth = -1;
    private int imageHeight = -1;
    private String videoUrl;
    private String videoType;
    private int videoWidth = -1;
    private int videoHeight = -1;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImageWidth(String width) {
        if (width != null) {
            this.imageWidth = Integer.parseInt(width);
        }
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setImageHeight(String height) {
        if (height != null) {
            this.imageHeight = Integer.parseInt(height);
        }
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public void setVideoWidth(String videoWidth) {
        if (videoWidth != null) {
            this.videoWidth = Integer.parseInt(videoWidth);
        }
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public void setVideoHeight(String videoHeight) {
        if (videoHeight != null) {
            this.videoHeight = Integer.parseInt(videoHeight);
        }
    }
}
