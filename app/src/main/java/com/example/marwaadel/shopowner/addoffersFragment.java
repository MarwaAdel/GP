package com.example.marwaadel.shopowner;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.Toolbar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.marwaadel.shopowner.utils.Constants;
import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class addoffersFragment extends Fragment {




    Firebase listsRef;
    EditText mTitle, mDescription, mBefore, mAfter, mDay1, mMonth1, mYear1, mDay2, mMonth2, mYear2;
    ImageButton btn1;
    String img;

    private int PICK_IMAGE_REQUEST = 1;


    public addoffersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                // Toast.makeText(getActivity(), "msg msg", Toast.LENGTH_SHORT).show();
                listsRef = new Firebase(Constants.FIREBASE_URL).child("OfferList");

                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();
                String before = mBefore.getText().toString();
                String after = mAfter.getText().toString();
                String day1 = mDay1.getText().toString();
                String month1 = mMonth1.getText().toString();
                String year1 = mYear1.getText().toString();
                String day2 = mDay2.getText().toString();
                String month2 = mMonth2.getText().toString();
                String year2 = mYear2.getText().toString();
                Map<String, Object> values = new HashMap<>();
                values.put("Title", title);
                values.put("Description", description);
                values.put("Discount Before", before);
                values.put("Discount After", after);
                values.put("Day start Offer", day1);
                values.put("Month start Offer", month1);
                values.put("Year start offer", year1);
                values.put("Day end Offer", day2);
                values.put("Month end Offer", month2);
                values.put("Year end Offer", year2);
                Log.e("image", img);
                values.put("offerImage", img);
                listsRef.push().setValue(values);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_addoffers, container, false);


        Toolbar  toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.close);
       ((addoffers)getActivity()). setSupportActionBar(toolbar);
        ((addoffers)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), showoffers.class);
                startActivity(in);
            }
        });


        mTitle = (EditText) rootView.findViewById(R.id.editTilte);
        mDescription = (EditText) rootView.findViewById(R.id.editDescription);
        mBefore = (EditText) rootView.findViewById(R.id.editTilte2);
        mAfter = (EditText) rootView.findViewById(R.id.editTilte4);
        mDay1 = (EditText) rootView.findViewById(R.id.editDay);
        mMonth1 = (EditText) rootView.findViewById(R.id.editMonth);
        mYear1 = (EditText) rootView.findViewById(R.id.editYear);
        mDay2 = (EditText) rootView.findViewById(R.id.editDay2);
        mMonth2 = (EditText) rootView.findViewById(R.id.editMonth2);
        mYear2 = (EditText) rootView.findViewById(R.id.editYear2);
        btn1 = (ImageButton) rootView.findViewById(R.id.btn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });


        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bYtE);
                // bitmap.recycle();
                byte[] byteArray = bYtE.toByteArray();
                img= Base64.encodeToString(byteArray, Base64.DEFAULT);
                // Log.d(TAG, String.valueOf(bitmap));
//
//                ImageView imageView = (ImageView) findViewById(R.id.btn);
                btn1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}