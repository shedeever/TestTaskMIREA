package org.shedever.testtaskmirea.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Mark {
    Great(5, "Отлично"),
    Well(4, "Хорошо"),
    Good(3, "Удовлетворительно"),
    Bad(2, "Неудовлетворительно"),
    DidntShow(0, "Не явился");

    private final int value;
    private final String description;

    Mark(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
