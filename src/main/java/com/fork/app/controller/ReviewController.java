package com.fork.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

//    @GetMapping("/api/review")
//    public ResponseEntity<?> createReview(){
//        return ResponseEntity.ok(Map.of("result", "success"));
//    }
//
//    @GetMapping("/api/{userId}/reviews")
//    public ResponseEntity<?> getReviews(){
//        return ResponseEntity.ok(Map.of("result", "success"));
//    }
}
