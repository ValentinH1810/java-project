package org.home.hotelmanagementapi.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.home.hotelmanagementapi.enumeration.StaffPositionEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffEntityDto {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits long")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private StaffPositionEnum position;

}
