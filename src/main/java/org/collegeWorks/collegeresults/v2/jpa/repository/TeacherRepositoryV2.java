package org.collegeWorks.collegeresults.v2.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v2.jpa.entity.TeacherEntityV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepositoryV2 extends JpaRepository<TeacherEntityV2, Integer> {

  Optional<TeacherEntityV2> findByPhoneNumber(String phoneNumber);

  Optional<TeacherEntityV2> findByEmail(String email);

  List<TeacherEntityV2> findByCollegeId(Integer collegeId);
}
