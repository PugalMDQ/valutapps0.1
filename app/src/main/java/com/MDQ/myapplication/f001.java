package com.MDQ.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class f001 extends Fragment {

    LineChart chart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_f001, container, false);




        chart=view.findViewById(R.id.line);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);

        chart.getDescription().setEnabled(false);

        //code for removing grid lines
        //chart.getXAxis().setDrawGridLines(false);
        // chart.getAxisLeft().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);

        //chart.getXAxis().setAxisLineColor(getResources().getColor(R.color.black));

        chart.getXAxis().setGridColor(getResources().getColor(R.color.gridcolor));
        chart.getXAxis().setGridLineWidth(2f);
        chart.getXAxis().enableGridDashedLine(10f, 10f, 0f);

        //code for removing outer lines
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getXAxis().setDrawAxisLine(false);

        chart.setDrawingCacheBackgroundColor(getResources().getColor(R.color.graphcolor));

        //to remove the marker value
        chart.highlightValue(null);


        //remove right side value
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //remove left side value
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setEnabled(false);

        //remove x axis value
        XAxis xAxis=chart.getXAxis();
        //xAxis.setEnabled(false);

        //to write the value in bottom of x axis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setTextColor(getResources().getColor(R.color.white));

        xAxis.setTextSize(14f);

        //to remove the description box
        chart.getLegend().setEnabled(false);

        chart.setTouchEnabled(true);

        ArrayList<Entry> yvalue=new ArrayList<>();

        yvalue.add(new Entry(0,60));
        yvalue.add(new Entry(1,65));
        yvalue.add(new Entry(2,40));
        yvalue.add(new Entry(3,45));
        yvalue.add(new Entry(5,70));
        yvalue.add(new Entry(6,60));

        LineDataSet set=new LineDataSet(yvalue,null);
        set.setFillDrawable(getResources().getDrawable(R.drawable.balancebackground));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawFilled(true);
        set.setLabel(null);
        set.setDrawValues(false);
        set.setCircleColor(getResources().getColor(R.color.graphcolor));
        //outer circle
        set.setCircleColorHole(Color.WHITE);
        set.setCircleHoleRadius(4f);
        set.setLineWidth(3f);
        set.setCircleRadius(6f);
        set.setDrawHorizontalHighlightIndicator(false);
        set.setDrawVerticalHighlightIndicator(false);

        set.setColor(getResources().getColor(R.color.graphcolor));
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(set);
        LineData data=new LineData(dataSets);
        chart.setData(data);
        chart.setExtraTopOffset(10f);


        return view;
    }
}