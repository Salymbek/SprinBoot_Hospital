package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Hospital;
import peaksoft.model.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query("from Patient p join p.hospital h where h.id = :patientId")
    List<Patient> getAll (Long patientId);


    @Query("from Hospital where id = :hospitalId")
    void save (Long hospitalId, Patient patient);
}
