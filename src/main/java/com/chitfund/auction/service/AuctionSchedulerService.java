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
        // List<ChitGroup> groups = chitGroupRepository.findUpcomingBiddings(LocalDateTime.now());
        List<ChitGroup> groups =null;
        for (ChitGroup group : groups) {
            if (!group.getAuctionStarted()) {
                group.setAuctionStarted(true);
                chitGroupRepository.save(group);
                auctionManager.startAuction(group);
            }
        }
    }
}
