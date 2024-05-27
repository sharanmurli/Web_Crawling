package com.sharanmurli.usc_csci572_hw2;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.regex.Pattern;

public class MyCrawlerHW2 extends WebCrawler {
    private static final Logger logger = LoggerFactory.getLogger(MyCrawlerHW2.class);

    private static final Pattern DOC_PATTERNS = Pattern.compile(".*(\\.(html?|php|pdf|docx?))$");
    private static final Pattern IMAGE_PATTERNS = Pattern.compile(".*(\\.(jpe?g|ico|png|bmp|svg|gif|webp|tiff))$");
    private static final Pattern OTHER_PATTERNS = Pattern.compile(".*(\\.(css|js|mp3|zip|gz|vcf|xml|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v))$");
    
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();

        boolean isWithinTargetSite = href.startsWith("https://www.nytimes.com/");
        boolean hasAllowedExtension = DOC_PATTERNS.matcher(href).matches() || IMAGE_PATTERNS.matcher(href).matches() || OTHER_PATTERNS.matcher(href).matches();

        URLTracker.addUrl(href);

        return isWithinTargetSite && hasAllowedExtension;
    }

    @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        Fetching.add(webUrl.getURL(), statusCode);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        String contentType = getContentType(page);
        
        logger.info("Visited: " + url + " with content-type: " + contentType);

        if (hasRequiredContentType(contentType)) {
            int size = page.getContentData().length;
            Set<WebURL> links = page.getParseData() instanceof HtmlParseData ?
                    ((HtmlParseData) page.getParseData()).getOutgoingUrls() : null;
            int numberOfOutlinks = links != null ? links.size() : 0;

            Visited.add(url, size, numberOfOutlinks, contentType);
        }
    }

    private String getContentType(Page page) {
        String contentType = page.getContentType();
        if (contentType != null && contentType.contains(";")) {
            return contentType.split(";")[0];
        }
        return contentType;
    }

    private boolean hasRequiredContentType(String contentType) {
        contentType = contentType.toLowerCase();
        return contentType.startsWith("image")
                || contentType.equals("application/pdf")
                || contentType.equals("application/msword")
                || contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                || contentType.equals("text/html");
    }
}
