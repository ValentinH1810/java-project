package org.home.hotelmanagementapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class EventEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDateTime dateAndTime;

    @ManyToOne
    private FacilityEntity facility;

}
