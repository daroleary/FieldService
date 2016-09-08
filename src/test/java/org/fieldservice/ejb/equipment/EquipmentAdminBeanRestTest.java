package org.fieldservice.ejb.equipment;

import com.google.common.collect.ImmutableList;
import org.fieldservice.model.equipment.Equipment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class EquipmentAdminBeanRestTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(EmbeddedGradleImporter.class)
                .forThisProjectDirectory().importBuildOutput()
                .as(WebArchive.class);
    }

    @EJB
    static EquipmentRemote _equipmentAdmin;

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

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @RunAsClient
    @InSequence
    public void findByAssetNumber_equipmentWasNotCreated_returns204(@ArquillianResteasyResource final WebTarget webTarget) {

        Response response = webTarget
                .path("equipments/assetNumber/AssetNumber")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildGet()
                .invoke();

        // TODO:
        // this should return 404, however the framework returns 204 (no content) which although accurate is not per
        // specification. Future work required to ensure it returns the correct code.
        assertEquals(204, response.getStatus());
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @RunAsClient
    @InSequence(1)
    public void find_equipmentWasNotCreated_returns204(@ArquillianResteasyResource final WebTarget webTarget) {

        Response response = webTarget
                .path("equipments/100000")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildGet()
                .invoke();

        assertEquals(204, response.getStatus());
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    @InSequence(2)
    public void setupFor_find_equipmentWasCreated_returnsEquipmentAsJson() {
        createEquipment(DEFAULT_ASSET_NUMBER);
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @RunAsClient
    @InSequence(3)
    public void findByAssetNumber_equipmentWasCreated_returnsEquipmentAsJson(@ArquillianResteasyResource final WebTarget webTarget) {

        Response response = webTarget
                .path("equipments/assetNumber/AssetNumber")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildGet()
                .invoke();

        assertEquals(200, response.getStatus());
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    @InSequence(4)
    public void clearDataFor_find_equipmentWasCreated_returnsEquipmentAsJson() {
        // clears data in setup method.
    }

    private Equipment createEquipment(String assetNumber) {
        Equipment equipment = new Equipment();
        equipment.setAssetNumber(assetNumber);

        _equipmentAdmin.create(equipment);
        //noinspection OptionalGetWithoutIsPresent
        return _equipmentAdmin.getEquipmentFrom(assetNumber);
    }
}
