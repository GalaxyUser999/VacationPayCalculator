package ru.bolotnaya.VacationPayCalculator.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "holiday")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Официальные праздники РФ")
@Data
@RequiredArgsConstructor
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @NonNull
    @Schema(description = "Название праздника", example = "День защитника Отечества")
    @Column(name = "holiday_name")
    String holidayName;

    @NonNull
    @Schema(description = "Дата праздника", example = "2026-02-23")
    @Column(name = "holiday_Date")
    LocalDate holidayDate;

}
