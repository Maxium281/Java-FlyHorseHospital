package main.java.dao;

import main.java.entity.Department;
import main.java.entity.Doctor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门数据访问接口的实现类，基于内存存储实现基础功能
 */
public class DepartmentDAOImpl implements DepartmentDAO {

    // 内存存储，以部门名称作为唯一键
    private static final Map<String, Department> departmentMap = new HashMap<>();

    @Override
    public Department findByDeptName(String deptName) {
        return departmentMap.get(deptName);
    }

    @Override
    public void addDepartment(Department department) {
        if (department == null || department.getDeptName() == null) {
            throw new IllegalArgumentException("部门对象或部门名称不能为空");
        }
        String deptName = department.getDeptName();
        if (departmentMap.containsKey(deptName)) {
            throw new IllegalArgumentException("该部门已存在：" + deptName);
        }
        departmentMap.put(deptName, department);
    }

    @Override
    public void updateDepartment(Department department) {
        if (department == null || department.getDeptName() == null) {
            throw new IllegalArgumentException("部门对象或部门名称不能为空");
        }
        String deptName = department.getDeptName();
        if (!departmentMap.containsKey(deptName)) {
            throw new IllegalArgumentException("该部门不存在：" + deptName);
        }
        departmentMap.put(deptName, department);
    }

    @Override
    public void deleteDepartment(String deptName) {
        if (deptName == null) {
            throw new IllegalArgumentException("部门名称不能为空");
        }
        departmentMap.remove(deptName);
    }

    @Override
    public void addDoctorToDepartment(String deptName, Doctor doctor) {
        Department department = findByDeptName(deptName);
        if (department == null) {
            throw new IllegalArgumentException("部门不存在：" + deptName);
        }
        if (doctor == null) {
            throw new IllegalArgumentException("医生对象不能为空");
        }
        department.addDoctor(doctor);
    }

    @Override
    public void removeDoctorFromDepartment(String deptName, Doctor doctor) {
        Department department = findByDeptName(deptName);
        if (department == null) {
            throw new IllegalArgumentException("部门不存在：" + deptName);
        }
        if (doctor == null) {
            throw new IllegalArgumentException("医生对象不能为空");
        }
        department.removeDoctor(doctor);
    }

    @Override
    public List<Doctor> getDoctorsInDepartment(String deptName) {
        Department department = findByDeptName(deptName);
        if (department == null) {
            return new ArrayList<>();
        }
        return department.getDoctorList();
    }
}