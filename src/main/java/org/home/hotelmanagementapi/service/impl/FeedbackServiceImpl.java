package org.home.hotelmanagementapi.service.impl;

import org.home.hotelmanagementapi.model.dto.FeedBackDto;
import org.home.hotelmanagementapi.model.entity.FeedbackEntity;
import org.home.hotelmanagementapi.model.entity.GuestEntity;
import org.home.hotelmanagementapi.repository.FeedbackRepository;
import org.home.hotelmanagementapi.service.FeedbackService;
import org.home.hotelmanagementapi.service.GuestService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final GuestService guestService;
    private final ModelMapper modelMapper;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,
                               GuestService guestService,
                               ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.guestService = guestService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<String> createFeedback(FeedBackDto feedbackDto) {

       FeedbackEntity feedback = modelMapper.map(feedbackDto, FeedbackEntity.class);
       GuestEntity guest = guestService.findGuest(feedbackDto.getGuestEmail());

       if (guest == null) {
           return new ResponseEntity<>("Guest with email: " + feedbackDto.getGuestEmail() + " doesn't exists!", HttpStatus.BAD_REQUEST);
       }

       feedback.setGuest(guest);
       feedbackRepository.save(feedback);

        return new ResponseEntity<>("FeedBack created!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<FeedBackDto>> getFeedbacksByRating(int rating) {
        List<FeedbackEntity> allByRating = feedbackRepository.findAllByRating(rating);

        List<FeedBackDto> dtoList = new ArrayList<>();

        for (FeedbackEntity entity : allByRating) {
            dtoList.add(modelMapper.map(entity, FeedBackDto.class));
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAll() {
        feedbackRepository.deleteAll();
        return new ResponseEntity<>("Feedbacks cleared!", HttpStatus.OK);
    }

}
