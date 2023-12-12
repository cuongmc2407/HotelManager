package team1.fpoly.duan_n1_17303.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Database.MyDatabase;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.Product;
import team1.fpoly.duan_n1_17303.Object.ProductRoom;

@SuppressLint("Range")
public class ProductRoomDAO {
    SQLiteDatabase db;

    public ProductRoomDAO(Context context){
        MyDatabase myDatabase = new MyDatabase(context);
        db = myDatabase.getWritableDatabase();
    }

    //Hiện thị dữ liệu
    public ArrayList<ProductRoom> getDataProductRoom(){
        ArrayList<ProductRoom> listProductRoom = new ArrayList<>();
        String sql = "SELECT * FROM ROOM_PRODUCT";
        Cursor cursor =db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            ProductRoom productRoom = new ProductRoom();
            productRoom.setProductId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))));
            productRoom.setProductId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ROOM_ID"))));
            productRoom.setProductRoomName(cursor.getString(cursor.getColumnIndex("PRODUCT_NAME")));
            productRoom.setProductRoomPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRICE"))));
            productRoom.setProductRoomQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("QUANTITY"))));
            listProductRoom.add(productRoom);
        }
        return listProductRoom;
    }

    public ArrayList<ProductRoom> getDataProductRoomByID(int ROOM_ID){
        ArrayList<ProductRoom> listProductRoom = new ArrayList<>();
        Cursor cursor =db.rawQuery("SELECT * FROM ROOM_PRODUCT WHERE ROOM_ID=?", new String[]{String.valueOf(ROOM_ID)});
        while (cursor.moveToNext()){
            ProductRoom productRoom = new ProductRoom();
            productRoom.setProductId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))));
            productRoom.setProductId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ROOM_ID"))));
            productRoom.setProductRoomName(cursor.getString(cursor.getColumnIndex("PRODUCT_NAME")));
            productRoom.setProductRoomPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRICE"))));
            productRoom.setProductRoomQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("QUANTITY"))));
            listProductRoom.add(productRoom);
        }
        return listProductRoom;
    }
    //thêm dl
    public boolean insertProductRoom(ProductRoom productRoom){
        ContentValues values = new ContentValues();
        values.put("PRODUCT_ID", productRoom.getProductId());
        values.put("ROOM_ID", productRoom.getRoomId());
        values.put("PRODUCT_NAME", productRoom.getProductRoomName());
        values.put("PRICE", productRoom.getProductRoomPrice());
        values.put("QUANTITY", productRoom.getProductRoomQuantity());
        long check = db.insert("ROOM_PRODUCT", null, values);
        if(check == -1)
            return false;
        return true;
    }

    //sửa dl
    public boolean updateProductRoom(ProductRoom productRoom){
        ContentValues values = new ContentValues();
        values.put("PRODUCT_ID", productRoom.getProductId());
        values.put("ROOM_ID", productRoom.getRoomId());
        values.put("PRODUCT_NAME", productRoom.getProductRoomName());
        values.put("PRICE", productRoom.getProductRoomPrice());
        values.put("QUANTITY", productRoom.getProductRoomQuantity());
        long check = db.update("ROOM_PRODUCT",values,"ROOM_ID = ?", new String[]{String.valueOf(productRoom.getRoomId())});
        if(check==-1)
            return false;
        return true;
    }
    public int deleteProductRoomByID(String id){
        int res = db.delete("ROOM_PRODUCT", "ROOM_ID=?",new String[]{id});
        return res;
    }
    public int deleteProductRoomByProductID(String id){
        int res = db.delete("ROOM_PRODUCT", "PRODUCT_ID=?",new String[]{id});
        return res;
    }
}
