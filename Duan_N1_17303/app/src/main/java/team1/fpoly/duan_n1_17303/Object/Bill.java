package team1.fpoly.duan_n1_17303.Object;

import java.sql.Date;

public class Bill {
    private int billId;
    private int staffId;
    private int customerId;
    private int roomId;
    private String dateCheckIn;
    private String timeCheckIn;
    private String dateCheckOut;
    private String timeCheckOut;
    private int money;
    private String note;


    public Bill() {
    }


    public Bill(int billId, int staffId, int customerId, int roomId, String dateCheckIn, String timeCheckIn, String dateCheckOut, String timeCheckOut, int money, String note) {
        this.billId = billId;
        this.staffId = staffId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.dateCheckIn = dateCheckIn;
        this.timeCheckIn = timeCheckIn;
        this.dateCheckOut = dateCheckOut;
        this.timeCheckOut = timeCheckOut;
        this.money = money;
        this.note = note;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(String dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public String getTimeCheckIn() {
        return timeCheckIn;
    }

    public void setTimeCheckIn(String timeCheckIn) {
        this.timeCheckIn = timeCheckIn;
    }

    public String getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(String dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public String getTimeCheckOut() {
        return timeCheckOut;
    }

    public void setTimeCheckOut(String timeCheckOut) {
        this.timeCheckOut = timeCheckOut;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
