package com.munna.rating.services;


import com.munna.rating.entities.Rating;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.util.List;

@Service
public interface RatingService {

    //create

    Rating create(Rating rating);

    //get ALl ratings

    List<Rating> getRatings();
    //get all by userid

    List<Rating> getRatingByUserId(String UserId);

    //get all by hotel
   List<Rating> getRatingByHotelId(String hotelId);

}
