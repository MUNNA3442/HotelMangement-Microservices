package com.techmunna.user.service.controllers;

import com.techmunna.user.service.Entities.User;
import com.techmunna.user.service.impl.UserServiceImpl;
import com.techmunna.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    private Logger logger =  LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
     User user1=userService.saveUser(user);
     return  ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    int retryCount=1;
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
   // @Retry(name = "ratingHotelService",fallbackMethod="ratingHotelFallback")
    @RateLimiter(name = "UserRateLimiter",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
       logger.info("Get singe user Handler: UserController");
        logger.info("retry count: {}",retryCount);
        retryCount++;
       User user = userService.getUser(userId);
       return  ResponseEntity.ok(user);
    }

    //creating a fallback method for circuit breaker

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
//       logger.info("Fallback is executed because service is down : ",ex.getMessage());

       User user= User.builder()
               .email("dummy@gmail.com")
               .name("Dummy")
               .about("This user is created dummy because some service is down")
               .userId("141234")
               .build();
       return new ResponseEntity<>(user,HttpStatus.OK);

    }

    //all user get
    @GetMapping
    public ResponseEntity<List<User>>getAllUser(){
        List<User> allUser= userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

}
