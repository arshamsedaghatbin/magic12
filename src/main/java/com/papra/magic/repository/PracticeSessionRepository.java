package com.papra.magic.repository;

import com.papra.magic.domain.PracticeSession;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PracticeSession entity.
 */
@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {
    @Query(
        value = "select distinct practiceSession from PracticeSession practiceSession left join fetch practiceSession.actions",
        countQuery = "select count(distinct practiceSession) from PracticeSession practiceSession"
    )
    Page<PracticeSession> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct practiceSession from PracticeSession practiceSession left join fetch practiceSession.actions")
    List<PracticeSession> findAllWithEagerRelationships();

    @Query(
        "select practiceSession from PracticeSession practiceSession left join fetch practiceSession.actions where practiceSession.id =:id"
    )
    Optional<PracticeSession> findOneWithEagerRelationships(@Param("id") Long id);
}
