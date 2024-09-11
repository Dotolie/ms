package com.mysite.sbb.scenario;

import com.mysite.sbb.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    @Query("select "
            + "distinct q "
            + "from Scenario q "
            + "where "
            + "   q.orgNm like %:kw% " )
    Page<Scenario> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
