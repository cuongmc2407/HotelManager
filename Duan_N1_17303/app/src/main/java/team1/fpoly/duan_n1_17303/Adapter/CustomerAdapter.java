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

import team1.fpoly.duan_n1_17303.Dao.CustomerDao;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.R;

public class CustomerAdapter extends ArrayAdapter<Customer> {

    Context context;
    ArrayList<Customer> listCustomer;
    CustomerDao customerDao;

    TextView tv_Customer_Id, tv_Customer_Name,
            tv_Customer_Date_Of_Birth, tv_Customer_Number_Phone, tv_Customer_Sex;
    ImageView img_Delete_Customer;

    public CustomerAdapter( Context context, ArrayList<Customer> listCustomer, CustomerDao customerDao) {
        super(context, 0, listCustomer);
        this.context = context;
        this.listCustomer = listCustomer;
        this.customerDao = customerDao;
    }

    @Override
    public int getCount() {
        return listCustomer.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_customer, parent, false);
        }

        final Customer customer = listCustomer.get(position);

        if (customer != null){
            tv_Customer_Id = view.findViewById(R.id.tv_Customer_Id);
            tv_Customer_Name = view.findViewById(R.id.tv_Customer_Name);
            tv_Customer_Date_Of_Birth = view.findViewById(R.id.tv_Customer_Date_Of_Birth);
            tv_Customer_Number_Phone = view.findViewById(R.id.tv_Customer_Number_Phone);
            tv_Customer_Sex = view.findViewById(R.id.tv_Customer_Sex);
            img_Delete_Customer = view.findViewById(R.id.img_Delete_Customer);

            tv_Customer_Id.setText("Mã khách hàng: " + customer.getCustomerId());
            tv_Customer_Name.setText("Tên khách hàng: " + customer.getCustomerName());
            tv_Customer_Date_Of_Birth.setText("Ngày sinh: " + customer.getDayOfBirth());
            tv_Customer_Number_Phone.setText("Số điện thoại: " + customer.getPhone());
            tv_Customer_Sex.setText("Giới tính: " + customer.getSex());

            img_Delete_Customer.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bạn có muốn xóa.");
                builder.setMessage("Khách hàng " + customer.getCustomerName());
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listCustomer.remove(customer);
                        notifyDataSetChanged();
                        if (customerDao.deleteCustomer(String.valueOf(customer.getCustomerId())) > 0){
                            Toast.makeText(context, "Xóa thành công khách hàng " + customer.getCustomerName(), Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            });
        }
        return view;
    }

    public void setData(ArrayList<Customer> listNew) {
        this.listCustomer = listNew;
        notifyDataSetChanged();
    }
}
