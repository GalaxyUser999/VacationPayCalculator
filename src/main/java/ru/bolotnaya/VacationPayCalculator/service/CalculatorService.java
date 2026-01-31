package ru.bolotnaya.VacationPayCalculator.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.bolotnaya.VacationPayCalculator.model.Holiday;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CalculatorService {
    @NonNull
    HolidayService holidayService;
    static List<Holiday> holidaysList;
    static BigDecimal AVERAGE_MONTHLY_CALENDAR_DAYS = new BigDecimal("29.3");
    static BigDecimal MONTHS_IN_YEAR = new BigDecimal("12");

    @PostConstruct
    private void fillInHolidaysList() {
        holidaysList = holidayService.getAllHolidays();
    }

    public BigDecimal calculateVacationPay(BigDecimal yearlySalary, Integer vacationDays, LocalDate firstVacationDay) {
        Integer holidaysDuringVacation = findHolidaysDuringVacation(vacationDays, firstVacationDay);
        vacationDays -= holidaysDuringVacation;


        BigDecimal dailyAvgSalary = yearlySalary.divide(AVERAGE_MONTHLY_CALENDAR_DAYS.multiply(MONTHS_IN_YEAR),
                10, RoundingMode.HALF_UP);
        BigDecimal vacationPay = dailyAvgSalary.multiply(BigDecimal.valueOf(vacationDays));

        vacationPay = vacationPay.setScale(2, RoundingMode.HALF_UP);

        return vacationPay;
    }

    private Integer findHolidaysDuringVacation(Integer vacationDays, LocalDate firstVacationDay) {
        Integer holidaysDuringVacation = 0;
        for (int i = 0; i < vacationDays; i++) {
            for (Holiday holiday : holidaysList) {
                if (holiday.getHolidayDate().equals(firstVacationDay.plusDays(i)))
                    holidaysDuringVacation++;
            }
        }
        return holidaysDuringVacation;
    }
}

