package ru.bolotnaya.VacationPayCalculator.services;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.bolotnaya.VacationPayCalculator.models.Holiday;
import ru.bolotnaya.VacationPayCalculator.repositories.HolidayRepository;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class HolidayService {
    HolidayRepository holidayRepository;

    public List<Holiday> getAllHolidays(){
        return holidayRepository.findAll();
    }

}
