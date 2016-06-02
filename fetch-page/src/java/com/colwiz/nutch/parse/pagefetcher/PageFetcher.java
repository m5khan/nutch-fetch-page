package com.colwiz.nutch.parse.pagefetcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.parse.*;
import org.apache.nutch.protocol.Content;
import org.w3c.dom.DocumentFragment;

import java.io.UnsupportedEncodingException;

/**
 * @author shoaib
 *         Main interface implementation of HtmlParseFilter.
 *         <p/>
 *         This Plugin extends nutch's HtmlParseFilter interface
 *         to save HTML pages and content locally in a separate files
 *         during <code>bin/nutch parse</code> task.
 *         <p/>
 *         Define property `page.export.path` in nutch-site.xml
 *         to provide the location to the directory where to save pages.
 */
public class PageFetcher implements HtmlParseFilter {
    public static final Log LOG = LogFactory.getLog(PageFetcher.class);
    private static final String CONF_PROPERTY = "page.export.path";
    public static String FolderPath;
    private Configuration conf;

    public ParseResult filter(Content content, ParseResult parseResult, HTMLMetaTags htmlMetaTags, DocumentFragment documentFragment) {
        try {
            String url = content.getUrl();
            Parse parse = parseResult.get(url);
            String contentType = parse.getData().getMeta(Metadata.CONTENT_TYPE);
            if (contentType.contains("text/html")) {
                String textContent = parse.getText();
                String rawContent = new String(content.getContent(), "UTF-8");
                ContentExporter.getInstance().extract(url, rawContent, textContent);
            }

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            LOG.error(ex.getMessage());
            LOG.error(ex.getStackTrace());
        }
        return parseResult;
    }

    @Override
    public void setConf(Configuration configuration) {
        this.conf = configuration;

        if (conf == null)
            return;

        // Get folder path property from nutch-site.xml
        FolderPath = conf.get(CONF_PROPERTY);
    }

    @Override
    public Configuration getConf() {
        return conf;
    }
}
