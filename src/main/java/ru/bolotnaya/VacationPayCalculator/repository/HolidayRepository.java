package ru.bolotnaya.VacationPayCalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolotnaya.VacationPayCalculator.model.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
}
