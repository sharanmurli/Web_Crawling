Name: Sharan Murli
USC ID: 5232838315

I had been assigned nytimes as the website domain to perform crawling as per my USC ID's last two digits. I have created the crawler in java. I have created 5 classes namely Controller, mYCrawlerHW2, Summary, Fetching, Visited and URLTracker in order to perform web crawling using crawler4j library. Controller class is created to initialize and start the crawler. Crawler class has the logic for the web data parsing. Fetching, Visited and URLTracker are used to store the necessary statistical data. Summary incorporates all these statistics and stores it in a txt file.

In my code I have properly defined the logic for the crawler to crawl all types of content files but when I run it for 20000 pages, I'm getting only text/html content types. Just wanted to bring this to your notice.