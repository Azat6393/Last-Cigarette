package com.kastudio.sonsigaram.basarilar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kastudio.sonsigaram.AnaEkranActivity;
import com.kastudio.sonsigaram.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

public class BasarilarFragmentIki extends Fragment {

    TextView progressTextView1, progressTExtView2, progressTextView3, progressTExtView4, progressTextView5, progressTExtView6,
            progressTextView7, progressTExtView8, progressTextView9, progressTExtView10, progressTextView11, progressTExtView12;

    CircularProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6,
            progressBar7, progressBar8, progressBar9, progressBar10, progressBar11, progressBar12;

    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,
            imageView7, imageView8, imageView9, imageView10, imageView11, imageView12;

    TextView sayac, textView1, textView2, textView3, textView4, textView5, textView6,
            textView7, textView8, textView9, textView10, textView11, textView12;

    TextView finishDateTextView1, finishDateTextView2, finishDateTextView3, finishDateTextView4, finishDateTextView5, finishDateTextView6,
            finishDateTextView7, finishDateTextView8, finishDateTextView9, finishDateTextView10, finishDateTextView11, finishDateTextView12;

    TextView nameTextView1, nameTextView2, nameTextView3, nameTextView4, nameTextView5, nameTextView6, nameTextView7, nameTextView8, nameTextView9,
            nameTextView10, nameTextView11, nameTextView12;

    public static BasarilarFragmentIki newInstance(){
        return new BasarilarFragmentIki();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basarilar_fragment_iki,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        progressTextView1 = view.findViewById(R.id.recycler_view_second_progress_text1);
        progressTExtView2 = view.findViewById(R.id.recycler_view_second_progress_text2);
        progressTextView3 = view.findViewById(R.id.recycler_view_second_progress_text3);
        progressTExtView4 = view.findViewById(R.id.recycler_view_second_progress_text4);
        progressTextView5 = view.findViewById(R.id.recycler_view_second_progress_text5);
        progressTExtView6 = view.findViewById(R.id.recycler_view_second_progress_text6);
        progressTextView7 = view.findViewById(R.id.recycler_view_second_progress_text7);
        progressTExtView8 = view.findViewById(R.id.recycler_view_second_progress_text8);
        progressTextView9 = view.findViewById(R.id.recycler_view_second_progress_text9);
        progressTExtView10 = view.findViewById(R.id.recycler_view_second_progress_text10);
        progressTextView11 = view.findViewById(R.id.recycler_view_second_progress_text11);
        progressTExtView12 = view.findViewById(R.id.recycler_view_second_progress_text12);

        progressBar1 = view.findViewById(R.id.recycler_view_second_progress_bar1);
        progressBar2 = view.findViewById(R.id.recycler_view_second_progress_bar2);
        progressBar3 = view.findViewById(R.id.recycler_view_second_progress_bar3);
        progressBar4 = view.findViewById(R.id.recycler_view_second_progress_bar4);
        progressBar5 = view.findViewById(R.id.recycler_view_second_progress_bar5);
        progressBar6 = view.findViewById(R.id.recycler_view_second_progress_bar6);
        progressBar7 = view.findViewById(R.id.recycler_view_second_progress_bar7);
        progressBar8 = view.findViewById(R.id.recycler_view_second_progress_bar8);
        progressBar9 = view.findViewById(R.id.recycler_view_second_progress_bar9);
        progressBar10 = view.findViewById(R.id.recycler_view_second_progress_bar10);
        progressBar11 = view.findViewById(R.id.recycler_view_second_progress_bar11);
        progressBar12 = view.findViewById(R.id.recycler_view_second_progress_bar12);

        imageView1 = view.findViewById(R.id.recycler_view_second_progress_image1);
        imageView2 = view.findViewById(R.id.recycler_view_second_progress_image2);
        imageView3 = view.findViewById(R.id.recycler_view_second_progress_image3);
        imageView4 = view.findViewById(R.id.recycler_view_second_progress_image4);
        imageView5 = view.findViewById(R.id.recycler_view_second_progress_image5);
        imageView6 = view.findViewById(R.id.recycler_view_second_progress_image6);
        imageView7 = view.findViewById(R.id.recycler_view_second_progress_image7);
        imageView8 = view.findViewById(R.id.recycler_view_second_progress_image8);
        imageView9 = view.findViewById(R.id.recycler_view_second_progress_image9);
        imageView10 = view.findViewById(R.id.recycler_view_second_progress_image10);
        imageView11 = view.findViewById(R.id.recycler_view_second_progress_image11);
        imageView12 = view.findViewById(R.id.recycler_view_second_progress_image12);

        finishDateTextView1 = view.findViewById(R.id.basarilar_iki_finish_date1);
        finishDateTextView2 = view.findViewById(R.id.basarilar_iki_finish_date2);
        finishDateTextView3 = view.findViewById(R.id.basarilar_iki_finish_date3);
        finishDateTextView4 = view.findViewById(R.id.basarilar_iki_finish_date4);
        finishDateTextView5 = view.findViewById(R.id.basarilar_iki_finish_date5);
        finishDateTextView6 = view.findViewById(R.id.basarilar_iki_finish_date6);
        finishDateTextView7 = view.findViewById(R.id.basarilar_iki_finish_date7);
        finishDateTextView8 = view.findViewById(R.id.basarilar_iki_finish_date8);
        finishDateTextView9 = view.findViewById(R.id.basarilar_iki_finish_date9);
        finishDateTextView10 = view.findViewById(R.id.basarilar_iki_finish_date10);
        finishDateTextView11 = view.findViewById(R.id.basarilar_iki_finish_date11);
        finishDateTextView12 = view.findViewById(R.id.basarilar_iki_finish_date12);

        nameTextView1 = view.findViewById(R.id.basarilar_iki_name_textView1);
        nameTextView2 = view.findViewById(R.id.basarilar_iki_name_textView2);
        nameTextView3 = view.findViewById(R.id.basarilar_iki_name_textView3);
        nameTextView4 = view.findViewById(R.id.basarilar_iki_name_textView4);
        nameTextView5 = view.findViewById(R.id.basarilar_iki_name_textView5);
        nameTextView6 = view.findViewById(R.id.basarilar_iki_name_textView6);
        nameTextView7 = view.findViewById(R.id.basarilar_iki_name_textView7);
        nameTextView8 = view.findViewById(R.id.basarilar_iki_name_textView8);
        nameTextView9 = view.findViewById(R.id.basarilar_iki_name_textView9);
        nameTextView10 = view.findViewById(R.id.basarilar_iki_name_textView10);
        nameTextView11 = view.findViewById(R.id.basarilar_iki_name_textView11);
        nameTextView12 = view.findViewById(R.id.basarilar_iki_name_textView12);

        textView1 = view.findViewById(R.id.basarilar_iki_textView1);
        textView2 = view.findViewById(R.id.basarilar_iki_textView2);
        textView3 = view.findViewById(R.id.basarilar_iki_textView3);
        textView4 = view.findViewById(R.id.basarilar_iki_textView4);
        textView5 = view.findViewById(R.id.basarilar_iki_textView5);
        textView6 = view.findViewById(R.id.basarilar_iki_textView6);
        textView7 = view.findViewById(R.id.basarilar_iki_textView7);
        textView8 = view.findViewById(R.id.basarilar_iki_textView8);
        textView9 = view.findViewById(R.id.basarilar_iki_textView9);
        textView10 = view.findViewById(R.id.basarilar_iki_textView10);
        textView11 = view.findViewById(R.id.basarilar_iki_textView11);
        textView12 = view.findViewById(R.id.basarilar_iki_textView12);
        sayac = view.findViewById(R.id.fragment_basarilar_iki_sayac);
        setTextToTextView();
        setProgressMax();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int money = sharedPreferences.getInt("money",0);

        float dalFiyat = (float) AnaEkranActivity.fiyat / AnaEkranActivity.pakettekiSigaraSayisi;
        float cigaretteInHour = (float) AnaEkranActivity.sigaraSayisi / 24;
        float profitInHour = (float) dalFiyat * cigaretteInHour;

        float[] floats = new float[]{(float) (10 / profitInHour),(float) (20 / profitInHour),(float) (50 / profitInHour),(float) (100 / profitInHour),(float) (150 / profitInHour),
                (float) (250 / profitInHour),(float) (500 / profitInHour),(float) (1000 / profitInHour),(float) (2500 / profitInHour),(float) (5000 / profitInHour),
                (float) (100000 / profitInHour), (float) (20000 / profitInHour)};

        long x = AnaEkranActivity.timeInMillis;
        long[] finishDateLongs = new long[]{(long)(floats[0] * 3600000 + x), (long)(floats[1] * 3600000 + x), (long)(floats[2] * 3600000 + x), (long)(floats[3] * 3600000 + x),
                (long)(floats[4] * 3600000 + x), (long)(floats[5] * 3600000 + x), (long)(floats[6] * 3600000 + x), (long)(floats[7] * 3600000 + x), (long)(floats[8] * 3600000 + x),
                (long)(floats[9] * 3600000 + x), (long)(floats[10] * 3600000 + x), (long)(floats[11] * 3600000 + x)};

        TextView[] nameTextViews = new TextView[]{textView1, textView2, textView3, textView4, textView5, textView6,
                textView7, textView8, textView9, textView10, textView11, textView12};

        TextView[] nameTextViews2 = new TextView[]{nameTextView1, nameTextView2, nameTextView3, nameTextView4, nameTextView5, nameTextView6, nameTextView7, nameTextView8, nameTextView9,
                nameTextView10, nameTextView11, nameTextView12};

        TextView[] finishDateTextViewList = new TextView[]{finishDateTextView1, finishDateTextView2, finishDateTextView3, finishDateTextView4,
                finishDateTextView5, finishDateTextView6, finishDateTextView7, finishDateTextView8, finishDateTextView9, finishDateTextView10,
                finishDateTextView11, finishDateTextView12};

        TextView[] progressTextViewList = new TextView[]{progressTextView1, progressTExtView2, progressTextView3, progressTExtView4, progressTextView5, progressTExtView6,
                progressTextView7, progressTExtView8, progressTextView9, progressTExtView10, progressTextView11, progressTExtView12};

        ImageView[] imageViews = new ImageView[]{imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,
                imageView7, imageView8, imageView9, imageView10, imageView11, imageView12};

        int[] progresses = new int[]{(money * 100) / 10, (money * 100) / 20, (money * 100) / 50, (money * 100) / 100, (money * 100) / 150, (money * 100) / 250,
                                    (money * 100) / 500, (money * 100) / 1000, (money * 100) / 2500, (money * 100) / 5000, (money * 100) / 10000, (money * 100) / 20000};

        CircularProgressBar[] progressBars = new CircularProgressBar[]{progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6,
                progressBar7, progressBar8, progressBar9, progressBar10, progressBar11, progressBar12};

        int sayacInt = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");

        for(int i = 0; i <=11; i++){
            progressBars[i].setProgress(progresses[i]);
            progressTextViewList[i].setText(progresses[i]+"%");
            if (progressBars[i].getProgress() >= 100){
                progressTextViewList[i].setVisibility(View.INVISIBLE);
                imageViews[i].setVisibility(View.VISIBLE);
                progressBars[i].setVisibility(View.INVISIBLE);
                nameTextViews2[i].setTextColor(getResources().getColor(R.color.BLACK));
                nameTextViews[i].setTextColor(getResources().getColor(R.color.BLACK));
                finishDateTextViewList[i].setText(simpleDateFormat.format(finishDateLongs[i]));
                sayacInt++;
            }
        }
        sayac.setText("" + sayacInt);
    }

    public void setTextToTextView(){
        textView1.setText("10.00" + AnaEkranActivity.money);
                textView2.setText("20.00" + AnaEkranActivity.money);
        textView3.setText("50.00" + AnaEkranActivity.money);
                textView4.setText("100.00" + AnaEkranActivity.money);
        textView5.setText("150.00" + AnaEkranActivity.money);
                textView6.setText("250.00" + AnaEkranActivity.money);
        textView7.setText("500.00" + AnaEkranActivity.money);
                textView8.setText("1000.00" + AnaEkranActivity.money);
        textView9.setText("2500.00" + AnaEkranActivity.money);
                textView10.setText("5000.00" + AnaEkranActivity.money);
        textView11.setText("10000.00" + AnaEkranActivity.money);
                textView12.setText("20000.00" + AnaEkranActivity.money);

    }

    public void setProgressMax(){

        progressBar1.setProgressMax(100);
        progressBar2.setProgressMax(100);
        progressBar3.setProgressMax(100);
        progressBar4.setProgressMax(100);
        progressBar5.setProgressMax(100);
        progressBar6.setProgressMax(100);
        progressBar7.setProgressMax(100);
        progressBar8.setProgressMax(100);
        progressBar9.setProgressMax(100);
        progressBar10.setProgressMax(100);
        progressBar11.setProgressMax(100);
        progressBar12.setProgressMax(100);
    }
}
