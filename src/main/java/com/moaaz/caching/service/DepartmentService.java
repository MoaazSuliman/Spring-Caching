package com.moaaz.caching.service;

import com.moaaz.caching.model.Department;
import com.moaaz.caching.repository.DepartmentRepository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {


	private final DepartmentRepository departmentRepository;

	@CacheEvict(cacheNames = "departments", allEntries = true)
	public Department create(Department department) {
		return departmentRepository.save(department);
	}

	@Cacheable(cacheNames = "departments", key = "#root.methodName")
	public List<Department> getAll() {
		return departmentRepository.findAll();
	}


	@Cacheable(cacheNames = "departments", key = "#departmentId")
	public Department getById(Long departmentId) {
		return departmentRepository.findById(departmentId).orElseThrow(
				() -> new NoSuchElementException("There are no department with id  = " + departmentId)
		);
	}


	@CacheEvict(cacheNames = "departments", allEntries = true)
	public Department update(Long departmentId, Department department) {
		Department existingDepartment = getById(departmentId);
		return departmentRepository.save(department);
	}

}
