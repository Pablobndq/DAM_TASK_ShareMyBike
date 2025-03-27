package com.example.sharemybike.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sharemybike.R;
import com.example.sharemybike.databinding.FragmentFirstBinding;

import java.util.Date;

public class FirstFragment extends Fragment {

    private CalendarView calendar;
    private TextView date;
    private String sendDate;
    private Button nextButton;

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                binding.dateTextView.setText("Fecha: " + dayOfMonth + "/" + (month + 1) + "/" + year );
                sendDate = dayOfMonth + "/" + month + "/" + year;
            }
        });

        binding.nextButton.setOnClickListener(v ->{
                Bundle bundle = new Bundle();
                bundle.putString("sendDate", sendDate);
                NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                });


        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}