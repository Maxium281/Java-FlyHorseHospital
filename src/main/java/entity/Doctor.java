package main.java.entity;

/**
 * 医生实体类
 * 对应数据库中的医生表
 */
public class Doctor {
    // 医生ID：8位数字组成（系统分配）
    private String doctorId;

    // 姓名：最多20个字符
    private String name;

    // 密码：不少于4位
    private String password;

    // 科室：最多30个字符
    private String department;

    // 专长描述：最多200个字符
    private String specialty;

    public Doctor(String name, String password, String department, String specialty) {
        this.name = name;
        this.password = password;
        this.department = department;
        this.specialty = specialty;
    }

    // 构造函数
    public Doctor() {
        // 医生ID将由系统生成
    }

    // Getter方法
    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartment() {
        return department;
    }

    public String getSpecialty() {
        return specialty;
    }

    // Setter方法（带有验证）

    /**
     * 设置医生ID（由系统分配，客户端不能直接设置）
     * 格式验证：8位数字
     */
    public void setDoctorId(String doctorId) {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new IllegalArgumentException("医生ID不能为空");
        }

        // 验证是否为8位数字
        if (!doctorId.matches("\\d{8}")) {
            throw new IllegalArgumentException("医生ID必须是8位数字");
        }

        this.doctorId = doctorId;
    }

    /**
     * 设置姓名
     * 验证：不能为空，最多20个字符
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("医生姓名不能为空");
        }

        if (name.length() > 20) {
            throw new IllegalArgumentException("医生姓名不能超过20个字符");
        }

        this.name = name;
    }

    /**
     * 设置密码
     * 验证：不少于4位
     */
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        if (password.length() < 4) {
            throw new IllegalArgumentException("密码不能少于4位");
        }

        this.password = password;
    }

    /**
     * 设置科室
     * 验证：不能为空，最多30个字符
     */
    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("科室不能为空");
        }

        if (department.length() > 30) {
            throw new IllegalArgumentException("科室不能超过30个字符");
        }

        this.department = department;
    }

    /**
     * 设置专长描述
     * 验证：最多200个字符（可以为空）
     */
    public void setSpecialty(String specialty) {
        if (specialty != null && specialty.length() > 200) {
            throw new IllegalArgumentException("专长描述不能超过200个字符");
        }

        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId='" + doctorId + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
