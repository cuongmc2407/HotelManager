package team1.fpoly.duan_n1_17303.Object;

public class ProductRoom {
    private int productId;
    private int roomId;
    private String productRoomName;
    private int productRoomPrice;
    private int productRoomQuantity;

    public ProductRoom() {
    }

    public ProductRoom(int productId, int roomId, String productRoomName, int productRoomPrice, int productRoomQuantity) {
        this.productId = productId;
        this.roomId = roomId;
        this.productRoomName = productRoomName;
        this.productRoomPrice = productRoomPrice;
        this.productRoomQuantity = productRoomQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getProductRoomName() {
        return productRoomName;
    }

    public void setProductRoomName(String productRoomName) {
        this.productRoomName = productRoomName;
    }

    public int getProductRoomPrice() {
        return productRoomPrice;
    }

    public void setProductRoomPrice(int productRoomPrice) {
        this.productRoomPrice = productRoomPrice;
    }

    public int getProductRoomQuantity() {
        return productRoomQuantity;
    }

    public void setProductRoomQuantity(int productRoomQuantity) {
        this.productRoomQuantity = productRoomQuantity;
    }
}
