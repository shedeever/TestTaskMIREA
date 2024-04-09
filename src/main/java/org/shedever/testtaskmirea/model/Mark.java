package org.shedever.testtaskmirea.model;


public enum Mark {
    Great(5, "Отлично"),
    Well(4, "Хорошо"),
    Good(3, "Удовлетворительно"),
    Bad(2, "Неудовлетворительно"),
    DidntShow(0, "Не явился");

    private final int mark;
    private final String strvalue;

    Mark(int mark, String strvalue) {
        this.mark = mark;
        this.strvalue = strvalue;
    }

    public int getValue() {
        return mark;
    }

    public String getStrvalue() {
        return strvalue;
    }
}
