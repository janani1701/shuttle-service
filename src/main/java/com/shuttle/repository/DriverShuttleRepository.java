package com.shuttle.repository;

import com.shuttle.models.DriverShuttle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverShuttleRepository extends JpaRepository<DriverShuttle, Long> {
    public DriverShuttle findTopByOrderByIdDesc();
}
