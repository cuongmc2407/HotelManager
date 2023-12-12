package team1.fpoly.duan_n1_17303.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import team1.fpoly.duan_n1_17303.Adapter.FindRoomAdapter;
import team1.fpoly.duan_n1_17303.Adapter.RoomAdapter;
import team1.fpoly.duan_n1_17303.Dao.BillDao;
import team1.fpoly.duan_n1_17303.Dao.RoomDao;
import team1.fpoly.duan_n1_17303.Object.Bill;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;

public class SearchRoomFragment extends Fragment {

    EditText ed_Date_In, ed_Date_Out, ed_Hour_Our, ed_Hour_In;
    Button btn_Search;
    ListView lv_Search_Room;
    ImageView iv_datePick_In, iv_findRoom_Hour_in;
    ImageView iv_datePick_Out, iv_findRoom_Hour_Our;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ed_Date_In = view.findViewById(R.id.ed_Date_In);
        ed_Date_Out = view.findViewById(R.id.ed_Date_Out);
        ed_Hour_Our = view.findViewById(R.id.ed_Hour_Our);
        ed_Hour_In = view.findViewById(R.id.ed_Hour_In);
        ed_Date_In.setEnabled(false);
        ed_Date_Out.setEnabled(false);
        btn_Search = view.findViewById(R.id.btn_findRoom_find);
        lv_Search_Room = view.findViewById(R.id.lv_Search_Room);
        iv_datePick_In = view.findViewById(R.id.iv_findRoom_Date_In);
        iv_datePick_Out = view.findViewById(R.id.iv_findRoom_Date_Out);
        iv_findRoom_Hour_in = view.findViewById(R.id.iv_findRoom_Hour_in);
        iv_findRoom_Hour_Our = view.findViewById(R.id.iv_findRoom_Hour_Our);

        iv_findRoom_Hour_Our.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_time_picker);
            TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.dialog_time_picker_TimePicker);
            timePicker.setIs24HourView(true);
            Button btnSubmit = dialog.findViewById(R.id.dialog_time_picker_buttonSubmit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String hour = String.valueOf(timePicker.getHour());
                    String minute = String.valueOf(timePicker.getMinute());

                    String time_picked = hour + ":" + minute;
                    ed_Hour_Our.setText(time_picked);
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
        iv_findRoom_Hour_in.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_time_picker);
            TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.dialog_time_picker_TimePicker);
            timePicker.setIs24HourView(true);
            Button btnSubmit = dialog.findViewById(R.id.dialog_time_picker_buttonSubmit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String hour = String.valueOf(timePicker.getHour());
                    String minute = String.valueOf(timePicker.getMinute());

                    String time_picked = hour + ":" + minute;
                    ed_Hour_In.setText(time_picked);
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
        iv_datePick_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_date_picker);
                DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.dialog_date_picker_DatePicker);
                Button btnSubmit = dialog.findViewById(R.id.dialog_date_picker_buttonSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String day = String.valueOf(datePicker.getDayOfMonth());
                        String month = String.valueOf(datePicker.getMonth() + 1);
                        String year = String.valueOf(datePicker.getYear());
                        String date_picked = day + "/" + month + "/" + year;
                        ed_Date_In.setText(date_picked);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        iv_datePick_Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_date_picker);
                DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.dialog_date_picker_DatePicker);
                Button btnSubmit = dialog.findViewById(R.id.dialog_date_picker_buttonSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String day = String.valueOf(datePicker.getDayOfMonth());
                        String month = String.valueOf(datePicker.getMonth() + 1);
                        String year = String.valueOf(datePicker.getYear());
                        String date_picked = day + "/" + month + "/" + year;
                        ed_Date_Out.setText(date_picked);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_Date_In.getText().toString().isEmpty() || ed_Hour_In.getText().toString().isEmpty()
                        || ed_Date_Out.getText().toString().isEmpty() || ed_Hour_Our.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập dủ thông tin", Toast.LENGTH_SHORT).show();
                    return;

                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String date_in = ed_Date_In.getText().toString() + " " + ed_Hour_In.getText().toString();
                String date_out = ed_Date_Out.getText().toString() + " " + ed_Hour_Our.getText().toString();
                Date check_dateIn = null;
                Date check_dateOut = null;
                try {
                    check_dateIn = dateFormat.parse(date_in);
                    check_dateOut = dateFormat.parse(date_out);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                BillDao billDao = new BillDao(getContext());
                RoomDao roomDao = new RoomDao(getContext());
                ArrayList<Bill> billArrayList = billDao.getListBill();
                ArrayList<Room> roomArrayList = roomDao.getDataRoom();

                ArrayList<Room> foundedRoom = roomArrayList;
                for (int i = roomArrayList.size()-1;i>=0;i--){
                    for(Bill y:billArrayList){
                        if (roomArrayList.get(i).getRoomId()==y.getRoomId()){
                            String bill_dateIn= y.getDateCheckIn()+" "+y.getTimeCheckIn();
                            String bill_dateOut= y.getDateCheckOut()+" "+y.getTimeCheckOut();
                            Log.i("checkdate", bill_dateIn);
                            Log.i("checkdate", bill_dateOut);
                            Log.i("checkdate", date_in);
                            Log.i("checkdate", date_out);

                            Date DateIn = null;
                            Date DateOut = null;
                            try {
                                DateIn = dateFormat.parse(bill_dateIn);
                                DateOut = dateFormat.parse(bill_dateOut);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long d0 = check_dateIn.getTime();
                            long d1 = check_dateOut.getTime();
                            long d2 = DateIn.getTime();
                            long d3 = DateOut.getTime();
                            Log.i("checktime", String.valueOf(d0));
                            Log.i("checktime", String.valueOf(d1));
                            Log.i("checktime", String.valueOf(d2));
                            Log.i("checktime", String.valueOf(d3));
                            if(d1<d2||d0>d3){
                                //==========
                            }else {
                                foundedRoom.remove(foundedRoom.get(i));
                            }
                        }
                    }
                }
                FindRoomAdapter findRoomAdapter = new FindRoomAdapter(getContext(),foundedRoom,roomDao);
                lv_Search_Room.setAdapter(findRoomAdapter);

            }
        });

    }
}