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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Adapter.StaffAdapter;
import team1.fpoly.duan_n1_17303.Dao.StaffDao;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.Object.Staff;
import team1.fpoly.duan_n1_17303.R;
import team1.fpoly.duan_n1_17303.Tools.AddStaffFragment;

public class StaffFragment extends Fragment {

    ListView lv_Staff;
    FloatingActionButton fl_Add_Staff;
    ArrayList<Staff> listStaff;
    AddStaffFragment addStaffFragment;

    StaffAdapter staffAdapter;
    StaffDao staffDao;
    Staff staff;

    int thu_tu;

    Button btn_Staff_Search;
    EditText ed_Staff_Search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv_Staff = view.findViewById(R.id.lv_Staff);
        fl_Add_Staff = view.findViewById(R.id.fl_Add_Staff);
        staffDao = new StaffDao(getActivity());

        addStaffFragment = new AddStaffFragment();
        loadDataStaff(getActivity());

        FragmentManager fm = getActivity().getSupportFragmentManager();

        fl_Add_Staff.setOnClickListener(v -> {
            //Ẩn toolbar ở menu
            MainActivity mainActivity = (MainActivity) getActivity();
            ActionBar actionBar = mainActivity.getSupportActionBar();
            actionBar.hide();
            fm.beginTransaction().replace(R.id.frameLayout, addStaffFragment).commit();
        });

        lv_Staff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                staff = listStaff.get(position);
                thu_tu = position;
                updateData();
                loadDataStaff(getActivity());
            }
        });

        ed_Staff_Search = view.findViewById(R.id.ed_Staff_Search);
        btn_Staff_Search = view.findViewById(R.id.btn_Staff_Search);


        btn_Staff_Search.setOnClickListener(v -> {
            String staffName = ed_Staff_Search.getText().toString();
            searchStaff(staffName);
        });
    }

    private void searchStaff(String name) {
        ArrayList<Staff> listNew = staffDao.searchStaff(name);
        if (listNew.isEmpty()){
            Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        staffAdapter.setData(listNew);
    }

    private void updateData() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_add_staff);
        TextView tv_Title_Staff = dialog.findViewById(R.id.tv_Title_Staff);
        EditText ed_Staff_Id = dialog.findViewById(R.id.ed_Staff_Id);
        EditText ed_Staff_Username = dialog.findViewById(R.id.ed_Staff_Username);
        EditText ed_Staff_Password = dialog.findViewById(R.id.ed_Staff_Password);
        EditText ed_Staff_Display_Name = dialog.findViewById(R.id.ed_Staff_Display_Name);
        EditText ed_Staff_Date = dialog.findViewById(R.id.ed_Staff_Date);
        EditText ed_Staff_Sex = dialog.findViewById(R.id.ed_Staff_Sex);
        EditText ed_Staff_Phone = dialog.findViewById(R.id.ed_Staff_Phone);
        ImageView img_Back_Staff = dialog.findViewById(R.id.img_Back_Staff);
        ImageView img_Save_Staff = dialog.findViewById(R.id.img_Save_Staff);

        tv_Title_Staff.setText("Sửa nhân viên");

        ed_Staff_Id.setText(staff.getStaffId() + "");

        ed_Staff_Username.setText(staff.getStaffUsername());
        ed_Staff_Password.setText(staff.getStaffPassword());

        ed_Staff_Username.setEnabled(false);
        ed_Staff_Password.setEnabled(false);

        ed_Staff_Display_Name.setText(staff.getDisplayName());
        ed_Staff_Date.setText(staff.getDayOfBirth());
        ed_Staff_Sex.setText(staff.getSex());
        ed_Staff_Phone.setText(staff.getPhone());

        img_Back_Staff.setOnClickListener(v -> {
            dialog.dismiss();
        });

        img_Save_Staff.setOnClickListener(v -> {
            Staff staff = new Staff();

            staff.setStaffId(Integer.parseInt(ed_Staff_Id.getText().toString()));
            staff.setStaffUsername(ed_Staff_Username.getText().toString());
            staff.setStaffPassword(ed_Staff_Password.getText().toString());
            staff.setDisplayName(ed_Staff_Display_Name.getText().toString());
            staff.setDayOfBirth(ed_Staff_Date.getText().toString());
            staff.setSex(ed_Staff_Sex.getText().toString());
            staff.setPhone(ed_Staff_Phone.getText().toString());
            if (staffDao.updateStaff(staff) > 0) {
                listStaff.set(thu_tu, staff);
                staffAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Sửa thông tin nhân viên thành công", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    public void loadDataStaff(Context context) {
        listStaff = staffDao.getDataStaff();
        staffAdapter = new StaffAdapter(context, listStaff, staffDao);
        lv_Staff.setAdapter(staffAdapter);
    }
}