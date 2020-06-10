package com.algolia.transport

sealed trait Compression

case object NoCompression extends Compression
case object GZIP extends Compression
