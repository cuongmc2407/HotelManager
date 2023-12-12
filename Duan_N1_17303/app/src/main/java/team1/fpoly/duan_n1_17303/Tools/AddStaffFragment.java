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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.StaffDao;
import team1.fpoly.duan_n1_17303.Fragment.StaffFragment;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Staff;
import team1.fpoly.duan_n1_17303.R;

public class AddStaffFragment extends Fragment {

    FragmentManager fm;
    StaffFragment staffFragment;
    ArrayList<Staff> listStaff;
    StaffDao staffDao;
    Toolbar toolbarStaff;

    ImageView img_Back_Staff, img_Save_Staff;
    EditText ed_Staff_Id, ed_Staff_Username, ed_Staff_Password, ed_Staff_Display_Name, ed_Staff_Date, ed_Staff_Sex, ed_Staff_Phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbarStaff = view.findViewById(R.id.toolbarStaff);
        ed_Staff_Id = view.findViewById(R.id.ed_Staff_Id);
        ed_Staff_Username = view.findViewById(R.id.ed_Staff_Username);
        ed_Staff_Password = view.findViewById(R.id.ed_Staff_Password);
        ed_Staff_Display_Name = view.findViewById(R.id.ed_Staff_Display_Name);
        ed_Staff_Date = view.findViewById(R.id.ed_Staff_Date);
        ed_Staff_Sex = view.findViewById(R.id.ed_Staff_Sex);
        ed_Staff_Phone = view.findViewById(R.id.ed_Staff_Phone);
        img_Back_Staff = view.findViewById(R.id.img_Back_Staff);
        img_Save_Staff = view.findViewById(R.id.img_Save_Staff);

        fm = getActivity().getSupportFragmentManager();
        staffFragment = new StaffFragment();
        ed_Staff_Id.setVisibility(View.INVISIBLE);
        img_Back_Staff.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            ActionBar actionBar =  mainActivity.getSupportActionBar();
            actionBar.show();
            fm.beginTransaction().replace(R.id.frameLayout, staffFragment).commit();
        });
        img_Save_Staff.setOnClickListener(v -> {
            saveDataStaff();
        });
    }

    private void saveDataStaff() {
        listStaff = new ArrayList<>();
        staffDao = new StaffDao(getActivity());
        listStaff=staffDao.getDataStaff();
        Staff staff = new Staff();
        String user = ed_Staff_Username.getText().toString();
        String pass = ed_Staff_Password.getText().toString();
        String displayName = ed_Staff_Display_Name.getText().toString();
        String date = ed_Staff_Date.getText().toString();
        String sex = ed_Staff_Sex.getText().toString();
        String phone = ed_Staff_Phone.getText().toString();

        if (user.isEmpty() || pass.isEmpty() || displayName.isEmpty() || date.isEmpty() || sex.isEmpty() || phone.isEmpty()){
            Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        staff.setStaffUsername(user);
        staff.setStaffPassword(pass);
        staff.setDisplayName(displayName);
        staff.setDayOfBirth(date);
        staff.setSex(sex);
        staff.setPhone(phone);
        int err=0;
        for (Staff check:listStaff) if (staff.getStaffUsername().equalsIgnoreCase(check.getStaffUsername()))
        err++;
        if (err==0) {
            if (staffDao.insertStaff(staff) > 0) {
                Toast.makeText(getActivity(), "Thêm thành công nhân viên " + staff.getDisplayName(), Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(getContext(), "User đã tồn tại", Toast.LENGTH_SHORT).show();
        MainActivity mainActivity = (MainActivity) getActivity();
        ActionBar actionBar =  mainActivity.getSupportActionBar();
        actionBar.show();
        fm.beginTransaction().replace(R.id.frameLayout, staffFragment).commit();
    }
}