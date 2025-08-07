package com.example.teamMatch.services.timezone;

import com.example.teamMatch.model.Timezones;

import java.util.Optional;

public interface TimezoneService {
    Optional<Timezones> findByName(String name);
}
