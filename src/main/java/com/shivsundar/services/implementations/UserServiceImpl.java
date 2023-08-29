package com.shivsundar.services.implementations;

import com.shivsundar.exceptions.ResultNotFoundException;
import com.shivsundar.models.db.Hotel;
import com.shivsundar.models.db.Ratings;
import com.shivsundar.models.db.User;
import com.shivsundar.repositories.UserRepository;
import com.shivsundar.services.UserService;
import com.shivsundar.services.external.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            Ratings[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/findRatingsByUserId/" + user.getUserId(), Ratings[].class);
            List<Ratings> ratingsList = Arrays.stream(ratings).toList();
            List<Ratings> ratingsListFinal = ratingsList.stream().map(rating -> {
                rating.setHotel(restTemplate.getForObject("http://HOTEL-SERVICE/hotels/getHotel/" + rating.getHotelId(), Hotel.class));
                return rating;
            }).toList();
            user.setRatingsList(ratingsListFinal);
            return user;
        }).toList();

    }

    @Override
    public User getUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(ResultNotFoundException::new);
        Ratings[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/findRatingsByUserId/" + user.getUserId(), Ratings[].class);
        List<Ratings> ratingsList = Arrays.stream(ratings).toList();
        List<Ratings> ratingsListFinal = ratingsList.stream().map(rating -> {
//            rating.setHotel(restTemplate.getForObject("http://HOTEL-SERVICE/hotels/getHotel/" + rating.getHotelId(), Hotel.class));
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).toList();
        user.setRatingsList(ratingsListFinal);
        return user;
    }

}
