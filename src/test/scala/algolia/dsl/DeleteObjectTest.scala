package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{DELETE, HttpPayload, POST}

class DeleteObjectTest extends AlgoliaTest {

  describe("delete") {

    it("deletes object") {
      delete from "toto" objectId "oid"
    }

    it("deletes object with DSL \"/\"") {
      delete / "toto" / "oid"
    }

    it("deletes object with inverse DSL") {
      delete objectId "oid" from "toto"
    }

    it("should call API") {
      (delete from "toto").build() should be(
        HttpPayload(
          DELETE,
          Seq("1", "indexes", "toto"),
          isSearch = false
        )
      )
    }

    describe("batch") {

      it("deletes objects") {
        delete from "toto" objectIds Seq("1", "2")
      }

      it("should call the API") {
        val body =
          """
            | {
            |   "requests":[
            |     {
            |       "indexName":"toto",
            |       "objectID":"1",
            |       "action":"deleteObject"
            |     },{
            |       "indexName":"toto",
            |       "objectID":"2",
            |       "action":"deleteObject"
            |     }
            |   ]
            | }
          """.stripMargin.split("\n").map(_.trim).mkString

        (delete from "toto" objectIds Seq("1", "2")).build() should be(
          HttpPayload(
            POST,
            List("1", "indexes", "*", "batch"),
            body = Some(body),
            isSearch = false)
        )
      }

    }
  }

}
