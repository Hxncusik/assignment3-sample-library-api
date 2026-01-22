package kz.yerkebulan.model;

import lombok.NonNull;

public interface Nameable {
    String getName();
    void setName(@NonNull String name);
}
