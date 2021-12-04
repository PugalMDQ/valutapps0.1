package com.MDQ.myapplication;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

class YourMarkerView extends MarkerView {

    private TextView tvContent;
    Context context;
    public YourMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        this.context=context;
        // find your layout components
        tvContent = (TextView) findViewById(R.id.tvContent);



    }
    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
     public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText("" + e.getY());
        balancehome.textView.setText(""+e.getY());
        balancehome.linearLayout.setVisibility(VISIBLE);
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