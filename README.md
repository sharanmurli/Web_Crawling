# Web Crawling

This project involves the development of a web crawler using existing APIs to visit and fetch the contents of a list of URLs. The crawler initializes with an empty queue, which is then recursively processed by removing items and possibly adding new ones until the queue is empty. The primary goal is to save a set of web pages, including linked pages, to disk and compile statistics from the crawling results.

## Table of Contents

- [Summary](#summary)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Crawler Logic](#crawler-logic)


## Summary

This project involves launching a crawler, compiling statistics from the crawling results, and submitting those statistics. The crawler uses `crawler4j` in Java to perform its tasks, but the principles can be applied using various other programming languages and libraries.

## Features

- **Queue Initialization:** Starts with a single URL in the queue.
- **Content Fetching:** Removes the URL from the queue and fetches its contents.
- **Link Extraction:** Examines the contents and extracts links.
- **Queue Management:** Adds the extracted links to the queue.
- **Recursion:** Recursively processes the queue until it is empty.
- **Statistics Compilation:** Collects and compiles statistics from the crawling results.
- **Result Accumulation:** Systematically accumulates and writes out the results.

## Technologies Used

- **Java:** Primary programming language for implementing the crawler.
- **crawler4j:** Java library for web crawling.
- **Repl.it:** Online IDE for code development and testing.
- **GitHub:** Version control and code management.

## Crawler Logic

1. **Start with a Single URL:** Initialize the queue with a single URL.
2. **Remove Queue Item:** Remove the URL from the queue and fetch its contents.
3. **Extract Links:** Examine the fetched contents and extract links.
4. **Add Links to Queue:** Add the extracted links to the queue.
5. **Recursion:** Recursively process the queue until it is empty.
6. **Statistics Collection:** Collect and compile statistics during the crawling process.
7. **Result Accumulation:** Systematically accumulate and write out the results.

