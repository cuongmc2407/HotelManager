package team1.fpoly.duan_n1_17303.Tools;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import team1.fpoly.duan_n1_17303.Adapter.CustomerSpinnerAdapter;
import team1.fpoly.duan_n1_17303.Adapter.RoomSpinnerAdapter;
import team1.fpoly.duan_n1_17303.Dao.BillDao;
import team1.fpoly.duan_n1_17303.Dao.CustomerDao;
import team1.fpoly.duan_n1_17303.Dao.ProductBillDao;
import team1.fpoly.duan_n1_17303.Dao.ProductDao;
import team1.fpoly.duan_n1_17303.Dao.RoomDao;
import team1.fpoly.duan_n1_17303.Fragment.BillFragment;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Bill;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.Product;
import team1.fpoly.duan_n1_17303.Object.ProductBill;
import team1.fpoly.duan_n1_17303.Object.ProductRoom;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;

public class AddBillFragment extends Fragment {
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
    BillDao dao_bill;


    public AddBillFragment() {
    }

    public static AddBillFragment newInstance() {
        AddBillFragment fragment = new AddBillFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent it = getActivity().getIntent();
        spinner_Bill_Customer = view.findViewById(R.id.spinner_Bill_Customer);
        spinner_Bill_Room = view.findViewById(R.id.spinner_Bill_Room);
        staff_id = it.getIntExtra("id", 0);
        load_data();
        fm = getActivity().getSupportFragmentManager();
        ImageView iv_bill_back = view.findViewById(R.id.img_Back_Bill);
        ImageView iv_bill_save = view.findViewById(R.id.img_Save_Bill);
        billFragment = new BillFragment();
        date_picked_day_in = view.findViewById(R.id.fragmentAddBill_EditText_DateCheckIn);
        date_picked_day_in.setEnabled(false);
        date_picked_day_out = view.findViewById(R.id.fragmentAddBill_EditText_DateCheckOut);
        date_picked_day_out.setEnabled(false);
        date_pick_day_in = view.findViewById(R.id.fragmentAddBill_ImageView_DateCheckIn_Picker);
        date_pick_day_out = view.findViewById(R.id.fragmentAddBill_ImageView_DateCheckOut_Picker);
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

        time_picked_hour_in = view.findViewById(R.id.fragmentAddBill_EditText_HourCheckIn);
        ;
        time_picked_hour_in.setEnabled(false);
        time_pick_hour_in = view.findViewById(R.id.fragmentAddBill_ImageView_HourCheckIn_Picker);
        ;
        time_picked_hour_out = view.findViewById(R.id.fragmentAddBill_EditText_HourCheckOut);
        ;
        time_picked_hour_out.setEnabled(false);
        time_pick_hour_out = view.findViewById(R.id.fragmentAddBill_ImageView_HourCheckOut_Picker);
        ;

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
                MainActivity mainActivity = (MainActivity) getActivity();
                ActionBar actionBar = mainActivity.getSupportActionBar();
                actionBar.show();
                fm.beginTransaction().replace(R.id.frameLayout, billFragment).commit();
            }
        });
        iv_bill_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_bill();
            }
        });

    }

    private void save_bill() {
        dao_bill = new BillDao(getActivity());
        Bill b = new Bill();

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
        b.setStaffId(staff_id);
        b.setCustomerId(customer_id);
        b.setRoomId(room_id);
        b.setDateCheckIn(dayIn);
        b.setDateCheckOut(dayOut);
        b.setTimeCheckIn(hourIn);
        b.setTimeCheckOut(hourOut);

        if (dao_bill.insertBill(b)) {
            Toast.makeText(getActivity(), "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
            ProductBillDao productBillDao = new ProductBillDao(getContext());
            ProductDao productDao = new ProductDao(getContext());
            ArrayList<Product> liProduct = productDao.getListProduct();
            for (Product x : liProduct) {
                ArrayList<Bill> listBill = dao_bill.getListBill();
                ProductBill newPR = new ProductBill(x.getProductId(),
                        listBill.get(listBill.size() - 1)
                                .getBillId(), x
                        .getProductName(), x
                        .getPrice(), 0);
                productBillDao.insertProductBill(newPR);
            }
            Toast.makeText(getActivity(), "Thêm sản phẩm hóa đơn thành công", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Thêm hóa đơn thất bại", Toast.LENGTH_SHORT).show();
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        actionBar.show();
        fm.beginTransaction().replace(R.id.frameLayout, billFragment).commit();

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
}