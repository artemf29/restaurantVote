package com.artemf29.core.to;

import java.beans.ConstructorProperties;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class VoteTo extends BaseTo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final LocalDate date;

    private final int userId;

    private final int restId;

    @ConstructorProperties({"id", "date", "userId", "restId"})
    public VoteTo(Integer id, LocalDate date, int userId, int restId) {
        super(id);
        this.date = date;
        this.userId = userId;
        this.restId = restId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return userId == voteTo.userId && restId == voteTo.restId && date.equals(voteTo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, userId, restId);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", date=" + date +
                ", userId=" + userId +
                ", restId=" + restId +
                '}';
    }
}
