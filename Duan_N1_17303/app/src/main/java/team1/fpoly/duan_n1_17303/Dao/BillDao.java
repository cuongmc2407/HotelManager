package team1.fpoly.duan_n1_17303.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Database.MyDatabase;
import team1.fpoly.duan_n1_17303.Object.Bill;
import team1.fpoly.duan_n1_17303.Object.ThongKe;

public class BillDao {
    SQLiteDatabase db;
    MyDatabase myDatabase;
    public BillDao(Context context){
        myDatabase = new MyDatabase(context);
        db = myDatabase.getWritableDatabase();
    }


    public ArrayList<Bill> getListBill(){
        ArrayList<Bill> listBill = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDatabase.getReadableDatabase();
        String sql = "SELECT * FROM BILL";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            do {
                listBill.add(new Bill(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getString(9)));
            }while (cursor.moveToNext());
        }
        return listBill;
    }
    public Bill getDataBillByBillID(int id){
        Bill bill = new Bill();
        SQLiteDatabase sqLiteDatabase = myDatabase.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("SELECT * FROM BILL WHERE BILL_ID=?", new String[]{String.valueOf(id)});
        while (cursor.moveToNext()){
            bill.setBillId(cursor.getInt(0));
            bill.setStaffId(cursor.getInt(1));
            bill.setCustomerId(cursor.getInt(2));
            bill.setRoomId(cursor.getInt(3));
            bill.setDateCheckIn(cursor.getString(4));
            bill.setTimeCheckIn(cursor.getString(5));
            bill.setDateCheckOut(cursor.getString(6));
            bill.setTimeCheckOut(cursor.getString(7));
            bill.setMoney(cursor.getInt(8));
        }
        return bill;
    }

    public boolean insertBill(Bill bill){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("STAFF_ID", bill.getStaffId());
        values.put("CUSTOMER_ID",bill.getCustomerId() );
        values.put("ROOM_ID", bill.getRoomId());
        values.put("DATE_CHECK_IN", bill.getDateCheckIn());
        values.put("TIME_CHECK_IN", bill.getTimeCheckIn());
        values.put("DATE_CHECK_OUT", bill.getDateCheckOut());
        values.put("TIME_CHECK_OUT", bill.getTimeCheckOut());
        values.put("NOTE", bill.getNote());
        long check = sqLiteDatabase.insert("BILL", null, values);
        if(check == -1)
            return false;
        return true;
    }

    //sửa dl
    public boolean updateBill(Bill bill){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("STAFF_ID", bill.getStaffId());
        values.put("CUSTOMER_ID",bill.getCustomerId() );
        values.put("ROOM_ID", bill.getRoomId());
        values.put("DATE_CHECK_IN", bill.getDateCheckIn());
        values.put("TIME_CHECK_IN", bill.getTimeCheckIn());
        values.put("DATE_CHECK_OUT", bill.getDateCheckOut());
        values.put("TIME_CHECK_OUT", bill.getTimeCheckOut());
        values.put("MONEY", bill.getMoney());
        values.put("NOTE", bill.getNote());
        long check = sqLiteDatabase.update("BILL",values,"BILL_ID = ?",
                new String[]{String.valueOf(bill.getBillId())});
        if(check==-1)
            return false;
        return true;
    }
    public int deleteBill(String id){
        int check = db.delete("BILL", "BILL_ID = ?", new String[]{id});
        return check;
    }
    public ArrayList<ThongKe> getTotalMoneyOfYear(String year){
        ArrayList<ThongKe> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDatabase.getReadableDatabase();
        Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT substr(date_check_out,3,1), SUM(money) FROM BILL WHERE substr(date_check_out,5)=?GROUP BY substr(date_check_out,3,1) ORDER BY substr(date_check_out,3,1) ASC", new String[]{year});
        if(cursor1.getCount() !=0){
            cursor1.moveToFirst();
            do {
                list.add(new ThongKe(cursor1.getInt(0), cursor1.getLong(1)));
            }while (cursor1.moveToNext());
        }
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT substr(date_check_out,3,2), SUM(money) FROM BILL WHERE substr(date_check_out,6)=? GROUP BY substr(date_check_out,3,2) ORDER BY substr(date_check_out,3,2) ASC", new String[]{year});
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            do {
                list.add(new ThongKe(cursor.getInt(0), cursor.getLong(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }
}
