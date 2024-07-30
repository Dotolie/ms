package com.mysite.sbb.user;

import java.util.Optional;

import com.mysite.sbb.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	Optional<SiteUser> findByusername(String username);

	@Query("select "
			+ "distinct q "
			+ "from SiteUser q "
			+ "where "
			+ "   q.username like %:kw% " )
	Page<SiteUser> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
