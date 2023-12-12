package team1.fpoly.duan_n1_17303.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import team1.fpoly.duan_n1_17303.Adapter.BillAdapter;
import team1.fpoly.duan_n1_17303.Adapter.CustomerAdapter;
import team1.fpoly.duan_n1_17303.Adapter.CustomerSpinnerAdapter;
import team1.fpoly.duan_n1_17303.Adapter.RoomSpinnerAdapter;
import team1.fpoly.duan_n1_17303.Dao.BillDao;
import team1.fpoly.duan_n1_17303.Dao.CustomerDao;
import team1.fpoly.duan_n1_17303.Dao.RoomDao;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Bill;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;
import team1.fpoly.duan_n1_17303.Tools.AddBillFragment;


public class BillFragment extends Fragment {

    ListView lv_Bill;
    FloatingActionButton fl_Add_Bill;
    AddBillFragment addBillFragment;
    ArrayList<Bill> listBill;
    BillDao billDao;
    BillAdapter billAdapter;
    Spinner spinner_Bill_Customer, spinner_Bill_Room;
    TextView date_picked_day_in;
    ImageView date_pick_day_in;
    TextView date_picked_day_out;
    ImageView date_pick_day_out;
    TextView time_picked_hour_in;
    ImageView time_pick_hour_in;
    TextView time_picked_hour_out;
    ImageView time_pick_hour_out;
    FragmentManager fm;
    int customer_id, room_id, staff_id;
    BillFragment billFragment;
    BillDao dao_bill; FragmentManager childFragmentManager ;

    Button btn_Bill_Search;
    EditText ed_Bill_Search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fm = getActivity().getSupportFragmentManager();
        childFragmentManager = getChildFragmentManager();
        addBillFragment = new AddBillFragment();
        billDao = new BillDao(getActivity());
        lv_Bill = view.findViewById(R.id.lv_Bill);
        loadDataBill(getActivity());
        fl_Add_Bill = view.findViewById(R.id.fl_Add_Bill);
        fl_Add_Bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                ActionBar actionBar = mainActivity.getSupportActionBar();
                actionBar.hide();
                fm.beginTransaction().replace(R.id.frameLayout, addBillFragment).commit();
            }
        });
        lv_Bill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Dialog dialog = new Dialog(getContext());
                dialog.setTitle("Sửa bill");
                dialog.setContentView(R.layout.fragment_add_bill);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                listBill = billDao.getListBill();
                Bill thisBill = listBill.get(position);
                Intent it = getActivity().getIntent();
                spinner_Bill_Customer = dialog.findViewById(R.id.spinner_Bill_Customer);
                spinner_Bill_Room = dialog.findViewById(R.id.spinner_Bill_Room);

                staff_id = it.getIntExtra("id", 0);
                load_data();
                fm = getActivity().getSupportFragmentManager();
                ImageView iv_bill_back = dialog.findViewById(R.id.img_Back_Bill);
                ImageView iv_bill_save = dialog.findViewById(R.id.img_Save_Bill);
                billFragment = new BillFragment();
                date_picked_day_in = dialog.findViewById(R.id.fragmentAddBill_EditText_DateCheckIn);
                date_picked_day_in.setText(thisBill.getDateCheckIn());
                date_picked_day_in.setEnabled(false);
                date_picked_day_out = dialog.findViewById(R.id.fragmentAddBill_EditText_DateCheckOut);
                date_picked_day_out.setText(thisBill.getDateCheckOut());
                date_picked_day_out.setEnabled(false);
                date_pick_day_in = dialog.findViewById(R.id.fragmentAddBill_ImageView_DateCheckIn_Picker);
                date_pick_day_out = dialog.findViewById(R.id.fragmentAddBill_ImageView_DateCheckOut_Picker);
                date_pick_day_in.setOnClickListener(new View.OnClickListener() {
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
                                date_picked_day_in.setText(date_picked);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
                date_pick_day_out.setOnClickListener(new View.OnClickListener() {
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
                                date_picked_day_out.setText(date_picked);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });

                time_picked_hour_in = dialog.findViewById(R.id.fragmentAddBill_EditText_HourCheckIn);
                ;
                time_picked_hour_in.setEnabled(false);
                time_pick_hour_in = dialog.findViewById(R.id.fragmentAddBill_ImageView_HourCheckIn_Picker);
                ;
                time_picked_hour_out = dialog.findViewById(R.id.fragmentAddBill_EditText_HourCheckOut);
                ;
                time_picked_hour_out.setEnabled(false);
                time_pick_hour_out = dialog.findViewById(R.id.fragmentAddBill_ImageView_HourCheckOut_Picker);
                ;
                time_picked_hour_in.setText(thisBill.getTimeCheckIn());
                time_picked_hour_out.setText(thisBill.getTimeCheckOut());

                time_pick_hour_in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                                time_picked_hour_in.setText(time_picked);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
                time_pick_hour_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                                time_picked_hour_out.setText(time_picked);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });

                iv_bill_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                iv_bill_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        save_bill(position);

                        dialog.dismiss();
                        loadDataBill(getContext());
                    }
                });

                dialog.show();
            }

            private void save_bill(int pos) {
                dao_bill = new BillDao(getActivity());
                Bill b = listBill.get(pos);

                String dayIn = date_picked_day_in.getText().toString();
                String dayOut = date_picked_day_out.getText().toString();
                String hourIn = time_picked_hour_in.getText().toString();
                String hourOut = time_picked_hour_out.getText().toString();
                if (dayIn.isEmpty() || dayOut.isEmpty() || hourIn.isEmpty() || hourOut.isEmpty()) {
                    Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkdate(dayIn, hourIn, dayOut, hourOut)) {
                    Toast.makeText(getActivity(), "Ngày giờ đi sau ngày giờ đến", Toast.LENGTH_SHORT).show();
                    return;
                }

                b.setCustomerId(customer_id);
                b.setRoomId(room_id);
                b.setStaffId(staff_id);
                b.setDateCheckIn(dayIn);
                b.setDateCheckOut(dayOut);
                b.setTimeCheckIn(hourIn);
                b.setTimeCheckOut(hourOut);

                if (billDao.updateBill(b)) {
                    Toast.makeText(getContext(), "Sửa thông tin sản phẩm thành công", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
            }

            private boolean checkdate(String dayIn, String hourIn, String dayOut, String hourOut) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String timeIn = dayIn + " " + hourIn;
                String timeOut = dayOut + " " + hourOut;
                Date dateIn = null;
                Date dateOut = null;
                try {
                    dateIn = dateFormat.parse(timeIn);
                    dateOut = dateFormat.parse(timeOut);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (dateIn.getTime() < dateOut.getTime()) return true;

                return false;
            }

            private void load_data() {
                //Spinner Customer
                CustomerDao customerDao = new CustomerDao(getActivity());
                ArrayList<Customer> listCustomer = customerDao.getDataCustomer();
                CustomerSpinnerAdapter customerSpinnerAdapter = new CustomerSpinnerAdapter(getActivity(), listCustomer);

                spinner_Bill_Customer.setAdapter(customerSpinnerAdapter);
                spinner_Bill_Customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        customer_id = listCustomer.get(position).getCustomerId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //Spinner Room
                RoomDao roomDao = new RoomDao(getActivity());
                ArrayList<Room> listRoom = roomDao.getDataRoom();
                RoomSpinnerAdapter roomSpinnerAdapter = new RoomSpinnerAdapter(getActivity(), listRoom);
                spinner_Bill_Room.setAdapter(roomSpinnerAdapter);
                spinner_Bill_Room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        room_id = listRoom.get(position).getRoomId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        ed_Bill_Search = view.findViewById(R.id.ed_Bill_Search);
        btn_Bill_Search = view.findViewById(R.id.btn_Bill_Search);

        btn_Bill_Search.setOnClickListener(v -> {
            String billId = ed_Bill_Search.getText().toString();
            ArrayList<Bill> listNew = new ArrayList<>();
            for (Bill bill:listBill) {
                if (String.valueOf(bill.getBillId()).toLowerCase().toLowerCase().contains(billId.toLowerCase())){
                    listNew.add(bill);
                }
            }
            if (listNew.isEmpty()){
                Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            }else billAdapter.setData(listNew);
        });
    }


    public void loadDataBill(Context context) {
        listBill = billDao.getListBill();
        billAdapter = new BillAdapter(context, listBill, billDao);
        lv_Bill.setAdapter(billAdapter);
    }


}