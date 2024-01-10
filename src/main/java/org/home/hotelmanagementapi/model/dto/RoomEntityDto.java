package org.home.hotelmanagementapi.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.home.hotelmanagementapi.enumeration.RoomStatusEnum;
import org.home.hotelmanagementapi.enumeration.RoomTypeEnum;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntityDto {

    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomTypeEnum type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatusEnum status;

    @Column(nullable = false)
    private BigDecimal pricePerDay;

}