package org.collegeWorks.collegeresults.v1.jpa.repository;

import org.collegeWorks.collegeresults.v1.jpa.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

  SubjectEntity findByType(String type);
}

