# Algolia Search API Client for Scala

[Algolia Search](https://www.algolia.com) is a hosted full-text, numerical, and faceted search engine capable of delivering realtime results from the first keystroke.
The **Algolia Search API Client for Scala** lets you easily use the [Algolia Search REST API](https://www.algolia.com/doc/rest-api/search) from your Scala code.

[![Build Status](https://travis-ci.org/algolia/algoliasearch-client-scala.png?branch=master)](https://travis-ci.org/algolia/algoliasearch-client-scala) [![Coverage Status](https://coveralls.io/repos/algolia/algoliasearch-client-scala/badge.svg?branch=master&service=github)](https://coveralls.io/github/algolia/algoliasearch-client-scala?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.algolia/algoliasearch-scala_2.11/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.algolia/algoliasearch-scala_2.11/)


**WARNING:**
The JVM has an infinite cache on successful DNS resolution. As our hostnames points to multiple IPs, the load could be not evenly spread among our machines, and you might also target a dead machine.

You should change this TTL by setting the property `networkaddress.cache.ttl`. For example to set the cache to 60 seconds:
```java
java.security.Security.setProperty("networkaddress.cache.ttl", "60");
```

For debug purposes you can enable debug logging on the API client. It's using [slf4j](https://www.slf4j.org) so it should be compatible with most java loggers.
The logger is named `algoliasearch`.




## API Documentation

You can find the full reference on [Algolia's website](https://www.algolia.com/doc/api-client/scala/).


## Table of Contents



1. **[Supported platforms](#supported-platforms)**


1. **[Install](#install)**


1. **[Philosophy](#philosophy)**

    * [DSL](#dsl)
    * [Future](#future)
    * [JSON as case class](#json-as-case-class)

1. **[Quick Start](#quick-start)**

    * [Initialize the client](#initialize-the-client)

1. **[Push data](#push-data)**


1. **[Configure](#configure)**


1. **[Search](#search)**


1. **[Search UI](#search-ui)**

    * [index.html](#indexhtml)

1. **[List of available methods](#list-of-available-methods)**




# Getting Started




## Supported platforms

This API client only supports Scala 2.11 & 2.12.

## Install

If you're using `Maven`, add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>com.algolia</groupId>
    <artifactId>algoliasearch-scala_2.11</artifactId>
    <version>[1,)</version>
</dependency>
```

For snapshots, add the `sonatype` repository:
```xml
<repositories>
    <repository>
        <id>oss-sonatype</id>
        <name>oss-sonatype</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

If you're using `sbt`, add the following dependency to your `build.sbt` file:

```scala
libraryDependencies += "com.algolia" %% "algoliasearch-scala" % "[1,)"
```

For snapshots, add the `sonatype` repository:
```scala
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
```

## Philosophy

### DSL

The main goal of this client is to provide a human _accessible_ and _readable_ DSL for using Algolia search.

The entry point of the DSL is the [`algolia.AlgoliaDSL` object](https://github.com/algolia/algoliasearch-client-scala/blob/master/src/main/scala/algolia/AlgoliaDsl.scala).
This DSL is used in the `execute` method of [`algolia.AlgoliaClient`](https://github.com/algolia/algoliasearch-client-scala/blob/master/src/main/scala/algolia/AlgoliaClient.scala).

As we want to provide human readable DSL, there is more than one way to use this DSL. For example, to get an object by its `objectID`:
```scala
client.execute { from index "index" objectId "myId" }

//or

client.execute { get / "index" / "myId" }
```

### Future

The `execute` method always return a [`scala.concurrent.Future`](http://www.scala-lang.org/api/2.11.7/#scala.concurrent.Future).
Depending of the operation it will be parametrized by a `case class`. For example:
```scala
var future: Future[Search] =
    client.execute {
        search into "index" query "a"
    }
```

### JSON as case class
Putting or getting objects from the API is wrapped into `case class` automatically by [`json4s`](http://json4s.org).

If you want to get objects just search for it and unwrap the result:
```scala
case class Contact(firstname: String,
                   lastname: String,
                   followers: Int,
                   compagny: String)

var future: Future[Seq[Contact]] =
    client
        .execute {
            search into "index" query "a"
        }
        .map { search =>
            search.as[Contact]
        }
```

If you want to get the full results (with `_highlightResult`, etc.):
```scala
case class EnhanceContact(firstname: String,
                          lastname: String,
                          followers: Int,
                          compagny: String,
                          objectID: String,
                          _highlightResult: Option[Map[String, HighlightResult]
                          _snippetResult: Option[Map[String, SnippetResult]],
                          _rankingInfo: Option[RankingInfo]) extends Hit

var future: Future[Seq[EnhanceContact]] =
    client
        .execute {
            search into "index" query "a"
        }
        .map { search =>
            search.asHit[EnhanceContact]
        }
```

For indexing documents, just pass an instance of your `case class` to the DSL:
```scala
client.execute {
    index into "contacts" `object` Contact("Jimmie", "Barninger", 93, "California Paint")
}
```

## Quick Start

In 30 seconds, this quick start tutorial will show you how to index and search objects.

### Initialize the client

To begin, you will need to initialize the client. In order to do this you will need your **Application ID** and **API Key**.
You can find both on [your Algolia account](https://www.algolia.com/api-keys).

```scala
val client = new AlgoliaClient("YourApplicationID", "YourAPIKey")
//No initIndex
```

## Push data

```scala
//For the DSL
import algolia.AlgoliaDsl._

//For basic Future support, you might want to change this by your own ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

//case class of your objects
case class Contact(firstname: String,
                   lastname: String,
                   followers: Int,
                   compagny: String)

val indexing1: Future[Indexing] = client.execute {
    index into "contacts" `object` Contact("Jimmie", "Barninger", 93, "California Paint")
}

val indexing2: Future[Indexing] = client.execute {
    index into "contacts" `object` Contact("Warren", "Speach", 42, "Norwalk Crmc")
}
```

## Configure

Settings can be customized to fine tune the search behavior. For example, you can add a custom sort by number of followers to further enhance the built-in relevance:

```scala
client.execute {
	changeSettings of "myIndex" `with` IndexSettings(
		customRanking = Some(Seq(CustomRanking.desc("followers")))
	)
}
```

You can also configure the list of attributes you want to index by order of importance (most important first).

**Note:** The Algolia engine is designed to suggest results as you type, which means you'll generally search by prefix.
In this case, the order of attributes is very important to decide which hit is the best:

```scala
client.execute {
	changeSettings of "myIndex" `with` IndexSettings(
		searchableAttributes = Some(Seq("lastname", "firstname", "company"))
	)
}
```

## Search

You can now search for contacts using `firstname`, `lastname`, `company`, etc. (even with typos):

```scala
// search by firstname
client.execute { search into "contacts" query Query(query = Some("jimmie")) }

// search a firstname with typo
client.execute { search into "contacts" query Query(query = Some("jimie")) }

// search for a company
client.execute { search into "contacts" query Query(query = Some("california paint")) }

// search for a firstname & company
client.execute { search into "contacts" query Query(query = Some("jimmie paint")) }
```

## Search UI

**Warning:** If you are building a web application, you may be more interested in using one of our
[frontend search UI libraries](https://www.algolia.com/doc/guides/search-ui/search-libraries/)

The following example shows how to build a front-end search quickly using
[InstantSearch.js](https://community.algolia.com/instantsearch.js/)

### index.html

```html
<!doctype html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/instantsearch.js/1/instantsearch.min.css">
</head>
<body>
  <header>
    <div>
       <input id="search-input" placeholder="Search for products">
       <!-- We use a specific placeholder in the input to guides users in their search. -->
    
  </header>
  <main>
      
      
  </main>

  <script type="text/html" id="hit-template">
    
      <p class="hit-name">{{{_highlightResult.firstname.value}}} {{{_highlightResult.lastname.value}}}</p>
    
  </script>

  <script src="https://cdn.jsdelivr.net/instantsearch.js/1/instantsearch.min.js"></script>
  <script src="app.js"></script>
</body>
```

### app.js

```js
var search = instantsearch({
  // Replace with your own values
  appId: 'YourApplicationID',
  apiKey: 'YourSearchOnlyAPIKey', // search only API key, no ADMIN key
  indexName: 'contacts',
  urlSync: true
});

search.addWidget(
  instantsearch.widgets.searchBox({
    container: '#search-input'
  })
);

search.addWidget(
  instantsearch.widgets.hits({
    container: '#hits',
    hitsPerPage: 10,
    templates: {
      item: document.getElementById('hit-template').innerHTML,
      empty: "We didn't find any results for the search <em>\"{{query}}\"</em>"
    }
  })
);

search.start();
```




## List of available methods





### Search

  - [Search an index](https://algolia.com/doc/api-reference/api-methods/search/?language=scala)
  - [Search for facet values](https://algolia.com/doc/api-reference/api-methods/search-for-facet-values/?language=scala)
  - [Search multiple indexes](https://algolia.com/doc/api-reference/api-methods/multiple-queries/?language=scala)



### Indexing

  - [Add objects](https://algolia.com/doc/api-reference/api-methods/add-objects/?language=scala)
  - [Update objects](https://algolia.com/doc/api-reference/api-methods/update-objects/?language=scala)
  - [Partial update objects](https://algolia.com/doc/api-reference/api-methods/partial-update-objects/?language=scala)
  - [Delete objects](https://algolia.com/doc/api-reference/api-methods/delete-objects/?language=scala)
  - [Delete by query](https://algolia.com/doc/api-reference/api-methods/delete-by-query/?language=scala)
  - [Get objects](https://algolia.com/doc/api-reference/api-methods/get-objects/?language=scala)
  - [Wait for operations](https://algolia.com/doc/api-reference/api-methods/wait-task/?language=scala)



### Settings

  - [Get settings](https://algolia.com/doc/api-reference/api-methods/get-settings/?language=scala)
  - [Set settings](https://algolia.com/doc/api-reference/api-methods/set-settings/?language=scala)



### Manage indices

  - [List indexes](https://algolia.com/doc/api-reference/api-methods/list-indices/?language=scala)
  - [Delete index](https://algolia.com/doc/api-reference/api-methods/delete-index/?language=scala)
  - [Copy index](https://algolia.com/doc/api-reference/api-methods/copy-index/?language=scala)
  - [Move index](https://algolia.com/doc/api-reference/api-methods/move-index/?language=scala)
  - [Clear index](https://algolia.com/doc/api-reference/api-methods/clear-index/?language=scala)



### API Keys

  - [Create secured API Key](https://algolia.com/doc/api-reference/api-methods/generate-secured-api-key/?language=scala)
  - [Add API Key](https://algolia.com/doc/api-reference/api-methods/add-api-key/?language=scala)
  - [Update API Key](https://algolia.com/doc/api-reference/api-methods/update-api-key/?language=scala)
  - [Delete API Key](https://algolia.com/doc/api-reference/api-methods/delete-api-key/?language=scala)
  - [Get API Key permissions](https://algolia.com/doc/api-reference/api-methods/get-api-key/?language=scala)
  - [List API Keys](https://algolia.com/doc/api-reference/api-methods/list-api-keys/?language=scala)



### Synonyms

  - [Save synonym](https://algolia.com/doc/api-reference/api-methods/save-synonym/?language=scala)
  - [Batch synonyms](https://algolia.com/doc/api-reference/api-methods/batch-synonyms/?language=scala)
  - [Delete synonym](https://algolia.com/doc/api-reference/api-methods/delete-synonym/?language=scala)
  - [Clear all synonyms](https://algolia.com/doc/api-reference/api-methods/clear-synonyms/?language=scala)
  - [Get synonym](https://algolia.com/doc/api-reference/api-methods/get-synonym/?language=scala)
  - [Search synonyms](https://algolia.com/doc/api-reference/api-methods/search-synonyms/?language=scala)



### Query rules

  - [Save a single rule](https://algolia.com/doc/api-reference/api-methods/rules-save/?language=scala)
  - [Batch save multiple rules](https://algolia.com/doc/api-reference/api-methods/rules-save-batch/?language=scala)
  - [Read a rule](https://algolia.com/doc/api-reference/api-methods/rules-read/?language=scala)
  - [Delete a single rule](https://algolia.com/doc/api-reference/api-methods/rules-delete/?language=scala)
  - [Clear all rules](https://algolia.com/doc/api-reference/api-methods/rules-clear/?language=scala)
  - [Search for rules](https://algolia.com/doc/api-reference/api-methods/rules-search/?language=scala)





### Advanced

  - [Custom batch](https://algolia.com/doc/api-reference/api-methods/batch/?language=scala)
  - [Browse an index](https://algolia.com/doc/api-reference/api-methods/browse/?language=scala)
  - [Get latest logs](https://algolia.com/doc/api-reference/api-methods/get-logs/?language=scala)




## Getting Help

- **Need help**? Ask a question to the [Algolia Community](https://discourse.algolia.com/) or on [Stack Overflow](http://stackoverflow.com/questions/tagged/algolia).
- **Found a bug?** You can open a [GitHub issue](https://github.com/algolia/algoliasearch-client-scala/issues).


