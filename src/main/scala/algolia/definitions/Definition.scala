package algolia.definitions

import algolia.http.HttpPayload

trait Definition {
  private[algolia] def build(): HttpPayload
}
