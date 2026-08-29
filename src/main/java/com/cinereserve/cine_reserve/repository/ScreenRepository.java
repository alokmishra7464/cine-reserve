package com.cinereserve.cine_reserve.repository;

import com.cinereserve.cine_reserve.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
}
