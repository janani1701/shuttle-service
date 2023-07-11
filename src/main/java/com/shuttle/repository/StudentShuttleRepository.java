package com.shuttle.repository;

import com.shuttle.models.StudentShuttle;
import com.shuttle.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentShuttleRepository extends JpaRepository<StudentShuttle, Long> {

}
