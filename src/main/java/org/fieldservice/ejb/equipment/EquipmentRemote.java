package org.fieldservice.ejb.equipment;

import com.google.common.collect.ImmutableList;
import org.fieldservice.model.equipment.Equipment;
import org.fieldservice.model.equipment.EquipmentPk;

import javax.ejb.Remote;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Remote
@Path("/equipments")
public interface EquipmentRemote {

    void create(Equipment equipment);

    void edit(Equipment equipment);

    void remove(EquipmentPk id);

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    Equipment find(@PathParam("id") Long id);

    Equipment find(EquipmentPk id);

    @GET
    @Produces({"application/json"})
    ImmutableList<Equipment> getAll();

    @GET
    @Path("assetNumber/{id}")
    @Produces({"application/json"})
    Equipment getEquipmentFrom(@PathParam("id") String assetNumber);
}
