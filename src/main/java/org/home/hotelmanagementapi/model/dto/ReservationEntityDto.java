package org.home.hotelmanagementapi.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntityDto {

    @NotBlank
    private Long id;

    @NotNull
    private String guestEmail;

    @NotNull
    private Long roomId;

    @Column(nullable = false)
    @NotBlank
    private LocalDate checkingDay;

    @Size(min = 1, max = 10)
    @Column(nullable = false)
    @NotBlank
    private int stayingDuration;
}