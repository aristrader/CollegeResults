package org.collegeWorks.collegeresults.v2.jpa.repository;

import java.util.List;
import org.collegeWorks.collegeresults.v2.jpa.entity.CollegeEntityV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepositoryV2 extends JpaRepository<CollegeEntityV2, Integer> {

  /**
   * Retrieves a list of colleges where the College_Name contains the specified keyword, ignoring
   * case.
   *
   * This method performs a case-insensitive search for colleges whose names include the supplied
   * substring.
   *
   * @param name the substring to search for within the college names
   * @return a list of CollegeEntityV1 objects where the College_Name contains the keyword, ignoring
   * case
   */
  List<CollegeEntityV2> findByNameContainingIgnoreCase(String name);
}
