//package com.aussieogame.backend.service;
//
//import com.aussieogame.backend.model.dao.impl.ResourcesDetails;
//import com.aussieogame.backend.repo.ResourcesDetailsRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ResourceDetailsService {
//    private final ResourcesDetailsRepository resourcesDetailsRepository;
//
//    public List<ResourcesDetails> getStatus(Long userId, LocalDateTime wantedDateTime) {
//        return resourcesDetailsRepository.findByResourcesTownUserIdAndDateTimeLessThan(userId, wantedDateTime);
//    }
//}
