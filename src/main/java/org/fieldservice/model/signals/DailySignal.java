package org.fieldservice.model.signals;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class DailySignal extends Signals {

    private LocalDate _entryDate;

    public DailySignal(int statusCodeCount,
                       Long equipmentId,
                       String equipmentStatus,
                       Date entryDate) {
        super(statusCodeCount, equipmentId, equipmentStatus);
        _entryDate = ((Timestamp) entryDate).toLocalDateTime().toLocalDate();
    }

    public LocalDate getEntryDate() {
        return _entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        _entryDate = entryDate;
    }
}
