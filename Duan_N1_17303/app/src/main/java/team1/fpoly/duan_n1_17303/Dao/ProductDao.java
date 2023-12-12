package team1.fpoly.duan_n1_17303.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Database.MyDatabase;
import team1.fpoly.duan_n1_17303.Object.Product;

public class ProductDao {
    MyDatabase myDatabase;
    public ProductDao(Context context){
        myDatabase = new MyDatabase(context);
    }

    //hiển thị dữ liệu
    public ArrayList<Product> getListProduct(){
        ArrayList<Product> listProduct = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDatabase.getReadableDatabase();
        String sql = "SELECT * FROM PRODUCT";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            do {
                listProduct.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return listProduct;
    }

    //thêm dl
    public boolean insertProduct(Product product){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PRODUCT_NAME", product.getProductName());
        values.put("PRICE", product.getPrice());
        values.put("QUANTITY", product.getQuantity());
        long check = sqLiteDatabase.insert("PRODUCT", null, values);
        if(check == -1)
            return false;
        return true;
    }

    //sửa dl
    public boolean updateProduct(Product product){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PRODUCT_NAME", product.getProductName());
        values.put("PRICE", product.getPrice());
        values.put("QUANTITY", product.getQuantity());
        long check = sqLiteDatabase.update("PRODUCT",values,"PRODUCT_ID = ?", new String[]{String.valueOf(product.getProductId())});
        if(check==-1)
            return false;
        return true;
    }
    //1: xóa thành công- 0: thất bại - -1: đang có trong bill_product hoặc room_product
    public int deleteProduct(int id){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ROOM_PRODUCT WHERE PRODUCT_ID = ?", new String[]{String.valueOf(id)});
        if(cursor.getCount()!=0){
            return -1;
        }
        Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT * FROM BILL_PRODUCT WHERE PRODUCT_ID = ?", new String[]{String.valueOf(id)});
        if(cursor1.getCount()!=0){
            return -1;
        }

        long check = sqLiteDatabase.delete("PRODUCT", "PRODUCT_ID = ?", new String[]{String.valueOf(id)});
        if(check == -1)
            return 0;
        return 1;
    }
}
