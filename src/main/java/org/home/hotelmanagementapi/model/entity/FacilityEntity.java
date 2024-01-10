package org.home.hotelmanagementapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.home.hotelmanagementapi.enumeration.FacilityTypeEnum;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "facilities")
public class FacilityEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FacilityTypeEnum type;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToMany
    private List<GuestEntity> guests;
}
