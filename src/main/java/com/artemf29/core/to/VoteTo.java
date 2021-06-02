package com.artemf29.core.to;

import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class VoteTo extends BaseTo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    private final LocalDate date;

    @NotBlank
    private final int restId;

    @NotBlank
    private final String restName;

    @ConstructorProperties({"id", "date", "restId", "restName"})
    public VoteTo(Integer id, LocalDate date, int restId, String restName) {
        super(id);
        this.date = date;
        this.restId = restId;
        this.restName = restName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return restId == voteTo.restId && date.equals(voteTo.date) && restName.equals(voteTo.restName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, restId, restName);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", date=" + date +
                ", restId=" + restId +
                ", restName=" + restName +
                '}';
    }
}
