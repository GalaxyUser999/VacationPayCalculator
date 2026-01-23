package ru.bolotnaya.VacationPayCalculator.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Holiday")
@NoArgsConstructor
@Data
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "holiday_name")
    private String holidayName;

    @Column(name = "holiday_Date")
    private LocalDate holidayDate;

}
