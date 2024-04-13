package org.shedever.testtaskmirea.service;

import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.repository.StudyObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyObjectService {
    @Autowired
    private StudyObjectRepository studyObjectRepository;

    public void saveStudyObject(StudyObject studyObject) {
        studyObjectRepository.save(studyObject);
    }

    public StudyObject getStudyObject(Long id) {
        Optional<StudyObject> studyObject = studyObjectRepository.findById(id);
        return studyObject.orElse(null);
    }

    public void deleteStudyObject(Long id) {
        studyObjectRepository.deleteById(id);
    }
}
