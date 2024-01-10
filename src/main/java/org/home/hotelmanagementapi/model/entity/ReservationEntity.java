package org.home.hotelmanagementapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class ReservationEntity extends BaseEntity{

    @ManyToOne
    private GuestEntity guest;

    @OneToOne
    private RoomEntity room;

    @Column(nullable = false)
    @NotBlank
    private LocalDate checkingDay;

    @Size(min = 1, max = 10)
    @Column(nullable = false)
    @NotBlank
    private int stayingDuration;
}