/** Composition API (beta) The Algolia Composition API lets you run search requests on your Compositions. ## Client
  * libraries Use Algolia's API clients and libraries to reliably integrate Algolia's APIs with your apps. The official
  * API clients are covered by Algolia's [Service Level Agreement](https://www.algolia.com/policies/sla/). See:
  * [Algolia's ecosystem](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) ##
  * Base URLs The base URLs for requests to the Composition API are: - `https://{APPLICATION_ID}.algolia.net` -
  * `https://{APPLICATION_ID}-dsn.algolia.net`. If your subscription includes a [Distributed Search
  * Network](https://dashboard.algolia.com/infra), this ensures that requests are sent to servers closest to users. Both
  * URLs provide high availability by distributing requests with load balancing. **All requests must use HTTPS.** ##
  * Retry strategy To guarantee high availability, implement a retry strategy for all API requests using the URLs of
  * your servers as fallbacks: - `https://{APPLICATION_ID}-1.algolianet.com` -
  * `https://{APPLICATION_ID}-2.algolianet.com` - `https://{APPLICATION_ID}-3.algolianet.com` These URLs use a different
  * DNS provider than the primary URLs. You should randomize this list to ensure an even load across the three servers.
  * All Algolia API clients implement this retry strategy. ## Authentication To authenticate your API requests, add
  * these headers: - `x-algolia-application-id`. Your Algolia application ID. - `x-algolia-api-key`. An API key with the
  * necessary permissions to make the request. The required access control list (ACL) to make a request is listed in
  * each endpoint's reference. You can find your application ID and API key in the [Algolia
  * dashboard](https://dashboard.algolia.com/account). ## Request format Depending on the endpoint, request bodies are
  * either JSON objects or arrays of JSON objects, ## Parameters Parameters are passed as query parameters for GET and
  * DELETE requests, and in the request body for POST and PUT requests. Query parameters must be
  * [URL-encoded](https://developer.mozilla.org/en-US/docs/Glossary/Percent-encoding). Non-ASCII characters must be
  * UTF-8 encoded. Plus characters (`+`) are interpreted as spaces. Arrays as query parameters must be one of: - A
  * comma-separated string: `attributesToRetrieve=title,description` - A URL-encoded JSON array:
  * `attributesToRetrieve=%5B%22title%22,%22description%22%D` ## Response status and errors The Composition API returns
  * JSON responses. Since JSON doesn't guarantee any specific ordering, don't rely on the order of attributes in the API
  * response. Successful responses return a `2xx` status. Client errors return a `4xx` status. Server errors are
  * indicated by a `5xx` status. Error responses have a `message` property with more information. ## Version The current
  * version of the Composition API is version 1, as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.composition

import org.json4s._

sealed trait SupportedLanguage

/** ISO code for a supported language.
  */
object SupportedLanguage {
  case object Af extends SupportedLanguage {
    override def toString = "af"
  }
  case object Ar extends SupportedLanguage {
    override def toString = "ar"
  }
  case object Az extends SupportedLanguage {
    override def toString = "az"
  }
  case object Bg extends SupportedLanguage {
    override def toString = "bg"
  }
  case object Bn extends SupportedLanguage {
    override def toString = "bn"
  }
  case object Ca extends SupportedLanguage {
    override def toString = "ca"
  }
  case object Cs extends SupportedLanguage {
    override def toString = "cs"
  }
  case object Cy extends SupportedLanguage {
    override def toString = "cy"
  }
  case object Da extends SupportedLanguage {
    override def toString = "da"
  }
  case object De extends SupportedLanguage {
    override def toString = "de"
  }
  case object El extends SupportedLanguage {
    override def toString = "el"
  }
  case object En extends SupportedLanguage {
    override def toString = "en"
  }
  case object Eo extends SupportedLanguage {
    override def toString = "eo"
  }
  case object Es extends SupportedLanguage {
    override def toString = "es"
  }
  case object Et extends SupportedLanguage {
    override def toString = "et"
  }
  case object Eu extends SupportedLanguage {
    override def toString = "eu"
  }
  case object Fa extends SupportedLanguage {
    override def toString = "fa"
  }
  case object Fi extends SupportedLanguage {
    override def toString = "fi"
  }
  case object Fo extends SupportedLanguage {
    override def toString = "fo"
  }
  case object Fr extends SupportedLanguage {
    override def toString = "fr"
  }
  case object Ga extends SupportedLanguage {
    override def toString = "ga"
  }
  case object Gl extends SupportedLanguage {
    override def toString = "gl"
  }
  case object He extends SupportedLanguage {
    override def toString = "he"
  }
  case object Hi extends SupportedLanguage {
    override def toString = "hi"
  }
  case object Hu extends SupportedLanguage {
    override def toString = "hu"
  }
  case object Hy extends SupportedLanguage {
    override def toString = "hy"
  }
  case object Id extends SupportedLanguage {
    override def toString = "id"
  }
  case object Is extends SupportedLanguage {
    override def toString = "is"
  }
  case object It extends SupportedLanguage {
    override def toString = "it"
  }
  case object Ja extends SupportedLanguage {
    override def toString = "ja"
  }
  case object Ka extends SupportedLanguage {
    override def toString = "ka"
  }
  case object Kk extends SupportedLanguage {
    override def toString = "kk"
  }
  case object Ko extends SupportedLanguage {
    override def toString = "ko"
  }
  case object Ku extends SupportedLanguage {
    override def toString = "ku"
  }
  case object Ky extends SupportedLanguage {
    override def toString = "ky"
  }
  case object Lt extends SupportedLanguage {
    override def toString = "lt"
  }
  case object Lv extends SupportedLanguage {
    override def toString = "lv"
  }
  case object Mi extends SupportedLanguage {
    override def toString = "mi"
  }
  case object Mn extends SupportedLanguage {
    override def toString = "mn"
  }
  case object Mr extends SupportedLanguage {
    override def toString = "mr"
  }
  case object Ms extends SupportedLanguage {
    override def toString = "ms"
  }
  case object Mt extends SupportedLanguage {
    override def toString = "mt"
  }
  case object Nb extends SupportedLanguage {
    override def toString = "nb"
  }
  case object Nl extends SupportedLanguage {
    override def toString = "nl"
  }
  case object No extends SupportedLanguage {
    override def toString = "no"
  }
  case object Ns extends SupportedLanguage {
    override def toString = "ns"
  }
  case object Pl extends SupportedLanguage {
    override def toString = "pl"
  }
  case object Ps extends SupportedLanguage {
    override def toString = "ps"
  }
  case object Pt extends SupportedLanguage {
    override def toString = "pt"
  }
  case object PtBr extends SupportedLanguage {
    override def toString = "pt-br"
  }
  case object Qu extends SupportedLanguage {
    override def toString = "qu"
  }
  case object Ro extends SupportedLanguage {
    override def toString = "ro"
  }
  case object Ru extends SupportedLanguage {
    override def toString = "ru"
  }
  case object Sk extends SupportedLanguage {
    override def toString = "sk"
  }
  case object Sq extends SupportedLanguage {
    override def toString = "sq"
  }
  case object Sv extends SupportedLanguage {
    override def toString = "sv"
  }
  case object Sw extends SupportedLanguage {
    override def toString = "sw"
  }
  case object Ta extends SupportedLanguage {
    override def toString = "ta"
  }
  case object Te extends SupportedLanguage {
    override def toString = "te"
  }
  case object Th extends SupportedLanguage {
    override def toString = "th"
  }
  case object Tl extends SupportedLanguage {
    override def toString = "tl"
  }
  case object Tn extends SupportedLanguage {
    override def toString = "tn"
  }
  case object Tr extends SupportedLanguage {
    override def toString = "tr"
  }
  case object Tt extends SupportedLanguage {
    override def toString = "tt"
  }
  case object Uk extends SupportedLanguage {
    override def toString = "uk"
  }
  case object Ur extends SupportedLanguage {
    override def toString = "ur"
  }
  case object Uz extends SupportedLanguage {
    override def toString = "uz"
  }
  case object Zh extends SupportedLanguage {
    override def toString = "zh"
  }
  val values: Seq[SupportedLanguage] = Seq(
    Af,
    Ar,
    Az,
    Bg,
    Bn,
    Ca,
    Cs,
    Cy,
    Da,
    De,
    El,
    En,
    Eo,
    Es,
    Et,
    Eu,
    Fa,
    Fi,
    Fo,
    Fr,
    Ga,
    Gl,
    He,
    Hi,
    Hu,
    Hy,
    Id,
    Is,
    It,
    Ja,
    Ka,
    Kk,
    Ko,
    Ku,
    Ky,
    Lt,
    Lv,
    Mi,
    Mn,
    Mr,
    Ms,
    Mt,
    Nb,
    Nl,
    No,
    Ns,
    Pl,
    Ps,
    Pt,
    PtBr,
    Qu,
    Ro,
    Ru,
    Sk,
    Sq,
    Sv,
    Sw,
    Ta,
    Te,
    Th,
    Tl,
    Tn,
    Tr,
    Tt,
    Uk,
    Ur,
    Uz,
    Zh
  )

  def withName(name: String): SupportedLanguage = SupportedLanguage.values
    .find(_.toString == name)
    .getOrElse(throw new MappingException(s"Unknown SupportedLanguage value: $name"))
}

class SupportedLanguageSerializer
    extends CustomSerializer[SupportedLanguage](_ =>
      (
        {
          case JString(value) => SupportedLanguage.withName(value)
          case JNull          => null
        },
        { case value: SupportedLanguage =>
          JString(value.toString)
        }
      )
    )
