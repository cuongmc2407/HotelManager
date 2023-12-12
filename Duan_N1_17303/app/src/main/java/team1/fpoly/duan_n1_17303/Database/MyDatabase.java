package team1.fpoly.duan_n1_17303.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {
    private static int VERSION = 1;
    public MyDatabase(Context context){
        super(context, "HOTELMANAGER", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbStaff = "create table STAFF" +
                "(STAFF_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "STAFF_USERNAME TEXT not null, " +
                "STAFF_PASSWORD TEXT not null, " +
                "DISPLAY_NAME TEXT NOT NULL, " +
                "DATE_OF_BIRTH Date , " +
                "SEX TEXT, " +
                "PHONE TEXT)";
        sqLiteDatabase.execSQL(tbStaff);
        String sql = "INSERT INTO STAFF(STAFF_ID, STAFF_USERNAME, STAFF_PASSWORD, DISPLAY_NAME, DATE_OF_BIRTH, SEX, PHONE) VALUES " +
                "('1','admin','123','ADMIN', null, null, null)," +
                "('2','kienpcph23267','kien123','Phùng Chí Kiên','19/06/2003', 'Nam', '0979896589')";
        sqLiteDatabase.execSQL(sql);

        String tbCustomer = "create table CUSTOMER" +
                "(CUSTOMER_ID INTEGER primary key AUTOINCREMENT, " +
                "CUSTOMER_NAME TEXT not null, " +
                "DATE_OF_BIRTH Date not null, " +
                "PHONE TEXT  not null, " +
                "SEX TEXT not null)";
        sqLiteDatabase.execSQL(tbCustomer);
        //sql = "INSERT INTO CUSTOMER(CUSTOMER_ID, CUSTOMER_NAME, DATE_OF_BIRTH, PHONE, SEX) VALUES " +
        //        "('1','Phùng Chí Kiên','19/06/2003','0979896589','Nam')," +
        //        "('2','Lê Thu Thảo','14/08/2005','0979896589','Nữ')";
        //sqLiteDatabase.execSQL(sql);

        String tbRoom = "CREATE TABLE ROOM" +
                "(ROOM_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ROOM_NAME TEXT NOT NULL," +
                "ROOM_LOCATION INT NOT NULL," +
                "ROOM_TYPE TEXT NOT NULL," +
                "ROOM_VIEW TEXT NOT NULL," +
                "DAY_PRICE INTEGER NOT NULL," +
                "HOUR_PRICE INTEGER NOT NULL," +
                "NOTE TEXT)";
        sqLiteDatabase.execSQL(tbRoom);


        String tbProduct = "CREATE TABLE PRODUCT" +
                "(PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "PRODUCT_NAME TEXT NOT NULL," +
                "PRICE INTEGER NOT NULL," +
                "QUANTITY INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(tbProduct);


        String tbRoomProduct = "CREATE TABLE ROOM_PRODUCT" +
                "(PRODUCT_ID INTEGER REFERENCES PRODUCT(PRODUCT_ID)," +
                "ROOM_ID INTEGER REFERENCES ROOM(ROOM_ID)," +
                "PRODUCT_NAME TEXT NOT NULL," +
                "PRICE INTEGER NOT NULL," +
                "QUANTITY INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(tbRoomProduct);

        String tbBillProduct = "CREATE TABLE BILL_PRODUCT" +
                "(PRODUCT_ID INTEGER REFERENCES PRODUCT(PRODUCT_ID)," +
                "BILL_ID INTEGER REFERENCES BILL(BILL_ID)," +
                "PRODUCT_NAME TEXT NOT NULL," +
                "PRICE INTEGER NOT NULL," +
                "QUANTITY INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(tbBillProduct);

        String tbBill = "CREATE TABLE BILL" +
                "(BILL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "STAFF_ID INTEGER REFERENCES STAFF(STAFF_ID)," +
                "CUSTOMER_ID INTEGER REFERENCES CUSTOMER(CUSTOMER_ID)," +
                "ROOM_ID INTEGER REFERENCES ROOM(ROOM_ID)," +
                "DATE_CHECK_IN TEXT NOT NULL," +
                "TIME_CHECK_IN TEXT NOT NULL," +
                "DATE_CHECK_OUT TEXT NOT NULL," +
                "TIME_CHECK_OUT TEXT NOT NULL," +
                "MONEY INTEGER," +
                "NOTE TEXT)";
        sqLiteDatabase.execSQL(tbBill);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS STAFF");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CUSTOMER");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ROOM");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ROOM_PRODUCT");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BILL_PRODUCT");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PRODUCT");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BILL");
            onCreate(sqLiteDatabase);
        }
    }
}
