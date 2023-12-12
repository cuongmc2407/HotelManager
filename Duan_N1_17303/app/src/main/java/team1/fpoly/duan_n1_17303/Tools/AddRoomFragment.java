package team1.fpoly.duan_n1_17303.Tools;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.ProductDao;
import team1.fpoly.duan_n1_17303.Dao.ProductRoomDAO;
import team1.fpoly.duan_n1_17303.Dao.RoomDao;
import team1.fpoly.duan_n1_17303.Fragment.CustomerFragment;
import team1.fpoly.duan_n1_17303.Fragment.RoomFragment;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.Product;
import team1.fpoly.duan_n1_17303.Object.ProductRoom;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;

public class AddRoomFragment extends Fragment {
    Spinner spinnerRoomType;
    Spinner spinnerRoomView;
    FragmentManager fm;
    RoomFragment roomFragment;
    ImageView img_Back_Room, img_Save_Room;
    EditText ed_Room_Name, ed_Room_Location, ed_Room_Hour_Price, ed_Room_Day_Price;

    team1.fpoly.duan_n1_17303.Dao.RoomDao roomDao;
    String room_type;
    String room_view;

    public AddRoomFragment() {
        // Required empty public constructor
    }

    public static AddRoomFragment newInstance() {
        AddRoomFragment fragment = new AddRoomFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fm = getActivity().getSupportFragmentManager();
        roomFragment = new RoomFragment();
        {
            spinnerRoomType = (Spinner) view.findViewById(R.id.spinner_Room_Type);
            ArrayAdapter<CharSequence> adapter_Room_Type = ArrayAdapter.createFromResource(getContext(),
                    R.array.room_type, android.R.layout.simple_spinner_item);
            adapter_Room_Type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRoomType.setAdapter(adapter_Room_Type);
            spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    room_type = (getResources().getStringArray(R.array.room_type))[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    room_type = (getResources().getStringArray(R.array.room_type))[0];
                }
            });
        }
        {
            spinnerRoomView = (Spinner) view.findViewById(R.id.spinner_Room_View);
            ArrayAdapter<CharSequence> spinner_Room_View = ArrayAdapter.createFromResource(getContext(),
                    R.array.room_view, android.R.layout.simple_spinner_item);
            spinner_Room_View.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRoomView.setAdapter(spinner_Room_View);
            spinnerRoomView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    room_view = (getResources().getStringArray(R.array.room_view))[position];

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    room_view = (getResources().getStringArray(R.array.room_view))[0];

                }
            });
        }
        img_Back_Room = view.findViewById(R.id.img_Back_Room);
        img_Save_Room = view.findViewById(R.id.img_Save_Room);
        ed_Room_Name = view.findViewById(R.id.ed_Room_Name);
        ed_Room_Location = view.findViewById(R.id.ed_Room_Location);

        ed_Room_Hour_Price = view.findViewById(R.id.ed_Room_Price_Hour);
        ed_Room_Day_Price = view.findViewById(R.id.ed_Room_Price_Day);
        img_Back_Room.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            ActionBar actionBar = mainActivity.getSupportActionBar();
            actionBar.show();
            fm.beginTransaction().replace(R.id.frameLayout, roomFragment).commit();
        });
        img_Save_Room.setOnClickListener(v -> {
            saveDataRoom();
        });
    }

    private void saveDataRoom() {
        ProductDao productDao = new ProductDao(getContext());
        ArrayList<Product> liProduct= productDao.getListProduct();
        ProductRoomDAO productRoomDAO = new ProductRoomDAO(getContext());
        ArrayList<ProductRoom> liProductRoom= productRoomDAO.getDataProductRoom();


        roomDao = new RoomDao(getActivity());
        Room r = new Room();
        String id = null;
        String name = ed_Room_Name.getText().toString();
        String location;

        location = ed_Room_Location.getText().toString();
        String type = room_type;
        String view = room_view;
        String price_hour;
        price_hour =  ed_Room_Hour_Price.getText().toString();
        String price_day;
        price_day =  ed_Room_Day_Price.getText().toString();
        if (name.isEmpty()||location.isEmpty()||type.isEmpty()||view.isEmpty()||price_day.isEmpty()||price_hour.isEmpty()){
            Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        r.setRoomName(name);
        r.setRoomLocation(Integer.parseInt(location));
        r.setRoomType(room_type);
        r.setRoomView(room_view);
        r.setHourPrice(Integer.parseInt(price_hour));
        r.setDayPrice(Integer.parseInt(price_day));
        if (roomDao.insertRoom(r) ) {
            Toast.makeText(getActivity(), "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
            for(Product x:liProduct){
                ArrayList<Room> listRoom = roomDao.getDataRoom();
                ProductRoom newPR = new ProductRoom(x.getProductId(),
                            listRoom.get(listRoom.size()-1)
                                    .getRoomId(),x
                                    .getProductName(),x
                                    .getPrice(),0);
                productRoomDAO.insertProductRoom(newPR);
            }
            Toast.makeText(getActivity(), "Thêm sản phẩm phòng thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Thêm phòng thất bại", Toast.LENGTH_SHORT).show();
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        actionBar.show();
        fm.beginTransaction().replace(R.id.frameLayout, roomFragment).commit();
    }
}