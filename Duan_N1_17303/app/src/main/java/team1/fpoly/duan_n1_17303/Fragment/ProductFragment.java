package team1.fpoly.duan_n1_17303.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Adapter.ProductAdapter;
import team1.fpoly.duan_n1_17303.Dao.ProductDao;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Bill;
import team1.fpoly.duan_n1_17303.Object.Product;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;
import team1.fpoly.duan_n1_17303.Tools.AddProductFragment;


public class ProductFragment extends Fragment {

    ListView lv_Product;
    FloatingActionButton fl_Add_Product;
    ProductDao productDao;
    ArrayList<Product> listProduct = new ArrayList<>();
    AddProductFragment addProductFragment;
    Product product;
    ProductAdapter productAdapter;

    Button btn_Product_Search;
    EditText ed_Product_Search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv_Product = view.findViewById(R.id.lv_Product);
        fl_Add_Product = view.findViewById(R.id.fl_Add_Product);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        addProductFragment = new AddProductFragment();
        productDao = new ProductDao(getContext());
        loadData();




        lv_Product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //update
                product = listProduct.get(position);
                updateData(position);
            }
        });

        fl_Add_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ẩn toolbar ở menu
                MainActivity mainActivity = (MainActivity) getActivity();
                ActionBar actionBar =  mainActivity.getSupportActionBar();
                actionBar.hide();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, addProductFragment).commit();
            }
        });

        ed_Product_Search = view.findViewById(R.id.ed_Product_Search);
        btn_Product_Search = view.findViewById(R.id.btn_Product_Search);


        btn_Product_Search.setOnClickListener(v -> {
            String productName = ed_Product_Search.getText().toString();
            searchProduct(productName);
        });


    }

    private void searchProduct(String name) {
        ArrayList<Product> listNew = new ArrayList<>();
        for (Product product:listProduct) {
            if (product.getProductName().toLowerCase().toLowerCase().contains(name.toLowerCase())){
                listNew.add(product);
            }
        }
        if (listNew.isEmpty()){
            Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }else productAdapter.setData(listNew);
    }

    private void loadData() {
        listProduct.clear();
        listProduct = productDao.getListProduct();
        productAdapter = new ProductAdapter(getContext(), listProduct,productDao);
        lv_Product.setAdapter(productAdapter);
    }

    private void updateData(int position) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_add_product);
        TextView tv_Title_Product = dialog.findViewById(R.id.tv_Title_Product);
        EditText ed_Product_Name = dialog.findViewById(R.id.ed_Product_Name);
        EditText ed_Product_Price = dialog.findViewById(R.id.ed_Product_Price);
        EditText ed_Product_Quantity = dialog.findViewById(R.id.ed_Product_Quantity);
        ImageView img_Back_Product = dialog.findViewById(R.id.img_Back_Product);
        ImageView img_Save_Product = dialog.findViewById(R.id.img_Save_Product);

        tv_Title_Product.setText("Sửa sản phẩm");
        ed_Product_Name.setText(product.getProductName());
        ed_Product_Price.setText(String.valueOf(product.getPrice()));
        ed_Product_Quantity.setText(String.valueOf(product.getQuantity()));

        img_Back_Product.setOnClickListener(view -> {
            dialog.dismiss();
        });

        img_Save_Product.setOnClickListener(view -> {
            Product product = listProduct.get(position);

            product.setProductName(ed_Product_Name.getText().toString());
            product.setPrice(Integer.parseInt(ed_Product_Price.getText().toString()));
            product.setQuantity(Integer.parseInt(ed_Product_Quantity.getText().toString()));

            if (productDao.updateProduct(product)){
                loadData();
                productAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Sửa thông tin sản phẩm thành công", Toast.LENGTH_SHORT).show();

            }else Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }
}