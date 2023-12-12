package team1.fpoly.duan_n1_17303.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Database.MyDatabase;
import team1.fpoly.duan_n1_17303.Object.Staff;

public class StaffDao {
    SQLiteDatabase db;

    public StaffDao(Context context){
        MyDatabase myDatabase = new MyDatabase(context);
        db = myDatabase.getWritableDatabase();
    }


    @SuppressLint("Range")
    public ArrayList<Staff> getData(String sql, String...selectionArgs){
        ArrayList<Staff> listStaff = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            Staff staff = new Staff();
            staff.setStaffId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("STAFF_ID"))));
            staff.setStaffUsername(cursor.getString(cursor.getColumnIndex("STAFF_USERNAME")));
            staff.setStaffPassword(cursor.getString(cursor.getColumnIndex("STAFF_PASSWORD")));
            staff.setDisplayName(cursor.getString(cursor.getColumnIndex("DISPLAY_NAME")));
            staff.setDayOfBirth(cursor.getString(cursor.getColumnIndex("DATE_OF_BIRTH")));
            staff.setSex(cursor.getString(cursor.getColumnIndex("SEX")));
            staff.setPhone(cursor.getString(cursor.getColumnIndex("PHONE")));
            listStaff.add(staff);
        }
        return listStaff;
    }
    //Hiện thị dữ liệu
    @SuppressLint("Range")
    public ArrayList<Staff> getDataStaff(){
        String sql = "SELECT * FROM STAFF";

        return getData(sql);
    }

    //Lấy id Staff
    public Staff getIdStaff(String id){
        String sql = "SELECT * FROM STAFF WHERE STAFF_USERNAME=?";
        ArrayList<Staff> listStaff = getData(sql, id);
        return listStaff.get(0);
    }
    //Lấy id Staff
    public Staff getIdStaff_2(String id){
        String sql = "SELECT * FROM STAFF WHERE STAFF_ID=?";
        ArrayList<Staff> listStaff = getData(sql, id);
        return listStaff.get(0);
    }

    //Thêm dữ liệu
    public long insertStaff(Staff staff){
        ContentValues values = new ContentValues();
        values.put("STAFF_USERNAME", staff.getStaffUsername());
        values.put("STAFF_PASSWORD", staff.getStaffPassword());
        values.put("DISPLAY_NAME", staff.getDisplayName());
        values.put("DATE_OF_BIRTH", staff.getDayOfBirth());
        values.put("SEX", staff.getSex());
        values.put("PHONE", staff.getPhone());

        long res = db.insert("STAFF", null, values);
        return res;
    }

    //Cập nhật dữ liệu
    public int updateStaff(Staff staff){
        ContentValues values = new ContentValues();

        values.put("STAFF_USERNAME", staff.getStaffUsername());
        values.put("STAFF_PASSWORD", staff.getStaffPassword());
        values.put("DISPLAY_NAME", staff.getDisplayName());
        values.put("DATE_OF_BIRTH", staff.getDayOfBirth());
        values.put("SEX", staff.getSex());
        values.put("PHONE", staff.getPhone());

        int res = db.update("STAFF", values, "STAFF_ID=?",new String[]{String.valueOf(staff.getStaffId())});
        return res;
    }

    //Xóa dữ liệu
    public int deleteStaff(String id){
        int res = db.delete("STAFF", "STAFF_ID=?",new String[]{id});
        return res;
    }

    public ArrayList<Staff> searchStaff(String name) {
        String sql="SELECT * FROM STAFF WHERE DISPLAY_NAME like '%"+name.trim()+"%' ";
        return getData(sql);
    }
}
