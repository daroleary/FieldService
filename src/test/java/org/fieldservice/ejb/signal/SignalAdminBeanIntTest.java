package org.fieldservice.ejb.signal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.fieldservice.ejb.equipment.EquipmentRemote;
import org.fieldservice.model.equipment.Equipment;
import org.fieldservice.model.equipment.EquipmentPk;
import org.fieldservice.model.signal.EquipmentStatusCode;
import org.fieldservice.model.signal.Signal;
import org.fieldservice.model.signals.DailySignals;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class SignalAdminBeanIntTest {

    @EJB
    SignalRemote _signalAdmin;
    @EJB
    EquipmentRemote _equipmentAdmin;

    private static final String ASSET_NUMBER = "AssetNumber";
    private static final LocalDateTime ENTRY_DATE_TIME =
            LocalDateTime.of(LocalDate.of(2016, Month.JANUARY, 1), LocalTime.of(12, 0));
    private static final EquipmentStatusCode STATUS_CODE = EquipmentStatusCode.ACTIVE;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(EmbeddedGradleImporter.class)
                .forThisProjectDirectory().importBuildOutput()
                .as(WebArchive.class);
    }

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
    public void create_withDefaultInfo_returnsSignalWithSameInfo() {

        Signal signal = getSignal(createEquipment(ASSET_NUMBER));
        Signal actualSignal = _signalAdmin.find(signal.getSignalPk());

        assertTrue(actualSignal.getSignalPk().getSignalId() > 0L);
        Equipment actualEquipment = actualSignal.getEquipment();
        assertEquals(ASSET_NUMBER, actualEquipment.getAssetNumber());
        assertEquals(STATUS_CODE, actualSignal.getEquipmentStatusCode());
        assertEquals(ENTRY_DATE_TIME, actualSignal.getEntryDateTime());
    }

    @Test
    public void update_hasUpdatedEntryDateTime_returnsSignalWithNewEntryDateTime() {

        Signal signal = getSignal(createEquipment(ASSET_NUMBER));

        EquipmentStatusCode newStatusCode = EquipmentStatusCode.ENGAGED;
        signal.setEquipmentStatusCode(newStatusCode);
        _signalAdmin.edit(signal);

        Signal actualUpdatedSignal = _signalAdmin.find(signal.getPk());
        assertEquals(newStatusCode, actualUpdatedSignal.getEquipmentStatusCode());
    }

    @Test
    public void remove_signalWasCreated_returnNoSignal() {

        Signal signal = getSignal(createEquipment(ASSET_NUMBER));

        _signalAdmin.remove(signal.getPk());

        Signal removedSignal = _signalAdmin.find(signal.getPk());
        assertNull(removedSignal);
    }

    @Test
    public void getDailySignalsFrom_withEquipment_returnsDailySignals() {
        Equipment equipment = createEquipment(ASSET_NUMBER);
        createSignal(equipment);
        createSignal(equipment);

        ImmutableList<Signal> signals = getSignals(equipment.getEquipmentPk());
        assertEquals(2, signals.size());

        ImmutableList<DailySignals> dailySignals
                = _signalAdmin.getDailySignalsFrom(equipment.getEquipmentPk());
        assertEquals(1, dailySignals.size());
    }

    private Equipment createEquipment(String assetNumber) {
        Equipment equipment = new Equipment();
        equipment.setAssetNumber(assetNumber);

        _equipmentAdmin.create(equipment);
        //noinspection OptionalGetWithoutIsPresent
        return _equipmentAdmin.getEquipmentFrom(assetNumber);
    }

    private void createSignal(Equipment equipment) {
        Signal signal = new Signal();
        signal.setEquipment(equipment);
        signal.setEquipmentStatusCode(STATUS_CODE);
        signal.setEntryDateTime(ENTRY_DATE_TIME);

        _signalAdmin.create(signal);
    }

    private Signal getSignal(Equipment equipment) {

        createSignal(equipment);
        //noinspection OptionalGetWithoutIsPresent
        return Iterables.getOnlyElement(_signalAdmin.getSignalsFrom(equipment.getEquipmentPk()));
    }

    private ImmutableList<Signal> getSignals(EquipmentPk equipmentPk) {
        //noinspection OptionalGetWithoutIsPresent
        return _signalAdmin.getSignalsFrom(equipmentPk);
    }
}
