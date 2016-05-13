package com.example.marwaadel.shopowner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.marwaadel.shopowner.model.OfferDataModel;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by Marwa Adel on 5/2/2016.
 */
public class offerAdapter extends FirebaseListAdapter<OfferDataModel> {
    Activity activity;

    public offerAdapter(Activity activity, Class<OfferDataModel> modelClass, int modelLayout, Query ref) {

        super(activity, modelClass, modelLayout, ref);
        this.activity=activity;
        Log.d("ddd", ref.toString());
    }

    @Override
    protected void populateView(View v, final OfferDataModel model,final int position) {
        //Double num1,num2,result;
        LinearLayout deleteBtn = (LinearLayout) v.findViewById(R.id.deleteLayout);
        LinearLayout editBtn = (LinearLayout) v.findViewById(R.id.editLayout);

        TextView title = (TextView) v.findViewById(R.id.titlteoffer);
        TextView description = (TextView) v.findViewById(R.id.descriptionoffer);
        TextView before = (TextView) v.findViewById(R.id.beforeoffer);

        before.setPaintFlags(before.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        TextView after = (TextView) v.findViewById(R.id.afteroffer);
        TextView Discountdifference = (TextView) v.findViewById(R.id.Discountdifferencez);
        Log.d("data", "populateView " + model.getDescription());

//        num1 = Double.parseDouble(after.getText().toString());
//        num2 = Double.parseDouble(before.getText().toString());
//        result=(1- (num1/num2));
//        Discountdifference.setText(Double.toString(result));

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, addoffers.class);
                in.putExtra("editobj", model);
                in.putExtra("uuid", getRef(position).getKey());
                activity.startActivity(in);
            }
        });

        title.setText(model.getTitle());
        description.setText(model.getDescription());
        before.setText(model.getDiscountBefore());
        after.setText(model.getDiscountAfter());

    }
}
