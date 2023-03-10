package peaksoft.service.impl;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.exceptions.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.model.Patient;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.PatientRepository;
import peaksoft.service.PatientService;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public PatientServiceImpl(PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Patient> getAll(Long patientId) {
        return patientRepository.getAll(patientId);
    }

    @Override
    public void save(Long hospitalId, Patient patient) {
        patientRepository.save(hospitalId, patient);
    }

    @Override
    public Patient getById(Long patientId) {
        return patientRepository.findById(patientId).
                orElseThrow(
                        ()-> new RuntimeException("Patient by id "+ patientId +" not found"));
    }

    @Override
    public void update(Long patientId, Patient patient) {
        Patient patient1 = patientRepository.findById(patientId).get();
        patient1.setFirstName(patient.getFirstName());
        patient1.setLastName(patient.getLastName());
        patient1.setPhoneNumber(patient.getPhoneNumber());
        patient1.setGender(patient.getGender());
        patient1.setEmail(patient.getEmail());
        patientRepository.save(patientId, patient);
    }

    @Override
    public void delete(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(
                        ()-> new NotFoundException("Patient by id " + patientId + " not found"));
        Hospital hospital = patient.getHospital();
        List<Appointment> appointments = patient.getAppointments();
        appointments.forEach(a-> a.getPatient().setAppointments(null));
        appointments.forEach(a-> a.getDoctor().setAppointments(null));
        hospital.getAppointments().removeAll(appointments);
        for (int i = 0; i < appointments.size(); i++) {
            appointmentRepository.deleteById(appointments.get(i).getId());
        }
        patientRepository.deleteById(patientId);
    }

    @Override
    public List<Appointment> getAppointments(Long id, Long patientId) {
        return appointmentRepository.getAll(id).stream().filter(a-> a.getPatient().getId().equals(patientId)).toList();
    }
}