package com.algolia.transport

sealed trait Call

object Call {
  case object Read extends Call
  case object Write extends Call
}

object Accept {
  type Accept = Call => Boolean

  def Read(call: Call): Boolean = call match {
    case Call.Read => true
    case _         => false
  }

  def Write(call: Call): Boolean = call match {
    case Call.Write => true
    case _          => false
  }

  def ReadWrite(call: Call): Boolean = call match {
    case Call.Read | Call.Write => true
    case _                      => false
  }
}
