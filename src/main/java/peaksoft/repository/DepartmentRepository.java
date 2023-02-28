package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Department;
import peaksoft.model.Doctor;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long > {

    @Query("from Department d join d.hospital h where h.id = :id")
    List<Department> getAll (Long id);

    @Query("from Hospital where id = :hospitalId")
    void  save(Long hospitalId, Department department);

    @Query("from Doctor d join d.departments dep where dep.id = :departmenId")
    List<Doctor> getDoctors (Long departmentId);
}
