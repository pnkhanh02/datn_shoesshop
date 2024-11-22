package com.example.shoesshop.controller;

import com.example.shoesshop.dto.FeedbackDTO;
import com.example.shoesshop.entity.Feedback;
import com.example.shoesshop.request.FeedbackRequest;
import com.example.shoesshop.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/feedbacks")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<?> getAllFeedbacks(Pageable pageable, @RequestParam(required = false) String search){
        Page<Feedback> entitiesPage = feedbackService.getAllFeedbacks(pageable, search);
        Page<FeedbackDTO> dtoPage = entitiesPage.map(new Function<Feedback, FeedbackDTO>() {
            @Override
            public FeedbackDTO apply(Feedback feedback) {
                FeedbackDTO dto = new FeedbackDTO(feedback.getId(),
                        feedback.getComment(),
                        feedback.getFeedback_date(),
                        feedback.getRating(),
                        feedback.getAccount_customer().getId(),
                        feedback.getProduct_feedback().getId());
                return dto;
            }
        });
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping(value = ("/getAll"))
    public ResponseEntity<?> getFeedback(){
        ArrayList<Feedback> listResponse = feedbackService.getAll();
        List<FeedbackDTO> listRespo = listResponse.stream()
                .map(e -> new FeedbackDTO(
                        e.getId(),
                        e.getComment(),
                        e.getFeedback_date(),
                        e.getRating(),
                        e.getAccount_customer().getId(),
                        e.getAccount_customer().getUsername(),
                        e.getProduct_feedback().getId()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listRespo, HttpStatus.OK);
    }

    @PostMapping(value = "/customer")
    public ResponseEntity<?> createFeedback(@RequestBody FeedbackRequest feedbackRequest){
        feedbackService.createFeedback(feedbackRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getFeedbackById(@PathVariable(name = "id") int id){
        Feedback feedback = feedbackService.getFeedbackById(id);
        FeedbackDTO feedbackDTO = new FeedbackDTO(
                feedback.getId(), feedback.getComment(),
                feedback.getFeedback_date(),
                feedback.getRating(),
                feedback.getAccount_customer().getId(),
                feedback.getProduct_feedback().getId()
        );
        return new ResponseEntity<FeedbackDTO>(feedbackDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable(name = "id") int id){
        feedbackService.deleteFeedback(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }


    @DeleteMapping()
    public void deleteFeedbacks(@RequestParam(name="ids") List<Integer> ids){
        feedbackService.deleteFeedbacks(ids);
    }
}
