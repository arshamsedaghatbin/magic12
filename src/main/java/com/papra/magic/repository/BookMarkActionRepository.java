package com.papra.magic.repository;

import com.papra.magic.domain.BookMarkAction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BookMarkAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookMarkActionRepository extends JpaRepository<BookMarkAction, Long> {}
