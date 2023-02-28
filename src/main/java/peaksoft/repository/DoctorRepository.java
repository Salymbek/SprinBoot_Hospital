package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("from Doctor d join d.hospital h where h.id = :id")
    List<Doctor> getAll (Long id);

    @Query("from Hospital where id = :id")
    void save (Long id, Doctor doctor);

    @Query("delete from Doctor where id = :doctorId ")
    void delete (Long doctorId, Hospital hospital);

    @Query("from Department d join d.doctors doc where doc.id = :doctorId")
    List<Department> getDepartments (Long doctorId);

    @Query("from Appointment a join a.doctor d where d.id = :doctorId")
    List<Appointment> getAppointments (Long doctorId);
}
