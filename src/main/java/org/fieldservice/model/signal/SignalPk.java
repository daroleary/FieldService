package org.fieldservice.model.signal;

import org.fieldservice.model.LongPk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Objects;

@Embeddable
public class SignalPk implements LongPk {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="signal_id", nullable = false)
    private Long _signalId;

    public SignalPk() {
    }

    public SignalPk(Long id) {
        _signalId = id;
    }

    public static SignalPk of(Long id) {
        return new SignalPk(id);
    }

    public Long getSignalId() {
        return _signalId;
    }

    public void setSignalId(Long id) {
        _signalId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignalPk signalPk = (SignalPk) o;
        return Objects.equals(_signalId, signalPk._signalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_signalId);
    }
}
