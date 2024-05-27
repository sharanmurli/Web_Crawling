package com.sharanmurli.usc_csci572_hw2;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {
   
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

 
    private static final String CRAWL_STORAGE_FOLDER = "/data/crawl/root";
    private static final int MAX_DEPTH_OF_CRAWLING = 16;
    private static final int MAX_PAGES_TO_FETCH = 20000;
    private static final String SEED_URL = "https://www.nytimes.com";
    private static final int NUMBER_OF_CRAWLERS = 7;

    public static void main(String[] args) throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_STORAGE_FOLDER);
        config.setMaxDepthOfCrawling(MAX_DEPTH_OF_CRAWLING);
        config.setMaxPagesToFetch(MAX_PAGES_TO_FETCH);
        config.setPolitenessDelay(200); 

      
        config.setSocketTimeout(10000); 
        config.setThreadShutdownDelaySeconds(5); 
        config.setCleanupDelaySeconds(5); 

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(SEED_URL);

      
        controller.start(MyCrawlerHW2.class, NUMBER_OF_CRAWLERS);

       
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down crawler...");
            controller.shutdown();
            controller.waitUntilFinish();
   
            try {
                Fetching.write();
                Visited.write();
                URLTracker.write();
                Summary.write();
            } catch (IOException e) {
                logger.error("Error writing data to files: ", e);
            }
        }));
    }
}
