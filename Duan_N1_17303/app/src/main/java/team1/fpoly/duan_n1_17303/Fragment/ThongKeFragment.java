package team1.fpoly.duan_n1_17303.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.BillDao;
import team1.fpoly.duan_n1_17303.Object.ThongKe;
import team1.fpoly.duan_n1_17303.R;


public class ThongKeFragment extends Fragment {

    BarChart barChart;
    EditText ed_Year_Search;
    Button btnYearSearch;

    public ThongKeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChart = (BarChart) view.findViewById(R.id.barChart);

        ed_Year_Search = view.findViewById(R.id.ed_Year_Search);
        btnYearSearch = view.findViewById(R.id.btnYearSearch);
        BillDao billDao = new BillDao(getActivity());

        btnYearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                ArrayList<ThongKe> listMoney = billDao.getTotalMoneyOfYear(ed_Year_Search.getText().toString());
                ArrayList<BarEntry> listBarEntry = new ArrayList<>();
                listBarEntry.clear();
                if(listMoney.isEmpty()){
                    Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_LONG).show();
                }else {
                    for (int i = 1; i <= 12; i++) {
                        for (int j = 0; j<listMoney.size(); j++) {
                            if(listMoney.get(j).getMonth() == i){
                                listBarEntry.add(new BarEntry(listMoney.get(j).getMonth(),listMoney.get(j).getMoney()));
                            }else{
                                listBarEntry.add(new BarEntry(i,0));
                            }
                        }
                    }
                }
                BarDataSet barDataSet = new BarDataSet(listBarEntry,"Money");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);

                BarData barData = new BarData(barDataSet);
                barChart.setFitBars(true);
                barChart.setData(barData);
                barChart.animateY(2000);
            }
        });

    }

}