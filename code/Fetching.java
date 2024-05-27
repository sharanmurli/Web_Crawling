package com.sharanmurli.usc_csci572_hw2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Fetching {
    static final List<String[]> fetchAttempts = new ArrayList<>();
    private static final Map<Integer, Integer> statusCodeCounts = new ConcurrentHashMap<>();
    private static final String fetchFileName = "fetch_nytimes.csv";

    public static synchronized void add(String url, int statusCode) {
        fetchAttempts.add(new String[]{url, String.valueOf(statusCode)});
        statusCodeCounts.merge(statusCode, 1, Integer::sum);
    }

    public static void write() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fetchFileName))) {
            writer.write("URL,Status\n");
            for (String[] attempt : fetchAttempts) {
                writer.write(String.join(",", attempt) + "\n");
            }
        }
    }

    public static Map<Integer, Integer> getStatusCodeCounts() {
        return statusCodeCounts;
    }
    public static int getTotalFetchAttempts() {
        return fetchAttempts.size();
    }
}
