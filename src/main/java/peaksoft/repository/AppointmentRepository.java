package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long>{

    @Query("select a from Hospital h join h.appointments a where h.id = :hospitalId")
    List<Appointment> getAll (Long hospitalId);
}
