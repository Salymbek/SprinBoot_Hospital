package peaksoft.service.impl;

import org.springframework.stereotype.Service;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.HospitalService;

import java.util.List;
@Service

public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }


    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @Override
    public void save(Hospital hospital) {
        hospitalRepository.save(hospital);
    }

    @Override
    public void delete(Long id) {
        hospitalRepository.deleteById(id);
    }

    @Override
    public Hospital getById(Long id) {

        return hospitalRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Hospital by id " + id +  " not found")
        );
    }

    @Override
    public void update(Long id, Hospital hospital) {
        Hospital hospital1 = hospitalRepository.findById(id).get();
        hospital1.setName(hospital.getName());
        hospital1.setAddress(hospital.getAddress());
        hospital1.setImage(hospital.getImage());
        hospitalRepository.save(hospital1);
    }

    @Override
    public List<Hospital> getAllHospitals(String keyWord) {
        if (keyWord!= null && !keyWord.trim().isEmpty()){
            return hospitalRepository.search(keyWord);
        }else {
            return hospitalRepository.findAll();
        }
    }

}