package com.larvalabs.linkunfurl;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by matt on 5/9/16.
 */
public class UnfurlTest {

    @Test
    public void testImgurUrls() throws Exception {
        // Imgur defines all Twitter card items, so these should be those
        String url = "http://i.imgur.com/5AqbEBO.gifv";
        LinkInfo info = LinkUnfurl.unfurl(url, 30000);
        assertEquals("Imgur", info.getSite());
        assertEquals("video.other", info.getType());
        assertEquals(url, info.getUrl());
        assertEquals("Wait for it...", info.getTitle());
        assertEquals("Imgur: The most awesome images on the Internet.", info.getDescription());
        assertEquals("https://i.imgur.com/5AqbEBOh.jpg", info.getImageUrl());
        assertEquals("https://i.imgur.com/5AqbEBO.mp4", info.getVideoUrl());
        assertEquals(718, info.getVideoWidth().intValue());
        assertEquals(404, info.getVideoHeight().intValue());
    }

    @Test
    public void testImgurPageUrl() throws Exception {
        LinkInfo info = LinkUnfurl.unfurl("https://imgur.com/kWyErjx", 30000);
        assertEquals("Imgur: The most awesome images on the Internet", info.getTitle());
        assertEquals("Imgur: The most awesome images on the Internet.", info.getDescription());
        assertEquals("https://i.imgur.com/kWyErjx.png", info.getImageUrl());
        assertEquals(600, info.getImageWidth().intValue());
        assertEquals(315, info.getImageHeight().intValue());
        assertEquals(209071, info.getImageSize().longValue());
    }

    @Test
    public void testFacebookData() throws Exception {
        // Harvard only defines Facebook opengraph items (I WONDER WHY)
        LinkInfo info = LinkUnfurl.unfurl("http://www.harvard.edu", 30000);
        assertEquals("Harvard University", info.getTitle());
        assertEquals("Harvard University is devoted to excellence in teaching, learning, and research, and to developing leaders in many disciplines who make a difference globally. Harvard University is made up of 11 principal academic units.",
                info.getDescription());
        assertEquals("http://www.harvard.edu/sites/default/files/default_images/harvard-social1200.jpg", info.getImageUrl());
        assertEquals(142611, info.getImageSize().longValue());
        assertNull(info.getVideoUrl());
        assertEquals(null, info.getVideoWidth());
        assertEquals(null, info.getVideoHeight());
    }

    @Test
    public void testHTMLFallback() throws Exception {
        // Slashdot doesn't do any of the more modern stuff (I WONDER WHY) so use it to test HTML fallbacks
        LinkInfo info = LinkUnfurl.unfurl("http://slashdot.org", 30000);
        assertEquals("slashdot.org", info.getSite());
        assertEquals("Slashdot: News for nerds, stuff that matters", info.getTitle());
        assertEquals("Slashdot: News for nerds, stuff that matters. Timely news source for technology related news with a heavy slant towards Linux and Open Source issues.",
                info.getDescription());
        assertNull(info.getImageUrl());
        assertNull(info.getVideoUrl());
        assertEquals(null, info.getVideoWidth());
        assertEquals(null, info.getVideoHeight());
    }

    @Test
    public void testDirectImageUrl() throws Exception {
        {
            LinkInfo info = LinkUnfurl.unfurl("http://i.imgur.com/aePzKGR.png", 30000);
            assertEquals("image", info.getType());
            assertNull(info.getTitle());
            assertNull(info.getDescription());
            assertEquals("http://i.imgur.com/aePzKGR.png", info.getImageUrl());
            assertEquals(371733, info.getImageSize().longValue());
        }
        {
            LinkInfo info = LinkUnfurl.unfurl("http://i.imgur.com/1T6dAV8.jpg", 30000);
            assertEquals("image", info.getType());
            assertNull(info.getTitle());
            assertNull(info.getDescription());
            assertEquals("http://i.imgur.com/1T6dAV8.jpg", info.getImageUrl());
            assertEquals(105743, info.getImageSize().longValue());
        }
    }

    @Test
    public void testYoutube() throws Exception {
        LinkInfo info = LinkUnfurl.unfurl("https://www.youtube.com/watch?v=rJj_XssdEG0", 30000);
        assertEquals("video", info.getType());
        assertEquals("YouTube", info.getSite());
        assertEquals("2014 Blitzball Wiffleball Top 10 Plays", info.getTitle());
        assertEquals("Like this for not top plays! Make sure to buy Blitzballs here▼ http://blitzball.com/ Check out Our BRAND NEW WEBSITE here▼ http://leaguelineup.com/oakleafwif...", info.getDescription());
        assertEquals("https://i.ytimg.com/vi/rJj_XssdEG0/hqdefault.jpg", info.getImageUrl());
        assertEquals("https://www.youtube.com/embed/rJj_XssdEG0", info.getVideoUrl());
        assertEquals(1280, info.getVideoWidth().intValue());
        assertEquals(720, info.getVideoHeight().intValue());
        assertEquals(28207, info.getImageSize().longValue());

    }

    @Test
    public void testInstagramVideo() throws Exception {
        LinkInfo info = LinkUnfurl.unfurl("https://www.instagram.com/p/BFxgF2iQ63p/", 30000);
        assertEquals("video", info.getType());
    }

    @Test
    public void testDirectVideoUrl() throws Exception {
        String url = "https://scontent-sin1-1.cdninstagram.com/t50.2886-16/13284122_132031577207112_1351661032_n.mp4";
        LinkInfo info = LinkUnfurl.unfurl(url, 30000);
        assertEquals("video", info.getType());
        assertEquals(url, info.getVideoUrl());
    }
}
