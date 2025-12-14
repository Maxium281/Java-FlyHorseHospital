package main.java.dao;

import main.java.entity.Department;
import main.java.entity.Doctor;
import java.util.List;

/**
 * 部门数据访问接口，定义部门相关的基础数据操作
 */
public interface DepartmentDAO {

    /**
     * 根据部门名称查询部门
     * 
     * @param deptName 部门名称
     * @return 对应的部门对象，若不存在则返回null
     */
    Department findByDeptName(String deptName);

    /**
     * 添加新部门
     * 
     * @param department 待添加的部门对象
     */
    void addDepartment(Department department);

    /**
     * 更新部门信息
     * 
     * @param department 包含更新信息的部门对象
     */
    void updateDepartment(Department department);

    /**
     * 根据部门名称删除部门
     * 
     * @param deptName 部门名称
     */
    void deleteDepartment(String deptName);

    /**
     * 向部门添加医生
     * 
     * @param deptName 部门名称
     * @param doctor   待添加的医生对象
     */
    void addDoctorToDepartment(String deptName, Doctor doctor);

    /**
     * 从部门移除医生
     * 
     * @param deptName 部门名称
     * @param doctor   待移除的医生对象
     */
    void removeDoctorFromDepartment(String deptName, Doctor doctor);

    /**
     * 获取部门下的所有医生
     * 
     * @param deptName 部门名称
     * @return 部门内医生列表，若部门不存在则返回空列表
     */
    List<Doctor> getDoctorsInDepartment(String deptName);
}