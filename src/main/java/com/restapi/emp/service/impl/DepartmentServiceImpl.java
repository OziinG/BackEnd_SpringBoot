package com.restapi.emp.service.impl;

import com.restapi.emp.dto.DepartmentDto;
import com.restapi.emp.dto.mapper.DepartmentMapper;
import com.restapi.emp.entity.Department;
import com.restapi.emp.repository.DepartmentRepository;
import com.restapi.emp.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Transactional(readOnly = true)
    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department = EmpDeptCommon.getDepartment(departmentId, departmentRepository);
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments
                .stream()
                .map(DepartmentMapper::mapToDepartmentDto)
                .toList();
                //.map((department) -> DepartmentMapper.mapToDepartmentDto(department))
                //.collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment) {
        Department department = EmpDeptCommon.getDepartment(departmentId, departmentRepository);
        //Dirty Check - setter Method call
        if (updatedDepartment.getDepartmentName() != null)
            department.setDepartmentName(updatedDepartment.getDepartmentName());
        if (updatedDepartment.getDepartmentDescription() != null)
            department.setDepartmentDescription(updatedDepartment.getDepartmentDescription());

        // Department savedDepartment = departmentRepository.save(department);

        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
       Department department = EmpDeptCommon.getDepartment(departmentId,departmentRepository);

        departmentRepository.delete(department);
    }
}