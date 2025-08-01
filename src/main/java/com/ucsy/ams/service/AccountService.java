package com.ucsy.ams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ucsy.ams.dto.StudentAccountDto;
import com.ucsy.ams.dto.TeacherAccountDto;
import com.ucsy.ams.entity.Account;
import com.ucsy.ams.entity.Students;
import com.ucsy.ams.entity.Teachers;
import com.ucsy.ams.repository.AccountRepo;
import com.ucsy.ams.repository.AttendanceRepo;
import com.ucsy.ams.repository.StudentRepo;
import com.ucsy.ams.repository.TakesRepo;
import com.ucsy.ams.repository.TeacherAccountRepo;
import com.ucsy.ams.repository.TeacherHasCourseRepo;

@Service
public class AccountService {

	@Autowired
	private AccountRepo accRepo;

	@Autowired
	private TeacherAccountRepo teacherAccountRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private TeacherHasCourseRepo repo;

	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private TakesRepo takeRepo;

	@Autowired
	private AttendanceRepo attenRepo;
	
	public Integer getAmount(String role) {
		return accRepo.getTotal(role);
	}

	public void insertAccountTeachers(TeacherAccountDto dto) {

		var acc = new Account();
		acc.setEmail(dto.getEmail());
		acc.setName(dto.getName());
		acc.setPassword(passwordEncoder.encode(dto.getPassword()));
		acc.setRole("ROLE_TEACHER");

		accRepo.save(acc);
		var accId = accRepo.findByEmail(dto.getEmail());

		var teacher = new Teachers();
		teacher.setId(accId.getId());
		teacher.setNrcNo(dto.getNrcNo());
		teacher.setDob(dto.getDob());
		teacher.setPhoneNo(dto.getPhoneNo());
		teacher.setTeacherRank(dto.getTeacherRank());
		teacher.setHighestDegree(dto.getHighestDegree());
		teacher.setFaculty(dto.getFaculty());
		teacher.setRegisterDate(dto.getRegisterDate());

		try {
			teacherAccountRepo.save(teacher);
		} catch (DataIntegrityViolationException e) {
			accRepo.deleteById(accId.getId());
		}
	}

	public void insertAccountStudent(StudentAccountDto studentDto) {

		Account account = new Account();
		account.setName(studentDto.getName());
		account.setEmail(studentDto.getEmail());
		account.setPassword(passwordEncoder.encode("1234"));
		account.setRole("ROLE_STUDENT"); // Set default role as student
		accRepo.save(account);

		var accId = accRepo.findByEmail(studentDto.getEmail());

		Students student = new Students();
		student.setId(accId.getId());
		student.setRollNo(studentDto.getRollNo());
		student.setNrcNo(studentDto.getNrcNo());
		student.setDob(studentDto.getDob());
		student.setPhoneNo(studentDto.getPhoneNo());
		student.setMajor(studentDto.getMajor());
		student.setRegisterDate(studentDto.getRegisterDate());
		student.setSemester(studentDto.getSemester());

		studentRepo.save(student);
	}

	public void updateTeacherData(TeacherAccountDto dto) {
		if (dto.getRole().equals("ROLE_TEACHER")) {
			dto.setSemester(null);
		}
		teacherAccountRepo.teacherUpdate(dto.getPhoneNo(), dto.getTeacherRank(), dto.getHighestDegree(),
				dto.getFaculty(), dto.getSemester(), dto.getId());
		accRepo.accUpdate(dto.getEmail(), dto.getRole(), dto.getId());
	}

	public void deleteTeacher(int id) {

		repo.deleteTeacherId((Integer) id);
		teacherAccountRepo.deleteById(id);
		accRepo.deleteById(id);
	}

	public void deleteStudent(int id) {

		attenRepo.deleteStudentId(id);
		takeRepo.deleteStudentId(id);
		studentRepo.deleteById(id);
		accRepo.deleteById(id);
	}

//  find total  with role
	public List<Account> findTotalByRole(String role) {
		return accRepo.findTotalByRole(role);
	}
}
