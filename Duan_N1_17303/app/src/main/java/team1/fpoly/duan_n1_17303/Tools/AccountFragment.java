package team1.fpoly.duan_n1_17303.Tools;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.StaffDao;
import team1.fpoly.duan_n1_17303.Object.Staff;
import team1.fpoly.duan_n1_17303.R;


public class AccountFragment extends Fragment {
    TextView tv_User, tv_User_Type;
    CheckBox chkbox_Thong_Ke, chkbox_Staff;

    StaffDao staffDao;
    Staff staff;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_User = view.findViewById(R.id.tv_User);
        tv_User_Type = view.findViewById(R.id.tv_User_Type);
        chkbox_Thong_Ke = view.findViewById(R.id.chkbox_Thong_Ke);
        chkbox_Staff = view.findViewById(R.id.chkbox_Staff);

        String user = getActivity().getIntent().getStringExtra("user");
        staffDao = new StaffDao(getContext());
        staff = staffDao.getIdStaff(user);

        tv_User.setText(staff.getStaffUsername());
        if (staff.getStaffUsername().equalsIgnoreCase("admin")){
            tv_User_Type.setText("Quản lý");

        }else{
            tv_User_Type.setText("Nhân viên");
            chkbox_Staff.setChecked(false);
        }
    }
}