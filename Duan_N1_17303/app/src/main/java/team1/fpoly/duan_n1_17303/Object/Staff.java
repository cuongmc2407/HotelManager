package team1.fpoly.duan_n1_17303.Object;

import java.sql.Date;

public class Staff {
    private int staffId;
    private String staffUsername;
    private String staffPassword;
    private String displayName;
    private String dayOfBirth;
    private String sex;
    private String phone;

    public Staff() {
    }

    public Staff(int staffId, String staffUsername, String staffPassword, String displayName, String dayOfBirth, String sex, String phone) {
        this.staffId = staffId;
        this.staffUsername = staffUsername;
        this.staffPassword = staffPassword;
        this.displayName = displayName;
        this.dayOfBirth = dayOfBirth;
        this.sex = sex;
        this.phone = phone;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffUsername() {
        return staffUsername;
    }

    public void setStaffUsername(String staffUsername) {
        this.staffUsername = staffUsername;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
