package team1.fpoly.duan_n1_17303;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import team1.fpoly.duan_n1_17303.Dao.StaffDao;
import team1.fpoly.duan_n1_17303.Fragment.ThongKeFragment;
import team1.fpoly.duan_n1_17303.Tools.AccountFragment;
import team1.fpoly.duan_n1_17303.Fragment.BillFragment;
import team1.fpoly.duan_n1_17303.Fragment.CustomerFragment;
import team1.fpoly.duan_n1_17303.Fragment.ProductFragment;
import team1.fpoly.duan_n1_17303.Fragment.RoomFragment;
import team1.fpoly.duan_n1_17303.Fragment.SearchRoomFragment;
import team1.fpoly.duan_n1_17303.Fragment.StaffFragment;
import team1.fpoly.duan_n1_17303.Object.Staff;
import team1.fpoly.duan_n1_17303.Tools.ChangePassFragment;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View mHeaderView;
    ImageView imgAvatar, imgChangePass;
    TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigationView);
        mHeaderView = navigationView.getHeaderView(0);
        txtUser = mHeaderView.findViewById(R.id.txtUser);
        imgAvatar = mHeaderView.findViewById(R.id.imgAvatar);
        String user = getIntent().getStringExtra("user");
        Log.i("user",user);
        StaffDao staffDao = new StaffDao(this);
        Staff staff = staffDao.getIdStaff(user);
        String displayName = staff.getDisplayName();
        if (staff.getStaffUsername().equalsIgnoreCase("admin")){
            txtUser.setText("Xin chào admin! \n" + displayName);
            imgAvatar.setImageResource(R.drawable.avatar_admin);
        }else{
            txtUser.setText("Xin chào nhân viên! \n" + displayName);
            imgAvatar.setImageResource(R.drawable.avaar_staff);
        }

        drawerLayout = findViewById(R.id.drawerlayout);
        imgChangePass = findViewById(R.id.imgChangePass);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        setTitle("Quản lý hóa đơn thuê phòng");
        BillFragment billFragment = new BillFragment();
        fm.beginTransaction().replace(R.id.frameLayout, billFragment).commit();

        AccountFragment accountFragment = new AccountFragment();
        imgAvatar.setOnClickListener(v -> {
            setTitle("Thông tin tài khoản");
            imgChangePass.setVisibility(View.VISIBLE);
            imgChangePass.setOnClickListener(v1 -> {
                ChangePassFragment changePassFragment = new ChangePassFragment();
                setTitle("Đổi mật khẩu");
                actionBar.hide();
                fm.beginTransaction().replace(R.id.frameLayout, changePassFragment).commit();
            });

            fm.beginTransaction().replace(R.id.frameLayout, accountFragment).commit();
            drawerLayout.closeDrawers();
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                imgChangePass.setVisibility(View.INVISIBLE);
                switch (item.getItemId()){
                    case R.id.itemHoaDon:
                        setTitle("Quản lý hóa đơn thuê phòng");
                        fm.beginTransaction().replace(R.id.frameLayout, billFragment).commit();
                        break;
                    case R.id.itemPhong:
                        setTitle("Quản lý DS thông tin phòng");
                        RoomFragment roomFragment = new RoomFragment();
                        fm.beginTransaction().replace(R.id.frameLayout, roomFragment).commit();
                        break;
                    case R.id.itemKhachHang:
                        setTitle("Quản lý DS khách hàng");
                        CustomerFragment customerFragment = new CustomerFragment();
                        fm.beginTransaction().replace(R.id.frameLayout, customerFragment).commit();
                        break;
                    case R.id.itemSanPham:
                        setTitle("Quản lý sản phẩm");
                        ProductFragment productFragment = new ProductFragment();
                        fm.beginTransaction().replace(R.id.frameLayout, productFragment).commit();
                        break;

                    case R.id.itemPhongTrong:
                        setTitle("Tìm kiếm phòng trống");
                        SearchRoomFragment searchRoomFragment = new SearchRoomFragment();
                        fm.beginTransaction().replace(R.id.frameLayout, searchRoomFragment).commit();
                        break;

                    case R.id.itemThongKe:
                        setTitle("Thống kê");
                        ThongKeFragment thongKeFragment = new ThongKeFragment();
                        fm.beginTransaction().replace(R.id.frameLayout, thongKeFragment).commit();
                        break;
                    case R.id.itemTaiKhoan:
                        if (staff.getStaffUsername().equalsIgnoreCase("admin")){
                            setTitle("Quản lý tài khoản");
                            StaffFragment staffFragment = new StaffFragment();
                            fm.beginTransaction().replace(R.id.frameLayout, staffFragment).commit();
                        }else{
                            Toast.makeText(MainActivity.this, "Bạn không có quyền truy cập chức năng này", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.itemDangXuat:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}