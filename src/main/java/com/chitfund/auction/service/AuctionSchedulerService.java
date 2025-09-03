package com.chitfund.auction.service;

import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.chitfund.chitGroups.model.ChitGroup;
import com.chitfund.chitGroups.repository.ChitGroupRepository;

@Service
public class AuctionSchedulerService {

    private final ChitGroupRepository chitGroupRepository;
    private final AuctionManager auctionManager;

    public AuctionSchedulerService(ChitGroupRepository repo, AuctionManager manager) {
        this.chitGroupRepository = repo;
        this.auctionManager = manager;
    }

    @Scheduled(fixedRate = 5000)
    public void startAuctions() {
        try {
            // TODO: Implement findUpcomingBiddings query in repository
            // List<ChitGroup> groups = chitGroupRepository.findUpcomingBiddings(LocalDateTime.now());
            
            // For now, find groups that are ready for auction but not started yet
            List<ChitGroup> groups = chitGroupRepository.findAll()
                .stream()
                .filter(group -> !group.getAuctionStarted() && 
                               group.getBiddingStartTime() != null &&
                               group.getBiddingStartTime().isBefore(java.time.LocalDateTime.now()))
                .collect(java.util.stream.Collectors.toList());
            
            for (ChitGroup group : groups) {
                if (!group.getAuctionStarted()) {
                    group.setAuctionStarted(true);
                    chitGroupRepository.save(group);
                    auctionManager.startAuction(group);
                }
            }
        } catch (Exception e) {
            // Log error but don't let scheduler fail
            System.err.println("Error in auction scheduler: " + e.getMessage());
        }
    }
}
