package team1.fpoly.duan_n1_17303.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import team1.fpoly.duan_n1_17303.Dao.BillDao;
import team1.fpoly.duan_n1_17303.Dao.CustomerDao;
import team1.fpoly.duan_n1_17303.Dao.ProductBillDao;
import team1.fpoly.duan_n1_17303.Dao.RoomDao;
import team1.fpoly.duan_n1_17303.Dao.StaffDao;
import team1.fpoly.duan_n1_17303.Object.Bill;
import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.Object.ProductBill;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.Object.Staff;
import team1.fpoly.duan_n1_17303.R;

public class BillAdapter extends ArrayAdapter<Bill> {
    Context context;
    ArrayList<Bill> listBill;
    BillDao billDao;

    TextView tv_Bill_Id, tv_Staff, tv_Customer, tv_Room, tv_Date_Check_In, tv_Date_Check_Out, tv_Product, tv_Total_Money;
    ImageView img_Delete_Bill;
    public BillAdapter(Context context,ArrayList<Bill> listBill,BillDao billDao){
        super(context,0,listBill);
        this.context=context;
        this.listBill=listBill;
        this.billDao=billDao;
    }
    @Override
    public int getCount() {
        return listBill.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_bill, parent, false);
        }

        final Bill bill = listBill.get(position);

        if (bill != null){
            tv_Bill_Id = view.findViewById(R.id.tv_Bill_Id);
            tv_Staff = view.findViewById(R.id.tv_Staff);
            tv_Customer = view.findViewById(R.id.tv_Customer);
            tv_Room = view.findViewById(R.id.tv_Room);
            tv_Date_Check_In = view.findViewById(R.id.tv_Date_Check_In);
            tv_Date_Check_Out = view.findViewById(R.id.tv_Date_Check_Out);
            img_Delete_Bill = view.findViewById(R.id.img_Delete_Bill);
            tv_Product = view.findViewById(R.id.tv_Product);
            tv_Bill_Id.setText("Mã hóa đơn: " + bill.getBillId());
            tv_Total_Money= view.findViewById(R.id.tv_Total_Money);

            StaffDao staffDao = new StaffDao(context);
            Staff staff = staffDao.getIdStaff_2(String.valueOf(bill.getStaffId()));
            tv_Staff.setText("Nhân viên thêm: " + staff.getDisplayName());

            CustomerDao customerDao = new CustomerDao(context);
            Customer customer = customerDao.getIdCustomer(String.valueOf(bill.getCustomerId()));
            tv_Customer.setText("Tên khách hàng: " + customer.getCustomerName());

            RoomDao roomDao = new RoomDao(context);
            Room room = roomDao.getIdRoom(String.valueOf(bill.getRoomId()));
            tv_Room.setText("Tên phòng: " + room.getRoomName());
            tv_Date_Check_In.setText("Ngày đến: " + bill.getDateCheckIn() + " " + bill.getTimeCheckIn());
            tv_Date_Check_Out.setText("Ngày đi: " + bill.getDateCheckOut() + " " + bill.getTimeCheckOut());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String timeIn = bill.getDateCheckIn() + " " + bill.getTimeCheckIn();
            String timeOut = bill.getDateCheckOut() + " " + bill.getTimeCheckOut();
            Date dateIn = null;
            Date dateOut = null;
            try {
                dateIn = dateFormat.parse(timeIn);
                dateOut = dateFormat.parse(timeOut);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long time_minite = (long) (((dateOut.getTime()-dateIn.getTime())*0.001)/60);

            loadMoney(time_minite,room,bill.getBillId());
            img_Delete_Bill.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bạn có muốn xóa.");
                String bill_id=String.valueOf(bill.getBillId());
                builder.setMessage("Hóa đơn có mã " + bill.getBillId());
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listBill.remove(bill);
                        notifyDataSetChanged();
                        if (billDao.deleteBill(String.valueOf(bill.getBillId())) > 0){
                            ProductBillDao prd = new ProductBillDao(getContext());
                            prd.deleteProductBillByID(bill_id);
                            Toast.makeText(context, "Xóa thành công hóa đơn " + bill.getBillId(), Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            });
            tv_Product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogProductBill(bill.getBillId(),time_minite,room,bill.getBillId());
                    loadMoney(time_minite,room,bill.getBillId());

                }
            });
        }
        return view;
    }

    private void showDialogProductBill(int billId,long time_minite,Room room,int bill_id) {
        ProductBillDao productBillDao = new ProductBillDao(getContext());
        ArrayList<ProductBill> aListPrR= productBillDao.getDataProductBillByID(billId);

        Dialog dialog = new Dialog(getContext());
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.setContentView(R.layout.dialog_add_product);
        Button btn_done = dialog.findViewById(R.id.dialog_add_product_button_save);
        ListView lv_Dialog = dialog.findViewById(R.id.dialog_add_product_listview);
        DialogAddProductBillAdapter adapter = new DialogAddProductBillAdapter(getContext(),aListPrR,billId);

        lv_Dialog.setAdapter(adapter);
        dialog.show();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                loadMoney(time_minite,room,bill_id);
                notifyDataSetChanged();
            }
        });
    }
    void loadMoney(long time_minite,Room room,int bill_id){
        ProductBillDao productBillDao  = new ProductBillDao(getContext());

        ArrayList<ProductBill> lbill = productBillDao.getDataProductBillByID(bill_id);
        long money_product =0;
        for (ProductBill pl:lbill){
            money_product = money_product + (long) pl.getProductBillPrice() * pl.getProductBillQuantity();
        }
        Bill bill = billDao.getDataBillByBillID(bill_id);
        if (time_minite<60*24){
            int total_money = (int) (room.getHourPrice()*
                                Integer.parseInt(String.valueOf(time_minite/60+1))+money_product);
            tv_Total_Money.setText("Tổng tiền: "+String.valueOf(total_money));
            bill.setMoney(total_money);
            billDao.updateBill(bill);
        }else {
            int total_money = (int) (room.getDayPrice()*
                                Integer.parseInt(String.valueOf(time_minite/60/24+1))+money_product);
            tv_Total_Money.setText("Tổng tiền: "+String.valueOf(total_money));
            bill.setMoney(total_money);
            billDao.updateBill(bill);
        }
    }

    public void setData(ArrayList<Bill> listNew) {
        this.listBill = listNew;
        notifyDataSetChanged();
    }
}
