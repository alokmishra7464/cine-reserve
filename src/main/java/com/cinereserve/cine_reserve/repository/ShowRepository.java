package com.cinereserve.cine_reserve.repository;

import com.cinereserve.cine_reserve.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    boolean existsByScreenIdAndStartTimeBeforeAndEndTimeAfter(
            Long screenId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
