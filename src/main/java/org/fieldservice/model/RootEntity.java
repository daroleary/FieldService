package org.fieldservice.model;

import java.io.Serializable;

public interface RootEntity<TPK extends LongPk> extends Serializable {

    TPK getPk();

    void initializePk();
}
