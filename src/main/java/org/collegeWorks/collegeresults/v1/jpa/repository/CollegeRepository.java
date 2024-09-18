package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.CollegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<CollegeEntity, Integer> {

  /**
   * Retrieves a CollegeEntity based on the provided college name and address.
   *
   * @param collegeName the name of the college
   * @param address the address of the college
   * @return an Optional containing the CollegeEntity if found, or empty if not
   */
  Optional<CollegeEntity> findByNameAndAddress(String collegeName, String address);

  /**
   * Retrieves a list of colleges where the College_Name contains the specified keyword, ignoring case.
   *
   * This method performs a case-insensitive search for colleges whose names
   * include the supplied substring.
   *
   * @param keyword the substring to search for within the college names
   * @return a list of CollegeEntity objects where the College_Name contains the keyword, ignoring case
   */
  List<CollegeEntity> findByNameContainingIgnoreCase(String keyword);
}
