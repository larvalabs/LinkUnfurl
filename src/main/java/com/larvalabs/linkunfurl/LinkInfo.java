package com.larvalabs.linkunfurl;

/**
 * Created by matt on 5/9/16.
 */
public class LinkInfo {

    private String url;
    private boolean redirected = false;
    private String canonicalUrl;
    private String type;
    private String site;
    private String title;
    private String description;
    private String imageUrl;
    private Integer imageWidth;
    private Integer imageHeight;
    private Long imageSize;
    private String videoUrl;
    private String videoType;
    private Integer videoWidth;
    private Integer videoHeight;

    /**
     * This is the URL that the content was ultimately loaded from. If isRedirected() is true then some number
     * of redirects were necessary to get here.
     * @return
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRedirected() {
        return redirected;
    }

    public void setRedirected(boolean redirected) {
        this.redirected = redirected;
    }

    /**
     * This is the canonical URL reported by the site, usually by way of a <link rel='canonical'/> tag.
     * @return
     */
    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public Integer getImageWidth() {
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

    public Integer getImageHeight() {
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

    /**
     * The size of the image in bytes given by imageUrl
     * @return
     */
    public Long getImageSize() {
        return imageSize;
    }

    public void setImageSize(Long imageSize) {
        this.imageSize = imageSize;
    }

    public void setSize(String size) {
        if (size != null) {
            this.imageSize = Long.parseLong(size);
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

    public Integer getVideoWidth() {
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

    public Integer getVideoHeight() {
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
