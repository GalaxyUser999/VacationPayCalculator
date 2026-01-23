package ru.bolotnaya.VacationPayCalculator.controllers;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bolotnaya.VacationPayCalculator.services.CalculatorService;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/calculate")
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class CalculatorController {
    CalculatorService calculatorService;

    @GetMapping
    public ResponseEntity<String> countVacationPay(@RequestParam(name = "yearlyAvgSalary") BigDecimal yearlyAvgSalary,
                                                       @RequestParam(name = "vacationDays") int vacationDays,
                                                       @RequestParam(name = "firstVacationDay")
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       LocalDate  firstVacationDay) {

        return ResponseEntity.ok(calculatorService.calculateVacationPay(yearlyAvgSalary,
                vacationDays,
                firstVacationDay));
    }
}
