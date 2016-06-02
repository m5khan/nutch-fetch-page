package com.colwiz.nutch.parse.pagefetcher;

import org.joda.time.DateTime;

/**
 * @author Shoaib
 *         Utility class contains Helper methods
 */
public final class FetchUtil {

    /**
     * <h1>Create file name from url.</h1>
     * Prepends Unix Epoch milliseconds
     * to maintain uniqueness in file name.
     *
     * @param url a url of the page
     * @return string that will be used as file name
     */
    public static String getFileNameFromUrl(String url) {
        String fileName = Long.toString(DateTime.now().getMillis());
        if (url.contains("?")) {
            String[] urlArr = url.split("\\?")[0].split("/");
            fileName = fileName.concat("-").concat(urlArr[urlArr.length - 1]);
        } else {
            String[] urlArr = url.split("/");
            fileName = fileName.concat("-").concat(urlArr[urlArr.length - 1]);
        }
        return fileName;
    }
}
