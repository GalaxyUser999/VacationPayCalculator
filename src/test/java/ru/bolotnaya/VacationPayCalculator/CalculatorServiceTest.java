package ru.bolotnaya.VacationPayCalculator;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bolotnaya.VacationPayCalculator.model.Holiday;
import ru.bolotnaya.VacationPayCalculator.service.CalculatorService;
import ru.bolotnaya.VacationPayCalculator.service.HolidayService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CalculatorServiceTest {
    @Mock
    HolidayService holidayService;

    @InjectMocks
    CalculatorService calculatorService;

    private void initPostConstruct() {
        Method method;
        try {
            method = CalculatorService.class.getDeclaredMethod("fillInHolidaysList");
            method.setAccessible(true);
            method.invoke(calculatorService);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void calculateVacationPay_withHolidays_shouldExcludeHolidaysFromCalculation(){
        //Arrange

        BigDecimal yearlySalary = new BigDecimal("600000");
        Integer vacationDays = 14;
        LocalDate firstVacationDay = LocalDate.of(2026, 1, 1);
        List<Holiday> holidays = Arrays.asList(
                new Holiday("Новогодние каникулы", LocalDate.of(2026, 1, 1)),
                new Holiday("Рождество", LocalDate.of(2026, 1, 7)));

        when(holidayService.getAllHolidays()).thenReturn(holidays);

        initPostConstruct();

        //Act

        BigDecimal result = calculatorService.calculateVacationPay(yearlySalary, vacationDays, firstVacationDay);

        //Assert

        //Исключаем 2 праздника из расчёта

        vacationDays -= 2;

        BigDecimal expectedDailySalary = yearlySalary.divide(
                new BigDecimal("29.3").multiply(new BigDecimal("12")), 10,
                RoundingMode.HALF_UP);

        BigDecimal expectedVacationPay = expectedDailySalary.multiply(new BigDecimal(vacationDays)).setScale(2,
                RoundingMode.HALF_UP);

        assertEquals(expectedVacationPay, result);
    }

    @Test
    public void calculateVacationPay_withoutHolidays_shouldCountCorrectly() {
        //Arrange

        BigDecimal yearlySalary = new BigDecimal("600000");
        Integer vacationDays = 14;
        LocalDate firstVacationDay = LocalDate.of(2026, 7, 1);

        //Праздников нет в течение отпуска

        when(holidayService.getAllHolidays()).thenReturn(Collections.emptyList());

        initPostConstruct();

        //Act

        BigDecimal result = calculatorService.calculateVacationPay(yearlySalary, vacationDays, firstVacationDay);

        //Assert

        BigDecimal expectedDailySalary = yearlySalary.divide(
                new BigDecimal("29.3").multiply(new BigDecimal("12")), 10,
                RoundingMode.HALF_UP);

        BigDecimal expectedVacationPay = expectedDailySalary.multiply(new BigDecimal(vacationDays)).setScale(2,
                RoundingMode.HALF_UP);

        assertEquals(expectedVacationPay, result);
    }

    @Test
    public void findHolidaysDuringVacation_shouldCountHolidaysCorrectly(){
        //Arrange

        Integer vacationDays = 14;
        LocalDate firstVacationDay = LocalDate.of(2026, 1, 6);

        List<Holiday> holidays = Arrays.asList(
                new Holiday("Новогодние каникулы", LocalDate.of(2026, 1, 6)),
                new Holiday("Рождество", LocalDate.of(2026, 1, 7)),
                new Holiday("Новогодние каникулы", LocalDate.of(2026, 1, 8)),
                new Holiday("Не праздник -> не попадает в диапазон", LocalDate.of(2026, 7, 10)));

        when(holidayService.getAllHolidays()).thenReturn(holidays);
        initPostConstruct();

        Method method = null;
        try {
            method = CalculatorService.class.getDeclaredMethod("findHolidaysDuringVacation", Integer.class, LocalDate.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        method.setAccessible(true);

        //Act

        //Вызов приватного метода сервиса через Рефлексию
        Integer holidaysDuringVacation;
        try {
             holidaysDuringVacation = (Integer) method.invoke(calculatorService, vacationDays, firstVacationDay);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        //Assert

        assertEquals(3, holidaysDuringVacation); //6, 7, 8 января - праздничные дни
    }
}
