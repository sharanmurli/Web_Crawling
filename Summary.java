package com.sharanmurli.usc_csci572_hw2;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Summary {
    private static final String reportFileName = "CrawlReport_nytimes.txt";
    private static Properties properties = new Properties();

    static {
     
        try (InputStream input = Summary.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error reading config.properties", ex);
        }
    }

    private static final Map<Integer, String> statusCodeDescriptions = new HashMap<>();
    static {
        statusCodeDescriptions.put(200, "OK");
        statusCodeDescriptions.put(301, "Moved Permanently");
        statusCodeDescriptions.put(302, "Found");
        statusCodeDescriptions.put(304, "Not Modified");
        statusCodeDescriptions.put(400, "Bad Request");
        statusCodeDescriptions.put(401, "Unauthorized");
        statusCodeDescriptions.put(403, "Forbidden");
        statusCodeDescriptions.put(404, "Not Found");
        statusCodeDescriptions.put(500, "Internal Server Error");
   
    }

    public static void write() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName))) {
            writeHeader(writer);

            
            writer.write("Fetch Statistics:\n");
            writer.write("====================\n\n");
            writer.write(String.format("# fetches attempted: %d\n", Fetching.getTotalFetchAttempts()));
            Map<Integer, Integer> statusCodeCounts = Fetching.getStatusCodeCounts();
            int successFetches = statusCodeCounts.getOrDefault(200, 0);
            writer.write(String.format("# fetches succeeded: %d\n", successFetches));
            writer.write(String.format("# fetches failed or aborted: %d\n\n", Fetching.getTotalFetchAttempts() - successFetches));

          
            writeUrlStatistics(writer);
            
        
            writer.write("Status Codes:\n");
            writer.write("====================\n\n");
            List<Integer> sortedCodes = new ArrayList<>(statusCodeCounts.keySet());
            Collections.sort(sortedCodes, (code1, code2) -> {
                int group1 = code1 / 100;
                int group2 = code2 / 100;
                return Integer.compare(group1, group2);
            });
            for (Integer code : sortedCodes) {
                String description = statusCodeDescriptions.getOrDefault(code, "Unknown");
                writer.write(String.format("%d %s: %d\n", code, description, statusCodeCounts.get(code)));
            }
            writer.write("\n");

    
            writer.write("File Sizes:\n");
            writer.write("====================\n\n");
            Map<String, Integer> fileSizeDistribution = Visited.getFileSizeDistribution();
            fileSizeDistribution.forEach((category, count) -> {
                try {
                    writer.write(String.format("%s: %d\n", category, count));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.write("\n");

    
            writer.write("Content Types:\n");
            writer.write("====================\n\n");
            Map<String, Integer> contentTypesCounts = Visited.getContentTypesCounts();
            contentTypesCounts.forEach((type, count) -> {
                try {
                    writer.write(String.format("%s: %d\n", type, count));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.write("\n");
        }
    }
    
    private static void writeHeader(BufferedWriter writer) throws IOException {
        writeStatistic(writer, "Name ", properties.getProperty("name"));
        writeStatistic(writer, "USC ID ", properties.getProperty("uscId"));
        writeStatistic(writer, "News site crawled ", properties.getProperty("websiteDomain") + ".com");
        writeStatistic(writer, "Number of threads ", properties.getProperty("numberOfCrawlers"));
        writer.write("\n");
    }

    private static void writeUrlStatistics(BufferedWriter writer) throws IOException {
        writer.write("Outgoing URL's:\n");
        writer.write("====================\n\n");
        writer.write(String.format("Total URLs extracted: %d\n", URLTracker.getTotalDiscoveredUrls()));
        writer.write(String.format("# unique URLs extracted: %d\n", URLTracker.getTotalDiscoveredUrls())); 
        writer.write(String.format("# unique URLs within News Site: %d\n", URLTracker.getTotalWithinSiteUrls()));
        writer.write(String.format("# unique URLs outside News Site: %d\n\n", URLTracker.getTotalOutsideSiteUrls()));
    }

    private static void writeStatistic(BufferedWriter writer, String statistic, String value) throws IOException {
        writer.write(String.format("%s: %s\n", statistic, value));
    }
}
