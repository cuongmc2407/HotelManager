package team1.fpoly.duan_n1_17303.Object;

public class Customer {
    private int customerId;
    private String customerName;
    private String dayOfBirth;
    private String phone;
    private String sex;

    public Customer() {
    }

    public Customer(String customerName, String dayOfBirth, String phone, String sex) {
        this.customerName = customerName;
        this.dayOfBirth = dayOfBirth;
        this.phone = phone;
        this.sex = sex;
    }

    public Customer(int customerId, String customerName, String dayOfBirth, String phone, String sex) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.dayOfBirth = dayOfBirth;
        this.phone = phone;
        this.sex = sex;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
