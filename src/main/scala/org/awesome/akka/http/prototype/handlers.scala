package org.awesome.akka.http.prototype

import org.awesome.akka.http.prototype.domain.request.BidRequest
import org.awesome.akka.http.prototype.domain.response.{Bid, SeatBid, BidResponse}

object handlers {
  def handleBidRequest(bidRequest: BidRequest): BidResponse = BidResponse(
    id      = bidRequest.id,
    seatbid = SeatBid(
      bid = Bid(
        id    = "1",
        impid = "1",
        price = (Math.random() * 10).toFloat,
        nurl  = Some("http://nurl.com")
      ) :: Nil,
      seat = Some("proto-seat")
    ) :: Nil,
    cur     = bidRequest.cur
  )
}
