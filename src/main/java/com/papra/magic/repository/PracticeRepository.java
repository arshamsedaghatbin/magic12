package com.papra.magic.repository;

import com.papra.magic.domain.Practice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Practice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PracticeRepository extends JpaRepository<Practice, Long> {}
