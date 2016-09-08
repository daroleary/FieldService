package org.fieldservice.model.signal;

import org.fieldservice.Code;

public enum EquipmentStatusCode implements Code {
    ACTIVE,
    ENGAGED,
    LOAD,
    OVERRIDE,
    UNPLUG;

    @Override
    public String getName() {
        return name();
    }
}
