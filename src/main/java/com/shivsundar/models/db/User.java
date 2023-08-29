package com.shivsundar.models.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_details")
public class User {
    @Id
    private Long userId;

    @JsonProperty("username")
    private String userName;

    private String emailId;

    @JsonProperty("dob")
    private String dateOfBirth;

    @Transient
    private List<Ratings> ratingsList;
}
