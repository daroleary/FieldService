package org.fieldservice.ejb.equipment;

import com.google.common.collect.ImmutableList;
import org.fieldservice.model.equipment.Equipment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Please note a lot of the test classes have a lot of duplication.
 * More work is required in order to decouple, main issue is the lack of DI
 * and class path issues.
 *
 * Both of which can be resolved with more time.
 */
@RunWith(Arquillian.class)
public class EquipmentAdminBeanIntTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(EmbeddedGradleImporter.class)
                .forThisProjectDirectory().importBuildOutput()
                .as(WebArchive.class);
    }

    @EJB
    EquipmentRemote _equipmentAdmin;

    private static final String DEFAULT_ASSET_NUMBER = "AssetNumber";

    @Before
    public void setup() {
        if (_equipmentAdmin != null) {
            ImmutableList<Equipment> equipments = _equipmentAdmin.getAll();
            for (Equipment equipment : equipments) {
                _equipmentAdmin.remove(equipment.getPk());
            }
        }
    }

    @Test
    public void create_hasAssetNumber_returnsEquipmentWithSameAssetNumber() {

        Equipment equipment = createEquipment(DEFAULT_ASSET_NUMBER);
        Equipment actualEquipment = _equipmentAdmin.find(equipment.getEquipmentPk());

        assertEquals(DEFAULT_ASSET_NUMBER, actualEquipment.getAssetNumber());
        assertTrue(actualEquipment.getEquipmentPk().getEquipmentId() > 0L);
    }

    @Test
    public void update_hasUpdatedAssetNumber_returnsEquipmentWithNewAssetNumber() {

        Equipment equipment = createEquipment(DEFAULT_ASSET_NUMBER);
        assertEquals(DEFAULT_ASSET_NUMBER, equipment.getAssetNumber());

        String newAssetNumber = "NewAssetNumber";
        equipment.setAssetNumber(newAssetNumber);
        _equipmentAdmin.edit(equipment);

        Equipment actualUpdatedEquipment = _equipmentAdmin.find(equipment.getPk());
        assertEquals(newAssetNumber, actualUpdatedEquipment.getAssetNumber());
    }

    @Test
    public void remove_equipmentWasCreated_returnsNoEquipment() {

        Equipment actualEquipment = createEquipment(DEFAULT_ASSET_NUMBER);

        _equipmentAdmin.remove(actualEquipment.getPk());
        Equipment equipment = _equipmentAdmin.find(actualEquipment.getPk());
        assertNull(equipment);
    }

    private Equipment createEquipment(String assetNumber) {
        Equipment equipment = new Equipment();
        equipment.setAssetNumber(assetNumber);

        _equipmentAdmin.create(equipment);
        //noinspection OptionalGetWithoutIsPresent
        return _equipmentAdmin.getEquipmentFrom(assetNumber);
    }
}
