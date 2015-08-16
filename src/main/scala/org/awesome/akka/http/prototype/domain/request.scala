package org.awesome.akka.http.prototype.domain

object request {
  case class BidRequest(
    id:       String,
    imp:      List[Impression],
    app:      Option[Application],
    site:     Option[Site],
    device:   Option[Device],
    bidfloor: Option[Float],
    cur:      Option[String],
    pmp:      Option[String],
    ext:      Option[Map[String, Object]]
  )
  case class PrivateMarketplace(
    privateAuction: Option[Int],
    deals:          List[Deal],
    ext:            Option[Map[String, Object]]
  )
  case class Deal(
    id:       Option[String],
    bidfloor: Option[Float],
    bidcur:   Option[String],
    wseat:    List[String],
    wadomain: List[String],
    at:       Option[Int],
    ext:      Option[Map[String, Object]]
  )
  case class Impression(
    id:     String,
    video:  Option[Video],
    banner: Option[Banner],
    instl:  Option[Boolean],
    ext:    Option[Map[String, Object]]
  )
  case class Banner(
    id:     Option[String],
    w:      Option[Int],
    h:      Option[Int],
    btype:  List[Int],
    battr:  List[Int],
    mimes:  List[String],
    api:    List[Int],
    ext:    Option[Map[String, Object]]
  )
  case class Video(
    id:           Option[String],
    w:            Option[Int],
    h:            Option[Int],
    btype:        List[Int],
    battr:        List[Int],
    mimes:        List[String],
    api:          List[Int],
    linearity:    Option[Int],
    minduration:  Option[Int],
    maxduration:  Option[Int],
    protocol:     List[String],
    startdelay:   Option[Int],
    maxbitrate:   Option[Int],
    minbitrate:   Option[Int],
    delivery:     Option[Int],
    ext:          Option[Map[String, Object]]
  )
  case class Application(
    id:             String,
    name:           Option[String],
    privacypolicy:  Option[Int],
    ext:            Option[Map[String, Object]]
  )
  case class Site(
    id:     Option[String],
    name:   Option[String],
    domain: Option[String],
    ext:    Option[Map[String, Object]]
  )
  case class Device(
    os:         Option[String],
    ip:         Option[String],
    ua:         Option[String],
    devicetype: Option[String],
    dpidsha1:   Option[String],
    ext:        Option[Map[String, Object]]
  )
}
