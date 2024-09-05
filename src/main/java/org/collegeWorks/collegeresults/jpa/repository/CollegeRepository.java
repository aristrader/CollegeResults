package org.collegeWorks.collegeresults.jpa.repository;

import java.util.Optional;
import org.collegeWorks.collegeresults.jpa.entity.CollegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<CollegeEntity, Integer> {

  /**
   * Retrieves a CollegeEntity based on the provided college name and address.
   *
   * @param collegeName the name of the college
   * @param address the address of the college
   * @return an Optional containing the CollegeEntity if found, or empty if not
   */
  Optional<CollegeEntity> findByCollegeNameAndAddress(String collegeName, String address);
}
