# nutch-fetch-page
Fetch html pages and save them locally. This plugin integrates with apache nutch to fetch and save the html pages and its parsed text content.

## Plugin overview

This plugin extends nutch [HtmlParseFilter](https://wiki.apache.org/nutch/AboutPlugins) interface to extract html pages and its content during nutch's parsing phase. By default the plugin extracts html pages and its text content and save it in your local drive and also provides breeding ground to extract html pages and/or its parsed text content __conditionally__ i.e on the basis of its URL or page content.

 If you are crawling the site using apache nutch 1.x, nutch fetches the html content and save them in its hadoop cluster. Though you can read the segments and retrive the html dumps but that will be one complete html dump file. This plugin saves individual html files on your local drive.
 
## Configurations

If you are unfimiliar about Nutch plugins, you should read this [tutorial](https://wiki.apache.org/nutch/WritingPluginExample) to get yourself fimiliarize with how plugin works. Also I assume that you are fimiliar with how to build Nutch source code. If not then check out nutch [tutorial](https://wiki.apache.org/nutch/NutchTutorial).

Below are the steps to configure nutch-fetch-page plugin.

### Download and build nutch source

Download nutch [source](http://nutch.apache.org/downloads.html) and build the project. Don't use binary because you will need to build the plugin as well.

### Place plugin in plugins folder

checkout or download the source of nutch-fetch-page. Place the folder _fetch-page_ in `NUTCH_DIR/src/plugins` directory where other plugins are present.

### Add plugin details in build.xml

in the `NUTCH_DIR/src/plugin` edit _build.xml_ file and add our plugin details.

__in build.xml:__

- Under the <target name="deploy"> tag, add <ant dir="fetch-page" target="deploy"/>
- Under the <target name="clean"> tag, add <ant dir="fetch-page" target="clean"/>
 
### Add plugin information in nutch-site.xml

Add the following information in _nutch-site.xml_ which is located in `NUTCH_DIR/conf/nutch-site.xml`

Add crawler name under _http.agent.name_, include plugin _fetch-page_ to be used by nutch and define path that will be used by our plugin to save the documents in this location. 

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

### Build Nutch source

In `NUTCH_DIR/` run `ant`

__note:__ NUTCH_DIR/runtime/local is the folder that is created after we build our plugin and contains all the compiled source and binaries.

Plugin will save html files on the location provided in nutch-site.xml's property _page.export.path_ during parse phase.
