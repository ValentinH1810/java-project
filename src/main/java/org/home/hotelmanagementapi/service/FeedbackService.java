package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.FeedBackDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FeedbackService {
    ResponseEntity<String> createFeedback(FeedBackDto feedbackDto);

    ResponseEntity<List<FeedBackDto>> getFeedbacksByRating(int rating);

    ResponseEntity<String> deleteAll();
}
