package org.home.hotelmanagementapi.repository;

import org.home.hotelmanagementapi.model.entity.FacilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityEntity, Long> {

    Optional<FacilityEntity> findByName(String name);
}
