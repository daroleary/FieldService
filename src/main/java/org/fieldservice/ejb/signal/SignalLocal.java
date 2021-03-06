package org.fieldservice.ejb.signal;

import com.google.common.collect.ImmutableList;
import org.fieldservice.model.equipment.EquipmentPk;
import org.fieldservice.model.signal.Signal;
import org.fieldservice.model.signal.SignalPk;
import org.fieldservice.model.signals.Signals;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SignalLocal {

    void create(Signal signal);

    void edit(Signal signal);

    void remove(SignalPk id);

    Signal find(SignalPk id);

    List<Signal> getSignalsFrom(EquipmentPk equipmentPk);

    <T extends Signals> ImmutableList<T> getSignalsFrom(Class<T> clazz, EquipmentPk equipmentPk);
}
