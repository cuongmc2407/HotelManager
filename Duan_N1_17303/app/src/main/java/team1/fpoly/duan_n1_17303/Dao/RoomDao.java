package team1.fpoly.duan_n1_17303.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Database.MyDatabase;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.Object.Staff;

public class RoomDao {

    SQLiteDatabase db;
    public RoomDao(Context context) {
        MyDatabase myDatabase = new MyDatabase(context);
        db = myDatabase.getWritableDatabase();
    }

    //hiển thị dl
    public ArrayList<Room> getListRoom(String sql, String...selectionArgs){
        ArrayList<Room> listRoom = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            do{
                listRoom.add(new Room(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3), cursor.getString(4), cursor.getInt(5),cursor.getInt(6),cursor.getString(7)));
            }while (cursor.moveToNext());
        }
        return listRoom;
    }

    //Hiện thị dữ liệu
    @SuppressLint("Range")
    public ArrayList<Room> getDataRoom(){
        String sql = "SELECT * FROM ROOM";

        return getListRoom(sql);
    }

    //Lấy id Room
    public Room getIdRoom(String id){
        String sql = "SELECT * FROM ROOM WHERE ROOM_ID=?";
        ArrayList<Room> listRoom = getListRoom(sql, id);
        return listRoom.get(0);
    }

    //Tìm kiếm dữ liệu Room
    public ArrayList<Room> searchRoom(String name){
        String sql="SELECT * FROM ROOM WHERE ROOM_NAME like '%"+name.trim()+"%' ";
        return getListRoom(sql);
    }

    //thêm dl
    public boolean insertRoom(Room room){

        ContentValues values = new ContentValues();
        values.put("ROOM_NAME", room.getRoomName());
        values.put("ROOM_LOCATION", room.getRoomLocation());
        values.put("ROOM_TYPE", room.getRoomType());
        values.put("ROOM_VIEW", room.getRoomView());
        values.put("DAY_PRICE", room.getDayPrice());
        values.put("HOUR_PRICE", room.getHourPrice());
        values.put("NOTE", room.getNote());
        long check = db.insert("ROOM", null, values);
        if(check == -1)
            return false;
        return true;
    }

    //sửa dl
    public boolean updateRoom(Room room){

        ContentValues values = new ContentValues();
        values.put("ROOM_NAME", room.getRoomName());
        values.put("ROOM_LOCATION", room.getRoomLocation());
        values.put("ROOM_TYPE", room.getRoomType());
        values.put("ROOM_VIEW", room.getRoomView());
        values.put("DAY_PRICE", room.getDayPrice());
        values.put("HOUR_PRICE", room.getHourPrice());
        values.put("NOTE", room.getNote());
        long check = db.update("ROOM",values,"ROOM_ID = ?", new String[]{String.valueOf(room.getRoomId())});
        if(check==-1)
            return false;
        return true;
    }

    //1: xóa thành công- 0: thất bại - -1: đang có trong bill hoặc room_product
    public int deleteRoom(String id){
        Cursor cursor = db.rawQuery("SELECT * FROM BILL WHERE ROOM_ID = ?", new String[]{id});
        if(cursor.getCount()!=0){
            return -1;
        }
        cursor.close();
        db.delete("ROOM_PRODUCT", "ROOM_ID=?",new String[]{id});
        long check = db.delete("ROOM", "ROOM_ID = ?", new String[]{id});
        if(check == -1)
            return 0;
        return 1;
    }
}
