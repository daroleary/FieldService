package org.fieldservice.model.signals;

public class YearlySignals extends MonthlySignals {

    private int _year;

    public YearlySignals(int statusCodeCount,
                         Long equipmentId,
                         String equipmentStatus,
                         int month,
                         int year) {
        super(statusCodeCount, equipmentId, equipmentStatus, month);
        _year = year;
    }

    public int getYear() {
        return _year;
    }

    public void set_year(int year) {
        _year = year;
    }
}
