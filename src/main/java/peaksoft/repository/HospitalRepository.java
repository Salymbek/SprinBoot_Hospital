package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Hospital;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
    @Query("from Hospital where name ilike (:keyWord)")
    List<Hospital> search (String keyWord);
}
