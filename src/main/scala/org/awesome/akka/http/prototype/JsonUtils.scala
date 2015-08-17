package org.awesome.akka.http.prototype

import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization._
import org.json4s.{Formats, NoTypeHints, DefaultFormats}
import org.json4s.jackson.Serialization

object JsonUtils {
  implicit val readFormats = DefaultFormats
  implicit val writeFormats = Serialization.formats(NoTypeHints)
  implicit class JsonString2Value(val jsonString: String) extends AnyVal {
    def fromJson[T](implicit formats: Formats, mf: scala.reflect.Manifest[T]) = parse(jsonString).camelizeKeys.extract[T]
  }
  implicit class Value2JsonString[T <: AnyRef](val value: T) extends AnyVal {
    def toJson = write(value)
  }
}
