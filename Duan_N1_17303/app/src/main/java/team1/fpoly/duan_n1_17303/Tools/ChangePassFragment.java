package team1.fpoly.duan_n1_17303.Tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import team1.fpoly.duan_n1_17303.Dao.StaffDao;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Staff;
import team1.fpoly.duan_n1_17303.R;


public class ChangePassFragment extends Fragment {

    TextInputEditText edPassOld, edPassChange, edRePassChange;
    Button btnSaveChange, btnCancelChange;
    StaffDao staffDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edPassOld = view.findViewById(R.id.edPassOld);
        edPassChange = view.findViewById(R.id.edPassChange);
        edRePassChange = view.findViewById(R.id.edRePassChange);
        btnSaveChange = view.findViewById(R.id.btnSaveChange);
        btnCancelChange = view.findViewById(R.id.btnCancelChange);

        staffDao = new StaffDao(getActivity());
        AccountFragment accountFragment = new AccountFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        MainActivity mainActivity = (MainActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        btnCancelChange.setOnClickListener(v -> {
            actionBar.show();
            fm.beginTransaction().replace(R.id.frameLayout, accountFragment).commit();
        });

        btnSaveChange.setOnClickListener(v -> {
            String user = getActivity().getIntent().getStringExtra("user");
            if (validate() > 0){
                Staff staff = staffDao.getIdStaff(user);
                staff.setStaffPassword(edPassChange.getText().toString());

                if (staffDao.updateStaff(staff) > 0){
                    Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    actionBar.show();
                    fm.beginTransaction().replace(R.id.frameLayout, accountFragment).commit();
                }else Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int validate(){
        int check = 1;
        if (edPassOld.getText().length() == 0 || edPassChange.getText().length() == 0 || edRePassChange.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else{
            //Đọc user, pass lấy từ Intent
            Intent intent = getActivity().getIntent();
            String passOld = intent.getStringExtra("pass");
            String pass = edPassChange.getText().toString();
            String rePass = edRePassChange.getText().toString();
            if (!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }else if (!pass.equals(rePass)){
                Toast.makeText(getContext(), "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}