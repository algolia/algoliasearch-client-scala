# Change Log

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
- Wait indexing [\#11](https://github.com/algolia/algoliasearch-client-scala/issues/11)
- Partial object update [\#4](https://github.com/algolia/algoliasearch-client-scala/issues/4)

**Merged pull requests:**

- Partial update objects [\#70](https://github.com/algolia/algoliasearch-client-scala/pull/70) ([ElPicador](https://github.com/ElPicador))
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

**Fixed bugs:**

- Weird HTTP exception if APP\_ID is `""` [\#54](https://github.com/algolia/algoliasearch-client-scala/issues/54)
- Have the `ExecutionContext` passed as implicit [\#24](https://github.com/algolia/algoliasearch-client-scala/issues/24)
- Indexing does not uses indexingHosts [\#23](https://github.com/algolia/algoliasearch-client-scala/issues/23)
- The hosts are not randomized on query [\#22](https://github.com/algolia/algoliasearch-client-scala/issues/22)
- If a server answers a 4XX all the servers are called [\#1](https://github.com/algolia/algoliasearch-client-scala/issues/1)

**Merged pull requests:**

- 1.0.0 [\#64](https://github.com/algolia/algoliasearch-client-scala/pull/64) ([ElPicador](https://github.com/ElPicador))
- Add imports on readme [\#62](https://github.com/algolia/algoliasearch-client-scala/pull/62) ([ElPicador](https://github.com/ElPicador))
- Add validation for AlgoliaClient [\#61](https://github.com/algolia/algoliasearch-client-scala/pull/61) ([ElPicador](https://github.com/ElPicador))
- Seq to Traversable [\#60](https://github.com/algolia/algoliasearch-client-scala/pull/60) ([ElPicador](https://github.com/ElPicador))
- DSL: simplify it [\#59](https://github.com/algolia/algoliasearch-client-scala/pull/59) ([ElPicador](https://github.com/ElPicador))



\* *This Change Log was automatically generated by [github_changelog_generator](https://github.com/skywinder/Github-Changelog-Generator)*