package algolia.objects

/** Represents Dictionary settings. */
case class DictionarySettings(
    disableStandardEntries: Option[DisableStandardEntries] = None
)

/**
  * Map of language ISO code supported by the dictionary (e.g., “en” for English) to a boolean value.
  * When set to true, the standard entries for the language are disabled. Changes are set for the
  * given languages only. To re-enable standard entries, set the language to false. To reset settings
  * to default values, set dictionary to `null`.
  */
case class DisableStandardEntries(
    /** Settings for the stop word dictionary. */
    stopwords: Option[Map[String, Boolean]] = None
)
