package com.cinereserve.cine_reserve.repository;

import com.cinereserve.cine_reserve.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
