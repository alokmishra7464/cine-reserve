package com.cinereserve.cine_reserve.repository;

import com.cinereserve.cine_reserve.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
