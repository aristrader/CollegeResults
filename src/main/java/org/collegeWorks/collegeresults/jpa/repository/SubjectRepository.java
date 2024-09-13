package org.collegeWorks.collegeresults.jpa.repository;

import org.collegeWorks.collegeresults.jpa.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

  SubjectEntity findByType(String type);
}

