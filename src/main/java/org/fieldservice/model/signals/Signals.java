package org.fieldservice.model.signals;

import org.fieldservice.model.signal.EquipmentStatusCode;

import java.io.Serializable;

public abstract class Signals implements Serializable {

    private int _statusCodeCount;
    private EquipmentStatusCode _equipmentStatusCode;

    public Signals(int statusCodeCount,
                   String equipmentStatus) {
        _statusCodeCount = statusCodeCount;
        _equipmentStatusCode = EquipmentStatusCode.valueOf(equipmentStatus);
    }

    public int getStatusCodeCount() {
        return _statusCodeCount;
    }

    public void setStatusCodeCount(int statusCodeCount) {
        _statusCodeCount = statusCodeCount;
    }

    public EquipmentStatusCode getEquipmentStatusCode() {
        return _equipmentStatusCode;
    }

    public void setEquipmentStatusCode(EquipmentStatusCode equipmentStatusCode) {
        _equipmentStatusCode = equipmentStatusCode;
    }
}
