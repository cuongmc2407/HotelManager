package team1.fpoly.duan_n1_17303.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.ProductBillDao;
import team1.fpoly.duan_n1_17303.Dao.ProductDao;
import team1.fpoly.duan_n1_17303.Dao.ProductRoomDAO;
import team1.fpoly.duan_n1_17303.Object.Product;
import team1.fpoly.duan_n1_17303.R;

public class ProductAdapter extends ArrayAdapter<Product> {

    Context context;
    ArrayList<Product> listProduct;
    ProductDao productDao;

    public ProductAdapter(Context context, ArrayList<Product> listProduct, ProductDao productDao) {
        super(context, 0, listProduct);
        this.context = context;
        this.listProduct = listProduct;
        this.productDao = productDao;
    }

    TextView tv_Product_Name, tv_Price, tv_Quantity;
    ImageView iv_Delete;

    @Override
    public int getCount() {
        if (!listProduct.isEmpty()) return listProduct.size();
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        }

        final Product product = listProduct.get(position);

        if(product != null){
            tv_Product_Name = convertView.findViewById(R.id.tv_Product_Name);
            tv_Price = convertView.findViewById(R.id.tv_Price);
            tv_Quantity = convertView.findViewById(R.id.tv_Quantity);
            iv_Delete = convertView.findViewById(R.id.iv_DeleteProduct);

            tv_Product_Name.setText("Tên sản phẩm: " + product.getProductName());
            tv_Price.setText("Giá: " + product.getPrice());
            tv_Quantity.setText("Số lượng: " + product.getQuantity());
            iv_Delete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa.");
                builder.setMessage("Sản phẩm " + product.getProductName());
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int check  = productDao.deleteProduct(product.getProductId());
                        switch (check){
                            case 1:
                                Toast.makeText(context.getApplicationContext(), "Xóa thành công",Toast.LENGTH_LONG).show();
                                ProductRoomDAO productRoomDAO = new ProductRoomDAO(getContext());
                                productRoomDAO.deleteProductRoomByProductID(String.valueOf(product.getProductId()));
                                ProductBillDao productBillDao = new ProductBillDao(getContext());
                                productBillDao.deleteProductBillByProductID(String.valueOf(product.getProductId()));
                                listProduct.remove(product);
                                notifyDataSetChanged();
                                break;
                            case -1:
                                Toast.makeText(context.getApplicationContext(), "Sản phẩm đã tồn tại trong phòng hoặc trong hóa đơn. Không thể xóa",Toast.LENGTH_LONG).show();
                                break;
                            case 0:
                                Toast.makeText(context.getApplicationContext(), "Xóa thất bại",Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            });
        }

        return convertView;

    }

    public void setData(ArrayList<Product> listNew) {
        this.listProduct = listNew;
        notifyDataSetChanged();
    }
}
