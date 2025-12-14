package main.java.entity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 排班实体类
 * 对应数据库中的排班表
 */
public class Schedule {
    // 排班ID（自增或编码）
    private String scheduleId;

    // 医生ID
    private String doctorId;

    // 排班日期
    private LocalDate scheduleDate;

    // 开始时间
    private LocalTime startTime;

    // 结束时间
    private LocalTime endTime;

    // 时间段类型（上午、下午、晚上）
    private String timeSlot;

    // 可预约数量
    private int availableSlots;

    // 已预约数量
    private int bookedSlots;

    // 状态（正常、停诊等）
    private String status;

    // 时间段类型常量
    public static final String MORNING = "上午";
    public static final String AFTERNOON = "下午";
    public static final String EVENING = "晚上";

    // 状态常量
    public static final String STATUS_NORMAL = "正常";
    public static final String STATUS_CANCELLED = "停诊";
    public static final String STATUS_FULL = "已满";

    // 构造函数
    public Schedule() {
        this.availableSlots = 10; // 默认10个号源
        this.bookedSlots = 0;
        this.status = STATUS_NORMAL;
    }

    public Schedule(String doctorId, LocalDate scheduleDate,
            LocalTime startTime, LocalTime endTime, String timeSlot) {
        this();
        setDoctorId(doctorId);
        setScheduleDate(scheduleDate);
        setStartTime(startTime);
        setEndTime(endTime);
        setTimeSlot(timeSlot);
    }

    // Getter方法
    public String getScheduleId() {
        return scheduleId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public int getBookedSlots() {
        return bookedSlots;
    }

    public String getStatus() {
        return status;
    }

    /**
     * 获取剩余号源
     */
    public int getRemainingSlots() {
        return availableSlots - bookedSlots;
    }

    // Setter方法（带有验证）

    /**
     * 设置排班ID
     */
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
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
     * 设置排班日期
     * 验证：不能为空，不能是过去日期（简化验证）
     */
    public void setScheduleDate(LocalDate scheduleDate) {
        if (scheduleDate == null) {
            throw new IllegalArgumentException("排班日期不能为空");
        }

        // 验证排班日期不能早于当前日期（简化验证）
        LocalDate today = LocalDate.now();
        if (scheduleDate.isBefore(today)) {
            throw new IllegalArgumentException("排班日期不能是过去日期");
        }

        this.scheduleDate = scheduleDate;
    }

    /**
     * 设置开始时间
     * 验证：不能为空
     */
    public void setStartTime(LocalTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        this.startTime = startTime;
    }

    /**
     * 设置结束时间
     * 验证：不能为空，必须晚于开始时间
     */
    public void setEndTime(LocalTime endTime) {
        if (endTime == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (this.startTime != null && !endTime.isAfter(this.startTime)) {
            throw new IllegalArgumentException("结束时间必须晚于开始时间");
        }

        this.endTime = endTime;
    }

    /**
     * 设置时间段类型
     * 验证：必须是预定义的类型
     */
    public void setTimeSlot(String timeSlot) {
        if (timeSlot == null || timeSlot.trim().isEmpty()) {
            throw new IllegalArgumentException("时间段类型不能为空");
        }

        if (!timeSlot.equals(MORNING) &&
                !timeSlot.equals(AFTERNOON) &&
                !timeSlot.equals(EVENING)) {
            throw new IllegalArgumentException("时间段类型必须是：上午、下午、晚上");
        }

        this.timeSlot = timeSlot;
    }

    /**
     * 设置可预约数量
     * 验证：必须大于0
     */
    public void setAvailableSlots(int availableSlots) {
        if (availableSlots <= 0) {
            throw new IllegalArgumentException("可预约数量必须大于0");
        }

        this.availableSlots = availableSlots;
        updateStatus();
    }

    /**
     * 设置已预约数量
     * 验证：不能超过可预约数量
     */
    public void setBookedSlots(int bookedSlots) {
        if (bookedSlots < 0) {
            throw new IllegalArgumentException("已预约数量不能为负数");
        }

        if (bookedSlots > this.availableSlots) {
            throw new IllegalArgumentException("已预约数量不能超过可预约数量");
        }

        this.bookedSlots = bookedSlots;
        updateStatus();
    }

    /**
     * 设置状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 增加已预约数量（用于预约时调用）
     * 
     * @return 是否预约成功
     */
    public boolean bookSlot() {
        if (bookedSlots >= availableSlots) {
            return false;
        }

        bookedSlots++;
        updateStatus();
        return true;
    }

    /**
     * 减少已预约数量（用于取消预约时调用）
     * 
     * @return 是否取消成功
     */
    public boolean cancelSlot() {
        if (bookedSlots <= 0) {
            return false;
        }

        bookedSlots--;
        updateStatus();
        return true;
    }

    /**
     * 更新状态
     * 根据已预约数量自动更新状态
     */
    private void updateStatus() {
        if (bookedSlots >= availableSlots) {
            status = STATUS_FULL;
        } else {
            status = STATUS_NORMAL;
        }
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", timeSlot='" + timeSlot + '\'' +
                ", availableSlots=" + availableSlots +
                ", bookedSlots=" + bookedSlots +
                ", remainingSlots=" + getRemainingSlots() +
                ", status='" + status + '\'' +
                '}';
    }
}
