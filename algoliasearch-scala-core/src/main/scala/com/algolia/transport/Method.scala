package com.algolia.transport

sealed trait Method

case object GET extends Method
case object POST extends Method
case object PUT extends Method
case object DELETE extends Method
