package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.CourseEntityV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepositoryV1 extends JpaRepository<CourseEntityV1, Integer> {
  List<CourseEntityV1> findByNameContainingIgnoreCase(String name);

  Optional<CourseEntityV1> findByNameAndLength(String name, int length);
}
