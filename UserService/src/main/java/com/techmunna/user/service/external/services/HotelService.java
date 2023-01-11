package com.techmunna.user.service.external.services;


import com.techmunna.user.service.Entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/hotels/{hotelId}")
     Hotel getHotel( @PathVariable("hotelId") String hotelId);
}
