package com.kastudio.sonsigaram.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
import com.kastudio.sonsigaram.LastContentProvider;
import com.kastudio.sonsigaram.MyApp;
import com.kastudio.sonsigaram.ProfilePopUpActivity;
import com.kastudio.sonsigaram.R;
import com.kastudio.sonsigaram.RegisterInfoActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import maes.tech.intentanim.CustomIntent;

public class FragmentProfile extends Fragment implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    LinearLayout icilenSigaraLinearLayout, harcananParaLinearLayout, kaybEdilenZamanLinearLayout,
                gunlukIcilenSigaraLinearLayout, pakettekiSigaraLinearLayout, nameLinearLayout,
                tarihLinearLayout, fiyatLinearLayout, deleteLinearLayout, signOutLinearLayout,
                startOverLinearLayout;

    Animation animationOnClick;

    TextView icilenSigaraTextView, harcananParaTextView, zamanTextView, birakilanTarihTextView,
            gunlukIcilenTextView, paketinFiyatiTextView, pakettekiSigaraTextView, isimTextView;

    String tarihString, nameString, priceString;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String uuid;


    public static FragmentProfile newInstance(){
        return new FragmentProfile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_xml,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Manually check internet connection
        checkInternetConnection();
        System.out.println(uuid);

        //Linear Layout
        icilenSigaraLinearLayout = view.findViewById(R.id.linear_layout_icilen_sigaralar);
        harcananParaLinearLayout = view.findViewById(R.id.harcana_para_linear_layout);
        kaybEdilenZamanLinearLayout = view.findViewById(R.id.kayb_edilen_zaman_linear_layout);
        gunlukIcilenSigaraLinearLayout = view.findViewById(R.id.gunlun_icilen_sigara_linear_layout);
        pakettekiSigaraLinearLayout = view.findViewById(R.id.paketteki_sigara_sayisi_linear_layout);
        nameLinearLayout = view.findViewById(R.id.name_linear_layout);
        fiyatLinearLayout = view.findViewById(R.id.price_linear_layout);
        tarihLinearLayout = view.findViewById(R.id.tarih_linear_layout);
        deleteLinearLayout = view.findViewById(R.id.delete_linear_layout);
        signOutLinearLayout = view.findViewById(R.id.sign_out_linear_layout);
        startOverLinearLayout = view.findViewById(R.id.start_over_linear_layout);

        //Text View
        icilenSigaraTextView = view.findViewById(R.id.profile_icilen_sigara_textView);
        harcananParaTextView = view.findViewById(R.id.profile_harcanan_para_textView);
        zamanTextView = view.findViewById(R.id.profile_zaman_textView);
        birakilanTarihTextView = view.findViewById(R.id.profile_birakilan_zaman_textView);
        gunlukIcilenTextView = view.findViewById(R.id.profile_gunluk_sigara_textView);
        paketinFiyatiTextView = view.findViewById(R.id.profile_sigara_fiyati_textView);
        pakettekiSigaraTextView = view.findViewById(R.id.profile_paketteki_sigara_text_view);
        isimTextView = view.findViewById(R.id.profile_isim_textView);


        gunlukIcilenTextView.setText("" + AnaEkranActivity.sigaraSayisi);
        pakettekiSigaraTextView.setText("" + AnaEkranActivity.pakettekiSigaraSayisi);
        icilenSigaraTextView.setText("" + AnaEkranActivity.profileOne);
        float harcananParaFloat = AnaEkranActivity.profileTwo;
        harcananParaTextView.setText("" + String.format("%.2f",harcananParaFloat) + AnaEkranActivity.money);
        int profileUcHour = AnaEkranActivity.profileThreeHour;
        int profileUcDay = AnaEkranActivity.profileThreeDay;
        int profileUcMonth = AnaEkranActivity.profileThreeMonth;
        zamanTextView.setText(profileUcHour + getResources().getString(R.string.mn) + " " +
                profileUcDay + getResources().getString(R.string.d) + " " +
                profileUcMonth + getResources().getString(R.string.h));

        nameString = AnaEkranActivity.name;
        priceString = String.valueOf(AnaEkranActivity.fiyat);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.LL.yyyy  kk:mm");
        tarihString = simpleDateFormat.format(AnaEkranActivity.timeInMillis);
        birakilanTarihTextView.setText("" + simpleDateFormat.format(AnaEkranActivity.timeInMillis));

        animationOnClick = AnimationUtils.loadAnimation(getContext(),R.anim.on_click_button);

        icilenSigaraLinearLayout.setOnClickListener(this);
        harcananParaLinearLayout.setOnClickListener(this);
        kaybEdilenZamanLinearLayout.setOnClickListener(this);
        gunlukIcilenSigaraLinearLayout.setOnClickListener(this);
        pakettekiSigaraLinearLayout.setOnClickListener(this);
        nameLinearLayout.setOnClickListener(this);
        tarihLinearLayout.setOnClickListener(this);
        fiyatLinearLayout.setOnClickListener(this);
        deleteLinearLayout.setOnClickListener(this);
        signOutLinearLayout.setOnClickListener(this);
        startOverLinearLayout.setOnClickListener(this);

    }

    public void getUUID(){
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
            }
        });
    }

    private void checkInternetConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected){
            getUUID();
        }
    }

    @Override
    public void onClick(View v) {

        boolean isConnected = ConnectivityReceiver.isConnected();

        switch (v.getId()){
            case R.id.linear_layout_icilen_sigaralar:
                icilenSigaraLinearLayout.startAnimation(animationOnClick);
                break;
            case R.id.harcana_para_linear_layout:
                harcananParaLinearLayout.startAnimation(animationOnClick);
                break;
            case R.id.kayb_edilen_zaman_linear_layout:
                kaybEdilenZamanLinearLayout.startAnimation(animationOnClick);
                break;
            case R.id.gunlun_icilen_sigara_linear_layout:
                gunlukIcilenSigaraLinearLayout.startAnimation(animationOnClick);
                break;
            case R.id.paketteki_sigara_sayisi_linear_layout:
                pakettekiSigaraLinearLayout.startAnimation(animationOnClick);
                break;
            case R.id.name_linear_layout:
                //update name
                    final Intent intent = ProfilePopUpActivity.newIntent(getContext());
                    intent.putExtra("x",1);
                    intent.putExtra("name",nameString);
                    startActivity(intent);
                break;
            case R.id.tarih_linear_layout:
                //update time
                    AlertDialog.Builder alert4 = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
                    alert4.setTitle(getResources().getString(R.string.warning));
                    alert4.setMessage(getResources().getString(R.string.change_date_dialog));
                    alert4.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getUUID();
                            Intent intent4 = new Intent(getContext(), RegisterInfoActivity.class);
                            intent4.putExtra("check",true);
                            startActivity(intent4);
                            getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        }
                    }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert4.show();
                break;
            case R.id.price_linear_layout:
                //update price
                    Intent intent2 = ProfilePopUpActivity.newIntent(getContext());
                    intent2.putExtra("x",3);
                    intent2.putExtra("price",priceString);
                    startActivity(intent2);
                break;
            case R.id.delete_linear_layout:
                //delete profile
                if (isConnected) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
                    alert.setTitle(getResources().getString(R.string.warning));
                    alert.setMessage(getResources().getString(R.string.delete_account_message));
                    alert.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            boolean isConnected = ConnectivityReceiver.isConnected();

                            if (isConnected){
                                getUUID();
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                databaseReference.child(uuid).removeValue();
                                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            String userName = AnaEkranActivity.username;
                                            String [] selectionArguments = {userName};
                                            String contentUriString = "content://com.kastudio.sonsigaram.LastContentProvider/last";
                                            Uri contentUri = Uri.parse(contentUriString);
                                            getActivity().getContentResolver().delete(contentUri,"username=?",selectionArguments);

                                            mAuth.signOut();

                                            Intent intent3 = new Intent(getActivity(), KayitOlmaActivity.class);
                                            startActivity(intent3);
                                            CustomIntent.customType(getActivity(),"fadein-to-fadeout");
                                            getActivity().finish();
                                        }else {
                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.failed_network), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.failed_network), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.sign_out_linear_layout:

                getUUID();
                AlertDialog.Builder alert2 = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
                alert2.setTitle(getResources().getString(R.string.warning));
                alert2.setMessage(getResources().getString(R.string.sign_out_message));
                alert2.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userName = AnaEkranActivity.username;
                        String [] selectionArguments = {userName};
                        String contentUriString = "content://com.kastudio.sonsigaram.LastContentProvider/last";
                        Uri contentUri = Uri.parse(contentUriString);
                        getActivity().getContentResolver().delete(contentUri,"username=?",selectionArguments);

                        LoginManager.getInstance().logOut();
                        mAuth.signOut();

                        Intent intent4 = new Intent(getActivity(), KayitOlmaActivity.class);
                        startActivity(intent4);
                        CustomIntent.customType(getActivity(),"fadein-to-fadeout");
                        getActivity().finish();
                    }
                });
                alert2.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert2.show();
                break;
            case R.id.start_over_linear_layout:

                    AlertDialog.Builder alert3 = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
                    alert3.setTitle(getResources().getString(R.string.warning));
                    alert3.setMessage(getResources().getString(R.string.start_over_message));
                    alert3.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent4 = new Intent(getContext(), RegisterInfoActivity.class);
                            intent4.putExtra("check",true);
                            startActivity(intent4);
                            getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        }
                    });
                    alert3.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert3.show();
                    break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        isimTextView.setText(AnaEkranActivity.name);
        float fiyat = AnaEkranActivity.fiyat;
        paketinFiyatiTextView.setText("" + String.format("%.2f",fiyat) + AnaEkranActivity.money);

        //register intent filter
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        getActivity().registerReceiver(connectivityReceiver,intentFilter);

        //register connection status listener
        MyApp.getInstance().setConnectivityListener(this);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected){
            getUUID();
        }
    }
}
