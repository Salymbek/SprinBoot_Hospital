package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.exceptions.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, AppointmentRepository appointmentRepository) {
        this.departmentRepository = departmentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Department> getAll(Long id) {
        return departmentRepository.getAll(id);
    }

    @Override
    public void update(Long departmentId, Department department) {
        Department department1 = departmentRepository.findById(departmentId).get();
        department1.setName(department.getName());
        departmentRepository.save(department1);
    }

    @Override
    public void save(Long hospitalId, Department department) {
        departmentRepository.save(hospitalId, department);
    }

    @Override
    public void delete(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(
                        ()-> new NotFoundException("Department by id " + departmentId + " not found"));


        Hospital hospital = department.getHospital();
        List<Appointment> appointments = appointmentRepository.getAll(hospital.getId());
        List<Appointment> depApp = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDepartment().getId().equals(departmentId)) {
                depApp.add(appointment);
            }
        }
        depApp.forEach(a-> a.getPatient().setAppointments(null));
        hospital.getAppointments().removeAll(depApp);

        for (int i = 0; i < depApp.size(); i++) {
            appointmentRepository.deleteById(depApp.get(i).getId());
        }
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public Department findById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(
                        ()-> new NotFoundException("Department by id " + departmentId + " not found"));
    }

    @Override
    public List<Doctor> getDoctors(Long id, Long departmentId) {
        return departmentRepository.getDoctors(departmentId);
    }
}