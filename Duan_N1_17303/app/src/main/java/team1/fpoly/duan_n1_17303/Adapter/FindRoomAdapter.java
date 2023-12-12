package team1.fpoly.duan_n1_17303.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.ProductRoomDAO;
import team1.fpoly.duan_n1_17303.Dao.RoomDao;

import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;

public class FindRoomAdapter extends ArrayAdapter<Room> {
    Context context;
    ArrayList<Room> listRoom;


    TextView tv_Room_Name, tv_Location, tv_Type, tv_View, tv_Hour_Price, tv_Day_Price, tv_Note;
    ImageView iv_DeleteRoom, iv_Room_Product;

    public FindRoomAdapter(Context context, ArrayList<Room> listRoom, RoomDao roomDao) {
        super(context, 0, listRoom);
        this.context = context;
        this.listRoom = listRoom;

    }

    @Override
    public int getCount() {
        if (!listRoom.isEmpty()) return listRoom.size();
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        }
        final Room room = listRoom.get(position);
        if (room != null) {


            tv_Room_Name = convertView.findViewById(R.id.tv_Room_Name);
            tv_Location = convertView.findViewById(R.id.tv_Location);
            tv_Type = convertView.findViewById(R.id.tv_Type);
            tv_View = convertView.findViewById(R.id.tv_View);
            tv_Hour_Price = convertView.findViewById(R.id.tv_Hour_Price);
            tv_Day_Price = convertView.findViewById(R.id.tv_Day_Price);
            iv_DeleteRoom = convertView.findViewById(R.id.iv_DeleteRoom);
            iv_Room_Product = convertView.findViewById(R.id.iv_Room_Product);
            tv_Room_Name.setText("Tên phòng: " + room.getRoomName());
            tv_Location.setText("Vị trí phòng: " + room.getRoomLocation());
            tv_Type.setText("Loại phòng: " + room.getRoomType());
            tv_View.setText("View phòng: " + room.getRoomView());
            tv_Hour_Price.setText("Giá giờ: " + room.getHourPrice());
            tv_Day_Price.setText("Giá ngày: " + room.getDayPrice());

        }

        return convertView;
    }
}
