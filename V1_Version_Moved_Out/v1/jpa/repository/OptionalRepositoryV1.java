package org.collegeWorks.collegeresults.v1.jpa.repository;

import org.collegeWorks.collegeresults.v1.jpa.entity.OptionalEntityV1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionalRepositoryV1 extends JpaRepository<OptionalEntityV1, Integer> {

  List<OptionalEntityV1> findByOptionalNameContainingIgnoreCase(String optionalName);

  List<OptionalEntityV1> findByOptionalNameContainingIgnoreCaseAndHasPractical(String optionalName, Boolean hasPractical);
}
