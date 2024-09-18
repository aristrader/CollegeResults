package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<SemesterEntity, Integer> {

  Optional<SemesterEntity> findByNumber(int number);
}
