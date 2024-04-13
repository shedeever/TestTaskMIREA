package org.shedever.testtaskmirea.service;

import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.repository.MarkRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MarkRecordService {
    @Autowired
    private MarkRecordRepository markRecordRepository;

    public void saveMark(MarkRecord markRecord) {
        markRecordRepository.save(markRecord);
    }

    public void deleteMarkById(Long id) {
        markRecordRepository.deleteById(id);
    }
}
