package org.home.hotelmanagementapi.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackDto {

    @NotEmpty
    private String guestEmail;

    @Column(nullable = false)
    @Size(min = 1, max = 5)
    private int rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;


}
