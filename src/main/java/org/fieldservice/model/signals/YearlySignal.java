package org.fieldservice.model.signals;

public class YearlySignal extends Signals {

    private int _year;

    public YearlySignal(int statusCodeCount,
                        String equipmentStatus,
                        short year) {
        super(statusCodeCount, equipmentStatus);
        _year = year;
    }

    public int getYear() {
        return _year;
    }

    public void setYear(int year) {
        _year = year;
    }
}
