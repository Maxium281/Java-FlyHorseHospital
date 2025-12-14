package main.java.entity;

// import java.util.regex.Pattern;

/**
 * 患者实体类
 * 对应数据库中的患者表
 */
public class Patient {
    // 患者ID：10位数字组成（系统分配）
    private String patientId;

    // 姓名：最多20个字符
    private String name;

    // 密码：不少于4位
    private String password;

    // 身份ID：身份证号（18位数字）
    private String identityId;

    // 手机号：正常手机号
    private String phone;

    // 性别：M(男)或F(女)
    private Gender gender;

    // 年龄（根据身份证号计算，不存储在数据库，但可以计算）
    private int age;

    public Patient(String name, String password, String identityId, String phone) {
        this.name = name;
        this.password = password;
        this.identityId = identityId;
        this.phone = phone;
    }

    // 构造函数
    public Patient() {
        // 患者ID将由系统生成
    }

    // Getter方法
    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentityId() {
        return identityId;
    }

    public String getPhone() {
        return phone;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    // Setter方法（带有验证）

    /**
     * 设置患者ID（由系统分配，客户端不能直接设置）
     * 格式验证：10位数字
     */
    public void setPatientId(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new IllegalArgumentException("患者ID不能为空");
        }

        // 验证是否为10位数字
        if (!patientId.matches("\\d{10}")) {
            throw new IllegalArgumentException("患者ID必须是10位数字");
        }

        this.patientId = patientId;
    }

    /**
     * 设置姓名
     * 验证：不能为空，最多20个字符
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不能为空");
        }

        if (name.length() > 20) {
            throw new IllegalArgumentException("姓名不能超过20个字符");
        }

        // 只允许英文
        // if (!Pattern.matches("^[\\u4E00-\\u9FA5A-Za-z0-9]+$", name)) {
        // throw new IllegalArgumentException("姓名只能包含中文、英文和数字");
        // }

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
     * 设置身份证号
     * 验证：18位数字（简化验证，实际应验证校验位）
     */
    public void setIdentityId(String identityId) {
        if (identityId == null || identityId.trim().isEmpty()) {
            throw new IllegalArgumentException("身份证号不能为空");
        }

        // 验证是否为18位数字
        if (!identityId.matches("\\d{18}")) {
            throw new IllegalArgumentException("身份证号必须是18位数字");
        }

        this.identityId = identityId;

        // 根据身份证号计算年龄和性别（简化版）
        calculateAgeFromIdentityId();

        // 根据身份证的第17位来设置性别
        this.gender = identityId.charAt(16) % 2 == 0 ? Gender.F : Gender.M;
    }

    /**
     * 设置手机号
     * 验证：正常手机号（11位数字，1开头）
     */
    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("手机号不能为空");
        }

        // 验证手机号格式（简单验证）
        if (!phone.matches("1[3-9]\\d{9}")) {
            throw new IllegalArgumentException("手机号格式不正确");
        }

        this.phone = phone;
    }

    /**
     * 根据身份证号计算年龄（精确版，考虑月日）
     */
    private void calculateAgeFromIdentityId() {
        if (identityId == null || identityId.length() < 14) {
            return;
        }

        try {
            // 提取出生年月日（身份证第7-14位，格式：yyyyMMdd）
            int birthYear = Integer.parseInt(identityId.substring(6, 10));
            int birthMonth = Integer.parseInt(identityId.substring(10, 12));
            int birthDay = Integer.parseInt(identityId.substring(12, 14));

            // 直接创建出生日期对象（DateTimeException会自动处理无效日期）
            java.time.LocalDate birthDate = java.time.LocalDate.of(birthYear, birthMonth, birthDay);
            java.time.LocalDate currentDate = java.time.LocalDate.now();

            // 如果出生日期在未来，年龄设为0
            // if (birthDate.isAfter(currentDate)) {
            // this.age = 0;
            // return;
            // }
            // 抛出异常，说明年龄不足，要求重新输入身份证号或退出注册（返回主页

            // 计算精确年龄
            this.age = java.time.Period.between(birthDate, currentDate).getYears();

        } catch (Exception e) {
            // 任何异常情况（无效日期、解析失败等）都设置年龄为0
            // this.age = 0;
        }
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", identityId='" + identityId + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}