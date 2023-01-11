package com.munna.hotel.servicesImpl;

import com.munna.hotel.entities.Hotel;
import com.munna.hotel.exception.ResourceNotFoundException;
import com.munna.hotel.repositories.HotelRepository;
import com.munna.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public Hotel create(Hotel hotel) {
      String hotelId =  UUID.randomUUID().toString();
      hotel.setId(hotelId);
      return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("hotel with given id not found !!"));
    }
}
