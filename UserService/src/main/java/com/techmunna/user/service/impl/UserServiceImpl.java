package com.techmunna.user.service.impl;

import com.techmunna.user.service.Entities.Hotel;
import com.techmunna.user.service.Entities.Rating;
import com.techmunna.user.service.Entities.User;
import com.techmunna.user.service.exception.ResourceNotFoundException;
import com.techmunna.user.service.external.services.HotelService;
import com.techmunna.user.service.repositiories.UserRepository;
import com.techmunna.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {
        //generate
       String randomUserId= UUID.randomUUID().toString();
       user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    //get single user

    @Override
    public User getUser(String userId) {
       //get user from database with thr help of user repository
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on server !! :"+userId));
        //fetch rating of the above user from RATING-SERVICE
       //http://localhost:8095/ratings/users/429dcfba-970c-41e7-bc6e-519f812ed571
       Rating[] ratingsOfUser= restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(),Rating[].class);
       logger.info("{} ",ratingsOfUser);
       List<Rating> ratings= Arrays.stream(ratingsOfUser).toList();

       List<Rating>ratingList= ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
      //http://localhost:8080/hotels/5ac4b0d7-0811-4da3-8cdf-e0d654ffa84b

//         ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
          Hotel hotel= hotelService.getHotel(rating.getHotelId());
//          logger.info("response status code: {} ",forEntity.getStatusCode());
           //set the hotel to ratings
           rating.setHotel(hotel);
           //return the rating
           return rating;
       }).collect(Collectors.toList());


       user.setRatings(ratingList);

        return user;
    }
}
