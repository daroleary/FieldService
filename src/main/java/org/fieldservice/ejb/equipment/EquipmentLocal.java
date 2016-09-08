package org.fieldservice.ejb.equipment;

import com.google.common.collect.ImmutableList;
import org.fieldservice.model.equipment.Equipment;
import org.fieldservice.model.equipment.EquipmentPk;

import javax.ejb.Local;

@Local
public interface EquipmentLocal {

    void create(Equipment equipment);

    void edit(Equipment equipment);

    void remove(EquipmentPk id);

    Equipment find(EquipmentPk id);

    ImmutableList<Equipment> getAll();

    Equipment getEquipmentFrom(String assetNumber);
}
