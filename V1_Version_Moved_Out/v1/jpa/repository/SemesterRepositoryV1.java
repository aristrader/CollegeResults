package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.SemesterEntityV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepositoryV1 extends JpaRepository<SemesterEntityV1, Integer> {

  Optional<SemesterEntityV1> findByNumber(int number);
}
