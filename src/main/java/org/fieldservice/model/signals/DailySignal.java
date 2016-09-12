package org.fieldservice.model.signals;

import java.util.Date;

public class DailySignal extends Signals {

    private Date _entryDate;

    public DailySignal(int statusCodeCount,
                       String equipmentStatus,
                       Date entryDate) {
        super(statusCodeCount, equipmentStatus);
        _entryDate = entryDate;
    }

    public Date getEntryDate() {
        return _entryDate;
    }

    public void setEntryDate(Date entryDate) {
        _entryDate = entryDate;
    }
}
