package org.fieldservice.ejb.signal;

import com.google.common.collect.ImmutableList;
import org.fieldservice.model.equipment.EquipmentPk;
import org.fieldservice.model.signal.Signal;
import org.fieldservice.model.signal.SignalPk;
import org.fieldservice.model.signals.DailySignals;

import javax.ejb.Remote;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Remote
@Path("/signals")
public interface SignalRemote {

    void create(Signal signal);

    void edit(Signal signal);

    void remove(SignalPk id);

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    Signal find(@PathParam("id") java.lang.Long id);

    Signal find(SignalPk id);

    @GET
    @Path("equipment/{id}")
    @Produces({"application/json"})
    ImmutableList<Signal> getSignalsFrom(@PathParam("id") Long equipmentId);

    ImmutableList<Signal> getSignalsFrom(EquipmentPk equipmentPk);

    ImmutableList<DailySignals> getDailySignalsFrom(EquipmentPk equipmentPk);
}
