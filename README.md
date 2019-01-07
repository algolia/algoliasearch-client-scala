# Algolia Search API Client for Scala

[Algolia Search](https://www.algolia.com) is a hosted search engine capable of delivering realtime results from the first keystroke.

The **Algolia Search API Client for Scala** lets
you easily use the [Algolia Search REST API](https://www.algolia.com/doc/rest-api/search) from
your Scala code.

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



1. **[Supported platforms](#supported-platforms)**


1. **[Install](#install)**


1. **[Quick Start](#quick-start)**


1. **[Push data](#push-data)**


1. **[Configure](#configure)**


1. **[Search](#search)**


1. **[Search UI](#search-ui)**


1. **[List of available methods](#list-of-available-methods)**


# Getting Started



## Supported platforms

The API client only supports Scala 2.11 & 2.12.

## Install

With [Maven](https://maven.apache.org/), add the following dependency to your `pom.xml` file:

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

#### DSL

The main goal of this client is to provide a human-accessible and readable DSL for using AlgoliaSearch.

The entry point of the DSL is the [`algolia.AlgoliaDSL` object](https://github.com/algolia/algoliasearch-client-scala/blob/master/src/main/scala/algolia/AlgoliaDsl.scala).
This DSL is used in the `execute` method of [`algolia.AlgoliaClient`](https://github.com/algolia/algoliasearch-client-scala/blob/master/src/main/scala/algolia/AlgoliaClient.scala).

As we want to provide human-readable DSL, there's more than one way to use this DSL. For example, to get an object by its `objectID`:
```scala
client.execute { from index "index" objectId "myId" }

// or

client.execute { get / "index" / "myId" }
```

#### Future

The `execute` method always returns a [`scala.concurrent.Future`](http://www.scala-lang.org/api/2.11.7/#scala.concurrent.Future).
Depending on the operation, it's parametrized by a `case class`. For example:
```scala
val future: Future[Search] =
    client.execute {
        search into "index" query "a"
    }
```

#### JSON as case class

Putting or getting objects from the API is wrapped into `case class` automatically with [`json4s`](http://json4s.org).

If you want to get objects, search for them and unwrap the result:
```scala
case class Contact(firstname: String,
                   lastname: String,
                   followers: Int,
                   compagny: String)

val future: Future[Seq[Contact]] =
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

val future: Future[Seq[EnhanceContact]] =
    client
        .execute {
            search into "index" query "a"
        }
        .map { search =>
            search.asHit[EnhanceContact]
        }
```

For indexing documents, pass an instance of your `case class` to the DSL:
```scala
client.execute {
    index into "contacts" `object` Contact("Jimmie", "Barninger", 93, "California Paint")
}
```

## Quick Start

In 30 seconds, this quick start tutorial will show you how to index and search objects.

### Initialize the client

To start, you need to initialize the client. To do this, you need your **Application ID** and **API Key**.
You can find both on [your Algolia account](https://www.algolia.com/api-keys).

```scala
// No initIndex
val client = new AlgoliaClient("YourApplicationID", "YourAPIKey")
```

## Push data

```scala
// For the DSL
import algolia.AlgoliaDsl._

// For basic Future support, you might want to change this by your own ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

// case class of your objects
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

You can customize settings to fine tune the search behavior. For example, you can add a custom ranking by number of followers to further enhance the built-in relevance:

```scala
client.execute {
    changeSettings of "myIndex" `with` IndexSettings(
        customRanking = Some(Seq(CustomRanking.desc("followers")))
    )
}
```

You can also configure the list of attributes you want to index by order of importance (most important first).

**Note:** Algolia is designed to suggest results as you type, which means you'll generally search by prefix.
In this case, the order of attributes is crucial to decide which hit is the best.

```scala
client.execute {
    changeSettings of "myIndex" `with` IndexSettings(
        searchableAttributes = Some(Seq("lastname", "firstname", "company"))
    )
}
```

## Search

You can now search for contacts by `firstname`, `lastname`, `company`, etc. (even with typos):

```scala
// Search for a first name
client.execute { search into "contacts" query Query(query = Some("jimmie")) }

// Search for a first name with typo
client.execute { search into "contacts" query Query(query = Some("jimie")) }

// Search for a company
client.execute { search into "contacts" query Query(query = Some("california paint")) }

// Search for a first name and a company
client.execute { search into "contacts" query Query(query = Some("jimmie paint")) }
```

## Search UI

**Warning:** If you're building a web application, you may be interested in using one of our
[front-end search UI libraries](https://www.algolia.com/doc/guides/building-search-ui/what-is-instantsearch/js/).

The following example shows how to quickly build a front-end search using
[InstantSearch.js](https://www.algolia.com/doc/guides/building-search-ui/what-is-instantsearch/js/)

### index.html

```html
<!doctype html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/instantsearch.css@7.1.0/themes/algolia.css" />
</head>
<body>
  <header>
    <div>
       <input id="search-input" placeholder="Search for products">
       <!-- We use a specific placeholder in the input to guide users in their search. -->
    
  </header>
  <main>
      
      
  </main>

  <script type="text/html" id="hit-template">
    
      <p class="hit-name">
        {}{ "attribute": "firstname" }{{/helpers.highlight}}
        {}{ "attribute": "lastname" }{{/helpers.highlight}}
      </p>
    
  </script>

  <script src="https://cdn.jsdelivr.net/npm/instantsearch.js@3.0.0"></script>
  <script src="app.js"></script>
</body>
```

### app.js

```js
// Replace with your own values
var searchClient = algoliasearch(
  'YourApplicationID',
  'YourAPIKey' // search only API key, no ADMIN key
);

var search = instantsearch({
  indexName: 'instant_search',
  searchClient: searchClient,
  routing: true,
  searchParameters: {
    hitsPerPage: 10
  }
});

search.addWidget(
  instantsearch.widgets.searchBox({
    container: '#search-input'
  })
);

search.addWidget(
  instantsearch.widgets.hits({
    container: '#hits',
    templates: {
      item: document.getElementById('hit-template').innerHTML,
      empty: "We didn't find any results for the search <em>\"{{query}}\"</em>"
    }
  })
);

search.start();
```




## List of available methods





### Personalization

- [Add strategy](https://algolia.com/doc/api-reference/api-methods/add-strategy/?language=scala)
- [Get strategy](https://algolia.com/doc/api-reference/api-methods/get-strategy/?language=scala)




### Search

- [Search index](https://algolia.com/doc/api-reference/api-methods/search/?language=scala)
- [Search for facet values](https://algolia.com/doc/api-reference/api-methods/search-for-facet-values/?language=scala)
- [Search multiple indices](https://algolia.com/doc/api-reference/api-methods/multiple-queries/?language=scala)
- [Browse index](https://algolia.com/doc/api-reference/api-methods/browse/?language=scala)




### Indexing

- [Add objects](https://algolia.com/doc/api-reference/api-methods/add-objects/?language=scala)
- [Save objects](https://algolia.com/doc/api-reference/api-methods/save-objects/?language=scala)
- [Partial update objects](https://algolia.com/doc/api-reference/api-methods/partial-update-objects/?language=scala)
- [Delete objects](https://algolia.com/doc/api-reference/api-methods/delete-objects/?language=scala)
- [Replace all objects](https://algolia.com/doc/api-reference/api-methods/replace-all-objects/?language=scala)
- [Delete by](https://algolia.com/doc/api-reference/api-methods/delete-by/?language=scala)
- [Clear objects](https://algolia.com/doc/api-reference/api-methods/clear-objects/?language=scala)
- [Get objects](https://algolia.com/doc/api-reference/api-methods/get-objects/?language=scala)
- [Custom batch](https://algolia.com/doc/api-reference/api-methods/batch/?language=scala)




### Settings

- [Get settings](https://algolia.com/doc/api-reference/api-methods/get-settings/?language=scala)
- [Set settings](https://algolia.com/doc/api-reference/api-methods/set-settings/?language=scala)
- [Copy settings](https://algolia.com/doc/api-reference/api-methods/copy-settings/?language=scala)




### Manage indices

- [List indices](https://algolia.com/doc/api-reference/api-methods/list-indices/?language=scala)
- [Delete index](https://algolia.com/doc/api-reference/api-methods/delete-index/?language=scala)
- [Copy index](https://algolia.com/doc/api-reference/api-methods/copy-index/?language=scala)
- [Move index](https://algolia.com/doc/api-reference/api-methods/move-index/?language=scala)




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
- [Replace all synonyms](https://algolia.com/doc/api-reference/api-methods/replace-all-synonyms/?language=scala)
- [Copy synonyms](https://algolia.com/doc/api-reference/api-methods/copy-synonyms/?language=scala)
- [Export Synonyms](https://algolia.com/doc/api-reference/api-methods/export-synonyms/?language=scala)




### Query rules

- [Save rule](https://algolia.com/doc/api-reference/api-methods/save-rule/?language=scala)
- [Batch rules](https://algolia.com/doc/api-reference/api-methods/batch-rules/?language=scala)
- [Get rule](https://algolia.com/doc/api-reference/api-methods/get-rule/?language=scala)
- [Delete rule](https://algolia.com/doc/api-reference/api-methods/delete-rule/?language=scala)
- [Clear rules](https://algolia.com/doc/api-reference/api-methods/clear-rules/?language=scala)
- [Search rules](https://algolia.com/doc/api-reference/api-methods/search-rules/?language=scala)
- [Replace all rules](https://algolia.com/doc/api-reference/api-methods/replace-all-rules/?language=scala)
- [Copy rules](https://algolia.com/doc/api-reference/api-methods/copy-rules/?language=scala)
- [Export rules](https://algolia.com/doc/api-reference/api-methods/export-rules/?language=scala)




### A/B Test

- [Add A/B test](https://algolia.com/doc/api-reference/api-methods/add-ab-test/?language=scala)
- [Get A/B test](https://algolia.com/doc/api-reference/api-methods/get-ab-test/?language=scala)
- [List A/B tests](https://algolia.com/doc/api-reference/api-methods/list-ab-tests/?language=scala)
- [Stop A/B test](https://algolia.com/doc/api-reference/api-methods/stop-ab-test/?language=scala)
- [Delete A/B test](https://algolia.com/doc/api-reference/api-methods/delete-ab-test/?language=scala)




### Insights

- [Clicked Object IDs After Search](https://algolia.com/doc/api-reference/api-methods/clicked-object-ids-after-search/?language=scala)
- [Clicked Object IDs](https://algolia.com/doc/api-reference/api-methods/clicked-object-ids/?language=scala)
- [Clicked Filters](https://algolia.com/doc/api-reference/api-methods/clicked-filters/?language=scala)
- [Converted Objects IDs After Search](https://algolia.com/doc/api-reference/api-methods/converted-object-ids-after-search/?language=scala)
- [Converted Object IDs](https://algolia.com/doc/api-reference/api-methods/converted-object-ids/?language=scala)
- [Converted Filters](https://algolia.com/doc/api-reference/api-methods/converted-filters/?language=scala)
- [Viewed Object IDs](https://algolia.com/doc/api-reference/api-methods/viewed-object-ids/?language=scala)
- [Viewed Filters](https://algolia.com/doc/api-reference/api-methods/viewed-filters/?language=scala)




### MultiClusters

- [Assign or Move userID](https://algolia.com/doc/api-reference/api-methods/assign-user-id/?language=scala)
- [Get top userID](https://algolia.com/doc/api-reference/api-methods/get-top-user-id/?language=scala)
- [Get userID](https://algolia.com/doc/api-reference/api-methods/get-user-id/?language=scala)
- [List clusters](https://algolia.com/doc/api-reference/api-methods/list-clusters/?language=scala)
- [List userIDs](https://algolia.com/doc/api-reference/api-methods/list-user-id/?language=scala)
- [Remove userID](https://algolia.com/doc/api-reference/api-methods/remove-user-id/?language=scala)
- [Search userID](https://algolia.com/doc/api-reference/api-methods/search-user-id/?language=scala)




### Advanced

- [Get logs](https://algolia.com/doc/api-reference/api-methods/get-logs/?language=scala)
- [Configuring timeouts](https://algolia.com/doc/api-reference/api-methods/configuring-timeouts/?language=scala)
- [Set extra header](https://algolia.com/doc/api-reference/api-methods/set-extra-header/?language=scala)
- [Wait for operations](https://algolia.com/doc/api-reference/api-methods/wait-task/?language=scala)





## Getting Help

- **Need help**? Ask a question to the [Algolia Community](https://discourse.algolia.com/) or on [Stack Overflow](http://stackoverflow.com/questions/tagged/algolia).
- **Found a bug?** You can open a [GitHub issue](https://github.com/algolia/algoliasearch-client-scala/issues).

