package com.MDQ.myapplication;

import android.content.Context;
import android.widget.TextView;

import com.MDQ.myapplication.pojo.jsonresponse.DataForDashBoard;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

class YourMarkerView extends MarkerView {

    public static TextView tvContent,tvContent1;
    Context context;
    List<DataForDashBoard> data;
    public YourMarkerView(Context context, int layoutResource, List<DataForDashBoard> data) {
        super(context, layoutResource);

        this.data=data;
        this.context=context;
        // find your layout components
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent1 = (TextView) findViewById(R.id.tvContent1);


    }
    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
     public void refreshContent(Entry e, Highlight highlight) {
        //initialize x and y axis values
        int i= (int) e.getX();
        tvContent.setText(data.get(i).getDay());
        tvContent1.setText("" + e.getY());
        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }}