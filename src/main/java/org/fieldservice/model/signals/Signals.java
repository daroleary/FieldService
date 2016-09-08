package org.fieldservice.model.signals;

import org.fieldservice.model.equipment.EquipmentPk;
import org.fieldservice.model.signal.EquipmentStatusCode;

import java.io.Serializable;

public abstract class Signals implements Serializable {

    private int _statusCodeCount;
    private EquipmentPk _equipmentPk;
    private EquipmentStatusCode _equipmentStatusCode;

    public Signals(int statusCodeCount,
                   Long equipmentId,
                   String equipmentStatus) {
        _statusCodeCount = statusCodeCount;
        _equipmentPk = EquipmentPk.of(equipmentId);
        _equipmentStatusCode = EquipmentStatusCode.valueOf(equipmentStatus);
    }

    public int getStatusCodeCount() {
        return _statusCodeCount;
    }

    public void setStatusCodeCount(int statusCodeCount) {
        _statusCodeCount = statusCodeCount;
    }

    public EquipmentPk getEquipment() {
        return _equipmentPk;
    }

    public void setEquipmentPk(EquipmentPk equipmentPk) {
        _equipmentPk = equipmentPk;
    }

    public EquipmentStatusCode getEquipmentStatusCode() {
        return _equipmentStatusCode;
    }

    public void setEquipmentStatusCode(EquipmentStatusCode equipmentStatusCode) {
        _equipmentStatusCode = equipmentStatusCode;
    }
}
