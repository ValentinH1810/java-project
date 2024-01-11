package org.home.hotelmanagementapi.repository;

import org.home.hotelmanagementapi.model.entity.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
    Optional<GuestEntity> findByEmail(String email);

    @Modifying
    @Query("UPDATE GuestEntity g SET g.phoneNumber = :newNumber WHERE g.id = :id")
    void updatePhone(Long id, String newNumber);
}
