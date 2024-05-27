package com.sharanmurli.usc_csci572_hw2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class URLTracker {
    private static final Set<String> allDiscoveredUrls = ConcurrentHashMap.newKeySet();
    private static final Set<String> withinSiteUrls = ConcurrentHashMap.newKeySet();
    private static final String urlsFileName = "urls_nytimes.csv";

    public static synchronized void addUrl(String url) {
        allDiscoveredUrls.add(url);
        if (url.startsWith("https://www.nytimes.com")) { 
            withinSiteUrls.add(url);
        }
    }

    public static void write() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(urlsFileName))) {
            writer.write("URL,Indicator\n");
            for (String url : allDiscoveredUrls) {
                String indicator = withinSiteUrls.contains(url) ? "OK" : "N_OK";
                writer.write(String.format("%s,%s\n", url, indicator));
            }
        }
    }

    public static int getTotalDiscoveredUrls() {
        return allDiscoveredUrls.size();
    }

    public static int getTotalWithinSiteUrls() {
        return withinSiteUrls.size();
    }

    public static int getTotalOutsideSiteUrls() {
        return allDiscoveredUrls.size() - withinSiteUrls.size();
    }
}
