package org.shedever.testtaskmirea.repository;

import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MarkRecordRepository extends JpaRepository<MarkRecord, Long> {
    MarkRecord findMarkRecordByStudyObject(StudyObject studyObject);
}
