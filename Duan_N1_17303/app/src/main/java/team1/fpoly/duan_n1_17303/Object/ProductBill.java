package team1.fpoly.duan_n1_17303.Object;

public class ProductBill {
    private int productId;
    private int billId;
    private String productBillName;
    private int productBillPrice;
    private int productBillQuantity;

    public ProductBill() {
    }

    public ProductBill(int productId, int billId, String productBillName, int productBillPrice, int productBillQuantity) {
        this.productId = productId;
        this.billId = billId;
        this.productBillName = productBillName;
        this.productBillPrice = productBillPrice;
        this.productBillQuantity = productBillQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getProductBillName() {
        return productBillName;
    }

    public void setProductBillName(String productBillName) {
        this.productBillName = productBillName;
    }

    public int getProductBillPrice() {
        return productBillPrice;
    }

    public void setProductBillPrice(int productBillPrice) {
        this.productBillPrice = productBillPrice;
    }

    public int getProductBillQuantity() {
        return productBillQuantity;
    }

    public void setProductBillQuantity(int productBillQuantity) {
        this.productBillQuantity = productBillQuantity;
    }
}
