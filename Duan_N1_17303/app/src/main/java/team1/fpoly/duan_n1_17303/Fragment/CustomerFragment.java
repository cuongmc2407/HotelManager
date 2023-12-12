package team1.fpoly.duan_n1_17303.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Adapter.CustomerAdapter;
import team1.fpoly.duan_n1_17303.Dao.CustomerDao;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;
import team1.fpoly.duan_n1_17303.Tools.AddCustomerFragment;


public class CustomerFragment extends Fragment {

    ListView lv_Customer;
    FloatingActionButton fl_Add_Customer;
    CustomerDao customerDao;
    ArrayList<Customer> listCustomer = new ArrayList<>();
    CustomerAdapter customerAdapter;
    AddCustomerFragment addCustomerFragment;
    Customer customer;
    String Customer_gender ="Nam";
    int thu_tu;
    RadioButton add_Customer_radio_btn_nam,add_Customer_radio_btn_nu;

    Button btn_Customer_Search;
    EditText ed_Customer_Search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_Customer = view.findViewById(R.id.lv_Customer);
        fl_Add_Customer = view.findViewById(R.id.fl_Add_Customer);
        addCustomerFragment = new AddCustomerFragment();



        customerDao = new CustomerDao(getActivity());

        loadDataCustomer(getActivity());

        FragmentManager fm = getActivity().getSupportFragmentManager();

        lv_Customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //code update
                customer = listCustomer.get(position);

                updateData(customer);
                loadDataCustomer(getActivity());
            }
        });

        fl_Add_Customer.setOnClickListener(v -> {

            //Ẩn toolbar ở menu
            MainActivity mainActivity = (MainActivity) getActivity();
            ActionBar actionBar =  mainActivity.getSupportActionBar();
            actionBar.hide();
            fm.beginTransaction().replace(R.id.frameLayout, addCustomerFragment).commit();
        });

        ed_Customer_Search = view.findViewById(R.id.ed_Customer_Search);
        btn_Customer_Search = view.findViewById(R.id.btn_Customer_Search);


        btn_Customer_Search.setOnClickListener(v -> {
            String customerName = ed_Customer_Search.getText().toString();
            searchCustomer(customerName);
        });
    }

    private void searchCustomer(String name) {
        ArrayList<Customer> listNew = customerDao.searchCustomer(name);
        if (listNew.isEmpty()){
            Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        customerAdapter.setData(listNew);
    }


    public void loadDataCustomer(Context context){
        listCustomer = customerDao.getDataCustomer();
        customerAdapter = new CustomerAdapter(context, listCustomer, customerDao);
        lv_Customer.setAdapter(customerAdapter);
    }


    public void updateData(Customer temp_customer){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_add_customer);

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView iv_date_pick = dialog.findViewById(R.id.fragmentCustomer_ImageView_DatePicker);
        TextView tv_Tile_Customer = dialog.findViewById(R.id.tv_Title_Customer);
        EditText ed_Customer_Name = dialog.findViewById(R.id.fragmentCustomer_EditText_Customer_Name);
        EditText ed_Customer_Date = dialog.findViewById(R.id.fragmentCustomer_EditText_Customer_DOB);
        EditText ed_Customer_Phone = dialog.findViewById(R.id.fragmentCustomer_EditText_Customer_Phone);
        ImageView img_Back_Customer = dialog.findViewById(R.id.img_Back_Customer);
        ImageView img_Save_Customer = dialog.findViewById(R.id.img_Save_Customer);
        add_Customer_radio_btn_nam = dialog.findViewById(R.id.fragmentCustomer_RadioButon_Nam);
        add_Customer_radio_btn_nu = dialog.findViewById(R.id.fragmentCustomer_RadioButon_Nu);
        ed_Customer_Date.setEnabled(false);
        ed_Customer_Name.setText(customer.getCustomerName());
        ed_Customer_Date.setText(customer.getDayOfBirth());
        ed_Customer_Phone.setText(customer.getPhone());
        iv_date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog1 = new Dialog(getActivity());
                dialog1.setContentView(R.layout.dialog_date_picker);
                DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.dialog_date_picker_DatePicker);
                Button btnSubmit = dialog1.findViewById(R.id.dialog_date_picker_buttonSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String	day	=String.valueOf(datePicker.getDayOfMonth());
                        String	month =String.valueOf(datePicker.getMonth() + 1);
                        String	year =String.valueOf(datePicker.getYear());
                        String date_picked = day +"/" + month +"/" + year;
                        ed_Customer_Date.setText(date_picked);
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }
        });


        tv_Tile_Customer.setText("Sửa khách hàng");
        img_Back_Customer.setOnClickListener(v -> {
            dialog.dismiss();
        });
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
        img_Save_Customer.setOnClickListener(v -> {

            Customer customer = new Customer();
            customer.setCustomerId(temp_customer.getCustomerId());
            customer.setCustomerName(ed_Customer_Name.getText().toString());
            customer.setDayOfBirth(ed_Customer_Date.getText().toString());
            customer.setPhone(ed_Customer_Phone.getText().toString());
            customer.setSex(Customer_gender);
            if (customerDao.updateCustomer(customer) > 0){
               listCustomer.set(thu_tu, customer);
                customerAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Sửa thông tin khách hàng thành công", Toast.LENGTH_SHORT).show();

            }else Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();

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