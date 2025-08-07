package com.example.teamMatch.services.timezone;

import com.example.teamMatch.model.Timezones;
import com.example.teamMatch.repositories.TimezoneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimezoneServiceImpl implements TimezoneService {

    private TimezoneRepository timezoneRepository;

    TimezoneServiceImpl(TimezoneRepository timezoneRepository) {
        this.timezoneRepository = timezoneRepository;
    }

    public Optional<Timezones> findByName(String name) {
        return timezoneRepository.findByName(name);
    }
}
