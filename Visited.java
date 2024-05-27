package com.sharanmurli.usc_csci572_hw2;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Visited {
    private static final List<String[]> visitedUrls = new ArrayList<>();
    private static final Map<String, Integer> contentTypesCounts = new ConcurrentHashMap<>();
    private static final Map<String, Integer> fileSizeDistribution = new ConcurrentHashMap<>();
    private static final String visitFileName = "visit_nytimes.csv";

    
    static {
        initializeFileSizeDistribution();
    }

    public static synchronized void add(String url, int size, int outLinks, String contentType) {
        visitedUrls.add(new String[]{url, String.valueOf(size), String.valueOf(outLinks), contentType});
        
       
        contentTypesCounts.merge(contentType, 1, Integer::sum);
        
      
        fileSizeDistribution.merge(getFileSizeCategory(size), 1, Integer::sum);
    }

    public static void write() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(visitFileName))) {
            writer.write("URL,Size(Bytes),# of Outlinks,Content-Type\n");
            for (String[] visit : visitedUrls) {
                writer.write(String.join(",", visit) + "\n");
            }
        }
    }

    private static String getFileSizeCategory(int size) {
        if (size < 1024) return "< 1KB";
        else if (size < 10240) return "1KB ~ <10KB";
        else if (size < 102400) return "10KB ~ <100KB";
        else if (size < 1048576) return "100KB ~ <1MB";
        else return ">= 1MB";
    }

 
    private static void initializeFileSizeDistribution() {
        fileSizeDistribution.put("< 1KB", 0);
        fileSizeDistribution.put("1KB ~ <10KB", 0);
        fileSizeDistribution.put("10KB ~ <100KB", 0);
        fileSizeDistribution.put("100KB ~ <1MB", 0);
        fileSizeDistribution.put(">= 1MB", 0);
    }

    public static Map<String, Integer> getContentTypesCounts() {
        return contentTypesCounts;
    }

    public static Map<String, Integer> getFileSizeDistribution() {
        return fileSizeDistribution;
    }

    public static int getTotalVisitedURLs() {
        return visitedUrls.size();
    }
}

