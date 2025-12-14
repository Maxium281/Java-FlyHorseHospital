package main.java.dao;

import main.java.entity.Doctor;
import java.util.List;

/**
 * 医生数据访问接口，定义医生相关的数据库操作
 */
public interface DoctorDAO {

    /**
     * 添加新医生
     * 
     * @param doctor 医生对象
     * @throws Exception 数据库操作异常
     */
    void add(Doctor doctor) throws Exception;

    /**
     * 根据医生ID查询医生信息
     * 
     * @param doctorId 医生ID
     * @return 医生对象，若不存在则返回null
     * @throws Exception 数据库操作异常
     */
    Doctor getById(String doctorId) throws Exception;

    /**
     * 更新医生信息
     * 
     * @param doctor 医生对象（包含更新后的信息）
     * @throws Exception 数据库操作异常
     */
    void update(Doctor doctor) throws Exception;

    /**
     * 根据医生ID删除医生信息
     * 
     * @param doctorId 医生ID
     * @throws Exception 数据库操作异常
     */
    void delete(String doctorId) throws Exception;

    /**
     * 获取所有医生信息
     * 
     * @return 医生列表
     * @throws Exception 数据库操作异常
     */
    List<Doctor> getAll() throws Exception;
}
