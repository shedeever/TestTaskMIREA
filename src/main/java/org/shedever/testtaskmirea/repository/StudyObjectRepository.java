package org.shedever.testtaskmirea.repository;

import org.shedever.testtaskmirea.entity.StudyObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyObjectRepository extends JpaRepository<StudyObject, Long> {
    StudyObject findStudyObjectByName(String Name);
}
