package org.home.hotelmanagementapi.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.home.hotelmanagementapi.enumeration.FacilityTypeEnum;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacilityEntityDto {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FacilityTypeEnum type;

    @Column(nullable = false)
    private BigDecimal price;

}
