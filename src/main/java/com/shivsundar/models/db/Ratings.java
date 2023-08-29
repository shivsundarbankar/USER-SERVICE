package com.shivsundar.models.db;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ratings {
    private Long ratingId;
    private Long userId;
    private Long hotelId;
    private Integer rating;
    private Hotel hotel;
}
