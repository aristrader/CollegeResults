package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository  extends JpaRepository<CourseEntity, Integer> {
  List<CourseEntity> findByNameContainingIgnoreCase(String name);

  Optional<CourseEntity> findByNameAndLength(String name, int length);
}
