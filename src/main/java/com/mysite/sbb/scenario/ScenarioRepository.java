package com.mysite.sbb.scenario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}
