package team1.fpoly.duan_n1_17303.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Adapter.ProductAdapter;
import team1.fpoly.duan_n1_17303.Adapter.RoomAdapter;
import team1.fpoly.duan_n1_17303.Dao.RoomDao;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Product;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;
import team1.fpoly.duan_n1_17303.Tools.AddRoomFragment;

public class RoomFragment extends Fragment {

    ListView lv_Room;
    FloatingActionButton fl_Add_Room;
    AddRoomFragment addRoomFragment;
    ArrayList<Room> listRoom;
    Room room;
    RoomAdapter roomAdapter;
    RoomDao roomDao;
    EditText ed_Room_Search;
    Button btnSearchRoom;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv_Room = view.findViewById(R.id.lv_Room);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fl_Add_Room = view.findViewById(R.id.fl_Add_Room);
        addRoomFragment  = new AddRoomFragment();
        listRoom = new ArrayList<>();
        roomDao = new RoomDao(getContext());
        loadData();
        lv_Room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                room = listRoom.get(position);
                updateData(position);
                loadData();

            }
        });


        fl_Add_Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                ActionBar actionBar =  mainActivity.getSupportActionBar();
                actionBar.hide();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, addRoomFragment).commit();
            }
        });

        ed_Room_Search = view.findViewById(R.id.ed_Room_Search);
        btnSearchRoom = view.findViewById(R.id.btnSearchRoom);


        btnSearchRoom.setOnClickListener(v -> {
            String roomName = ed_Room_Search.getText().toString();
            searchRoom(roomName);
        });
    }

    private void searchRoom(String name) {
        ArrayList<Room> listNew = roomDao.searchRoom(name);
        if (listNew.isEmpty()){
            Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        roomAdapter.setData(listNew);
    }

    private void updateData(int position) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_add_room);
        TextView tv_Title_Room = dialog.findViewById(R.id.tv_Title_Room);
        TextView tv_Room_Type = dialog.findViewById(R.id.tv_Room_Type);
        TextView tv_Room_View = dialog.findViewById(R.id.tv_Room_View);
        EditText ed_Room_Name = dialog.findViewById(R.id.ed_Room_Name);
        EditText ed_Room_Location = dialog.findViewById(R.id.ed_Room_Location);
        EditText ed_Room_Price_Day = dialog.findViewById(R.id.ed_Room_Price_Day);
        EditText ed_Room_Price_Hour = dialog.findViewById(R.id.ed_Room_Price_Hour);
        ImageView img_Back_Room = dialog.findViewById(R.id.img_Back_Room);
        ImageView img_Save_Room = dialog.findViewById(R.id.img_Save_Room);

        final String[] room_type = new String[1];
        final String[] room_view = new String[1];
        img_Back_Room.setOnClickListener(view -> {
            dialog.dismiss();
        });
        img_Save_Room.setOnClickListener(view -> {
            Room room = listRoom.get(position);
            {
                Spinner spinnerRoomType = (Spinner) view.findViewById(R.id.spinner_Room_Type);
                ArrayAdapter<CharSequence> adapter_Room_Type = ArrayAdapter.createFromResource(getContext(),
                        R.array.room_type, android.R.layout.simple_spinner_item);
                adapter_Room_Type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRoomType.setAdapter(adapter_Room_Type);
                spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        room_type[0] = (getResources().getStringArray(R.array.room_type))[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        room_type[0] = (getResources().getStringArray(R.array.room_type))[0];
                    }
                });
            }
            {
                Spinner spinnerRoomView = (Spinner) view.findViewById(R.id.spinner_Room_View);
                ArrayAdapter<CharSequence> spinner_Room_View = ArrayAdapter.createFromResource(getContext(),
                        R.array.room_view, android.R.layout.simple_spinner_item);
                spinner_Room_View.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRoomView.setAdapter(spinner_Room_View);
                spinnerRoomView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        room_view[0] = (getResources().getStringArray(R.array.room_view))[position];

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        room_view[0] = (getResources().getStringArray(R.array.room_view))[0];

                    }
                });
            }

            tv_Room_Type.setText(room_type[0]);
            tv_Room_View.setText(room_view[0]);

            room.setRoomName(ed_Room_Name.getText().toString());
            room.setRoomLocation(Integer.parseInt(ed_Room_Location.getText().toString()));
            room.setDayPrice(Integer.parseInt(ed_Room_Price_Day.getText().toString()));
            room.setHourPrice(Integer.parseInt(ed_Room_Price_Hour.getText().toString()));


            if (roomDao.updateRoom(room)){
                roomAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Sửa thông tin phòng thành công", Toast.LENGTH_SHORT).show();

            }else Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void loadData() {
        listRoom.clear();
        listRoom = roomDao.getDataRoom();
        roomAdapter = new RoomAdapter(getContext(), listRoom,roomDao);
        lv_Room.setAdapter(roomAdapter);
    }


}