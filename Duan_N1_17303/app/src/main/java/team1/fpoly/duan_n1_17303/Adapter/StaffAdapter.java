package team1.fpoly.duan_n1_17303.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.StaffDao;
import team1.fpoly.duan_n1_17303.Object.Staff;
import team1.fpoly.duan_n1_17303.R;

public class StaffAdapter extends ArrayAdapter<Staff> {
    Context context;
    ArrayList<Staff> listStaff;
    StaffDao staffDao;

    TextView tv_Display_Name, tv_User_Name,
            tv_Password, tv_Staff_Date_Of_Birth, tv_Staff_Sex, tv_Staff_Number_Phone;
    ImageView img_Delete_Staff;

    public StaffAdapter( Context context, ArrayList<Staff> listStaff, StaffDao staffDao) {
        super(context, 0, listStaff);
        this.context = context;
        this.listStaff = listStaff;
        this.staffDao = staffDao;
    }

    @Override
    public int getCount() {
        return listStaff.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_staff, parent, false);
        }

        final Staff staff = listStaff.get(position);

        if (staff != null){
            tv_Display_Name = view.findViewById(R.id.tv_Display_Name);
            tv_User_Name = view.findViewById(R.id.tv_User_Name);
            tv_Password = view.findViewById(R.id.tv_Password);
            tv_Staff_Date_Of_Birth = view.findViewById(R.id.tv_Staff_Date_Of_Birth);
            tv_Staff_Sex = view.findViewById(R.id.tv_Staff_Sex);
            tv_Staff_Number_Phone = view.findViewById(R.id.tv_Staff_Number_Phone);
            img_Delete_Staff = view.findViewById(R.id.img_Delete_Staff);

            tv_Display_Name.setText(staff.getDisplayName());
            tv_User_Name.setText("Tên tài khoản: " + staff.getStaffUsername());
            tv_Password.setText("Mật khẩu: " + staff.getStaffPassword());
            tv_Staff_Date_Of_Birth.setText("Ngày sinh: " + staff.getDayOfBirth());
            tv_Staff_Sex.setText("Giới tính: " + staff.getSex());
            tv_Staff_Number_Phone.setText("Số điện thoại: "+ staff.getPhone());

            img_Delete_Staff.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa nhân viên");
                builder.setMessage(staff.getDisplayName());

                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listStaff.remove(position);
                        notifyDataSetChanged();
                        if (staffDao.deleteStaff(String.valueOf(staff.getStaffId())) > 0){
                            Toast.makeText(context, "Xóa thành công nhân viên\n" + staff.getDisplayName(), Toast.LENGTH_LONG).show();
                        }else Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_LONG).show();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            });
        }
        return view;
    }

    public void setData(ArrayList<Staff> listNew) {
        this.listStaff = listNew;
        notifyDataSetChanged();
    }
}
