package org.collegeWorks.collegeresults.v1.jpa.repository;

import org.collegeWorks.collegeresults.v1.jpa.entity.OptionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionalRepository extends JpaRepository<OptionalEntity, Integer> {

  List<OptionalEntity> findByOptionalNameContainingIgnoreCase(String optionalName);

  List<OptionalEntity> findByOptionalNameContainingIgnoreCaseAndHasPractical(String optionalName, Boolean hasPractical);
}
