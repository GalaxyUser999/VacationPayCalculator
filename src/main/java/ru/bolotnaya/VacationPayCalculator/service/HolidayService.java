package ru.bolotnaya.VacationPayCalculator.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.bolotnaya.VacationPayCalculator.model.Holiday;
import ru.bolotnaya.VacationPayCalculator.repository.HolidayRepository;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HolidayService {
    HolidayRepository holidayRepository;

    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

}
