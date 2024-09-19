package org.collegeWorks.collegeresults.v1.jpa.repository;

import org.collegeWorks.collegeresults.v1.jpa.entity.SubjectEntityV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepositoryV1 extends JpaRepository<SubjectEntityV1, Integer> {

  SubjectEntityV1 findByType(String type);
}

