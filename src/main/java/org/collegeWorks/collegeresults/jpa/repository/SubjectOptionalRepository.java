package org.collegeWorks.collegeresults.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.jpa.entity.SubjectOptionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectOptionalRepository extends JpaRepository<SubjectOptionalEntity, Integer> {

  List<SubjectOptionalEntity> findBySubjectId(Integer subjectId);

  List<SubjectOptionalEntity> findByOptionalId(Integer optionalId);

  Optional<SubjectOptionalEntity> findBySubjectIdAndOptionalId(Integer subjectId,
      Integer optionalId);
}

