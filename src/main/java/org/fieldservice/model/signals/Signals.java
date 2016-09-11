package org.fieldservice.model.signals;

import org.fieldservice.model.signal.EquipmentStatusCode;

import java.io.Serializable;

public abstract class Signals implements Serializable {

    private int _statusCodeCount;
    private Long _equipmentId;
    private EquipmentStatusCode _equipmentStatusCode;

    public Signals(int statusCodeCount,
                   Long equipmentId,
                   String equipmentStatus) {
        _statusCodeCount = statusCodeCount;
        _equipmentId = equipmentId;
        _equipmentStatusCode = EquipmentStatusCode.valueOf(equipmentStatus);
    }

    public int getStatusCodeCount() {
        return _statusCodeCount;
    }

    public void setStatusCodeCount(int statusCodeCount) {
        _statusCodeCount = statusCodeCount;
    }

    public Long getEquipmentId() {
        return _equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        _equipmentId = equipmentId;
    }

    public EquipmentStatusCode getEquipmentStatusCode() {
        return _equipmentStatusCode;
    }

    public void setEquipmentStatusCode(EquipmentStatusCode equipmentStatusCode) {
        _equipmentStatusCode = equipmentStatusCode;
    }
}
