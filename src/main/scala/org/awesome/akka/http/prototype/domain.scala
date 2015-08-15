package org.awesome.akka.http.prototype

object domain {
  case class BidRequest(
    id: String,
    imp: List[Impression],
    app: Option[Application],
    device: Option[Device]
  )
  case class Impression(
    id: String,
    video: Option[Video],
    instl: Option[Boolean]
  )
  case class Video(
    mimes: List[String],
    linearity: Option[Int],
    minduration: Option[Int],
    maxduration: Option[Int],
    protocol: List[String],
    api: List[Int],
    w: Option[Int],
    h: Option[Int],
    startdelay: Option[Int],
    maxbitrate: Option[Int],
    minbitrate: Option[Int],
    delivery: Option[Int]
  )
  case class Application(
    id: String,
    name: Option[String],
    privacypolicy: Option[Int]
  )
  case class Device(
    os: Option[String],
    ip: Option[String],
    ua: Option[String],
    devicetype: Option[String],
    dpidsha1: Option[String])
}
