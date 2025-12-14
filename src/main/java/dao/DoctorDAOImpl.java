package main.java.dao;

import main.java.entity.Doctor;
import main.java.util.JDBCUtil; // 假设存在数据库工具类

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 医生数据访问实现类，基于JDBC实现数据库操作
 */
public class DoctorDAOImpl implements DoctorDAO {

    @Override
    public void add(Doctor doctor) throws Exception {
        String sql = "INSERT INTO doctor (doctor_id, name, password, department, specialty) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctor.getDoctorId());
            pstmt.setString(2, doctor.getName());
            pstmt.setString(3, doctor.getPassword());
            pstmt.setString(4, doctor.getDepartment());
            pstmt.setString(5, doctor.getSpecialty());

            pstmt.executeUpdate();
        }
    }

    @Override
    public Doctor getById(String doctorId) throws Exception {
        String sql = "SELECT * FROM doctor WHERE doctor_id = ?";
        Doctor doctor = null;

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor();
                    doctor.setDoctorId(rs.getString("doctor_id"));
                    doctor.setName(rs.getString("name"));
                    doctor.setPassword(rs.getString("password"));
                    doctor.setDepartment(rs.getString("department"));
                    doctor.setSpecialty(rs.getString("specialty"));
                }
            }
        }
        return doctor;
    }

    @Override
    public void update(Doctor doctor) throws Exception {
        String sql = "UPDATE doctor SET name = ?, password = ?, department = ?, specialty = ? " +
                "WHERE doctor_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getPassword());
            pstmt.setString(3, doctor.getDepartment());
            pstmt.setString(4, doctor.getSpecialty());
            pstmt.setString(5, doctor.getDoctorId());

            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(String doctorId) throws Exception {
        String sql = "DELETE FROM doctor WHERE doctor_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctorId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Doctor> getAll() throws Exception {
        String sql = "SELECT * FROM doctor";
        List<Doctor> doctorList = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(rs.getString("doctor_id"));
                doctor.setName(rs.getString("name"));
                doctor.setPassword(rs.getString("password"));
                doctor.setDepartment(rs.getString("department"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctorList.add(doctor);
            }
        }
        return doctorList;
    }
}
