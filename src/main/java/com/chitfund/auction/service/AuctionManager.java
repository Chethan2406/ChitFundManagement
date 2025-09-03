package com.chitfund.auction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;
import com.chitfund.auction.dto.AuctionRoom;
import com.chitfund.chitGroups.model.ChitGroup;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuctionManager {

    private final Map<Long, AuctionRoom> liveAuctions = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    public void startAuction(ChitGroup group) {
        try {
            LocalDateTime endTime = group.getBiddingStartTime().plusSeconds(group.getBiddingDurationInSeconds());
            AuctionRoom room = new AuctionRoom(group.getGroupId(), endTime, new ConcurrentHashMap<>());
            liveAuctions.put(group.getGroupId(), room);

            scheduledExecutorService.schedule(() -> closeAuction(group.getGroupId()),
                    group.getBiddingDurationInSeconds(), TimeUnit.SECONDS);
            
            log.info("CFM_AM_001 - Auction started for group: {}", group.getGroupId());
        } catch (Exception e) {
            log.error("CFM_AM_002 - Error starting auction for group: {}", group.getGroupId(), e);
        }
    }

    public void placeBid(Long groupId, Long memberId, BigDecimal amount) {
        try {
            AuctionRoom room = liveAuctions.get(groupId);
            if (room != null && LocalDateTime.now().isBefore(room.getEndTime())) {
                room.getBids().put(memberId, amount);
                log.info("CFM_AM_003 - Bid placed for group: {}, member: {}, amount: {}", groupId, memberId, amount);
            } else {
                log.warn("CFM_AM_004 - Bid rejected for group: {}, auction not active or expired", groupId);
            }
        } catch (Exception e) {
            log.error("CFM_AM_005 - Error placing bid for group: {}, member: {}", groupId, memberId, e);
        }
    }

    private void closeAuction(Long groupId) {
        try {
            AuctionRoom room = liveAuctions.remove(groupId);
            if (room != null && !room.getBids().isEmpty()) {
                Map.Entry<Long, BigDecimal> winner = room.getBids()
                        .entrySet()
                        .stream()
                        .min(Comparator.comparing(Map.Entry::getValue))
                        .orElse(null);

                if (winner != null) {
                    log.info("CFM_AM_006 - Auction completed for group: {}, Winner: Member {} with Bid {}", 
                            groupId, winner.getKey(), winner.getValue());
                } else {
                    log.info("CFM_AM_007 - Auction completed for group: {} with no valid bids", groupId);
                }
            } else {
                log.info("CFM_AM_008 - Auction completed for group: {} with no bids", groupId);
            }
        } catch (Exception e) {
            log.error("CFM_AM_009 - Error closing auction for group: {}", groupId, e);
        }
    }
    
    public Map<Long, AuctionRoom> getLiveAuctions() {
        return new ConcurrentHashMap<>(liveAuctions);
    }
    
    // Cleanup method to be called on application shutdown
    public void shutdown() {
        scheduledExecutorService.shutdown();
        try {
            if (!scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduledExecutorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
