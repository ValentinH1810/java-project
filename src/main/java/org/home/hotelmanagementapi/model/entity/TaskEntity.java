package org.home.hotelmanagementapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class TaskEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String taskName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate deadline;

}
