package team1.fpoly.duan_n1_17303;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Adapter.StaffAdapter;
import team1.fpoly.duan_n1_17303.Dao.StaffDao;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.Staff;
import team1.fpoly.duan_n1_17303.Tools.AddStaffFragment;

public class LoginActivity extends AppCompatActivity {
    EditText edUserName, edPassword;
    TextView tvForgerPass;
    ArrayList<Staff> listStaff;
    String strUser, strPass;
    team1.fpoly.duan_n1_17303.Dao.StaffDao staffDao;
    ImageView ivCheck;
    Button btnLogin;
    int maNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final int[] isShow = {0};
        staffDao = new StaffDao(this);
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        tvForgerPass = findViewById(R.id.tvForgerPass);
        btnLogin=findViewById(R.id.btn_login);
        ivCheck= findViewById(R.id.ivPeek);
        loadDataStaff(this);
        ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow[0]!=0){
                    edPassword.setHint("");
                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isShow[0]=0;
                    ivCheck.setImageResource(R.drawable.ic_eye_close);
                    }
                else {edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivCheck.setImageResource(R.drawable.ic_eye_open);
                    edPassword.setHint("Enter password");
                    isShow[0]=1;}
            }

        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUser=edUserName.getText().toString();
                strPass=edPassword.getText().toString();
                if(strUser.isEmpty()||strPass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }else{
                    if (checkAcc(strUser,strPass)==0){
                        Toast.makeText(LoginActivity.this, "Bạn đăng nhập với quyền quản lý", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("user", strUser);
                        intent.putExtra("pass", strPass);
                        intent.putExtra("id", maNV);

                        startActivity(intent);
                    }
                    if (checkAcc(strUser,strPass)==1){
                        Toast.makeText(LoginActivity.this, "Bạn đăng nhập với quyền nhân viên", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("user", strUser);
                        intent.putExtra("pass", strPass);
                        intent.putExtra("id", maNV);
                        startActivity(intent);
                    }
                    if (checkAcc(strUser,strPass)==2){
                        Toast.makeText(LoginActivity.this, "Tài khoản đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }
    @SuppressLint("SuspiciousIndentation")
    public int checkAcc(String Acc, String Pass){
        for(Staff x:listStaff){
            if (x.getStaffUsername().equalsIgnoreCase("ADMIN")){
                if (Acc.equalsIgnoreCase(x.getStaffUsername())&&(Pass.equalsIgnoreCase(x.getStaffPassword()))){
                    maNV=x.getStaffId();
                    return 0;
                }
            }else{
                if (Acc.equalsIgnoreCase(x.getStaffUsername())&&(Pass.equalsIgnoreCase(x.getStaffPassword())))
                    maNV=x.getStaffId();
                    return 1;
            }
        }
        return 2;
    }
    public void loadDataStaff(Context context){
        listStaff = staffDao.getDataStaff();
    }

}