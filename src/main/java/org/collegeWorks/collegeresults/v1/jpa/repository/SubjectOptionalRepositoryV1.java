package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.SubjectOptionalEntityV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectOptionalRepositoryV1 extends JpaRepository<SubjectOptionalEntityV1, Integer> {

  List<SubjectOptionalEntityV1> findBySubjectId(Integer subjectId);

  List<SubjectOptionalEntityV1> findByOptionalId(Integer optionalId);

  Optional<SubjectOptionalEntityV1> findBySubjectIdAndOptionalId(Integer subjectId,
      Integer optionalId);

  @Query("SELECT o.hasPractical FROM SubjectOptionalEntityV1 s " +
      "JOIN OptionalEntityV1 o ON s.optionalId = o.id " +
      "WHERE s.id = :subjectOptionalId")
  Optional<Boolean> findHasPracticalBySubjectOptionalId(@Param("subjectOptionalId") Integer subjectOptionalId);
}

