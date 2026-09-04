package com.cinereserve.cine_reserve.repository;

import com.cinereserve.cine_reserve.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
