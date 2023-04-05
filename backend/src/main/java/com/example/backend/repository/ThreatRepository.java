package com.example.backend.repository;

import com.example.backend.domain.Alert;
import com.example.backend.domain.Threat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreatRepository extends JpaRepository<Threat, Long> {

    @Query(value = "SELECT * FROM threat ORDER BY RAND() limit ?1", nativeQuery = true)
    List<Threat> findRandom(int limit);

}
