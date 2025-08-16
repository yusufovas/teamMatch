package com.example.teamMatch.model.join;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class TeamMemberId implements Serializable {

    private UUID team;
    private UUID user;

    public TeamMemberId() {
    }

    public TeamMemberId(UUID team, UUID user) {
        this.team = team;
        this.user = user;
    }

    public UUID getTeam() {
        return team;
    }

    public UUID getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamMemberId that)) return false;
        return Objects.equals(team, that.team) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, user);
    }
}
