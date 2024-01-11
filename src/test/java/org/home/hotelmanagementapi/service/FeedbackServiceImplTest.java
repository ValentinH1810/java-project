package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.FeedBackDto;
import org.home.hotelmanagementapi.model.entity.FeedbackEntity;
import org.home.hotelmanagementapi.model.entity.GuestEntity;
import org.home.hotelmanagementapi.repository.FeedbackRepository;
import org.home.hotelmanagementapi.service.impl.FeedbackServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private GuestService guestService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @Test
    void testCreateFeedbackSuccess() {
        FeedBackDto feedbackDto = new FeedBackDto();
        feedbackDto.setGuestEmail("test@guest.com");

        when(modelMapper.map(feedbackDto, FeedbackEntity.class)).thenReturn(new FeedbackEntity());
        when(guestService.findGuest("test@guest.com")).thenReturn(new GuestEntity());
        when(feedbackRepository.save(any())).thenReturn(new FeedbackEntity());

        ResponseEntity<String> response = feedbackService.createFeedback(feedbackDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("FeedBack created!", response.getBody());

        verify(modelMapper, times(1)).map(feedbackDto, FeedbackEntity.class);
        verify(guestService, times(1)).findGuest("test@guest.com");
        verify(feedbackRepository, times(1)).save(any());
    }


    @Test
    void testGetFeedbacksByRating() {
        int rating = 5;
        List<FeedbackEntity> feedbackEntities = new ArrayList<>();
        feedbackEntities.add(new FeedbackEntity());
        feedbackEntities.add(new FeedbackEntity());

        when(feedbackRepository.findAllByRating(rating)).thenReturn(feedbackEntities);
        when(modelMapper.map(any(), eq(FeedBackDto.class))).thenReturn(new FeedBackDto());

        ResponseEntity<List<FeedBackDto>> response = feedbackService.getFeedbacksByRating(rating);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(feedbackRepository, times(1)).findAllByRating(rating);
        verify(modelMapper, times(2)).map(any(), eq(FeedBackDto.class));
    }

    @Test
    void testDeleteAll() {
        ResponseEntity<String> response = feedbackService.deleteAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Feedbacks cleared!", response.getBody());

        verify(feedbackRepository, times(1)).deleteAll();
    }
}