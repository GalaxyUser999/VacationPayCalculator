package ru.bolotnaya.VacationPayCalculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bolotnaya.VacationPayCalculator.service.CalculatorService;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/calculate")
@Tag(name = "Калькулятор отпускных", description = "Операции с калькулятором отпускных")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class CalculatorController {
    CalculatorService calculatorService;

    @Operation(summary = "Посчитать сумму отпускных", description =
            "Принимает среднюю заработную плату сотрудника за 12 месяцев, количество дней отпуска и точный день ухода в отпуск. " +
                    "На основе входных данных рассчитывает сумму отпускных с учётом праздничных дней. " +
                    "Формула расчёта отпускных: средний дневной заработок * (количество дней отпуска - праздничные дни) " +
                    "**Важно:** " +
                    "-Расчёт ведётся по календарным дням." +
                    "-Среднемесячное число календарных дней для расчёта отпускных: 29.3 (установлено ТК РФ)." +
                    "-Расчёт суммы с точностью до копейки (двух знаков после запятой) по правилам расчёта выплаты отпускных.")
    @GetMapping
    public ResponseEntity<BigDecimal> countVacationPay(@RequestParam(name = "yearlyAvgSalary")
                                                   @DecimalMin(value = "1", message = "Число должно быть положительным и больше нуля")
                                                   BigDecimal yearlySalary,
                                                   @RequestParam(name = "vacationDays")
                                                   @Min(value = 1, message = "Число должно быть положительным и больше нуля")
                                                   Integer vacationDays,
                                                   @RequestParam(name = "firstVacationDay")
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                   LocalDate firstVacationDay) {

        return ResponseEntity.ok(calculatorService.calculateVacationPay(yearlySalary,
                vacationDays,
                firstVacationDay));
    }
}
