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
    
## TODO
    
* Search/Index should be parametrize by a case class and Search(hits: Seq[T])    
* If server sends a 4XX/5XX do not continue to the others  
* Multiple commands: https://github.com/sksamuel/elastic4s/blob/master/guide/multisearch.md
```
it("should search query") {
  val client = new AlgoliaClient("APPID", "KEY")
  client.search { into "toto" document Basic}
  client.execute {
    val a = query "toto" hitsPerPage 1
    search into "index1"
    search into "index2" query "toto" hitsPerPage 1
  }
  
  client.search("index1") { query "toto" }
  
  client.execute {
    search into "index" {
      query "1"
      query "2"
    }
  }
}
```