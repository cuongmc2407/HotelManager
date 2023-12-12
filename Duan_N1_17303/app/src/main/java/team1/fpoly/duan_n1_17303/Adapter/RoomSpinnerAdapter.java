package team1.fpoly.duan_n1_17303.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;

public class RoomSpinnerAdapter extends ArrayAdapter<Room> {
    Context context;
    ArrayList<Room> listRoom;
    TextView tv_Spn_Room_Name;

    public RoomSpinnerAdapter( Context context, ArrayList<Room> listRoom) {
        super(context, 0, listRoom);
        this.context = context;
        this.listRoom = listRoom;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view =inflater.inflate(R.layout.item_spn_room_bill, parent, false);
        }

        final Room room = listRoom.get(position);

        if (room != null){
            tv_Spn_Room_Name = view.findViewById(R.id.tv_Spn_Room_Name);
            tv_Spn_Room_Name.setText(room.getRoomName());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view =inflater.inflate(R.layout.item_spn_room_bill, parent, false);
        }

        final Room room = listRoom.get(position);

        if (room != null){
            tv_Spn_Room_Name = view.findViewById(R.id.tv_Spn_Room_Name);
            tv_Spn_Room_Name.setText(room.getRoomName());
        }
        return view;
    }
}
