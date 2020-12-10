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

public class BasarilarFragmentUc extends Fragment {

    TextView progressTextView1, progressTExtView2, progressTextView3, progressTExtView4, progressTextView5, progressTExtView6,
             progressTextView7, progressTExtView8, progressTextView9, progressTExtView10, progressTextView11, progressTExtView12;

    CircularProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6,
                        progressBar7, progressBar8, progressBar9, progressBar10, progressBar11, progressBar12;

    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,
              imageView7, imageView8, imageView9, imageView10, imageView11, imageView12;

    TextView sayac, finishDateTextView1, finishDateTextView2, finishDateTextView3, finishDateTextView4, finishDateTextView5, finishDateTextView6,
            finishDateTextView7, finishDateTextView8, finishDateTextView9, finishDateTextView10, finishDateTextView11, finishDateTextView12;

    TextView nameTextView1, nameTextView2, nameTextView3, nameTextView4, nameTextView5, nameTextView6, nameTextView7, nameTextView8, nameTextView9,
            nameTextView10, nameTextView11, nameTextView12;

    TextView nameText1, nameText2, nameText3, nameText4, nameText5, nameText6, nameText7, nameText8, nameText9, nameText10, nameText11, nameText12;

    public static BasarilarFragmentUc newInstance(){
        return new BasarilarFragmentUc();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basarilar_fragment_uc,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameText1 = view.findViewById(R.id.basarilar_uc_name1);
        nameText2 = view.findViewById(R.id.basarilar_uc_name2);
        nameText3 = view.findViewById(R.id.basarilar_uc_name3);
        nameText4 = view.findViewById(R.id.basarilar_uc_name4);
        nameText5 = view.findViewById(R.id.basarilar_uc_name5);
        nameText6 = view.findViewById(R.id.basarilar_uc_name6);
        nameText7 = view.findViewById(R.id.basarilar_uc_name7);
        nameText8 = view.findViewById(R.id.basarilar_uc_name8);
        nameText9 = view.findViewById(R.id.basarilar_uc_name9);
        nameText10 = view.findViewById(R.id.basarilar_uc_name10);
        nameText11 = view.findViewById(R.id.basarilar_uc_name11);
        nameText12 = view.findViewById(R.id.basarilar_uc_name12);

        nameTextView1 = view.findViewById(R.id.basarilar_uc_name_textView1);
        nameTextView2 = view.findViewById(R.id.basarilar_uc_name_textView2);
        nameTextView3 = view.findViewById(R.id.basarilar_uc_name_textView3);
        nameTextView4 = view.findViewById(R.id.basarilar_uc_name_textView4);
        nameTextView5 = view.findViewById(R.id.basarilar_uc_name_textView5);
        nameTextView6 = view.findViewById(R.id.basarilar_uc_name_textView6);
        nameTextView7 = view.findViewById(R.id.basarilar_uc_name_textView7);
        nameTextView8 = view.findViewById(R.id.basarilar_uc_name_textView8);
        nameTextView9 = view.findViewById(R.id.basarilar_uc_name_textView9);
        nameTextView10 = view.findViewById(R.id.basarilar_uc_name_textView10);
        nameTextView11 = view.findViewById(R.id.basarilar_uc_name_textView11);
        nameTextView12 = view.findViewById(R.id.basarilar_uc_name_textView12);

        progressTextView1 = view.findViewById(R.id.recycler_view_third_progress_text1);
        progressTExtView2 = view.findViewById(R.id.recycler_view_third_progress_text2);
        progressTextView3 = view.findViewById(R.id.recycler_view_third_progress_text3);
        progressTExtView4 = view.findViewById(R.id.recycler_view_third_progress_text4);
        progressTextView5 = view.findViewById(R.id.recycler_view_third_progress_text5);
        progressTExtView6 = view.findViewById(R.id.recycler_view_third_progress_text6);
        progressTextView7 = view.findViewById(R.id.recycler_view_third_progress_text7);
        progressTExtView8 = view.findViewById(R.id.recycler_view_third_progress_text8);
        progressTextView9 = view.findViewById(R.id.recycler_view_third_progress_text9);
        progressTExtView10 = view.findViewById(R.id.recycler_view_third_progress_text10);
        progressTextView11 = view.findViewById(R.id.recycler_view_third_progress_text11);
        progressTExtView12 = view.findViewById(R.id.recycler_view_third_progress_text12);

        progressBar1 = view.findViewById(R.id.recycler_view_third_progress_bar1);
        progressBar2 = view.findViewById(R.id.recycler_view_third_progress_bar2);
        progressBar3 = view.findViewById(R.id.recycler_view_third_progress_bar3);
        progressBar4 = view.findViewById(R.id.recycler_view_third_progress_bar4);
        progressBar5 = view.findViewById(R.id.recycler_view_third_progress_bar5);
        progressBar6 = view.findViewById(R.id.recycler_view_third_progress_bar6);
        progressBar7 = view.findViewById(R.id.recycler_view_third_progress_bar7);
        progressBar8 = view.findViewById(R.id.recycler_view_third_progress_bar8);
        progressBar9 = view.findViewById(R.id.recycler_view_third_progress_bar9);
        progressBar10 = view.findViewById(R.id.recycler_view_third_progress_bar10);
        progressBar11 = view.findViewById(R.id.recycler_view_third_progress_bar11);
        progressBar12 = view.findViewById(R.id.recycler_view_third_progress_bar12);

        imageView1 = view.findViewById(R.id.recycler_view_third_progress_image1);
        imageView2 = view.findViewById(R.id.recycler_view_third_progress_image2);
        imageView3 = view.findViewById(R.id.recycler_view_third_progress_image3);
        imageView4 = view.findViewById(R.id.recycler_view_third_progress_image4);
        imageView5 = view.findViewById(R.id.recycler_view_third_progress_image5);
        imageView6 = view.findViewById(R.id.recycler_view_third_progress_image6);
        imageView7 = view.findViewById(R.id.recycler_view_third_progress_image7);
        imageView8 = view.findViewById(R.id.recycler_view_third_progress_image8);
        imageView9 = view.findViewById(R.id.recycler_view_third_progress_image9);
        imageView10 = view.findViewById(R.id.recycler_view_third_progress_image10);
        imageView11 = view.findViewById(R.id.recycler_view_third_progress_image11);
        imageView12 = view.findViewById(R.id.recycler_view_third_progress_image12);

        sayac = view.findViewById(R.id.fragment_basarilar_uc_sayac);
        finishDateTextView1 = view.findViewById(R.id.basarilar_uc_finish_date1);
        finishDateTextView2 = view.findViewById(R.id.basarilar_uc_finish_date2);
        finishDateTextView3 = view.findViewById(R.id.basarilar_uc_finish_date3);
        finishDateTextView4 = view.findViewById(R.id.basarilar_uc_finish_date4);
        finishDateTextView5 = view.findViewById(R.id.basarilar_uc_finish_date5);
        finishDateTextView6 = view.findViewById(R.id.basarilar_uc_finish_date6);
        finishDateTextView7 = view.findViewById(R.id.basarilar_uc_finish_date7);
        finishDateTextView8 = view.findViewById(R.id.basarilar_uc_finish_date8);
        finishDateTextView9 = view.findViewById(R.id.basarilar_uc_finish_date9);
        finishDateTextView10 = view.findViewById(R.id.basarilar_uc_finish_date10);
        finishDateTextView11 = view.findViewById(R.id.basarilar_uc_finish_date11);
        finishDateTextView12 = view.findViewById(R.id.basarilar_uc_finish_date12);

        setProgressMax();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int days = sharedPreferences.getInt("days",0);
        int hours = sharedPreferences.getInt("hours",0);

        long hourInMillis = 3600000;
        long x = AnaEkranActivity.timeInMillis;
        long[] finishDateLongs = new long[]{24 * hourInMillis + x, 72 * hourInMillis + x, 168 * hourInMillis + x, 336 * hourInMillis + x, 504 * hourInMillis + x, 724 * hourInMillis + x,
                2232 * hourInMillis + x, 4464 * hourInMillis + x, 6696 * hourInMillis + x, 8760 * hourInMillis + x, 17520 * hourInMillis + x, 43800 * hourInMillis + x};

        TextView[] finishDateTextViewList = new TextView[]{finishDateTextView1, finishDateTextView2, finishDateTextView3, finishDateTextView4,
                finishDateTextView5, finishDateTextView6, finishDateTextView7, finishDateTextView8, finishDateTextView9, finishDateTextView10,
                finishDateTextView11, finishDateTextView12};

        TextView[] nameTextViews2 = new TextView[]{nameTextView1, nameTextView2, nameTextView3, nameTextView4, nameTextView5, nameTextView6, nameTextView7, nameTextView8, nameTextView9,
                nameTextView10, nameTextView11, nameTextView12};

        TextView[] nameTextViews = new TextView[]{nameText1, nameText2, nameText3, nameText4, nameText5, nameText6, nameText7, nameText8, nameText9, nameText10, nameText11, nameText12};

        TextView[] progressTextViewList = new TextView[]{progressTextView1, progressTExtView2, progressTextView3, progressTExtView4, progressTextView5, progressTExtView6,
                progressTextView7, progressTExtView8, progressTextView9, progressTExtView10, progressTextView11, progressTExtView12};

        ImageView[] imageViews = new ImageView[]{imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,
                imageView7, imageView8, imageView9, imageView10, imageView11, imageView12};

        int[] progresses = new int[]{(hours * 100) / 24, (hours * 100) / 24, (hours * 100) / 168, (hours * 100) / 336, (hours * 100) / 504, (days * 100) / 31,
                (days * 100) / 93, (days * 100) / 186, (days * 100) / 279, (days * 100) / 365, (days * 100) / 730, (days * 100) / 1825};

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
                nameTextViews2[i].setTextColor(getResources().getColor(R.color.BLACK));
                nameTextViews[i].setTextColor(getResources().getColor(R.color.BLACK));
                finishDateTextViewList[i].setText(simpleDateFormat.format(finishDateLongs[i]));
                sayacInt++;
            }
        }
        sayac.setText("" + sayacInt);
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
