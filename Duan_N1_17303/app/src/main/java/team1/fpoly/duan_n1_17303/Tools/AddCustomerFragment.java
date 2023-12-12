package team1.fpoly.duan_n1_17303.Tools;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.CustomerDao;
import team1.fpoly.duan_n1_17303.Fragment.CustomerFragment;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.R;

public class AddCustomerFragment extends Fragment {
    RadioButton add_Customer_radio_btn_nam,add_Customer_radio_btn_nu;
    FragmentManager fm;
    CustomerFragment customerFragment;
    ImageView img_Back_Customer, img_Save_Customer;
    EditText ed_Customer_Name, ed_Customer_Date, ed_Customer_Phone;
    ArrayList<Customer> listCustomer;
    CustomerDao customerDao;
    String Customer_gender ="Nam";
    ImageView iv_date_pick;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_customer, container, false);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_date_pick = view.findViewById(R.id.fragmentCustomer_ImageView_DatePicker);
        add_Customer_radio_btn_nam = view.findViewById(R.id.fragmentCustomer_RadioButon_Nam);
        add_Customer_radio_btn_nu = view.findViewById(R.id.fragmentCustomer_RadioButon_Nu);
        ed_Customer_Name = view.findViewById(R.id.fragmentCustomer_EditText_Customer_Name);
        ed_Customer_Date = view.findViewById(R.id.fragmentCustomer_EditText_Customer_DOB);
        ed_Customer_Phone = view.findViewById(R.id.fragmentCustomer_EditText_Customer_Phone);
        img_Back_Customer = view.findViewById(R.id.img_Back_Customer);
        img_Save_Customer = view.findViewById(R.id.img_Save_Customer);
        ed_Customer_Date.setEnabled(false);
        fm = getActivity().getSupportFragmentManager();
        customerFragment = new CustomerFragment();
        add_Customer_radio_btn_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGender(v);
            }
        });
        add_Customer_radio_btn_nu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGender(v);
            }
        });
        img_Back_Customer.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            ActionBar actionBar = mainActivity.getSupportActionBar();
            actionBar.show();
            fm.beginTransaction().replace(R.id.frameLayout, customerFragment).commit();
        });
        img_Save_Customer.setOnClickListener(v -> {
            saveDataCustomer();
        });

        iv_date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_date_picker);
                DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.dialog_date_picker_DatePicker);
                Button btnSubmit = dialog.findViewById(R.id.dialog_date_picker_buttonSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String	day	=String.valueOf(datePicker.getDayOfMonth());
                        String	month =String.valueOf(datePicker.getMonth() + 1);
                        String	year =String.valueOf(datePicker.getYear());
                        String date_picked = day +"/" + month +"/" + year;
                        ed_Customer_Date.setText(date_picked);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void saveDataCustomer() {

        customerDao = new CustomerDao(getActivity());
        Customer c = new Customer();
        String id = null;
        String name = ed_Customer_Name.getText().toString();
        String date = ed_Customer_Date.getText().toString();
        String phone = ed_Customer_Phone.getText().toString();
        String sex =Customer_gender;

        if (name.isEmpty() || date.isEmpty() || phone.isEmpty() || sex.isEmpty()) {
            Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        c.setCustomerName(name);
        c.setDayOfBirth(date);
        c.setPhone(phone);
        c.setSex(sex);
        if (customerDao.insertCustomer(c) > 0) {
            Toast.makeText(getActivity(), "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Thêm khách hàng thất bại", Toast.LENGTH_SHORT).show();
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        actionBar.show();
        fm.beginTransaction().replace(R.id.frameLayout, customerFragment).commit();


    }
    public void setGender(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.fragmentCustomer_RadioButon_Nam:
                if (checked)
                    Customer_gender="Nam";
                    break;
            case R.id.fragmentCustomer_RadioButon_Nu:
                if (checked)
                    Customer_gender="Nữ";
                    break;
        }
    }
}