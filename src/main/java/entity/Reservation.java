package main.java.entity;

import java.time.LocalDateTime;

/**
 * 预约实体类
 * 对应数据库中的预约表
 */
public class Reservation {
    // 预约号：12位数字（唯一）
    private String reservationId;

    // 患者ID
    private String patientId;

    // 医生ID
    private String doctorId;

    // 预约时间段（具体到分钟）
    private LocalDateTime reservationTime;

    // 状态（已预约、已取消、已完成）
    private String status;

    // 创建时间（用于生成预约号）
    private LocalDateTime createTime;

    // 取消时间（可选）
    private LocalDateTime cancelTime;

    // 完成时间（可选）
    private LocalDateTime completeTime;

    // 状态常量
    public static final String STATUS_BOOKED = "已预约";
    public static final String STATUS_CANCELLED = "已取消";
    public static final String STATUS_COMPLETED = "已完成";

    // 构造函数
    public Reservation() {
        this.createTime = LocalDateTime.now();
        this.status = STATUS_BOOKED; // 默认状态为已预约
    }

    public Reservation(String patientId, String doctorId, LocalDateTime reservationTime) {
        this();
        setPatientId(patientId);
        setDoctorId(doctorId);
        setReservationTime(reservationTime);
    }

    // Getter方法
    public String getReservationId() {
        return reservationId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getCancelTime() {
        return cancelTime;
    }

    public LocalDateTime getCompleteTime() {
        return completeTime;
    }

    // Setter方法（带有验证）

    /**
     * 设置预约号（由系统根据时间生成）
     * 格式验证：12位数字
     */
    public void setReservationId(String reservationId) {
        if (reservationId == null || reservationId.trim().isEmpty()) {
            throw new IllegalArgumentException("预约号不能为空");
        }

        // 验证是否为12位数字
        if (!reservationId.matches("\\d{12}")) {
            throw new IllegalArgumentException("预约号必须是12位数字");
        }

        this.reservationId = reservationId;
    }

    /**
     * 设置患者ID
     * 验证：10位数字
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
     * 设置医生ID
     * 验证：8位数字
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
     * 设置预约时间
     * 验证：不能为空，不能是过去的时间（简化验证）
     */
    public void setReservationTime(LocalDateTime reservationTime) {
        if (reservationTime == null) {
            throw new IllegalArgumentException("预约时间不能为空");
        }

        // 验证预约时间不能早于当前时间（简化验证，实际业务可能更复杂）
        LocalDateTime now = LocalDateTime.now();
        if (reservationTime.isBefore(now)) {
            throw new IllegalArgumentException("预约时间不能是过去的时间");
        }

        this.reservationTime = reservationTime;
    }

    /**
     * 设置状态
     * 验证：只能是预定义的三种状态
     */
    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("状态不能为空");
        }

        if (!status.equals(STATUS_BOOKED) &&
                !status.equals(STATUS_CANCELLED) &&
                !status.equals(STATUS_COMPLETED)) {
            throw new IllegalArgumentException("状态必须是：已预约、已取消、已完成");
        }

        this.status = status;

        // 如果是取消状态，记录取消时间
        if (status.equals(STATUS_CANCELLED) && this.cancelTime == null) {
            this.cancelTime = LocalDateTime.now();
        }

        // 如果是完成状态，记录完成时间
        if (status.equals(STATUS_COMPLETED) && this.completeTime == null) {
            this.completeTime = LocalDateTime.now();
        }
    }

    /**
     * 设置创建时间（只读属性，创建时自动设置）
     */
    private void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 设置取消时间（只读属性，取消时自动设置）
     */
    public void setCancelTime(LocalDateTime cancelTime) {
        this.cancelTime = cancelTime;
    }

    /**
     * 设置完成时间（只读属性，完成时自动设置）
     */
    public void setCompleteTime(LocalDateTime completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * 取消预约
     */
    public void cancel() {
        setStatus(STATUS_CANCELLED);
    }

    /**
     * 完成预约
     */
    public void complete() {
        setStatus(STATUS_COMPLETED);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", reservationTime=" + reservationTime +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
