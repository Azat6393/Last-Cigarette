package com.kastudio.sonsigaram.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kastudio.sonsigaram.AnaEkranActivity;
import com.kastudio.sonsigaram.ConnectivityReceiver;
import com.kastudio.sonsigaram.KayitOlmaActivity;
import com.kastudio.sonsigaram.ProfilePopUpActivity;
import com.kastudio.sonsigaram.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FragmentProfilePopUp extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spinner;

    String money, firstName, firstPrice, firstMoney;

    String uuid;

    ImageView icon;

    EditText nameEditText, priceEditView;

    Button dontSaveName, saveName, dontSavePrice, savePrice;

    LinearLayout nameLinearLayout, priceLinearLayout;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static FragmentProfilePopUp newInstance(){
        return new FragmentProfilePopUp();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pop_up_profile_setting,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profiles");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String userName = hashMap.get("username");
                    if (userName.matches(mAuth.getCurrentUser().getEmail().toString())){
                        uuid = ds.getKey();
                        System.out.println(uuid);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        spinner = view.findViewById(R.id.profile_pop_up_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.moneys,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        icon = view.findViewById(R.id.pop_up_profile_icon);
        dontSaveName = view.findViewById(R.id.pop_up_dont_save_name);
        saveName = view.findViewById(R.id.pop_up_save_name);
        dontSavePrice = view.findViewById(R.id.pop_up_dont_save_price);
        savePrice = view.findViewById(R.id.pop_up_save_price);
        nameEditText = view.findViewById(R.id.pop_up_name_editText);
        priceEditView = view.findViewById(R.id.pop_up_price_editText);
        nameLinearLayout = view.findViewById(R.id.pop_up_name_linear_layout);
        priceLinearLayout = view.findViewById(R.id.pop_up_price_linear_layout);

        Intent intent = getActivity().getIntent();
        int x = intent.getIntExtra("x",0);

        if (x == 1){

            nameLinearLayout.setVisibility(View.VISIBLE);
            priceLinearLayout.setVisibility(View.INVISIBLE);

            nameEditText.setText(AnaEkranActivity.name);
            firstName = nameEditText.getText().toString();

        }else if (x == 3){

            icon.setImageResource(R.drawable.profile_icon_money);
            nameLinearLayout.setVisibility(View.INVISIBLE);
            priceLinearLayout.setVisibility(View.VISIBLE);

            priceEditView.setText(""+AnaEkranActivity.fiyat);
            firstPrice = priceEditView.getText().toString();
            firstMoney = AnaEkranActivity.money;

        }

        dontSaveName.setOnClickListener(this);
        saveName.setOnClickListener(this);
        dontSavePrice.setOnClickListener(this);
        savePrice.setOnClickListener(this);

        checkButton();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        money = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    public void onClick(View v) {

        ContentValues contentValues = new ContentValues();
        String contentUriString = "content://com.kastudio.sonsigaram.LastContentProvider/last";
        Uri contentUri = Uri.parse(contentUriString);
        boolean isConnected = ConnectivityReceiver.isConnected();
        switch (v.getId()){
            case R.id.pop_up_dont_save_name:
                getActivity().onBackPressed();
                break;
            case R.id.pop_up_save_name:
                if (isConnected){
                    if (!nameEditText.getText().toString().matches("")){
                        //update Firebase
                        DatabaseReference databaseReference1 = firebaseDatabase.getReference();
                        databaseReference1.child("Profiles").child(uuid).child("name").setValue(nameEditText.getText().toString());
                        //update Database
                        String[] selectionArguments = {firstName};
                        contentValues.put("name",nameEditText.getText().toString());
                        ProfilePopUpActivity.getInstance().getContentResolver().update(contentUri,contentValues,"name=?",selectionArguments);

                        AnaEkranActivity.getInstance().getData();
                        getActivity().finish();
                        getActivity().onBackPressed();
                    }
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.failed_network), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pop_up_dont_save_price:
                getActivity().onBackPressed();
                break;
            case R.id.pop_up_save_price:
                if (isConnected) {
                    if (!priceEditView.getText().toString().matches("")) {
                        //update Firebase
                        DatabaseReference databaseReference1 = firebaseDatabase.getReference();
                        databaseReference1.child("Profiles").child(uuid).child("fiyat").setValue(priceEditView.getText().toString());
                        databaseReference1.child("Profiles").child(uuid).child("money").setValue(money);
                        //update Database
                        String[] selectionArguments = {firstPrice};
                        String[] selectionArguments2 = {firstMoney};
                        contentValues.put("fiyat", priceEditView.getText().toString());
                        contentValues.put("money", money);
                        ProfilePopUpActivity.getInstance().getContentResolver().update(contentUri, contentValues, "fiyat=?", selectionArguments);
                        ProfilePopUpActivity.getInstance().getContentResolver().update(contentUri, contentValues, "money=?", selectionArguments2);

                        AnaEkranActivity.getInstance().getData();
                        ProfilePopUpActivity.getInstance().finish();
                        ProfilePopUpActivity.getInstance().onBackPressed();
                    }
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.failed_network), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void checkButton(){

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEditText();
            }
        };
        nameEditText.addTextChangedListener(textWatcher);
        priceEditView.addTextChangedListener(textWatcher);
        checkEditText();

    }

    public void checkEditText (){

        if (nameEditText.getText().toString().equals("")){
            saveName.setEnabled(false);
            saveName.setAlpha(0.5f);
        }else{
            saveName.setEnabled(true);
            saveName.setAlpha(1f);
        }
        if (priceEditView.getText().toString().equals("")){
            savePrice.setEnabled(false);
            savePrice.setAlpha(0.5f);
        }else {
            savePrice.setEnabled(true);
            savePrice.setAlpha(1f);
        }

    }

}

