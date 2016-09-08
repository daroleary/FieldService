package org.fieldservice.ejb.equipment;

import com.google.common.collect.ImmutableList;
import org.fieldservice.ejb.EJB3Adapter;
import org.fieldservice.model.equipment.Equipment;
import org.fieldservice.model.equipment.EquipmentPk;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class EquipmentAdminBean extends EJB3Adapter<EquipmentPk, Equipment> implements EquipmentLocal, EquipmentRemote {

    public EquipmentAdminBean() {
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
}
