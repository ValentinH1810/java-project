package org.home.hotelmanagementapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class FeedbackEntity extends BaseEntity{

    @ManyToOne
    private GuestEntity guest;

    @Column(nullable = false)
    @Size(min = 1, max = 5)
    private int rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

}
