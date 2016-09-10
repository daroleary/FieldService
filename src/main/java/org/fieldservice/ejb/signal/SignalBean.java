package org.fieldservice.ejb.signal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.fieldservice.ejb.EJB3Adapter;
import org.fieldservice.model.equipment.EquipmentPk;
import org.fieldservice.model.signal.Signal;
import org.fieldservice.model.signal.SignalPk;
import org.fieldservice.model.signals.DailySignal;
import org.fieldservice.model.signals.MonthlySignal;
import org.fieldservice.model.signals.Signals;
import org.fieldservice.model.signals.YearlySignal;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Stateless
public class SignalBean extends EJB3Adapter<SignalPk, Signal> implements SignalLocal, SignalRemote {

    //TODO: cleanup annotations
    private Map<Class<? extends Signals>, String> SIGNAL_IDENTIFIER =
            new ImmutableMap.Builder<Class<? extends Signals>, String>()
                    .put(DailySignal.class, Signal.DAILY_SIGNALS)
                    .put(MonthlySignal.class, Signal.MONTHLY_SIGNALS)
                    .put(YearlySignal.class, Signal.YEARLY_SIGNALS)
                    .build();

    public SignalBean() {
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
    public ImmutableList<DailySignal> getDailySignalsFrom(Long equipmentId) {
        return getSignalsFrom(DailySignal.class, equipmentId);
    }

    @Override
    public ImmutableList<MonthlySignal> getMonthlySignalsFrom(Long equipmentId) {
        return getSignalsFrom(MonthlySignal.class, equipmentId);
    }

    @Override
    public ImmutableList<YearlySignal> getYearlySignalsFrom(Long equipmentId) {
        return getSignalsFrom(YearlySignal.class, equipmentId);
    }

    private <T extends Signals> ImmutableList<T> getSignalsFrom(Class<T> clazz, Long equipmentId) {
        return getSignalsFrom(clazz, EquipmentPk.getPKorNull(equipmentId));
    }

    @Override
    public <T extends Signals> ImmutableList<T> getSignalsFrom(Class<T> clazz, EquipmentPk equipmentPk) {
        StringBuilder sqlString = getSignals(clazz, equipmentPk);

        Query query =
                _em.createNativeQuery(sqlString.toString(), SIGNAL_IDENTIFIER.get(clazz));
        if (equipmentPk != null) {
            query.setParameter("equipmentId", equipmentPk.getEquipmentId().intValue());
        }

        //noinspection unchecked
        List<T> signals = (List<T>) query.getResultList();
        return ImmutableList.copyOf(signals);
    }

    //TODO: cleanup annotations
    private <T extends Signals> StringBuilder getSignals(Class<T> clazz, EquipmentPk equipmentPk) {

        Map<Class<? extends Signals>, StringBuilder> signals =
                new ImmutableMap.Builder<Class<? extends Signals>, StringBuilder>()
                        .put(DailySignal.class, getDailySignals(equipmentPk))
                        .put(MonthlySignal.class, getMonthlySignals(equipmentPk))
                        .put(YearlySignal.class, getYearlySignals(equipmentPk))
                        .build();

        return signals.get(clazz);
    }

    private StringBuilder getDailySignals(EquipmentPk equipmentPk) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("SELECT\n" +
                                  "  COUNT(status_code) status_code_count,\n" +
                                  "  field_signal.equipment_id,\n" +
                                  "  field_signal.status_code,\n" +
                                  "  DATE(entry_date_time) entry_date\n" +
                                  "FROM field_signal\n" +
                                  "WHERE 1=1\n");
        if (equipmentPk != null) {
            sqlString.append("AND field_signal.equipment_id = :equipmentId\n");
        }

        sqlString.append("GROUP BY\n" +
                                  "  field_signal.equipment_id,\n" +
                                  "  field_signal.status_code,\n" +
                                  "  entry_date\n" +
                                  "ORDER BY\n" +
                                  "  entry_date,\n" +
                                  "  field_signal.status_code,\n" +
                                  "  field_signal.equipment_id\n");
        return sqlString;
    }

    private StringBuilder getMonthlySignals(EquipmentPk equipmentPk) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("SELECT\n" +
                                 "  count(status_code) status_code_count,\n" +
                                 "  field_signal.equipment_id,\n" +
                                 "  status_code,\n" +
                                 "  entry_year,\n" +
                                 "  entry_month\n" +
                                 "FROM field_signal\n" +
                                 "WHERE 1=1\n");
        if (equipmentPk != null) {
            sqlString.append("AND field_signal.equipment_id = :equipmentId\n");
        }

        sqlString.append("GROUP BY\n" +
                                 "  field_signal.equipment_id,\n" +
                                 "  field_signal.status_code,\n" +
                                 "  field_signal.entry_year,\n" +
                                 "  field_signal.entry_month\n" +
                                 "ORDER BY\n" +
                                 "  field_signal.entry_year,\n" +
                                 "  field_signal.entry_month,\n" +
                                 "  field_signal.status_code,\n" +
                                 "  field_signal.equipment_id\n");
        return sqlString;
    }

    private StringBuilder getYearlySignals(EquipmentPk equipmentPk) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("SELECT\n" +
                                 "  count(status_code) status_code_count,\n" +
                                 "  field_signal.equipment_id,\n" +
                                 "  status_code,\n" +
                                 "  entry_year\n" +
                                 "FROM field_signal\n" +
                                 "WHERE 1=1\n");
        if (equipmentPk != null) {
            sqlString.append("AND field_signal.equipment_id = :equipmentId\n");
        }

        sqlString.append("GROUP BY\n" +
                                 "  field_signal.equipment_id,\n" +
                                 "  field_signal.status_code,\n" +
                                 "  field_signal.entry_year\n" +
                                 "ORDER BY\n" +
                                 "  field_signal.entry_year,\n" +
                                 "  field_signal.status_code,\n" +
                                 "  field_signal.equipment_id\n");
        return sqlString;
    }
}
