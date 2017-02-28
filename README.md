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




## API Documentation

You can find the full reference on [Algolia's website](https://www.algolia.com/doc/api-client/scala/).


## Table of Contents


1. **[Supported platforms](#supported-platforms)**


1. **[Install](#install)**


1. **[Quick Start](#quick-start)**

    * [Initialize the client](#initialize-the-client)
    * [Push data](#push-data)
    * [Search](#search)
    * [Configure](#configure)
    * [Frontend search](#frontend-search)

1. **[Philosophy](#philosophy)**

    * [DSL](#dsl)
    * [Future](#future)
    * [JSON as case class](#json-as-case-class)

1. **[Getting Help](#getting-help)**





# Getting Started



## Supported platforms

This API client only supports Scala 2.11.

## Install

If you're using Maven, add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>com.algolia</groupId>
    <artifactId>algoliasearch-scala_2.11</artifactId>
    <version>[1,)</version>
</dependency>
```

For Snapshots add the Sonatype repository:
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

If you're using SBT, add the following dependency to your `build.sbt` file:

```scala
libraryDependencies += "com.algolia" %% "algoliasearch-scala" % "[1,)"
```

For Snapshots add the Sonatype repository:
```scala
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
```

## Quick Start

In 30 seconds, this quick start tutorial will show you how to index and search objects.

### Initialize the client

You first need to initialize the client. For that you need your **Application ID** and **API Key**.
You can find both of them on [your Algolia account](https://www.algolia.com/api-keys).

```scala
val client = new AlgoliaClient("YourApplicationID", "YourAPIKey")
```

### Push data

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

### Search

You can now search for contacts using firstname, lastname, company, etc. (even with typos):

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

### Configure

Settings can be customized to tune the search behavior. For example, you can add a custom sort by number of followers to the already great built-in relevance:

```scala
client.execute {
	changeSettings of "myIndex" `with` IndexSettings(
		customRanking = Some(Seq(CustomRanking.desc("followers")))
	)
}
```

You can also configure the list of attributes you want to index by order of importance (first = most important):

**Note:** Since the engine is designed to suggest results as you type, you'll generally search by prefix.
In this case the order of attributes is very important to decide which hit is the best:

```scala
client.execute {
	changeSettings of "myIndex" `with` IndexSettings(
		searchableAttributes = Some(Seq("lastname", "firstname", "company"))
	)
}
```

### Frontend search

**Note:** If you are building a web application, you may be more interested in using our [JavaScript client](https://github.com/algolia/algoliasearch-client-javascript) to perform queries.

It brings two benefits:
  * Your users get a better response time by not going through your servers
  * It will offload unnecessary tasks from your servers

```html
<script src="https://cdn.jsdelivr.net/algoliasearch/3/algoliasearch.min.js"></script>
<script>
var client = algoliasearch('ApplicationID', 'apiKey');
var index = client.initIndex('indexName');

// perform query "jim"
index.search('jim', searchCallback);

// the last optional argument can be used to add search parameters
index.search(
  'jim', {
    hitsPerPage: 5,
    facets: '*',
    maxValuesPerFacet: 10
  },
  searchCallback
);

function searchCallback(err, content) {
  if (err) {
    console.error(err);
    return;
  }

  console.log(content);
}
</script>
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
Putting or getting objects from the API is wrapped into `case class` automatically by [json4s](http://json4s.org).

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

## Getting Help

- **Need help**? Ask a question to the [Algolia Community](https://discourse.algolia.com/) or on [Stack Overflow](http://stackoverflow.com/questions/tagged/algolia).
- **Found a bug?** You can open a [GitHub issue](https://github.com/algolia/algoliasearch-client-scala/issues).



