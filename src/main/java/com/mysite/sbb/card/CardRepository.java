package com.mysite.sbb.card;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CardRepository extends JpaRepository<Card, Integer> {

    Long countByPartName(String partName);

    @Query("select "
            + "distinct q.partName "
            + "from Card q "
            )
    List<String> findDistinct();

    @Query("select "
            + "distinct q "
            + "from Card q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "where "
            + "   q.partName like %:kw% "
            + "   or q.partCode like %:kw% "
            + "   or q.serialNumber like %:kw% "
            + "   or q.compCabinet like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or u1.name like %:kw% ")
    Page<Card> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
