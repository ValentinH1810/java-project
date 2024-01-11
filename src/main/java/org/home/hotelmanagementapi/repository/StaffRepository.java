package org.home.hotelmanagementapi.repository;

import org.home.hotelmanagementapi.model.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long> {

    Optional<StaffEntity> findByPhoneNumber(String phone);

}
