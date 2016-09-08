package org.fieldservice.model.signals;

import java.time.Month;

public class MonthlySignals extends Signals {

    private Month _month;

    public MonthlySignals(int statusCodeCount,
                          Long equipmentId,
                          String equipmentStatus,
                          int month) {
        super(statusCodeCount, equipmentId, equipmentStatus);
        _month = Month.of(month);
    }

    public Month getMonth() {
        return _month;
    }

    public void setMonth(Month month) {
        _month = month;
    }
}
