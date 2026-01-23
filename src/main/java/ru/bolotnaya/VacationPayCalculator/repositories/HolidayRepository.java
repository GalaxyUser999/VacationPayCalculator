package ru.bolotnaya.VacationPayCalculator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolotnaya.VacationPayCalculator.models.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
}
