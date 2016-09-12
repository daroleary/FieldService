package org.fieldservice.model.signals;

import java.io.Serializable;

public class EquipmentSimple implements Serializable {

    private Long _equipmentId;
    private String _assetNumber;

    public EquipmentSimple(Long equipmentId,
                           String assetNumber) {
        _equipmentId = equipmentId;
        _assetNumber = assetNumber;
    }

    public Long getEquipmentId() {
        return _equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        _equipmentId = equipmentId;
    }

    public String getAssetNumber() {
        return _assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        _assetNumber = assetNumber;
    }
}
