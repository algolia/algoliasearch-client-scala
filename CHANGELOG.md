# Change Log

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