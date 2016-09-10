package org.fieldservice.model.equipment;

import org.fieldservice.model.LongPk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Objects;

@Embeddable
public class EquipmentPk implements LongPk {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="equipment_id", nullable = false)
    private Long _equipmentId;

    public EquipmentPk() {
    }

    public EquipmentPk(Long id) {
        _equipmentId = id;
    }

    public static EquipmentPk of(Long id) {
        return new EquipmentPk(id);
    }

    public static EquipmentPk getPKorNull(Long id) {
        return id == null ? null : of(id);
    }

    public Long getEquipmentId() {
        return _equipmentId;
    }

    public void setEquipmentId(Long id) {
        _equipmentId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentPk that = (EquipmentPk) o;
        return Objects.equals(_equipmentId, that._equipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_equipmentId);
    }
}
