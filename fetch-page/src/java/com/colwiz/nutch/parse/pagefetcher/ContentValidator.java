package com.colwiz.nutch.parse.pagefetcher;

/**
 * @author Shoaib
 *         <h1>Abstract class for content validation</h1>
 *         Custom implement this class to employ validations
 *         on the html pages and its text content and url before saving
 *         the pages on the local storage.
 *         <p/>
 *         example: If you want to save all urls containing the word "search"
 *         Then implement the validateUrl method.
 *         If you want to save pages based on text content in the page
 *         then implement validateText method.
 */
public abstract class ContentValidator {

    /**
     * Implement this method for content validation and saving the content
     * locally based on its validity.
     *
     * @param url         url of the html page
     * @param rawContent  raw html page
     * @param textContent parsed content from html page
     */
    public abstract void extract(String url, String rawContent, String textContent);

    /**
     * Implement this method to make validity decisions based on html content.
     * <p/>
     * Example:
     * Parse html page using libraries html parsing libraries like jsoup etc
     * and then return true if the heading tag contains the keyword "nutch"
     *
     * @param rawContent raw html content of the page
     * @return true if the page is valid and we want to save it, false otherwise
     */
    protected abstract Boolean validateHtml(String rawContent);

    /**
     * Implement this method to make validity decision based on parsed
     * text content of the html page.
     *
     * @param textContent text content parsed from html page.
     * @return true if the page is valid and we want to save it, false otherwise
     */
    protected abstract Boolean validateText(String textContent);

    /**
     * Implement this method to save a page based on the urls
     * <p/>
     * Example:
     * If we want to save all those pages with url containing
     * the keyword "plugin" we can do something like
     * <code>return url.contains("plugin"); </code>
     *
     * @param url url of the page
     * @return true if the page is valid based on its url
     * and we want to save it, false otherwise
     */
    protected Boolean validateUrl(String url) {
        return true;
    }
}
