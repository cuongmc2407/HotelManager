package team1.fpoly.duan_n1_17303.Dao;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Database.MyDatabase;
import team1.fpoly.duan_n1_17303.Object.ProductBill;


@SuppressLint("Range")
public class ProductBillDao {
    SQLiteDatabase db;

    public ProductBillDao(Context context){
        MyDatabase myDatabase = new MyDatabase(context);
        db = myDatabase.getWritableDatabase();
    }

    //Hiện thị dữ liệu
    public ArrayList<ProductBill> getDataProductBill(){
        ArrayList<ProductBill> listProductBill = new ArrayList<>();
        String sql = "SELECT * FROM BILL_PRODUCT";
        Cursor cursor =db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            ProductBill productbill = new ProductBill();
            productbill.setProductId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))));
            productbill.setProductId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("BILL_ID"))));
            productbill.setProductBillName(cursor.getString(cursor.getColumnIndex("PRODUCT_NAME")));
            productbill.setProductBillPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRICE"))));
            productbill.setProductBillQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("QUANTITY"))));
            listProductBill.add(productbill);
        }
        return listProductBill;
    }

    public ArrayList<ProductBill> getDataProductBillByID(int BILL_ID){
        ArrayList<ProductBill> listProductBill = new ArrayList<>();
        Cursor cursor =db.rawQuery("SELECT * FROM BILL_PRODUCT WHERE BILL_ID=?", new String[]{String.valueOf(BILL_ID)});
        while (cursor.moveToNext()){
            ProductBill productBill = new ProductBill();
            productBill.setProductId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))));
            productBill.setProductId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("BILL_ID"))));
            productBill.setProductBillName(cursor.getString(cursor.getColumnIndex("PRODUCT_NAME")));
            productBill.setProductBillPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRICE"))));
            productBill.setProductBillQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("QUANTITY"))));
            listProductBill.add(productBill);
        }
        return listProductBill;
    }
    //thêm dl
    public boolean insertProductBill(ProductBill productBill){
        ContentValues values = new ContentValues();
        values.put("PRODUCT_ID", productBill.getProductId());
        values.put("BILL_ID", productBill.getBillId());
        values.put("PRODUCT_NAME", productBill.getProductBillName());
        values.put("PRICE", productBill.getProductBillPrice());
        values.put("QUANTITY", productBill.getProductBillQuantity());
        long check = db.insert("BILL_PRODUCT", null, values);
        if(check == -1)
            return false;
        return true;
    }


    public int deleteProductBillByID(String id){
        int res = db.delete("BILL_PRODUCT", "BILL_ID=?",new String[]{id});
        return res;
    }
    public int deleteProductBillByProductID(String id){
        int res = db.delete("BILL_PRODUCT", "PRODUCT_ID=?",new String[]{id});
        return res;
    }
}
