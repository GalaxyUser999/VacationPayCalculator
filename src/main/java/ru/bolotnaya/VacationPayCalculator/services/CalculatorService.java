package ru.bolotnaya.VacationPayCalculator.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bolotnaya.VacationPayCalculator.models.Holiday;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculatorService {

    @NonNull
    private HolidayService holidayService;
    private static final float AVERAGE_MONTHLY_CALENDAR_DAYS = 29.3F;

    public String calculateVacationPay(BigDecimal yearlyAvgSalary, int vacationDays, LocalDate firstVacationDay) {
        int holidaysDuringVacation = findHolidaysDuringVacation(vacationDays, firstVacationDay);
        if (holidaysDuringVacation != 0) {
            vacationDays -= holidaysDuringVacation;
        }
        BigDecimal vacationPay = yearlyAvgSalary
                .divide(BigDecimal.valueOf(AVERAGE_MONTHLY_CALENDAR_DAYS), 3)
                .multiply(BigDecimal.valueOf(vacationDays));
        return "Ваша сумма отпускных равна: " + vacationPay;
    }

    private int findHolidaysDuringVacation(int vacationDays, LocalDate firstVacationDay) {
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


//    public BigDecimal calculateVacationPay(BigDecimal yearlyAvgSalary, int vacationDays){
//        BigDecimal vacationPay = yearlyAvgSalary
//                .divide(BigDecimal.valueOf(AVERAGE_MONTHLY_CALENDAR_DAYS), 3)
//                .multiply(BigDecimal.valueOf(vacationDays));
//        return vacationPay;
//    }
}

