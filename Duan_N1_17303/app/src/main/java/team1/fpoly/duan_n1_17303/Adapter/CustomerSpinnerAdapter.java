package team1.fpoly.duan_n1_17303.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Object.Customer;
import team1.fpoly.duan_n1_17303.R;

public class CustomerSpinnerAdapter extends ArrayAdapter<Customer> {
    Context context;
    ArrayList<Customer> listCustomer;
    TextView tv_Spn_Customer_Name;

    public CustomerSpinnerAdapter(@NonNull Context context, ArrayList<Customer> listCustomer) {
        super(context, 0, listCustomer);
        this.context = context;
        this.listCustomer = listCustomer;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
           view =inflater.inflate(R.layout.item_spn_customer_bill, parent, false);
        }

        final Customer customer = listCustomer.get(position);

        if (customer != null){
            tv_Spn_Customer_Name = view.findViewById(R.id.tv_Spn_Customer_Name);
            tv_Spn_Customer_Name.setText(customer.getCustomerName());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view =inflater.inflate(R.layout.item_spn_customer_bill, parent, false);
        }

        final Customer customer = listCustomer.get(position);

        if (customer != null){
            tv_Spn_Customer_Name = view.findViewById(R.id.tv_Spn_Customer_Name);
            tv_Spn_Customer_Name.setText(customer.getCustomerName());
        }
        return view;
    }
}
