package org.home.hotelmanagementapi.controller;

import org.home.hotelmanagementapi.model.dto.FeedBackDto;
import org.home.hotelmanagementapi.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<FeedBackDto>> getFeedbacksByRating(@PathVariable int rating) {
        return feedbackService.getFeedbacksByRating(rating);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFeedBack(@RequestBody FeedBackDto feedbackDto) {
        return feedbackService.createFeedback(feedbackDto);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> clearAllFeedBacks() {
        return feedbackService.deleteAll();
    }


}
