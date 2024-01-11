package org.home.hotelmanagementapi.repository;

import org.home.hotelmanagementapi.model.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {

    @Query("SELECT f FROM FeedbackEntity f WHERE f.rating = :rating")
    List<FeedbackEntity> findAllByRating(@Param("rating") int rating);

}
