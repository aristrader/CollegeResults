package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.SubjectOptionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectOptionalRepository extends JpaRepository<SubjectOptionalEntity, Integer> {

  List<SubjectOptionalEntity> findBySubjectId(Integer subjectId);

  List<SubjectOptionalEntity> findByOptionalId(Integer optionalId);

  Optional<SubjectOptionalEntity> findBySubjectIdAndOptionalId(Integer subjectId,
      Integer optionalId);

  @Query("SELECT o.hasPractical FROM SubjectOptionalEntity s " +
      "JOIN OptionalEntity o ON s.optionalId = o.id " +
      "WHERE s.id = :subjectOptionalId")
  Optional<Boolean> findHasPracticalBySubjectOptionalId(@Param("subjectOptionalId") Integer subjectOptionalId);
}

