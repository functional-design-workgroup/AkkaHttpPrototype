package org.awesome.akka.http.prototype.domain

object response {
  case class BidResponse(
    id:         String,
    seatbid:    List[SeatBid],
    bidid:      Option[String]              = None,
    cur:        Option[String]              = None,
    customdata: Option[String]              = None,
    nbr:        Option[Int]                 = None,
    ext:        Option[Map[String, Object]] = None
  )
  case class SeatBid(
    bid:    List[Bid],
    seat:   Option[String]              = None,
    group:  Option[String]              = None,
    ext:    Option[Map[String, Object]] = None
  )
  case class Bid(
    id:       String,
    impid:    String,
    price:    Float,
    adid:     Option[String]              = None,
    nurl:     Option[String]              = None,
    adm:      Option[String]              = None,
    adomain:  List[String]                = Nil,
    iurl:     Option[String]              = None,
    cid:      Option[String]              = None,
    crid:     Option[String]              = None,
    attr:     List[Int]                   = Nil,
    dealid:   Option[String]              = None,
    h:        Option[Int]                 = None,
    w:        Option[Int]                 = None,
    ext:      Option[Map[String, Object]] = None
  )
}
