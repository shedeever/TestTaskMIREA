package org.shedever.testtaskmirea.dto;

import lombok.Data;
import org.shedever.testtaskmirea.model.Mark;

@Data
public class MarkRecordDto {
    private Long studyObjectId;
    private Long studentId;
    private int term;
    private Mark mark;
}
