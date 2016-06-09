# nutch-fetch-page
Fetch html pages and save them locally. This plugin integrates with apache nutch to fetch and save the html pages and its parsed text content.

## Plugin overview

This plugin extends nutch [HtmlParseFilter](https://wiki.apache.org/nutch/WritingPlugins) interface to extract html pages and its content during nutch's parsing phase. By default the plugin extracts html pages and its text content and save it in your local drive and also provides breeding ground to extract html pages and/or its parsed text content __conditionally__ i.e on the basis of its URL or page content.

 If you are crawling the site using apache nutch 1.x, nutch fetches the html content and save them in its hadoop cluster. Though you can read the segments and retrive the html dumps but that will be one complete html dump file. This plugin saves individual html files on your local drive.
 
 ## Configurations
 
 ### Add plugin information in nutch-site.xml

```xml
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>

<!-- Put site-specific property overrides in this file. -->

<configuration>
         <property>
                <name>http.agent.name</name>
                <value>my crawler</value>
        </property>
        <property>
                <name>plugin.includes</name>
                <value>protocol-http|urlfilter-regex|parse-(html|tika)|fetch-page|index-(basic|anchor)|indexer-solr|scoring-opic|urlnormalizer-(pass|regex|basic)</value>
                <description>
					fetch-page plugin added to fetch pages and save them individually in hard drive.
                </description>
        </property>
        <property>
                <name>page.export.path</name>
                <value>/home/shoaib/content_dump/</value>
				<description>
					Path that will be used by fetch-page plugin to save the html content on local drive.
					Change this directory as per your requirements or username.
				</description>
        </property>

</configuration>
```
