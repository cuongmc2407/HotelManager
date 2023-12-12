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

import team1.fpoly.duan_n1_17303.Dao.ProductDao;
import team1.fpoly.duan_n1_17303.Dao.ProductRoomDAO;
import team1.fpoly.duan_n1_17303.Fragment.ProductFragment;
import team1.fpoly.duan_n1_17303.Object.Product;
import team1.fpoly.duan_n1_17303.Object.ProductRoom;
import team1.fpoly.duan_n1_17303.R;

public class DialogAddProductRoomAdapter extends ArrayAdapter<ProductRoom> {
    Context context;
    ArrayList<ProductRoom> listProduct;
    ProductRoomDAO roomProductDao;
    Integer room_id;

    public DialogAddProductRoomAdapter(Context context, ArrayList<ProductRoom> listProduct, int room_id) {
        super(context, 0, listProduct);
        this.context = context;
        this.listProduct = listProduct;
        this.room_id = room_id;
    }

    @Override
    public int getCount() {
        if (!listProduct.isEmpty()) return listProduct.size();
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        roomProductDao = new ProductRoomDAO(getContext());
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_add_product, parent, false);
        }
        ArrayList<ProductRoom> productRooms = listProduct;
        if (productRooms.get(position) != null) {

            TextView product_name = convertView.findViewById(R.id.item_dialog_add_product_textView_name);
            ImageView reduce = convertView.findViewById(R.id.item_dialog_add_product_image_view_reduce);
            ImageView increase = convertView.findViewById(R.id.item_dialog_add_product_image_view_increase);
            EditText quality = convertView.findViewById(R.id.item_dialog_add_product_editText_quality);
            quality.setEnabled(false);
            product_name.setText(productRooms.get(position).getProductRoomName());
            quality.setText(String.valueOf(productRooms.get(position).getProductRoomQuantity()));
            quality.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(quality.getText().toString()) < 0) ;
                    quality.setText(0);
                    updateDao(productRooms);
                }
            });
            reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(quality.getText().toString()) == 0) {
                    } else {
                        quality.setText(String.valueOf(Integer.parseInt(quality.getText().toString()) - 1));
                        productRooms.get(position).setProductRoomQuantity(Integer.parseInt(quality.getText().toString()));
                        updateDao(productRooms);
                    }
                }
            });
            ProductDao productDao = new ProductDao(getContext());
            ArrayList<Product> list = productDao.getListProduct();
            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productRooms.get(position).getProductRoomQuantity() < list.get(position).getQuantity()){
                        quality.setText(String.valueOf(Integer.parseInt(quality.getText().toString()) + 1));
                        productRooms.get(position).setProductRoomQuantity(Integer.parseInt(quality.getText().toString()));
                        updateDao(productRooms);
                    }else {
                        Toast.makeText(context, "Số lượng sản phẩm trong kho không đủ\nHãy nhập thêm", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        return convertView;
    }

    void updateDao(ArrayList<ProductRoom> listPr){
        roomProductDao = new ProductRoomDAO(getContext());
        roomProductDao.deleteProductRoomByID(String.valueOf(room_id));
        for (ProductRoom x:listPr){
            x.setRoomId(room_id);
            roomProductDao.insertProductRoom(x);
            Log.i("check11", String.valueOf(room_id)+'|' + x.getProductId() + '|' + x.getProductRoomQuantity());

        }
    }
}
