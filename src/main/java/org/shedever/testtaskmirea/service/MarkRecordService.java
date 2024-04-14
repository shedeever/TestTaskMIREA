package org.shedever.testtaskmirea.service;

import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.repository.MarkRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MarkRecordService {
    @Autowired
    private MarkRecordRepository markRecordRepository;

    public void deleteMarkByMarkId(Long id) {
        markRecordRepository.deleteById(id);
    }

    public MarkRecord findMarkByStudyObject(StudyObject studyObject) {
        return markRecordRepository.findMarkRecordByStudyObject(studyObject);
    }
}
