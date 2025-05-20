package com.chitfund.auction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;
import com.chitfund.auction.dto.AuctionRoom;
import com.chitfund.chitGroups.model.ChitGroup;

@Component
public class AuctionManager {

    private final Map<Long, AuctionRoom> liveAuctions = new ConcurrentHashMap<>();

    public void startAuction(ChitGroup group) {
        LocalDateTime endTime = group.getBiddingStartTime().plusSeconds(group.getBiddingDurationInSeconds());
        AuctionRoom room = new AuctionRoom(group.getGroupId(), endTime, new ConcurrentHashMap<>());
        liveAuctions.put(group.getGroupId(), room);

        Executors.newSingleThreadScheduledExecutor().schedule(() -> closeAuction(group.getGroupId()),
                group.getBiddingDurationInSeconds(), TimeUnit.SECONDS);
    }

    public void placeBid(Long groupId, Long memberId, BigDecimal amount) {
        AuctionRoom room = liveAuctions.get(groupId);
        if (room != null && LocalDateTime.now().isBefore(room.getEndTime())) {
            room.getBids().put(memberId, amount);
        }
    }

    private void closeAuction(Long groupId) {
        AuctionRoom room = liveAuctions.remove(groupId);
        if (room != null) {
            Map.Entry<Long, BigDecimal> winner = room.getBids()
                    .entrySet()
                    .stream()
                    .min(Comparator.comparing(Map.Entry::getValue))
                    .orElse(null);

            if (winner != null) {
                System.out.println("Winner: Member " + winner.getKey() + " with Bid " + winner.getValue());
            }
        }
    }
}
