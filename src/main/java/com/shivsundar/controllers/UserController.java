package com.shivsundar.controllers;

import com.shivsundar.models.db.User;
import com.shivsundar.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

   private final Logger logger = LoggerFactory.getLogger(UserController.class);

   private Integer retryCount=1;

    @PostMapping("/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User userSaved = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @GetMapping("/getUserByUserId/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
//    @RateLimiter(name = "rateLimiterService", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserByUserId(@PathVariable Long userId){
        User userByUserId = userService.getUserByUserId(userId);
        return ResponseEntity.ok(userByUserId);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    public ResponseEntity<User> ratingHotelFallback(Long userId, Exception e){
        logger.info("Fallback is call because service is down! {}",e.getMessage());
        logger.info("Retry count {}", retryCount);
        retryCount++;
        User user = User.builder()
                .userId(10000L)
                .userName("ErrorJI")
                .emailId("error@gmail.com")
                .dateOfBirth("00/00/0000")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
