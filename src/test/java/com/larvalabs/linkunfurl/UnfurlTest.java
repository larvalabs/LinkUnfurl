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
        assertEquals(url, info.getUrl());
        assertEquals("Wait for it...", info.getTitle());
        assertEquals("Imgur: The most awesome images on the Internet.", info.getDescription());
        assertEquals("https://i.imgur.com/5AqbEBOh.jpg", info.getImageUrl());
        assertEquals("https://i.imgur.com/5AqbEBO.mp4", info.getVideoUrl());
        assertEquals(718, info.getVideoWidth());
        assertEquals(404, info.getVideoHeight());
    }

    @Test
    public void testImgurPageUrl() throws Exception {
        LinkInfo info = LinkUnfurl.unfurl("https://imgur.com/kWyErjx", 30000);
        assertEquals("Imgur: The most awesome images on the Internet", info.getTitle());
        assertEquals("Imgur: The most awesome images on the Internet.", info.getDescription());
        assertEquals("https://i.imgur.com/kWyErjx.png", info.getImageUrl());
        assertEquals(600, info.getImageWidth());
        assertEquals(315, info.getImageHeight());
    }

    @Test
    public void testFacebookData() throws Exception {
        // Harvard only defines Facebook opengraph items (I WONDER WHY)
        LinkInfo info = LinkUnfurl.unfurl("http://www.harvard.edu", 30000);
        assertEquals("Harvard University", info.getTitle());
        assertEquals("Harvard University is devoted to excellence in teaching, learning, and research, and to developing leaders in many disciplines who make a difference globally. Harvard University is made up of 11 principal academic units.",
                info.getDescription());
        assertEquals("http://www.harvard.edu/sites/default/files/default_images/harvard-social1200.jpg", info.getImageUrl());
        assertNull(info.getVideoUrl());
        assertEquals(-1, info.getVideoWidth());
        assertEquals(-1, info.getVideoHeight());
    }

    @Test
    public void testHTMLFallback() throws Exception {
        // Slashdot doesn't do any of the more modern stuff (I WONDER WHY) so use it to test HTML fallbacks
        LinkInfo info = LinkUnfurl.unfurl("http://slashdot.org", 30000);
        assertEquals("Slashdot: News for nerds, stuff that matters", info.getTitle());
        assertEquals("Slashdot: News for nerds, stuff that matters. Timely news source for technology related news with a heavy slant towards Linux and Open Source issues.",
                info.getDescription());
        assertNull(info.getImageUrl());
        assertNull(info.getVideoUrl());
        assertEquals(-1, info.getVideoWidth());
        assertEquals(-1, info.getVideoHeight());
    }

    @Test
    public void testDirectImageUrl() throws Exception {
        {
            LinkInfo info = LinkUnfurl.unfurl("http://i.imgur.com/aePzKGR.png", 30000);
            assertNull(info.getTitle());
            assertNull(info.getDescription());
            assertEquals("http://i.imgur.com/aePzKGR.png", info.getImageUrl());
        }
        {
            LinkInfo info = LinkUnfurl.unfurl("http://i.imgur.com/KlV4Atb.jpg", 30000);
            assertNull(info.getTitle());
            assertNull(info.getDescription());
            assertEquals("http://i.imgur.com/KlV4Atb.jpg", info.getImageUrl());
        }
    }
}
