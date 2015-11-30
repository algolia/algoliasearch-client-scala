# /!\ Under developpement /!\

# algoliasearch-client-scala

[![Build Status](https://magnum.travis-ci.com/algolia/algoliasearch-client-scala.svg?token=JcfCqnibpemvurAz3bnz)](https://magnum.travis-ci.com/algolia/algoliasearch-client-scala)

## Prerequisite

* Java 8
* scala 2.10 or 2.11
* sbt 

## Build

    sbt
    
## Tests

In sbt REPL
    
    ~test
    

# Algolia Search API Client for Scala

[Algolia Search](http://www.algolia.com) is a hosted full-text, numerical, and faceted search engine capable of delivering realtime results from the first keystroke.

Our Scala client lets you easily use the [Algolia Search API](https://www.algolia.com/doc/rest_api) from your JVM based applications. It wraps the [Algolia Search REST API](http://www.algolia.com/doc/rest_api).


[![Build Status](https://travis-ci.org/algolia/algoliasearch-client-scala.png?branch=master)](https://travis-ci.org/algolia/algoliasearch-client-scala) [![GitHub version](https://badge.fury.io/gh/algolia%2Falgoliasearch-client-scala.png)](http://badge.fury.io/gh/algolia%2Falgoliasearch-client-scala) [![Coverage Status](https://coveralls.io/repos/algolia/algoliasearch-client-scala/badge.png)](https://coveralls.io/r/algolia/algoliasearch-client-scala)[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.algolia/algoliasearch/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.algolia/algoliasearch/)


Table of Contents
=================
**Getting Started**

1. [Setup](#setup)
1. [Quick Start](#quick-start)
1. [Online documentation](#documentation)
1. [Tutorials](#tutorials)


**Commands Reference**

1. [Add a new object](#add-a-new-object-to-the-index)
1. [Update an object](#update-an-existing-object-in-the-index)
1. [Search](#search)
1. [Multiple queries](#multiple-queries)
1. [Get an object](#get-an-object)
1. [Delete an object](#delete-an-object)
1. [Delete by query](#delete-by-query)
1. [Index settings](#index-settings)
1. [List indices](#list-indices)
1. [Delete an index](#delete-an-index)
1. [Clear an index](#clear-an-index)
1. [Wait indexing](#wait-indexing)
1. [Batch writes](#batch-writes)
1. [Security / User API Keys](#security--user-api-keys)
1. [Copy or rename an index](#copy-or-rename-an-index)
1. [Backup / Retrieve all index content](#backup--retrieve-of-all-index-content)
1. [Logs](#logs)



Setup
-------------
To setup your project, follow these steps:

If you're using SBT, add the following dependency to your pom file:

```scala
libraryDependencies += "com.algolia" %% "scala-client" % "0.0.1"
```

Initialize the client with your Application ID and API Key. You can find them on [your Algolia account](http://www.algolia.com/users/edit):


```scala
val client = new APIClient("YourApplicationID", "YourAPIKey");
```


Quick Start
-------------

In 30 seconds, this quick start tutorial will show you how to index and search objects.

Without any prior configuration, you can start indexing contacts in the `contacts` index using the following code:

```scala
case class Contact(firstname: String,
                   lastname: String,
                   followers: Int,
                   compagny: String)

val indexing1: Future[Indexing] = client.execute {
    index into "contacts" document Contact("Jimmie", "Barninger", 93, "California Paint")
}

val indexing2: Future[Indexing] = client.execute {
    index into "contacts" document Contact("Warren", "Speach", 42, "Norwalk Crmc")
}
```

You can now search for contacts using firstname, lastname, company, etc. (even with typos):

```scala
// search by firstname
client.execute { search into "contacts" query "jimmie" }

// search a firstname with typo
client.execute { search into "contacts" query "jimie" }

// search for a company
client.execute { search into "contacts" query "california paint" }

// search for a firstname & company
client.execute { search into "contacts" query "jimmie paint" }
```

Settings can be customized to tune the search behavior. For example, you can add a custom sort by number of followers to the already great built-in relevance:
```scala
TODO : index.setSettings(new JSONObject().append("customRanking", "desc(followers)"));
```

You can also configure the list of attributes you want to index by order of importance (first = most important):
```scala
TODO : index.setSettings(new JSONObject()
      .append("attributesToIndex", "lastname")
      .append("attributesToIndex", "firstname")
      .append("attributesToIndex", "company"));
```

Since the engine is designed to suggest results as you type, you'll generally search by prefix. In this case the order of attributes is very important to decide which hit is the best:
```scala
client.execute { search into "contacts" query "or" }
client.execute { search into "contacts" query "jim" }
```

**Notes:** If you are building a web application, you may be more interested in using our [JavaScript client](https://github.com/algolia/algoliasearch-client-js) to perform queries. It brings two benefits:
  * Your users get a better response time by not going through your servers
  * It will offload unnecessary tasks from your servers

```html
<script src="//cdn.jsdelivr.net/algoliasearch/3/algoliasearch.min.js"></script>
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


Documentation
================
Check our [online documentation](http://www.algolia.com/doc/guides/java):
 * [Initial Import](http://www.algolia.com/doc/guides/java#InitialImport)
 * [Ranking &amp; Relevance](http://www.algolia.com/doc/guides/java#RankingRelevance)
 * [Indexing](http://www.algolia.com/doc/guides/java#Indexing)
 * [Search](http://www.algolia.com/doc/guides/java#Search)
 * [Sorting](http://www.algolia.com/doc/guides/java#Sorting)
 * [Filtering](http://www.algolia.com/doc/guides/java#Filtering)
 * [Faceting](http://www.algolia.com/doc/guides/java#Faceting)
 * [Geo-Search](http://www.algolia.com/doc/guides/java#Geo-Search)
 * [Security](http://www.algolia.com/doc/guides/java#Security)
 * [REST API](http://www.algolia.com/doc/rest)

Tutorials
================

Check out our [tutorials](http://www.algolia.com/doc/tutorials):
 * [Search bar with autocomplete menu](http://www.algolia.com/doc/tutorials/auto-complete)
 * [Search bar with multi category autocomplete menu](http://www.algolia.com/doc/tutorials/multi-auto-complete)
 * [Instant search result pages](http://www.algolia.com/doc/tutorials/instant-search)


Commands Reference
==================

Add a new object to the Index
-------------

Each entry in an index has a unique identifier called `objectID`. There are two ways to add en entry to the index:

 1. Using automatic `objectID` assignment. You will be able to access it in the answer.
 2. Supplying your own `objectID`.

You don't need to explicitly create an index, it will be automatically created the first time you add an object.
Objects are schema less so you don't need any configuration to start indexing. If you wish to configure things, the settings section provides details about advanced settings.

You only neeed to create a `case class` describing your JSON Object to start indexing:

```scala
case class Contact(firstname: String,
                   lastname: String,
                   followers: Int,
                   compagny: String)
```

These `case class` are serialized using `json4s`.


Example with automatic `objectID` assignment:

```scala
val indexing: Future[Indexing] = client.execute {
    index into "contacts" document Contact("Jimmie", "Barninger", 93, "California Paint")
}

indexing onComplete {
    case Success(indexing) => println(indexing.objectID)
    case Failure(e) =>  println("An error has occured: " + e.getMessage)
}
```

Example with manual `objectID` assignment:

```scala
val indexing: Future[Indexing] = client.execute {
    index into "contacts" objectId "myID" document Contact("Jimmie", "Barninger", 93, "California Paint")
}

indexing onComplete {
    case Success(indexing) => println(indexing.objectID)
    case Failure(e) =>  println("An error has occured: " + e.getMessage)
}
```

Update an existing object in the Index
-------------

TODO

Search
-------------

TODO


Multiple queries
--------------

TODO


Get an object
-------------

TODO

Delete an object
-------------

TODO


Delete by query
-------------

TODO

Index Settings
-------------

TODO

List indices
-------------
You can list all your indices along with their associated information (number of entries, disk size, etc.) with the `listIndexes` method:

```scala
val indexes: Future[Indexes] = client.indexes()

//or

val indexes: Future[Indexes] = client.execute { indexes }
```

Delete an index
-------------

TODO

Clear an index
-------------

You can delete the index contents without removing settings and index specific API keys by using the clearIndex command:

```scala
client.clear("index")

//or

client.execute { clear("index") }
```

Wait indexing
-------------

TODO

Batch writes
-------------

TODO

Security / User API Keys
-------------

TODO

Copy or rename an index
-------------

TODO


Backup / Retrieve of all index content
-------------

TODO

Logs
-------------

TODO
