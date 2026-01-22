package kz.yerkebulan.model;

import lombok.NonNull;

public interface Descriptable {
    String getDescription();
    void setDescription(@NonNull String description);
}
