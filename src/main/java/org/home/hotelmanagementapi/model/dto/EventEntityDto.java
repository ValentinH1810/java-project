package org.home.hotelmanagementapi.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventEntityDto {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDateTime dateAndTime;

    @NotEmpty
    private String facilityName;

}
