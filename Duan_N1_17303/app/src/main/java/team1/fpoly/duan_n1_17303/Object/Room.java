package team1.fpoly.duan_n1_17303.Object;

public class Room {
    private int roomId;
    private String roomName;
    private int roomLocation;
    private String roomType;
    private String roomView;
    private int dayPrice;
    private int hourPrice;
    private String note;

    public Room() {
    }

    public String getRoomView() {
        return roomView;
    }

    public void setRoomView(String roomView) {
        this.roomView = roomView;
    }

    public Room(int roomId, String roomName, int roomLocation, String roomType, String roomView, int dayPrice, int hourPrice, String note) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomLocation = roomLocation;
        this.roomType = roomType;
        this.roomView = roomView;
        this.dayPrice = dayPrice;
        this.hourPrice = hourPrice;
        this.note = note;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(int roomLocation) {
        this.roomLocation = roomLocation;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(int dayPrice) {
        this.dayPrice = dayPrice;
    }

    public int getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(int hourPrice) {
        this.hourPrice = hourPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
