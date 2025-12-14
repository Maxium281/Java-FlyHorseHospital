package main.java.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门类（仅包含部门名称属性 + 医生列表获取功能）
 * 无额外属性/方法，严格遵循需求：仅deptName的get/set + 获取部门下医生列表
 */
public class Department {
    // 唯一属性：部门名称
    private String deptName;

    // 存储该部门下的医生列表（用于实现“获得部门下医生列表”功能）
    private List<Doctor> doctorList = new ArrayList<>();

    // 部门名称的get方法
    public String getDeptName() {
        return deptName;
    }

    // 部门名称的set方法
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    // 获得该部门下的医生列表（仅get，无set/新增/删除医生等额外功能）
    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    // 或许以医生id实现以下功能
    // 内部方法：添加医生到该部门（仅供DAO实现类使用）
    public void addDoctor(Doctor doctor) {
        this.doctorList.add(doctor);
    }

    // 删除医生（仅供DAO实现类使用）
    public void removeDoctor(Doctor doctor) {
        this.doctorList.remove(doctor);
    }
}