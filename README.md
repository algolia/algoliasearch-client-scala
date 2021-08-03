<p align="center">
  <a href="https://www.algolia.com">
    <img alt="Algolia for Scala" src="https://user-images.githubusercontent.com/22633119/59600520-a8882400-9101-11e9-8034-2bf6d75bf962.png" >
  </a>

  <h4 align="center">The perfect starting point to integrate <a href="https://algolia.com" target="_blank">Algolia</a> within your Scala project</h4>

  <p align="center">
      <a href="https://maven-badges.herokuapp.com/maven-central/com.algolia/algoliasearch-scala_2.13/"><img src="https://maven-badges.herokuapp.com/maven-central/com.algolia/algoliasearch-scala_2.13/badge.svg" alt="Licence"></img></a> 
      <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="Licence"></img></a>
  </p>
</p>

<p align="center">
  <a href="https://www.algolia.com/doc/api-client/getting-started/install/scala/" target="_blank">Documentation</a>  •
  <a href="https://discourse.algolia.com" target="_blank">Community Forum</a>  •
  <a href="http://stackoverflow.com/questions/tagged/algolia" target="_blank">Stack Overflow</a>  •
  <a href="https://github.com/algolia/algoliasearch-client-scala/issues" target="_blank">Report a bug</a>  •
  <a href="https://www.algolia.com/doc/api-client/troubleshooting/faq/scala/" target="_blank">FAQ</a>  •
  <a href="https://www.algolia.com/support" target="_blank">Support</a>
</p>

## ✨ Features

* Supports Scala 2.11, 2.12, 2.13.
* Asynchronous methods to target Algolia's API
* Json as case class
* DSL

## 💡 Getting Started

**WARNING:**
The JVM has an infinite cache on successful DNS resolution. As our hostnames points to multiple IPs, the load could be not evenly spread among our machines, and you might also target a dead machine.

You should change this TTL by setting the property `networkaddress.cache.ttl`. For example to set the cache to 60 seconds:
```java
java.security.Security.setProperty("networkaddress.cache.ttl", "60");
```

For debug purposes you can enable debug logging on the API client. It's using [slf4j](https://www.slf4j.org) so it should be compatible with most java loggers.
The logger is named `algoliasearch`.


#### Install

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

In 30 seconds, this quick start tutorial will show you how to index and search objects.

#### Initialize the client

To start, you need to initialize the client. To do this, you need your **Application ID** and **API Key**.
You can find both on [your Algolia account](https://www.algolia.com/api-keys).

```scala
// No initIndex
val client = new AlgoliaClient("YourApplicationID", "YourAPIKey")
```

#### Push data

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

For full documentation, visit the **[Algolia Scala API Client](https://www.algolia.com/doc/api-client/getting-started/install/scala/)**.

## ❓ Troubleshooting

Encountering an issue? Before reaching out to support, we recommend heading to our [FAQ](https://www.algolia.com/doc/api-client/troubleshooting/faq/scala/) where you will find answers for the most common issues and gotchas with the client.

## Use the Dockerfile

If you want to contribute to this project without installing all its dependencies, you can use our Docker image. Please check our [dedicated guide](DOCKER_README.MD) to learn more.

## 📄 License
Algolia Scala API Client is an open-sourced software licensed under the [MIT license](LICENSE.md).
