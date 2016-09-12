package org.fieldservice.model.equipment;

import org.fieldservice.model.RootEntity;
import org.fieldservice.model.signal.Signal;
import org.fieldservice.model.signals.EquipmentSimple;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@Entity
@Table(name = "equipment")
@NamedQueries({
        @NamedQuery(name = Equipment.FIND_BY_ASSET_NUMBER,
                query = "SELECT e FROM Equipment e " +
                        "WHERE e._assetNumber = :assetNumber")
})
@SqlResultSetMapping(
        name = Equipment.ALL_EQUIPMENT_SIMPLE,
        classes = {
                @ConstructorResult(
                        targetClass = EquipmentSimple.class,
                        columns = {
                                @ColumnResult(name = "equipment_id", type = Long.class),
                                @ColumnResult(name = "asset_number", type = String.class)
                        }
                )
        }
)
public class Equipment implements RootEntity<EquipmentPk> {

    public static final String FIND_BY_ASSET_NUMBER = "Equipment.findByAssetNumber";
    public static final String ALL_EQUIPMENT_SIMPLE = "Equipment.findByAssetNumber";


    @EmbeddedId
    private EquipmentPk _equipmentPk;

    @Column(name = "asset_number", nullable = false)
    private String _assetNumber;

    public EquipmentPk getEquipmentPk() {
        return _equipmentPk;
    }

    public void setEquipmentPk(EquipmentPk id) {
        _equipmentPk = id;
    }

    public String getAssetNumber() {
        return _assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        _assetNumber = assetNumber;
    }

    @Override
    public EquipmentPk getPk() {
        return _equipmentPk;
    }

    @PrePersist
    public void initializePk() {
        if (_equipmentPk == null) {
            _equipmentPk = new EquipmentPk();
        }
    }
}
