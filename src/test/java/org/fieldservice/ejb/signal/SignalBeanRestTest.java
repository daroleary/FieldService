package org.fieldservice.ejb.signal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.fieldservice.ejb.equipment.EquipmentRemote;
import org.fieldservice.model.equipment.Equipment;
import org.fieldservice.model.signal.EquipmentStatusCode;
import org.fieldservice.model.signal.Signal;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class SignalBeanRestTest {

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

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @RunAsClient
    @InSequence
    public void findBySignalId_signalWasNotCreated_returns204(@ArquillianResteasyResource final WebTarget webTarget) {

        Response response = webTarget
                .path("signals/1000")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildGet()
                .invoke();

        assertEquals(204, response.getStatus());
    }

    @Ignore
    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @RunAsClient
    @InSequence(2)
    public void findByAssetNumber_equipmentWasCreated_returnsEquipmentAsJson(@ArquillianResteasyResource final WebTarget webTarget) {

        //TODO: improve framework so we can test perform accurate client testing based on ID's
    }

    private Equipment createEquipment(String assetNumber) {
        Equipment equipment = new Equipment();
        equipment.setAssetNumber(assetNumber);

        _equipmentAdmin.create(equipment);
        //noinspection OptionalGetWithoutIsPresent
        return _equipmentAdmin.getEquipmentFrom(assetNumber);
    }

    private Signal createSignal() {

        Equipment equipment = createEquipment(ASSET_NUMBER);

        Signal signal = new Signal();
        signal.setEquipment(equipment);
        signal.setEquipmentStatusCode(STATUS_CODE);
        signal.setEntryDateTime(ENTRY_DATE_TIME);

        _signalAdmin.create(signal);
        //noinspection OptionalGetWithoutIsPresent
        return Iterables.getOnlyElement(_signalAdmin.getSignalsFrom(equipment.getEquipmentPk()));
    }
}
