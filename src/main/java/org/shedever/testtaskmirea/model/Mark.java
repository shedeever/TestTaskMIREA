package org.shedever.testtaskmirea.model;

public enum Mark {
    Great(5), Well(4), Good(3), Bad(2), DidntShow(0);

    private final int mark;
    Mark(int mark) {
        this.mark = mark;
    }

    public int getValue() {
        return mark;
    }
}
