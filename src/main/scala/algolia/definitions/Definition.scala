package algolia.definitions

import algolia.HttpPayload

trait Definition {
  private[algolia] def build(): HttpPayload
}
