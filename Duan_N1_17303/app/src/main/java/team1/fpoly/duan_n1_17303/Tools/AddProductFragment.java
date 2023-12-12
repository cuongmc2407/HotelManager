package team1.fpoly.duan_n1_17303.Tools;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.BillDao;
import team1.fpoly.duan_n1_17303.Dao.ProductBillDao;
import team1.fpoly.duan_n1_17303.Dao.ProductDao;
import team1.fpoly.duan_n1_17303.Dao.ProductRoomDAO;
import team1.fpoly.duan_n1_17303.Dao.RoomDao;
import team1.fpoly.duan_n1_17303.Fragment.CustomerFragment;
import team1.fpoly.duan_n1_17303.Fragment.ProductFragment;
import team1.fpoly.duan_n1_17303.MainActivity;
import team1.fpoly.duan_n1_17303.Object.Bill;
import team1.fpoly.duan_n1_17303.Object.Product;
import team1.fpoly.duan_n1_17303.Object.ProductBill;
import team1.fpoly.duan_n1_17303.Object.ProductRoom;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;

public class AddProductFragment extends Fragment {

    FragmentManager fm;
    ProductFragment productFragment;
    Toolbar toolbarProduct;

    ImageView img_Back_Product,img_Save_Product;
    EditText ed_Product_Name,ed_Product_Price,ed_Product_Quantity;

    ArrayList<Product> listProduct;
    ProductDao productDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbarProduct = view.findViewById(R.id.toolbarProduct);
        ed_Product_Name = view.findViewById(R.id.ed_Product_Name);
        ed_Product_Price = view.findViewById(R.id.ed_Product_Price);
        ed_Product_Quantity = view.findViewById(R.id.ed_Product_Quantity);
        img_Back_Product = view.findViewById(R.id.img_Back_Product);
        img_Save_Product = view.findViewById(R.id.img_Save_Product);

        fm = getActivity().getSupportFragmentManager();
        productFragment = new ProductFragment();

        img_Back_Product.setOnClickListener(view1 -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            ActionBar actionBar =  mainActivity.getSupportActionBar();
            actionBar.show();
            fm.beginTransaction().replace(R.id.frameLayout, productFragment).commit();
        });
        img_Save_Product.setOnClickListener(view1 -> {
            saveDataProduct();
        });
    }

    private void saveDataProduct() {

        productDao = new ProductDao(getActivity());
        Product p = new Product();
        String name="";int price=0; int quantity=0;
        try {
             name = ed_Product_Name.getText().toString();
            price = Integer.parseInt(ed_Product_Price.getText().toString());
             quantity = Integer.parseInt(ed_Product_Quantity.getText().toString());
        }catch (Exception e){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        if(name.isEmpty()|| price==0||quantity==0){
            Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        p.setProductName(name);
        p.setPrice(price);
        p.setQuantity(quantity);

        if(productDao.insertProduct(p)){
            Toast.makeText(getActivity(), "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
            addPR(p);
            addPB(p);
        }else{
            Toast.makeText(getActivity(), "Thêm sản phẩm thất bại", Toast.LENGTH_LONG).show();
        }

        MainActivity mainActivity = (MainActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        actionBar.show();
        fm.beginTransaction().replace(R.id.frameLayout, productFragment).commit();
    }

    private void addPB(Product p) {
        ProductBillDao productBillDao = new ProductBillDao(getContext());
        BillDao billDao = new BillDao(getContext());
        ArrayList<Bill> listBill = billDao.getListBill();
        for(Bill x: listBill){
            productBillDao.insertProductBill(new ProductBill(p.getProductId(), x.getBillId(), p.getProductName(),p.getPrice(),0));
        }
    }

    private void addPR(Product p) {
        ProductRoomDAO productRoomDao = new ProductRoomDAO(getContext());

        RoomDao roomDao = new RoomDao(getContext());
        ArrayList<Room> listRoom = roomDao.getDataRoom();
        for (Room x: listRoom) {
            productRoomDao.insertProductRoom(new ProductRoom(p.getProductId(),x.getRoomId(),p.getProductName(),p.getPrice(),0));
        }
    }
}