package com.larvalabs.linkunfurl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by matt on 5/9/16.
 */
public class LinkUnfurl {

    public static final String METANAME_TWITTER_SITE = "twitter:domain";
    public static final String METANAME_TWITTER_TITLE = "twitter:title";
    public static final String METANAME_TWITTER_DESCRIPTION = "twitter:description";
    public static final String METANAME_TWITTER_IMAGE = "twitter:image";
    public static final String METANAME_TWITTER_IMAGE_WIDTH = "twitter:image:width";
    public static final String METANAME_TWITTER_IMAGE_HEIGHT = "twitter:image:height";
    public static final String METANAME_TWITTER_VIDEO = "twitter:player:stream";
    public static final String METANAME_TWITTER_VIDEO_WIDTH = "twitter:player:width";
    public static final String METANAME_TWITTER_VIDEO_HEIGHT = "twitter:player:height";

    public static final String METANAME_FACEBOOK_SITE = "og:site_name";
    public static final String METANAME_FACEBOOK_TYPE = "og:type";
    public static final String METANAME_FACEBOOK_TITLE = "og:title";
    public static final String METANAME_FACEBOOK_DESCRIPTION = "og:description";
    public static final String METANAME_FACEBOOK_IMAGE = "og:image";
    public static final String METANAME_FACEBOOK_IMAGE_WIDTH = "og:image:width";
    public static final String METANAME_FACEBOOK_IMAGE_HEIGHT = "og:image:height";
    public static final String METANAME_FACEBOOK_VIDEO = "og:video";
    public static final String METANAME_FACEBOOK_VIDEO_URL = "og:video:url";
    public static final String METANAME_FACEBOOK_VIDEO_WIDTH = "og:video:width";
    public static final String METANAME_FACEBOOK_VIDEO_HEIGHT = "og:video:height";

    public static final String TAG_HTMLMETA_TITLE = "title";
    public static final String METANAME_HTML_DESCRIPTION = "description";

    public static final String ATTR_CONTENT = "content";
    public static final String USER_AGENT = "JavaUnfurlBot/0.1";

    /**
     * Attempt to "unfurl" a URL by retrieving details about the URL. Based on how Slack handles URLs which
     * is detailed here: https://medium.com/slack-developer-blog/everything-you-ever-wanted-to-know-about-unfurling-but-were-afraid-to-ask-or-how-to-make-your-e64b4bb9254#.6ptw5x2zx
     * Priority for information retrieved is currently as follows: Twitter Card > Facebook Opengraph > HTML meta tags.
     * If the content type indicates the URL is an image then we do not attempt to parse and instead just return the
     * url as the hero image. Other types of failure to parse will result in IOExceptions.
     * @param url
     * @param timeoutMillis
     * @return
     * @throws IOException
     */
    public static LinkInfo unfurl(String url, int timeoutMillis) throws IOException {
        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent(USER_AGENT)
                .timeout(timeoutMillis)
                .followRedirects(true)
                .execute();

        String contentType = response.contentType();
        if (contentType != null && contentType.toLowerCase().contains("image")) {
            // Don't attempt to parse, this is an image file - set as hero image
            LinkInfo info = new LinkInfo();
            info.setType("image");
            info.setImageUrl(url);
            setImageLength(info);
            return info;
        }

        Document document = response.parse();

        LinkInfo info = new LinkInfo();
        info.setUrl(url);

        {
            String twitterTitle = getMetaElementIfExists(document, METANAME_TWITTER_TITLE);
            String facebookTitle = getMetaElementIfExists(document, METANAME_FACEBOOK_TITLE);
            if (twitterTitle != null) {
                info.setTitle(twitterTitle);
            } else if (facebookTitle != null) {
                info.setTitle(facebookTitle);
            } else {
                // Try to fall back to html title
                if (document.select(TAG_HTMLMETA_TITLE).size() > 0) {
                    String htmlTitle = document.select("title").first().text();
                    info.setTitle(htmlTitle);
                }
            }
        }

        info.setType(getContentFromMetaTag(document, METANAME_FACEBOOK_TYPE));
        if (info.getType() == null) {
            info.setType("link");
        }

        info.setSite(getContentFromMetaTag(document, METANAME_FACEBOOK_SITE, METANAME_TWITTER_SITE));
        if (info.getSite() == null) {
            // fallback to parsing URL to get site name
            info.setSite(new URL(url).getHost());
        }

        info.setDescription(getContentFromMetaTag(document, METANAME_TWITTER_DESCRIPTION, METANAME_FACEBOOK_DESCRIPTION, METANAME_HTML_DESCRIPTION));
        info.setImageUrl(getContentFromMetaTag(document, METANAME_TWITTER_IMAGE, METANAME_FACEBOOK_IMAGE));
        info.setImageWidth(getContentFromMetaTag(document, METANAME_TWITTER_IMAGE_WIDTH, METANAME_FACEBOOK_IMAGE_WIDTH));
        info.setImageHeight(getContentFromMetaTag(document, METANAME_TWITTER_IMAGE_HEIGHT, METANAME_FACEBOOK_IMAGE_HEIGHT));
        info.setVideoUrl(getContentFromMetaTag(document, METANAME_TWITTER_VIDEO, METANAME_FACEBOOK_VIDEO, METANAME_FACEBOOK_VIDEO_URL));
        info.setVideoWidth(getContentFromMetaTag(document, METANAME_TWITTER_VIDEO_WIDTH, METANAME_FACEBOOK_VIDEO_WIDTH));
        info.setVideoHeight(getContentFromMetaTag(document, METANAME_TWITTER_VIDEO_HEIGHT, METANAME_FACEBOOK_VIDEO_HEIGHT));

        if (info.getImageUrl() != null) {
            setImageLength(info);
        }

        return info;
    }

    private static void setImageLength(LinkInfo info) {
        try {
            URL imageUrl = new URL(info.getImageUrl());
            HttpURLConnection urlConnection = (HttpURLConnection) imageUrl.openConnection();
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            urlConnection.setRequestMethod("HEAD");
            long length = urlConnection.getContentLengthLong();
            info.setImageSize(length);
        } catch (IOException e) {
            System.out.println("Unable to retrieve image length due to exception: "+e.getMessage());
        }
    }

    private static String getContentFromMetaTag(Document document, String... tags) {
        for (String tag : tags) {
            String content = getMetaElementIfExists(document, tag);
            if (content != null) {
                return content;
            }
        }
        return null;
    }

    /**
     * Retrieve the content attribute for a meta tag if the tag exists, and the content isn't empty.
     * @param document
     * @param metaAttr
     * @return
     */
    private static String getMetaElementIfExists(Document document, String metaAttr) {
        {
            // check for meta tags of type name="*"
            Elements elements = document.select(makeMetaNameSelector(metaAttr));
            if (elements.size() > 0) {
                String content = elements.first().attr(ATTR_CONTENT);
                if (content != null && content.length() > 0) {
                    return content;
                }
            }
        }
        {
            // check for meta tags of type property="*"
            Elements elements = document.select(makeMetaPropertySelector(metaAttr));
            if (elements.size() > 0) {
                String content = elements.first().attr(ATTR_CONTENT);
                if (content != null && content.length() > 0) {
                    return content;
                }
            }
        }
        return null;
    }

    private static String makeMetaNameSelector(String name) {
        return "meta[name='" + name + "']";
    }

    private static String makeMetaPropertySelector(String name) {
        return "meta[property='" + name + "']";
    }

}
