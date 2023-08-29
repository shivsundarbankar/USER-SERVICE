package com.shivsundar.services.external;

import com.shivsundar.models.db.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("hotels/getHotel/{hotelId}")
    Hotel getHotel(@PathVariable Long hotelId);
}
