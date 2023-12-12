package team1.fpoly.duan_n1_17303.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Database.MyDatabase;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.Room;

public class CustomerDao {

    SQLiteDatabase db;

    public CustomerDao(Context context) {
        MyDatabase myDatabase = new MyDatabase(context);
        db = myDatabase.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Customer> getListCustomer(String sql, String...selectionArgs){
        ArrayList<Customer> listCustomer = new ArrayList<>();
        Cursor cursor =db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            Customer customer = new Customer();
            customer.setCustomerId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"))));
            customer.setCustomerName(cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME")));
            customer.setDayOfBirth(cursor.getString(cursor.getColumnIndex("DATE_OF_BIRTH")));
            customer.setPhone(cursor.getString(cursor.getColumnIndex("PHONE")));
            customer.setSex(cursor.getString(cursor.getColumnIndex("SEX")));
            listCustomer.add(customer);
        }
        return listCustomer;
    }

    //Hiện thị dữ liệu
    @SuppressLint("Range")
    public ArrayList<Customer> getDataCustomer(){
        String sql = "SELECT * FROM CUSTOMER";

        return getListCustomer(sql);
    }

    //Lấy id Customerr
    public Customer getIdCustomer(String id){
        String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID=?";
        ArrayList<Customer> listCustomer = getListCustomer(sql, id);
        return listCustomer.get(0);
    }

    //Thêm dữ liệu
    public long insertCustomer(Customer customer){
        ContentValues values = new ContentValues();

       // values.put("CUSTOMER_ID", customer.getCustomerId());
        values.put("CUSTOMER_NAME", customer.getCustomerName());
        values.put("DATE_OF_BIRTH", customer.getDayOfBirth());
        values.put("PHONE", customer.getPhone());
        values.put("SEX", customer.getSex());

        long res = db.insert("CUSTOMER", null, values);
        return res;
    }

    //Sửa dữ liệu
    public int updateCustomer(Customer customer){
        ContentValues values = new ContentValues();

     //   values.put("CUSTOMER_ID", customer.getCustomerId());
        values.put("CUSTOMER_NAME", customer.getCustomerName());
        values.put("DATE_OF_BIRTH", customer.getDayOfBirth());
        values.put("PHONE", customer.getPhone());
        values.put("SEX", customer.getSex());

        int res = db.update("CUSTOMER", values, "CUSTOMER_ID=?",new String[]{String.valueOf(customer.getCustomerId())});
        return res;
    }

    //Xóa dữ liệu
    public int deleteCustomer(String id){
        Cursor cursor = db.rawQuery("SELECT * FROM BILL WHERE CUSTOMER_ID=?", new String[]{id});
        if (cursor.getCount() != 0){
            return -1; //Không thể xóa vì có trong bill
        }

        int res = db.delete("CUSTOMER", "CUSTOMER_ID=?", new String[]{id});
        if (res == -1)
            return 0; //Xóa thất bại
        return 1; //Xóa thành công
    }

    public ArrayList<Customer> searchCustomer(String name) {
        String sql="SELECT * FROM CUSTOMER WHERE CUSTOMER_NAME like '%"+name.trim()+"%' ";
        return getListCustomer(sql);
    }
}
