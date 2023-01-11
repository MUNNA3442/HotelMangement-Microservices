package com.munna.hotel.repositories;

import com.munna.hotel.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface HotelRepository  extends JpaRepository<Hotel, String> {

}
