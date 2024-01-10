package org.home.hotelmanagementapi.model.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskEntityDto {

    @Column(nullable = false, unique = true)
    private String taskName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate deadline;

}
