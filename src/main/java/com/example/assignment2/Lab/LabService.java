package com.example.assignment2.Lab;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabService {

    private final LabRepo laboratoryRepo;

    private boolean isLab(int nr) {
        List<Lab> labs = laboratoryRepo.findAll();
        for (Lab lab : labs) if (nr == lab.getNr()) return true;

        return false;
    }

    public Lab dtoToEntity(LabDTO laboratoryDTO) {
        Lab lab = Lab.builder().build();
        lab.setNr(laboratoryDTO.getNr());
        lab.setTitle(laboratoryDTO.getTitle());
        lab.setDescription(laboratoryDTO.getDescription());
        lab.setDate(laboratoryDTO.getDate());
        return lab;

    }

    public LabDTO dtoFromEntity(Lab laboratory) {
        LabDTO labDTO = LabDTO.builder().build();
        labDTO.setNr(laboratory.getNr());
        labDTO.setDate(laboratory.getDate());
        labDTO.setTitle(laboratory.getTitle());
        labDTO.setDescription(laboratory.getDescription());
        return labDTO;
    }

    public Pair<String, HttpStatus> createLab(LabDTO laboratoryDTO) {
        Lab laboratory = dtoToEntity(laboratoryDTO);
        if (isLab(laboratory.getNr())) {
            return Pair.of("Lab already exists", HttpStatus.BAD_REQUEST);
        }
        laboratoryRepo.save(laboratory);
        return Pair.of("Lab created successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> deleteLab(LabDTO laboratoryDTO) {
        Lab laboratory = laboratoryRepo.findLabById(laboratoryDTO.getId());
        if (laboratory == null) {
            return Pair.of("Lab does not exist", HttpStatus.BAD_REQUEST);
        }

        laboratoryRepo.delete(laboratory);
        return Pair.of("Lab deleted successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> updateLab(LabDTO laboratoryDTO) {
        Lab laboratory = laboratoryRepo.findLabById(laboratoryDTO.getId());
        if (laboratory == null) {
            return Pair.of("Lab does not exist", HttpStatus.BAD_REQUEST);
        }

        laboratory.setNr(laboratoryDTO.getNr());
        laboratory.setTitle(laboratoryDTO.getTitle());
        laboratory.setDescription(laboratoryDTO.getDescription());
        laboratory.setDate(laboratoryDTO.getDate());

        laboratoryRepo.save(laboratory);
        return Pair.of("Lab updated successfully", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findLab(LabDTO laboratoryDTO) {
        Lab laboratory = laboratoryRepo.findLabById(laboratoryDTO.getId());
        if (laboratory == null) {
            return Pair.of("Lab does not exist", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(dtoFromEntity(laboratory), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllLaboratories() {
        List<Lab> laboratoryList = laboratoryRepo.findAll();
        List<LabDTO> laboratoryDTOS = new ArrayList<>();
        for (Lab laboratory : laboratoryList) {
            laboratoryDTOS.add(dtoFromEntity(laboratory));
        }
        return Pair.of(laboratoryDTOS, HttpStatus.OK);
    }
}