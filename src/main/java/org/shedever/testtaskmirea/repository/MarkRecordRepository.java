package org.shedever.testtaskmirea.repository;

import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRecordRepository extends JpaRepository<MarkRecord, Long> {
}
