package team1.fpoly.duan_n1_17303.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.ProductBillDao;
import team1.fpoly.duan_n1_17303.Dao.ProductDao;
import team1.fpoly.duan_n1_17303.Dao.ProductRoomDAO;
import team1.fpoly.duan_n1_17303.Object.Product;
import team1.fpoly.duan_n1_17303.Object.ProductBill;
import team1.fpoly.duan_n1_17303.Object.ProductRoom;
import team1.fpoly.duan_n1_17303.R;

public class DialogAddProductBillAdapter extends ArrayAdapter<ProductBill> {
    Context context;
    ArrayList<ProductBill> listProduct;
    ProductBillDao billProductDao;
    Integer bill_id;
    public DialogAddProductBillAdapter(Context context, ArrayList<ProductBill> listProduct, int bill_id) {
        super(context, 0, listProduct);
        this.context = context;
        this.listProduct = listProduct;
        this.bill_id = bill_id;
    }
    @Override
    public int getCount() {
        if (!listProduct.isEmpty()) return listProduct.size();
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        billProductDao = new ProductBillDao(getContext());
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_add_product, parent, false);
        }
        ArrayList<ProductBill> productBill = listProduct;
        if (productBill.get(position) != null) {

            TextView product_name = convertView.findViewById(R.id.item_dialog_add_product_textView_name);
            ImageView reduce = convertView.findViewById(R.id.item_dialog_add_product_image_view_reduce);
            ImageView increase = convertView.findViewById(R.id.item_dialog_add_product_image_view_increase);
            EditText quality = convertView.findViewById(R.id.item_dialog_add_product_editText_quality);
            quality.setEnabled(false);
            product_name.setText(productBill.get(position).getProductBillName());
            quality.setText(String.valueOf(productBill.get(position).getProductBillQuantity()));
            quality.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(quality.getText().toString()) < 0) ;
                    quality.setText(0);
                    updateDao(productBill);
                }
            });
            reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(quality.getText().toString()) == 0) {
                    } else {
                        quality.setText(String.valueOf(Integer.parseInt(quality.getText().toString()) - 1));
                        productBill.get(position).setProductBillQuantity(Integer.parseInt(quality.getText().toString()));
                        updateDao(productBill);
                    }
                }
            });

            ProductDao productDao = new ProductDao(context);
            ArrayList<Product> listP = productDao.getListProduct();
            ProductRoomDAO productRoomDAO = new ProductRoomDAO(context);
            ArrayList<ProductRoom> listPR = productRoomDAO.getDataProductRoom();
            int maxQuatity = listP.get(position).getQuantity() - listPR.get(position).getProductRoomQuantity();
            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productBill.get(position).getProductBillQuantity() < maxQuatity){
                        quality.setText(String.valueOf(Integer.parseInt(quality.getText().toString()) + 1));
                        productBill.get(position).setProductBillQuantity(Integer.parseInt(quality.getText().toString()));
                        updateDao(productBill);
                    }else {
                        Toast.makeText(context, "Sản phẩm trong kho đã hết\nHãy nhập thêm", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
        return convertView;
    }

    void updateDao(ArrayList<ProductBill> listPr){
        billProductDao = new ProductBillDao(getContext());
        billProductDao.deleteProductBillByID(String.valueOf(bill_id));
        for (ProductBill x:listPr){
            x.setBillId(bill_id);
            billProductDao.insertProductBill(x);
            Log.i("check11", String.valueOf(bill_id)+'|'+x.getProductId() + '|' + x.getProductBillQuantity());

        }
    }

}
