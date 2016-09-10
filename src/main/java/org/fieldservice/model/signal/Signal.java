package org.fieldservice.model.signal;

import org.fieldservice.model.RootEntity;
import org.fieldservice.model.equipment.Equipment;
import org.fieldservice.model.signals.DailySignal;
import org.fieldservice.model.signals.MonthlySignal;
import org.fieldservice.model.signals.YearlySignal;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "field_signal")
@NamedQueries({
        @NamedQuery(name = Signal.FIND_BY_EQUIPMENT,
                query = "SELECT s FROM Signal s " +
                        "INNER JOIN s._equipment e " +
                        "WHERE e._equipmentPk = :equipmentPk")
})
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = Signal.DAILY_SIGNALS,
                classes = {
                        @ConstructorResult(
                                targetClass = DailySignal.class,
                                columns = {
                                        @ColumnResult(name = "status_code_count", type = Integer.class),
                                        @ColumnResult(name = "equipment_id", type = Long.class),
                                        @ColumnResult(name = "status_code", type = String.class),
                                        @ColumnResult(name = "entry_date", type = Date.class),
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = Signal.MONTHLY_SIGNALS,
                classes = {
                        @ConstructorResult(
                                targetClass = MonthlySignal.class,
                                columns = {
                                        @ColumnResult(name = "status_code_count", type = Integer.class),
                                        @ColumnResult(name = "equipment_id", type = Long.class),
                                        @ColumnResult(name = "status_code", type = String.class),
                                        @ColumnResult(name = "entry_month", type = Integer.class),
                                        @ColumnResult(name = "entry_year", type = Short.class),
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = Signal.YEARLY_SIGNALS,
                classes = {
                        @ConstructorResult(
                                targetClass = YearlySignal.class,
                                columns = {
                                        @ColumnResult(name = "status_code_count", type = Integer.class),
                                        @ColumnResult(name = "equipment_id", type = Long.class),
                                        @ColumnResult(name = "status_code", type = String.class),
                                        @ColumnResult(name = "entry_year", type = Short.class),
                                }
                        )
                }
        )
})

public class Signal implements RootEntity<SignalPk> {

    public static final String FIND_BY_EQUIPMENT = "Signal.findBySignal";
    public static final String DAILY_SIGNALS = "Signal.getDailySignals";
    public static final String MONTHLY_SIGNALS = "Signal.getMonthlySignals";
    public static final String YEARLY_SIGNALS = "Signal.getYearlySignals";

    @EmbeddedId
    private SignalPk _signalPk;

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "equipment_id", updatable = false)
    private Equipment _equipment;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status_code")
    private EquipmentStatusCode _equipmentStatusCode;

    @NotNull
    @Column(name = "entry_date_time", updatable = false)
    private LocalDateTime _entryDateTime;

    public SignalPk getSignalPk() {
        return _signalPk;
    }

    public void setSignalPk(SignalPk signalPk) {
        _signalPk = signalPk;
    }

    public Equipment getEquipment() {
        return _equipment;
    }

    public void setEquipment(Equipment equipment) {
        _equipment = equipment;
    }

    public EquipmentStatusCode getEquipmentStatusCode() {
        return _equipmentStatusCode;
    }

    public void setEquipmentStatusCode(EquipmentStatusCode equipmentStatusCode) {
        _equipmentStatusCode = equipmentStatusCode;
    }

    public LocalDateTime getEntryDateTime() {
        return _entryDateTime;
    }

    public void setEntryDateTime(LocalDateTime entryDateTime) {
        _entryDateTime = entryDateTime;
    }

    @Override
    public SignalPk getPk() {
        return _signalPk;
    }

    @PrePersist
    public void initializePk() {
        if (_signalPk == null) {
            _signalPk = new SignalPk();
        }
    }
}
