package ru.bolotnaya.VacationPayCalculator.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Holiday")
@NoArgsConstructor
@Data
@Schema(description = "Официальные праздники РФ")
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Schema(description = "Название праздника", example = "День защитника Отечества")
    @Column(name = "holiday_name")
    private String holidayName;

    @Schema(description = "Дата праздника", example = "2026-02-23")
    @Column(name = "holiday_Date")
    private LocalDate holidayDate;

}
