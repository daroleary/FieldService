package org.fieldservice.ejb.signal;

import com.google.common.collect.ImmutableList;
import org.fieldservice.model.equipment.EquipmentPk;
import org.fieldservice.model.signal.Signal;
import org.fieldservice.model.signal.SignalPk;
import org.fieldservice.model.signals.DailySignal;
import org.fieldservice.model.signals.MonthlySignal;
import org.fieldservice.model.signals.Signals;
import org.fieldservice.model.signals.YearlySignal;

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
    Signal find(@PathParam("id") Long id);

    Signal find(SignalPk id);

    @GET
    @Path("equipment/{id}")
    @Produces({"application/json"})
    ImmutableList<Signal> getSignalsFrom(@PathParam("id") Long equipmentId);

    ImmutableList<Signal> getSignalsFrom(EquipmentPk equipmentPk);

    @GET
    @Path("daily/equipment/{id}")
    @Produces({"application/json"})
    ImmutableList<DailySignal> getDailySignalsFrom(@PathParam("id") Long equipmentId);

    @GET
    @Path("monthly/equipment/{id}")
    @Produces({"application/json"})
    ImmutableList<MonthlySignal> getMonthlySignalsFrom(@PathParam("id") Long equipmentId);

    @GET
    @Path("yearly/equipment/{id}")
    @Produces({"application/json"})
    ImmutableList<YearlySignal> getYearlySignalsFrom(@PathParam("id") Long equipmentId);

    <T extends Signals> ImmutableList<T> getSignalsFrom(Class<T> clazz, EquipmentPk equipmentPk);
}
