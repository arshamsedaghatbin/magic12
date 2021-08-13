package com.papra.magic.repository;

import com.papra.magic.domain.SubCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubCategory entity.
 */
@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query(
        value = "select distinct subCategory from SubCategory subCategory left join fetch subCategory.actions",
        countQuery = "select count(distinct subCategory) from SubCategory subCategory"
    )
    Page<SubCategory> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct subCategory from SubCategory subCategory left join fetch subCategory.actions")
    List<SubCategory> findAllWithEagerRelationships();

    @Query("select subCategory from SubCategory subCategory left join fetch subCategory.actions where subCategory.id =:id")
    Optional<SubCategory> findOneWithEagerRelationships(@Param("id") Long id);
}
