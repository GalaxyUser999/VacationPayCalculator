package ru.bolotnaya.VacationPayCalculator.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bolotnaya.VacationPayCalculator.models.Holiday;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    @NonNull
    private HolidayService holidayService;
    private static final BigDecimal AVERAGE_MONTHLY_CALENDAR_DAYS = new BigDecimal("29.3");
    private static final BigDecimal MONTHS_IN_YEAR = new BigDecimal("12");


    public String calculateVacationPay(BigDecimal yearlySalary, Integer vacationDays, LocalDate firstVacationDay) {
        int holidaysDuringVacation = findHolidaysDuringVacation(vacationDays, firstVacationDay);
        if (holidaysDuringVacation != 0) {
            vacationDays -= holidaysDuringVacation;
        }

        BigDecimal dailyAvgSalary = yearlySalary.divide(AVERAGE_MONTHLY_CALENDAR_DAYS.multiply(MONTHS_IN_YEAR),
                10, RoundingMode.HALF_UP);
        BigDecimal vacationPay = dailyAvgSalary.multiply(BigDecimal.valueOf(vacationDays));

        vacationPay = vacationPay.setScale(2, RoundingMode.HALF_UP);

        return "Ваша сумма отпускных равна: " + vacationPay;
    }

    private int findHolidaysDuringVacation(Integer vacationDays, LocalDate firstVacationDay) {
        int holidaysDuringVacation = 0;
        List<Holiday> holidays = holidayService.getAllHolidays();
        for (int i = 0; i < vacationDays; i++) {
            for (Holiday holiday : holidays) {
                if (holiday.getHolidayDate().equals(firstVacationDay.plusDays(i)))
                    holidaysDuringVacation++;
            }
        }
        return holidaysDuringVacation;
    }

}

