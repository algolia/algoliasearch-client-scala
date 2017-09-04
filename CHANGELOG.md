# Change Log

## [1.24.1](https://github.com/algolia/algoliasearch-client-scala/tree/1.24.1) (2017-09-04)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.24.0...1.24.1)

**Closed issues:**

- Pagination using offset/length is not working [\#404](https://github.com/algolia/algoliasearch-client-scala/issues/404)

**Merged pull requests:**

- fix\(mapping\): Fix mapping of SearchResult [\#407](https://github.com/algolia/algoliasearch-client-scala/pull/407) ([ElPicador](https://github.com/ElPicador))
- fix\(mapping\): Fix mapping for SearchResult & fix incorrect usage of formats [\#406](https://github.com/algolia/algoliasearch-client-scala/pull/406) ([ElPicador](https://github.com/ElPicador))
- feat\(delete\): Add delete by [\#400](https://github.com/algolia/algoliasearch-client-scala/pull/400) ([ElPicador](https://github.com/ElPicador))

## [1.24.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.24.0) (2017-08-22)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.23.2...1.24.0)

**Closed issues:**

- Provide information on how to contribute [\#388](https://github.com/algolia/algoliasearch-client-scala/issues/388)

**Merged pull requests:**

- Update README [\#399](https://github.com/algolia/algoliasearch-client-scala/pull/399) ([algolia-api-client-bot](https://github.com/algolia-api-client-bot))
- feat\(request\): Add request options [\#394](https://github.com/algolia/algoliasearch-client-scala/pull/394) ([ElPicador](https://github.com/ElPicador))
- Add tasks status definition + use HashedWheelTimer in waitFor [\#393](https://github.com/algolia/algoliasearch-client-scala/pull/393) ([jlogeart](https://github.com/jlogeart))
- feat\(rules\): Add query rules [\#382](https://github.com/algolia/algoliasearch-client-scala/pull/382) ([ElPicador](https://github.com/ElPicador))

## [1.23.2](https://github.com/algolia/algoliasearch-client-scala/tree/1.23.2) (2017-08-01)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.23.1...1.23.2)

## [1.23.1](https://github.com/algolia/algoliasearch-client-scala/tree/1.23.1) (2017-08-01)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.23.0...1.23.1)

**Closed issues:**

- Can you please create a new release with scoverage disabled [\#391](https://github.com/algolia/algoliasearch-client-scala/issues/391)

**Merged pull requests:**

- fix\(run\): Remove coverage from final JARs [\#392](https://github.com/algolia/algoliasearch-client-scala/pull/392) ([ElPicador](https://github.com/ElPicador))
- feat\(release\): Update release script to release 2.11 and 2.12 [\#390](https://github.com/algolia/algoliasearch-client-scala/pull/390) ([ElPicador](https://github.com/ElPicador))

## [1.23.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.23.0) (2017-07-25)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.22.1...1.23.0)

**Closed issues:**

- Add a method to close the connection pool of the underlying http client [\#383](https://github.com/algolia/algoliasearch-client-scala/issues/383)
- Add cross compilation to Scala 2.12 [\#196](https://github.com/algolia/algoliasearch-client-scala/issues/196)

**Merged pull requests:**

- feat\(client\): Add a .close\(\) method [\#389](https://github.com/algolia/algoliasearch-client-scala/pull/389) ([ElPicador](https://github.com/ElPicador))
- feat\(scala\): Add cross compilation to scala 2.11 and 2.12 [\#387](https://github.com/algolia/algoliasearch-client-scala/pull/387) ([ElPicador](https://github.com/ElPicador))
- chore\(dependencies\): Update dependencies [\#386](https://github.com/algolia/algoliasearch-client-scala/pull/386) ([ElPicador](https://github.com/ElPicador))

## [1.22.1](https://github.com/algolia/algoliasearch-client-scala/tree/1.22.1) (2017-07-18)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.22.0...1.22.1)

**Fixed bugs:**

- Control characters are not escaped automatically by json4s [\#381](https://github.com/algolia/algoliasearch-client-scala/issues/381)
- Client retries on 400 [\#194](https://github.com/algolia/algoliasearch-client-scala/issues/194)

**Closed issues:**

- Missing filters in Ranking to allow configuration of index settings [\#384](https://github.com/algolia/algoliasearch-client-scala/issues/384)
- Add DSL for update object [\#171](https://github.com/algolia/algoliasearch-client-scala/issues/171)

**Merged pull requests:**

- feat\(ranking\): Add ranking filters [\#385](https://github.com/algolia/algoliasearch-client-scala/pull/385) ([ElPicador](https://github.com/ElPicador))
- fix\(retry\): 4XX on retry [\#374](https://github.com/algolia/algoliasearch-client-scala/pull/374) ([ElPicador](https://github.com/ElPicador))

## [1.22.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.22.0) (2017-05-24)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.21.0...1.22.0)

**Merged pull requests:**

- bump json4s version from 3.4.0 to 3.5.2 [\#379](https://github.com/algolia/algoliasearch-client-scala/pull/379) ([robconrad](https://github.com/robconrad))

## [1.21.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.21.0) (2017-04-25)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.20.0...1.21.0)

**Implemented enhancements:**

- Simplify DSL for batches on same index [\#67](https://github.com/algolia/algoliasearch-client-scala/issues/67)
- Get Object with some attributes [\#19](https://github.com/algolia/algoliasearch-client-scala/issues/19)

**Closed issues:**

- Batch operations not supported for `PartialUpdateObjectDefinition` [\#376](https://github.com/algolia/algoliasearch-client-scala/issues/376)
- Add cross compilation to Scala 2.10 [\#296](https://github.com/algolia/algoliasearch-client-scala/issues/296)

**Merged pull requests:**

- fix\(batch\): Add partial update on a batch [\#377](https://github.com/algolia/algoliasearch-client-scala/pull/377) ([ElPicador](https://github.com/ElPicador))
- chore\(sbt\): Update sbt to 0.13.15 [\#375](https://github.com/algolia/algoliasearch-client-scala/pull/375) ([ElPicador](https://github.com/ElPicador))
- feat\(query\): Add percentileComputation [\#373](https://github.com/algolia/algoliasearch-client-scala/pull/373) ([ElPicador](https://github.com/ElPicador))

## [1.20.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.20.0) (2017-04-10)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.19.1...1.20.0)

**Closed issues:**

- IndexSettings bad types [\#369](https://github.com/algolia/algoliasearch-client-scala/issues/369)

**Merged pull requests:**

- feat\(query,indexsettings\): Fix index settings & query types [\#372](https://github.com/algolia/algoliasearch-client-scala/pull/372) ([ElPicador](https://github.com/ElPicador))
- Minor build clean up: use isSnapshot and sbt helpers [\#370](https://github.com/algolia/algoliasearch-client-scala/pull/370) ([pdalpra](https://github.com/pdalpra))

## [1.19.1](https://github.com/algolia/algoliasearch-client-scala/tree/1.19.1) (2017-03-17)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.19.0...1.19.1)

**Merged pull requests:**

- Revert "feat\(json4s\): Be less restrictiv for json4s dependency versio… [\#368](https://github.com/algolia/algoliasearch-client-scala/pull/368) ([ElPicador](https://github.com/ElPicador))

## [1.19.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.19.0) (2017-03-13)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.18.0...1.19.0)

**Fixed bugs:**

- insideBoundingBox & insidePolygon takes arrays of arrays [\#365](https://github.com/algolia/algoliasearch-client-scala/issues/365)
- Incompatibilities with Spark because of json4s [\#363](https://github.com/algolia/algoliasearch-client-scala/issues/363)

**Merged pull requests:**

- feat\(query\): Construtor with floats for InsidePolygon & InsideBoundin… [\#367](https://github.com/algolia/algoliasearch-client-scala/pull/367) ([ElPicador](https://github.com/ElPicador))
- fix\(query\): Fix InsideBoundingBox & InsidePolygon [\#366](https://github.com/algolia/algoliasearch-client-scala/pull/366) ([ElPicador](https://github.com/ElPicador))
- feat\(json4s\): Be less restrictiv for json4s dependency version, to make it compatible with Spark [\#364](https://github.com/algolia/algoliasearch-client-scala/pull/364) ([ElPicador](https://github.com/ElPicador))
- Update README [\#362](https://github.com/algolia/algoliasearch-client-scala/pull/362) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.18.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.18.0) (2017-02-06)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.17.0...1.18.0)

**Merged pull requests:**

- feat\(facetquery\): maxFacetHits [\#331](https://github.com/algolia/algoliasearch-client-scala/pull/331) ([ElPicador](https://github.com/ElPicador))
- Update README [\#330](https://github.com/algolia/algoliasearch-client-scala/pull/330) ([algoliareadmebot](https://github.com/algoliareadmebot))
- feat\(query\): Add facetingAfterDistinct [\#329](https://github.com/algolia/algoliasearch-client-scala/pull/329) ([ElPicador](https://github.com/ElPicador))
- Update README [\#328](https://github.com/algolia/algoliasearch-client-scala/pull/328) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#321](https://github.com/algolia/algoliasearch-client-scala/pull/321) ([algoliareadmebot](https://github.com/algoliareadmebot))
- chore\(syntax\): Add dot notation [\#297](https://github.com/algolia/algoliasearch-client-scala/pull/297) ([ElPicador](https://github.com/ElPicador))
- Update README [\#295](https://github.com/algolia/algoliasearch-client-scala/pull/295) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#282](https://github.com/algolia/algoliasearch-client-scala/pull/282) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.17.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.17.0) (2016-12-20)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.16.0...1.17.0)

**Fixed bugs:**

- `getRankingInfo` is a Boolean [\#195](https://github.com/algolia/algoliasearch-client-scala/issues/195)

**Merged pull requests:**

- feat\(query\): insidePolygon & insideBoundingBox as Seq [\#225](https://github.com/algolia/algoliasearch-client-scala/pull/225) ([ElPicador](https://github.com/ElPicador))
- fix\(query\): getRankingInfo is a boolean [\#224](https://github.com/algolia/algoliasearch-client-scala/pull/224) ([ElPicador](https://github.com/ElPicador))
- feat\(query\): Add responseFields [\#223](https://github.com/algolia/algoliasearch-client-scala/pull/223) ([ElPicador](https://github.com/ElPicador))
- Update README [\#221](https://github.com/algolia/algoliasearch-client-scala/pull/221) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.16.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.16.0) (2016-12-09)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.15.0...1.16.0)

**Closed issues:**

- ignorePlurals: accept a list of ISO codes [\#190](https://github.com/algolia/algoliasearch-client-scala/issues/190)
- Implement `search in facet` [\#178](https://github.com/algolia/algoliasearch-client-scala/issues/178)

**Merged pull requests:**

- feat\(index\): Rename search facet to search into facet value [\#202](https://github.com/algolia/algoliasearch-client-scala/pull/202) ([ElPicador](https://github.com/ElPicador))
- Update README [\#201](https://github.com/algolia/algoliasearch-client-scala/pull/201) ([algoliareadmebot](https://github.com/algoliareadmebot))
- feat\(retry\): New retry strategy [\#200](https://github.com/algolia/algoliasearch-client-scala/pull/200) ([ElPicador](https://github.com/ElPicador))
- Update README [\#199](https://github.com/algolia/algoliasearch-client-scala/pull/199) ([algoliareadmebot](https://github.com/algoliareadmebot))
- feat\(settings\): Ignore plurals accepts iso codes [\#193](https://github.com/algolia/algoliasearch-client-scala/pull/193) ([ElPicador](https://github.com/ElPicador))
- fix\(settings\): Distinct & RemoveStopWords [\#192](https://github.com/algolia/algoliasearch-client-scala/pull/192) ([ElPicador](https://github.com/ElPicador))
- chore\(scalafmt\): Update scalafmt version [\#191](https://github.com/algolia/algoliasearch-client-scala/pull/191) ([ElPicador](https://github.com/ElPicador))
- Update README [\#189](https://github.com/algolia/algoliasearch-client-scala/pull/189) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#188](https://github.com/algolia/algoliasearch-client-scala/pull/188) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#184](https://github.com/algolia/algoliasearch-client-scala/pull/184) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Reformat code with scalafmt [\#182](https://github.com/algolia/algoliasearch-client-scala/pull/182) ([ElPicador](https://github.com/ElPicador))
- Update README [\#181](https://github.com/algolia/algoliasearch-client-scala/pull/181) ([algoliareadmebot](https://github.com/algoliareadmebot))
- feat\(search\): Add search in facet [\#180](https://github.com/algolia/algoliasearch-client-scala/pull/180) ([ElPicador](https://github.com/ElPicador))
- Update README [\#179](https://github.com/algolia/algoliasearch-client-scala/pull/179) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#177](https://github.com/algolia/algoliasearch-client-scala/pull/177) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.15.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.15.0) (2016-10-06)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.14.0...1.15.0)

**Fixed bugs:**

- WaitTask support only indexing faster than 13 seconds [\#148](https://github.com/algolia/algoliasearch-client-scala/issues/148)

**Closed issues:**

- Add support for restrictSources [\#166](https://github.com/algolia/algoliasearch-client-scala/issues/166)
- Rename of attributesToIndex to searchableAttributes [\#164](https://github.com/algolia/algoliasearch-client-scala/issues/164)
- Replace master/slaves by primary/replicas [\#163](https://github.com/algolia/algoliasearch-client-scala/issues/163)
- Index: implement getObjects with attributesToRetrieve [\#150](https://github.com/algolia/algoliasearch-client-scala/issues/150)

**Merged pull requests:**

- Improve error handling if all retries failed [\#174](https://github.com/algolia/algoliasearch-client-scala/pull/174) ([ElPicador](https://github.com/ElPicador))
- waitFor task now waits Infinity [\#173](https://github.com/algolia/algoliasearch-client-scala/pull/173) ([ElPicador](https://github.com/ElPicador))
- Secure ApiKeys: Add support for restrictSources [\#172](https://github.com/algolia/algoliasearch-client-scala/pull/172) ([ElPicador](https://github.com/ElPicador))
- Rename of attributesToIndex to searchableAttributes [\#170](https://github.com/algolia/algoliasearch-client-scala/pull/170) ([ElPicador](https://github.com/ElPicador))
- Update README [\#169](https://github.com/algolia/algoliasearch-client-scala/pull/169) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#168](https://github.com/algolia/algoliasearch-client-scala/pull/168) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Rename \*slaves to \*replicas [\#165](https://github.com/algolia/algoliasearch-client-scala/pull/165) ([ElPicador](https://github.com/ElPicador))
- Update README [\#162](https://github.com/algolia/algoliasearch-client-scala/pull/162) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#152](https://github.com/algolia/algoliasearch-client-scala/pull/152) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#151](https://github.com/algolia/algoliasearch-client-scala/pull/151) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.14.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.14.0) (2016-08-19)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.13.1...1.14.0)

**Merged pull requests:**

- Upgrade Json4s to 3.4.0 [\#149](https://github.com/algolia/algoliasearch-client-scala/pull/149) ([ElPicador](https://github.com/ElPicador))
- Update README [\#147](https://github.com/algolia/algoliasearch-client-scala/pull/147) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#145](https://github.com/algolia/algoliasearch-client-scala/pull/145) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.13.1](https://github.com/algolia/algoliasearch-client-scala/tree/1.13.1) (2016-08-03)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.13.0...1.13.1)

**Closed issues:**

- Migrate `User-Agent` header to new conventions [\#140](https://github.com/algolia/algoliasearch-client-scala/issues/140)

**Merged pull requests:**

- Migrate user agent to new format [\#143](https://github.com/algolia/algoliasearch-client-scala/pull/143) ([ElPicador](https://github.com/ElPicador))
- Update README [\#142](https://github.com/algolia/algoliasearch-client-scala/pull/142) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#141](https://github.com/algolia/algoliasearch-client-scala/pull/141) ([algoliareadmebot](https://github.com/algoliareadmebot))
- docs\(README\): automatic update [\#139](https://github.com/algolia/algoliasearch-client-scala/pull/139) ([CBaptiste](https://github.com/CBaptiste))
- Update README [\#136](https://github.com/algolia/algoliasearch-client-scala/pull/136) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.13.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.13.0) (2016-07-26)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.12.0...1.13.0)

**Closed issues:**

- numberOfPendingTask should be removed [\#134](https://github.com/algolia/algoliasearch-client-scala/issues/134)
- Confusing documentation: variable naming [\#127](https://github.com/algolia/algoliasearch-client-scala/issues/127)
- Confusing documentation: missing imports [\#126](https://github.com/algolia/algoliasearch-client-scala/issues/126)

**Merged pull requests:**

- Indices -\> numberOfPendingTask is removed [\#135](https://github.com/algolia/algoliasearch-client-scala/pull/135) ([ElPicador](https://github.com/ElPicador))
- Update README [\#133](https://github.com/algolia/algoliasearch-client-scala/pull/133) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#132](https://github.com/algolia/algoliasearch-client-scala/pull/132) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#131](https://github.com/algolia/algoliasearch-client-scala/pull/131) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#130](https://github.com/algolia/algoliasearch-client-scala/pull/130) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#129](https://github.com/algolia/algoliasearch-client-scala/pull/129) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#128](https://github.com/algolia/algoliasearch-client-scala/pull/128) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.12.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.12.0) (2016-07-13)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.12...1.12.0)

## [1.12](https://github.com/algolia/algoliasearch-client-scala/tree/1.12) (2016-07-13)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.11.0...1.12)

**Implemented enhancements:**

- Allow extra index settings String to be specified [\#124](https://github.com/algolia/algoliasearch-client-scala/issues/124)
- Allow extra query String parameters to be specified [\#90](https://github.com/algolia/algoliasearch-client-scala/issues/90)
- Add custom HEADERS to the request [\#89](https://github.com/algolia/algoliasearch-client-scala/issues/89)

**Merged pull requests:**

- Add custom query parameters [\#125](https://github.com/algolia/algoliasearch-client-scala/pull/125) ([ElPicador](https://github.com/ElPicador))
- Add custom HEADERS to the requests [\#123](https://github.com/algolia/algoliasearch-client-scala/pull/123) ([ElPicador](https://github.com/ElPicador))

## [1.11.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.11.0) (2016-07-12)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.10.0...1.11.0)

**Merged pull requests:**

- Add missing query parameters & index settings [\#121](https://github.com/algolia/algoliasearch-client-scala/pull/121) ([ElPicador](https://github.com/ElPicador))
- Update README [\#120](https://github.com/algolia/algoliasearch-client-scala/pull/120) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#119](https://github.com/algolia/algoliasearch-client-scala/pull/119) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#118](https://github.com/algolia/algoliasearch-client-scala/pull/118) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.10.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.10.0) (2016-06-21)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.9.0...1.10.0)

**Closed issues:**

- Support of new query parameters [\#115](https://github.com/algolia/algoliasearch-client-scala/issues/115)

**Merged pull requests:**

- Support for new query parameters & forwardToSlaves in changeSettings [\#116](https://github.com/algolia/algoliasearch-client-scala/pull/116) ([ElPicador](https://github.com/ElPicador))
- Update README [\#114](https://github.com/algolia/algoliasearch-client-scala/pull/114) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.9.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.9.0) (2016-06-09)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.8.0...1.9.0)

**Closed issues:**

- Support deleting by query [\#92](https://github.com/algolia/algoliasearch-client-scala/issues/92)

**Merged pull requests:**

- Update README [\#113](https://github.com/algolia/algoliasearch-client-scala/pull/113) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update README [\#112](https://github.com/algolia/algoliasearch-client-scala/pull/112) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Add browse sync and delete by query [\#111](https://github.com/algolia/algoliasearch-client-scala/pull/111) ([ElPicador](https://github.com/ElPicador))
- Update README [\#110](https://github.com/algolia/algoliasearch-client-scala/pull/110) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.8.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.8.0) (2016-06-06)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.7.2...1.8.0)

**Closed issues:**

- Migrate to AsyncHttpClient v2 [\#100](https://github.com/algolia/algoliasearch-client-scala/issues/100)

**Merged pull requests:**

- Update README [\#109](https://github.com/algolia/algoliasearch-client-scala/pull/109) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Feat/remove dependency to dispatch [\#105](https://github.com/algolia/algoliasearch-client-scala/pull/105) ([ElPicador](https://github.com/ElPicador))

## [1.7.2](https://github.com/algolia/algoliasearch-client-scala/tree/1.7.2) (2016-06-06)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.7.1...1.7.2)

## [1.7.1](https://github.com/algolia/algoliasearch-client-scala/tree/1.7.1) (2016-06-06)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.7.0...1.7.1)

**Merged pull requests:**

- Update README [\#108](https://github.com/algolia/algoliasearch-client-scala/pull/108) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Specify UTF-8 character set in Content-Type request header [\#107](https://github.com/algolia/algoliasearch-client-scala/pull/107) ([abhalla-atl](https://github.com/abhalla-atl))
- Update README [\#104](https://github.com/algolia/algoliasearch-client-scala/pull/104) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.7.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.7.0) (2016-06-01)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.6.2...1.7.0)

**Implemented enhancements:**

- Synonym v2 integration [\#88](https://github.com/algolia/algoliasearch-client-scala/issues/88)

**Merged pull requests:**

- Update README [\#102](https://github.com/algolia/algoliasearch-client-scala/pull/102) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Synonyms v2 [\#91](https://github.com/algolia/algoliasearch-client-scala/pull/91) ([ElPicador](https://github.com/ElPicador))

## [1.6.2](https://github.com/algolia/algoliasearch-client-scala/tree/1.6.2) (2016-05-18)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.6.1...1.6.2)

**Fixed bugs:**

- Handle errors of DNS in the fallback [\#96](https://github.com/algolia/algoliasearch-client-scala/issues/96)

**Merged pull requests:**

- Fixing timeout on DNS, and SNI support [\#99](https://github.com/algolia/algoliasearch-client-scala/pull/99) ([ElPicador](https://github.com/ElPicador))

## [1.6.1](https://github.com/algolia/algoliasearch-client-scala/tree/1.6.1) (2016-05-17)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.6.0...1.6.1)

**Fixed bugs:**

- Fix the order of the hosts fallbacks [\#93](https://github.com/algolia/algoliasearch-client-scala/issues/93)

**Merged pull requests:**

- Fix targeted hosts for WaitForTask, IndexSettings and GetObject\(s\) [\#98](https://github.com/algolia/algoliasearch-client-scala/pull/98) ([ElPicador](https://github.com/ElPicador))
- Fix hosts automatic fallback [\#97](https://github.com/algolia/algoliasearch-client-scala/pull/97) ([ElPicador](https://github.com/ElPicador))
- Update README [\#95](https://github.com/algolia/algoliasearch-client-scala/pull/95) ([algoliareadmebot](https://github.com/algoliareadmebot))
- Update readme [\#94](https://github.com/algolia/algoliasearch-client-scala/pull/94) ([algoliareadmebot](https://github.com/algoliareadmebot))

## [1.6.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.6.0) (2016-03-23)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.5...1.6.0)

**Closed issues:**

- Search: Ability to return \> 1000 or Browse: Add custom query parameter\(s\) [\#86](https://github.com/algolia/algoliasearch-client-scala/issues/86)

**Merged pull requests:**

- Browse by query [\#87](https://github.com/algolia/algoliasearch-client-scala/pull/87) ([ElPicador](https://github.com/ElPicador))
- Doc: fix list indices [\#85](https://github.com/algolia/algoliasearch-client-scala/pull/85) ([ElPicador](https://github.com/ElPicador))

## [1.5](https://github.com/algolia/algoliasearch-client-scala/tree/1.5) (2016-03-09)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.4.0...1.5)

**Implemented enhancements:**

- Delete by query [\#8](https://github.com/algolia/algoliasearch-client-scala/issues/8)
- Generate Secured ApiKeys [\#79](https://github.com/algolia/algoliasearch-client-scala/issues/79)
- Multiple queries [\#17](https://github.com/algolia/algoliasearch-client-scala/issues/17)
- Backup / Retrieve of all index content [\#15](https://github.com/algolia/algoliasearch-client-scala/issues/15)

**Merged pull requests:**

- Add browsing without query [\#84](https://github.com/algolia/algoliasearch-client-scala/pull/84) ([ElPicador](https://github.com/ElPicador))
- Multi-queries [\#82](https://github.com/algolia/algoliasearch-client-scala/pull/82) ([ElPicador](https://github.com/ElPicador))
- Add query full test [\#81](https://github.com/algolia/algoliasearch-client-scala/pull/81) ([ElPicador](https://github.com/ElPicador))
- Generate secured api keys [\#80](https://github.com/algolia/algoliasearch-client-scala/pull/80) ([ElPicador](https://github.com/ElPicador))

## [1.4.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.4.0) (2016-02-18)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.3.0...1.4.0)

**Implemented enhancements:**

- Search: Add other options [\#5](https://github.com/algolia/algoliasearch-client-scala/issues/5)

**Merged pull requests:**

- Add complete search options [\#78](https://github.com/algolia/algoliasearch-client-scala/pull/78) ([ElPicador](https://github.com/ElPicador))

## [1.3.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.3.0) (2016-02-18)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.2.0...1.3.0)

**Implemented enhancements:**

- Logs [\#16](https://github.com/algolia/algoliasearch-client-scala/issues/16)
- Security / User API Keys [\#13](https://github.com/algolia/algoliasearch-client-scala/issues/13)
- Configure a index [\#3](https://github.com/algolia/algoliasearch-client-scala/issues/3)

**Closed issues:**

- Add doc for index settings [\#74](https://github.com/algolia/algoliasearch-client-scala/issues/74)

**Merged pull requests:**

- Fetch logs [\#77](https://github.com/algolia/algoliasearch-client-scala/pull/77) ([ElPicador](https://github.com/ElPicador))
- Add configure/add/etc. api keys [\#76](https://github.com/algolia/algoliasearch-client-scala/pull/76) ([ElPicador](https://github.com/ElPicador))
- Doc: Configure index [\#75](https://github.com/algolia/algoliasearch-client-scala/pull/75) ([ElPicador](https://github.com/ElPicador))
- Configure an index [\#73](https://github.com/algolia/algoliasearch-client-scala/pull/73) ([ElPicador](https://github.com/ElPicador))

## [1.2.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.2.0) (2016-02-02)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.1.0...1.2.0)

**Implemented enhancements:**

- Get multiple objects [\#34](https://github.com/algolia/algoliasearch-client-scala/issues/34)

**Fixed bugs:**

- Can not do batches on batches [\#32](https://github.com/algolia/algoliasearch-client-scala/issues/32)

**Merged pull requests:**

- Fix: batches of batches [\#72](https://github.com/algolia/algoliasearch-client-scala/pull/72) ([ElPicador](https://github.com/ElPicador))
- Multiple get objets [\#71](https://github.com/algolia/algoliasearch-client-scala/pull/71) ([ElPicador](https://github.com/ElPicador))

## [1.1.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.1.0) (2016-01-25)
[Full Changelog](https://github.com/algolia/algoliasearch-client-scala/compare/1.0.0...1.1.0)

**Implemented enhancements:**

- Partial Update Object No Create, on single object [\#69](https://github.com/algolia/algoliasearch-client-scala/issues/69)
- Batch partialUpdateObjects/partialUpdateObjectNoCreate [\#31](https://github.com/algolia/algoliasearch-client-scala/issues/31)
- Wait indexing [\#11](https://github.com/algolia/algoliasearch-client-scala/issues/11)
- Partial object update [\#4](https://github.com/algolia/algoliasearch-client-scala/issues/4)

**Merged pull requests:**

- Partial update objects [\#70](https://github.com/algolia/algoliasearch-client-scala/pull/70) ([ElPicador](https://github.com/ElPicador))
- Add partial update object [\#68](https://github.com/algolia/algoliasearch-client-scala/pull/68) ([ElPicador](https://github.com/ElPicador))
- Wait for task [\#66](https://github.com/algolia/algoliasearch-client-scala/pull/66) ([ElPicador](https://github.com/ElPicador))
- Coveralls plugins [\#65](https://github.com/algolia/algoliasearch-client-scala/pull/65) ([ElPicador](https://github.com/ElPicador))

## [1.0.0](https://github.com/algolia/algoliasearch-client-scala/tree/1.0.0) (2015-12-22)
**Implemented enhancements:**

- DSL: Rename `document` & `documents` [\#57](https://github.com/algolia/algoliasearch-client-scala/issues/57)
- Simplify the DSL [\#56](https://github.com/algolia/algoliasearch-client-scala/issues/56)
- docs: Add full example [\#55](https://github.com/algolia/algoliasearch-client-scala/issues/55)
- The API returns `Seq\[\]` maybe we should change it to something more universal `Traversable\[\]` ? [\#52](https://github.com/algolia/algoliasearch-client-scala/issues/52)
- Add configurable request timeouts on the http client [\#49](https://github.com/algolia/algoliasearch-client-scala/issues/49)
- Add configurable connect timeouts on the http client [\#48](https://github.com/algolia/algoliasearch-client-scala/issues/48)
- Add api version in userAgent [\#47](https://github.com/algolia/algoliasearch-client-scala/issues/47)
- Testing: Add a full Integration test on indicies [\#41](https://github.com/algolia/algoliasearch-client-scala/issues/41)
- Delete by multiple objectIDs [\#33](https://github.com/algolia/algoliasearch-client-scala/issues/33)
- Get an object [\#18](https://github.com/algolia/algoliasearch-client-scala/issues/18)
- Copy or rename an index [\#14](https://github.com/algolia/algoliasearch-client-scala/issues/14)
- Batch writes [\#12](https://github.com/algolia/algoliasearch-client-scala/issues/12)
- Clear an index [\#10](https://github.com/algolia/algoliasearch-client-scala/issues/10)
- Delete an index [\#9](https://github.com/algolia/algoliasearch-client-scala/issues/9)
- Delete object [\#7](https://github.com/algolia/algoliasearch-client-scala/issues/7)
- Create index [\#6](https://github.com/algolia/algoliasearch-client-scala/issues/6)
- Launch multiple commands in one `execute` [\#2](https://github.com/algolia/algoliasearch-client-scala/issues/2)

**Fixed bugs:**

- Weird HTTP exception if APP\_ID is `""` [\#54](https://github.com/algolia/algoliasearch-client-scala/issues/54)
- Have the `ExecutionContext` passed as implicit [\#24](https://github.com/algolia/algoliasearch-client-scala/issues/24)
- Indexing does not uses indexingHosts [\#23](https://github.com/algolia/algoliasearch-client-scala/issues/23)
- The hosts are not randomized on query [\#22](https://github.com/algolia/algoliasearch-client-scala/issues/22)
- If a server answers a 4XX all the servers are called [\#1](https://github.com/algolia/algoliasearch-client-scala/issues/1)

**Merged pull requests:**

- 1.0.0 [\#64](https://github.com/algolia/algoliasearch-client-scala/pull/64) ([ElPicador](https://github.com/ElPicador))
- Update build status [\#63](https://github.com/algolia/algoliasearch-client-scala/pull/63) ([ElPicador](https://github.com/ElPicador))
- Add imports on readme [\#62](https://github.com/algolia/algoliasearch-client-scala/pull/62) ([ElPicador](https://github.com/ElPicador))
- Add validation for AlgoliaClient [\#61](https://github.com/algolia/algoliasearch-client-scala/pull/61) ([ElPicador](https://github.com/ElPicador))
- Seq to Traversable [\#60](https://github.com/algolia/algoliasearch-client-scala/pull/60) ([ElPicador](https://github.com/ElPicador))
- DSL: simplify it [\#59](https://github.com/algolia/algoliasearch-client-scala/pull/59) ([ElPicador](https://github.com/ElPicador))
- DSL: Rename document to object [\#58](https://github.com/algolia/algoliasearch-client-scala/pull/58) ([ElPicador](https://github.com/ElPicador))
- Add Connect/Socket timeout [\#53](https://github.com/algolia/algoliasearch-client-scala/pull/53) ([ElPicador](https://github.com/ElPicador))
- Fix pom for maven release [\#51](https://github.com/algolia/algoliasearch-client-scala/pull/51) ([ElPicador](https://github.com/ElPicador))
- Add API version to User Agent [\#50](https://github.com/algolia/algoliasearch-client-scala/pull/50) ([ElPicador](https://github.com/ElPicador))
- Integration test on moving indices [\#45](https://github.com/algolia/algoliasearch-client-scala/pull/45) ([ElPicador](https://github.com/ElPicador))
- Add copy indices test [\#44](https://github.com/algolia/algoliasearch-client-scala/pull/44) ([ElPicador](https://github.com/ElPicador))
- Indices integration testing [\#43](https://github.com/algolia/algoliasearch-client-scala/pull/43) ([ElPicador](https://github.com/ElPicador))
- Refactoring of task naming [\#42](https://github.com/algolia/algoliasearch-client-scala/pull/42) ([ElPicador](https://github.com/ElPicador))
- Integration tests for baches [\#40](https://github.com/algolia/algoliasearch-client-scala/pull/40) ([ElPicador](https://github.com/ElPicador))
- Add plugin to automatically release on maven-central [\#39](https://github.com/algolia/algoliasearch-client-scala/pull/39) ([ElPicador](https://github.com/ElPicador))
- Add \(auto\) licence headers to files [\#38](https://github.com/algolia/algoliasearch-client-scala/pull/38) ([ElPicador](https://github.com/ElPicador))
- Delete objects in batches [\#37](https://github.com/algolia/algoliasearch-client-scala/pull/37) ([ElPicador](https://github.com/ElPicador))
- Enable IntegrationTests on travis [\#36](https://github.com/algolia/algoliasearch-client-scala/pull/36) ([ElPicador](https://github.com/ElPicador))
- Update Readme [\#35](https://github.com/algolia/algoliasearch-client-scala/pull/35) ([ElPicador](https://github.com/ElPicador))
- Batches for add/save/delete objects and clear indices [\#30](https://github.com/algolia/algoliasearch-client-scala/pull/30) ([ElPicador](https://github.com/ElPicador))
- Add code coverage [\#29](https://github.com/algolia/algoliasearch-client-scala/pull/29) ([ElPicador](https://github.com/ElPicador))
- Indexing objects on indexing hosts [\#28](https://github.com/algolia/algoliasearch-client-scala/pull/28) ([ElPicador](https://github.com/ElPicador))
- Use IndexHosts on non search requests [\#27](https://github.com/algolia/algoliasearch-client-scala/pull/27) ([ElPicador](https://github.com/ElPicador))
- Randomize the hosts on Client creation [\#26](https://github.com/algolia/algoliasearch-client-scala/pull/26) ([ElPicador](https://github.com/ElPicador))
- Add ExecutionContext as implicit [\#25](https://github.com/algolia/algoliasearch-client-scala/pull/25) ([ElPicador](https://github.com/ElPicador))
- Add doc [\#21](https://github.com/algolia/algoliasearch-client-scala/pull/21) ([ElPicador](https://github.com/ElPicador))



\* *This Change Log was automatically generated by [github_changelog_generator](https://github.com/skywinder/Github-Changelog-Generator)*