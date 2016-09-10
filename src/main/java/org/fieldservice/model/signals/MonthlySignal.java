package org.fieldservice.model.signals;

import java.time.Month;

public class MonthlySignal extends Signals {

    private Month _month;
    private int _year;

    public MonthlySignal(int statusCodeCount,
                         Long equipmentId,
                         String equipmentStatus,
                         int month,
                         short year) {
        super(statusCodeCount, equipmentId, equipmentStatus);

        //TODO: add validation to these classes
        _month = Month.of(month);
        _year = year;
    }

    public Month getMonth() {
        return _month;
    }

    public void setMonth(Month month) {
        _month = month;
    }

    public int getYear() {
        return _year;
    }

    public void setYear(short year) {
        _year = year;
    }
}
