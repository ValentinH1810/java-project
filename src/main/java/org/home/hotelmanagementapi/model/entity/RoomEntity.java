package org.home.hotelmanagementapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.home.hotelmanagementapi.enumeration.RoomStatusEnum;
import org.home.hotelmanagementapi.enumeration.RoomTypeEnum;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class RoomEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomTypeEnum type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatusEnum status;

    @Column(nullable = false)
    private BigDecimal pricePerDay;
}