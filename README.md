## Link Unfurling for Java

This library produces information that can be used to "unfurl" links in a way similar to Slack. This is described in detail here: https://medium.com/slack-developer-blog/everything-you-ever-wanted-to-know-about-unfurling-but-were-afraid-to-ask-or-how-to-make-your-e64b4bb9254#.6ptw5x2zx

Currently this library retrieves the basic information for a link like title, description, main image and a video if present. It does not retrieve optional labels and values as described in the article.

For example:

```
LinkInfo info = LinkUnfurl.unfurl("http://www.harvard.edu", 10000);
info.getTitle();
info.getDescription();
info.getImageUrl();
```

# Installing via dependency

Add https://jitpack.io to your repositories:

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Then add linkunfurl as a compile dependency:

```
dependencies {
    compile 'com.github.larvalabs:linkunfurl:v0.11'
}
```

# Tag documentation

* Twitter Cards: https://dev.twitter.com/cards/markup - Photo specific: https://dev.twitter.com/cards/types/photo
* Facebook OpenGraph: http://ogp.me/

# Build instructions

Just run ```gradle jar```