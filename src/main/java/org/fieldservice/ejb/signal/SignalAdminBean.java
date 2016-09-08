package org.fieldservice.ejb.signal;

import com.google.common.collect.ImmutableList;
import org.fieldservice.ejb.EJB3Adapter;
import org.fieldservice.model.equipment.EquipmentPk;
import org.fieldservice.model.signal.Signal;
import org.fieldservice.model.signal.SignalPk;
import org.fieldservice.model.signals.DailySignals;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class SignalAdminBean extends EJB3Adapter<SignalPk, Signal> implements SignalLocal, SignalRemote {

    public SignalAdminBean() {
        super(Signal.class);
    }

    @Override
    public void create(Signal signal) {
        super.create(signal);
    }

    @Override
    public void edit(Signal signal) {
        super.edit(signal);
    }

    @Override
    public void remove(SignalPk id) {
        super.remove(find(id));
    }

    @Override
    public Signal find(Long id) {
        return find(SignalPk.of(id));
    }

    @Override
    public Signal find(SignalPk id) {
        return super.find(id);
    }

    @Override
    public ImmutableList<Signal> getSignalsFrom(Long equipmentId) {
        return getSignalsFrom(EquipmentPk.of(equipmentId));
    }

    @Override
    public ImmutableList<Signal> getSignalsFrom(EquipmentPk equipmentPk) {
        Query query = _em.createNamedQuery(Signal.FIND_BY_EQUIPMENT);
        query.setParameter("equipmentPk", equipmentPk);

        //noinspection unchecked
        return ImmutableList.<Signal>copyOf(query.getResultList());
    }

    @Override
    public ImmutableList<DailySignals> getDailySignalsFrom(EquipmentPk equipmentPk) {
        StringBuilder _sqlString = new StringBuilder();
        _sqlString.append("SELECT\n" +
                                  "  COUNT(status_code) status_code_count,\n" +
                                  "  field_signal.equipment_id,\n" +
                                  "  field_signal.status_code,\n" +
                                  "  DATE(entry_date_time) entry_date\n" +
                                  "FROM field_signal\n" +
                                  "WHERE 1=1\n");
        if (equipmentPk != null) {
            _sqlString.append("AND field_signal.equipment_id = :equipmentId\n");
        }

        _sqlString.append("GROUP BY\n" +
                                  "  field_signal.equipment_id,\n" +
                                  "  field_signal.status_code,\n" +
                                  "  entry_date\n" +
                                  "ORDER BY\n" +
                                  "  entry_date,\n" +
                                  "  field_signal.status_code,\n" +
                                  "  field_signal.equipment_id\n");

        Query query =
                _em.createNativeQuery(_sqlString.toString(), Signal.DAILY_SIGNALS);
        if (equipmentPk != null) {
            query.setParameter("equipmentId", equipmentPk.getEquipmentId().intValue());
        }
        List<DailySignals> dailySignals = (List<DailySignals>) query.getResultList();
        return ImmutableList.copyOf(dailySignals);
    }
}
