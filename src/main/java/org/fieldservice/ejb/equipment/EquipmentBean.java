package org.fieldservice.ejb.equipment;

import com.google.common.collect.ImmutableList;
import org.fieldservice.ejb.EJB3Adapter;
import org.fieldservice.model.equipment.Equipment;
import org.fieldservice.model.equipment.EquipmentPk;
import org.fieldservice.model.signals.EquipmentSimple;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class EquipmentBean extends EJB3Adapter<EquipmentPk, Equipment> implements EquipmentLocal, EquipmentRemote {

    public EquipmentBean() {
        super(Equipment.class);
    }

    @Override
    public void create(Equipment equipment) {
        super.create(equipment);
    }

    @Override
    public void edit(Equipment equipment) {
        super.edit(equipment);
    }

    @Override
    public void remove(EquipmentPk id) {
        super.remove(find(id));
    }

    @Override
    public Equipment find(Long id) {
        return find(EquipmentPk.of(id));
    }

    @Override
    public Equipment find(EquipmentPk id) {
        return super.find(id);
    }

    @Override
    public ImmutableList<Equipment> getAll() {
        return super.getAll();
    }

    @Override
    public Equipment getEquipmentFrom(String assetNumber) {
        Query query = _em.createNamedQuery(Equipment.FIND_BY_ASSET_NUMBER);
        query.setParameter("assetNumber", assetNumber);

        return getSingleResult(query);
    }

    @Override
    public ImmutableList<EquipmentSimple> getAllEquipmentSimple() {
        String sqlString = "SELECT\n" +
                "  equipment.equipment_id,\n" +
                "  equipment.asset_number\n" +
                "FROM equipment";

        Query query =
                _em.createNativeQuery(sqlString, Equipment.ALL_EQUIPMENT_SIMPLE);

        //noinspection unchecked
        List<EquipmentSimple> signals = (List<EquipmentSimple>) query.getResultList();
        return ImmutableList.copyOf(signals);
    }
}
