package com.munna.hotel.services;

import com.munna.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
   Hotel create(Hotel hotel);
    //getall
  List<Hotel> getAll();

    //get single

    Hotel get(String id);
}
