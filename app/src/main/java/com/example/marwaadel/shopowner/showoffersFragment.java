package com.example.marwaadel.shopowner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.marwaadel.shopowner.model.OfferDataModel;
import com.example.marwaadel.shopowner.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import android.view.View.OnClickListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class showoffersFragment extends Fragment {
    GridView gridview;
    FirebaseListAdapter<OfferDataModel> mOfferAdapter;
    ImageButton FAB;
    TextView title;
    LinearLayout linlaHeaderProgress;

    public showoffersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showoffers, container, false);
        if (isConnected(getActivity().getApplicationContext())) {

            linlaHeaderProgress = (LinearLayout) rootView.findViewById(R.id.linlaHeaderProgress);
            gridview = (GridView) rootView.findViewById(R.id.mGridView);
            Firebase refListName = new Firebase(Constants.FIREBASE_URL_ACTIVE_LIST);
            refListName.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("fief", "onDataChange: " + dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            Log.d("kmjk", "onCreateView: " + refListName.getKey());
            mOfferAdapter = new FirebaseListAdapter<OfferDataModel>(getActivity(), OfferDataModel.class, R.layout.offeritem, refListName) {
                @Override
                protected void populateView(View v, OfferDataModel model, int position) {
                    //   double num1,num2,result;

                    title = (TextView) v.findViewById(R.id.titlteoffer);
                    TextView description = (TextView) v.findViewById(R.id.descriptionoffer);
                    TextView before = (TextView) v.findViewById(R.id.beforeoffer);
                    before.setPaintFlags(before.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    TextView after = (TextView) v.findViewById(R.id.afteroffer);
                    Log.d("data", "populateView " + model.getDescription());
                    TextView Discountdifference = (TextView) v.findViewById(R.id.Discountdifferencez);


//        num1 = Double.parseDouble(after.getText().toString());
//        num2 = Double.parseDouble(before.getText().toString());
//        result=(1- (num1/num2));
//        Discountdifference.setText(Double.toString(result));

                    title.setText(model.getTitle());
                    description.setText(model.getDescription());

                    before.setText(model.getDiscountBefore());
                    after.setText(model.getDiscountAfter());
                }
            };


            Log.e("ref", String.valueOf(refListName));
            linlaHeaderProgress.setVisibility(View.VISIBLE);
            gridview.setAdapter(mOfferAdapter);
            mOfferAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    linlaHeaderProgress.setVisibility(View.GONE);
                }
            });


            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    // 1. create an intent pass class name or intnet action name
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    // 2. create offer object
                    OfferDataModel offery = mOfferAdapter.getItem(position);
                    // 3. put offer in intent data
                    intent.putExtra("person", offery);
                    // 4. start the activity
                    startActivity(intent);
                }
            });


            FAB = (ImageButton) rootView.findViewById(R.id.imageButton);
            FAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), addoffers.class);
                    //Intent intent = new Intent(getContext(), MainActivity.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    // Toast.makeText(getActivity(), "Hello Worl", Toast.LENGTH_SHORT).show();


                }
            });


        } else {

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("No Internet connection.");
            alertDialog.setMessage("You have no internet connection");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        return rootView;
    }


}