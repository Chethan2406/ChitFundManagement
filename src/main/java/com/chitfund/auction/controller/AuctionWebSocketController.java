package com.chitfund.auction.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.chitfund.auction.dto.BidRequest;
import com.chitfund.auction.dto.BidResponse;
import com.chitfund.auction.service.AuctionManager;

@Controller
public class AuctionWebSocketController {

    private final AuctionManager auctionManager;

    public AuctionWebSocketController(AuctionManager manager) {
        this.auctionManager = manager;
    }

    @MessageMapping("/bid")
    @SendTo("/topic/bid-update")
    public BidResponse handleBid(BidRequest bid) {
        auctionManager.placeBid(bid.getGroupId(), bid.getMemberId(), bid.getAmount());
        return new BidResponse("Bid received", bid.getAmount());
    }
}
