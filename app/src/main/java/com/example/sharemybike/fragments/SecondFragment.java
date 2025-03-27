package com.example.sharemybike.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemybike.R;
import com.example.sharemybike.activities.BikeActivity;
import com.example.sharemybike.adapters.MyItemRecyclerViewAdapter;
import com.example.sharemybike.databinding.FragmentSecondBinding;
import com.example.sharemybike.models.BikesContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SecondFragment extends Fragment {

    List<BikesContent.Bike> bikes;
    private Button selectDateBtn;
    private RecyclerView bikeList;
    private FragmentSecondBinding binding;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private String receivedDate;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        bikes = this.getAllBikes();
        receivedDate = getArguments().getString("sendDate");
        if(receivedDate == null){
            Date todayDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            receivedDate = sdf.format(todayDate);
        }

        bikeList = binding.listBikes;
        layoutManager = new LinearLayoutManager(context);
        adapter = new MyItemRecyclerViewAdapter(bikes, R.layout.fragment_item, new MyItemRecyclerViewAdapter.OnEmailBtnClickListener() {
            @Override
            public void onBtnClick(BikesContent.Bike bike, int position) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("message/rfc822");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{bike.getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing bike");
                intent.putExtra(Intent.EXTRA_TEXT, "Dear Mr/Mrs " + bike.getOwner() + ": I'd like to use your bike at" + bike.getLocation() + " " + bike.getCity() + " for the following date: " + receivedDate + " Can you confirm its availability? Kindest regards");

                startActivity(Intent.createChooser(intent, "Elige una aplicación para enviar el correo:"));
            }
        });

        bikeList.setLayoutManager(layoutManager);
        bikeList.setAdapter(adapter);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.selectDateButton.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<BikesContent.Bike> getAllBikes(){
        Context context = requireActivity();
        BikesContent.ITEMS.clear();
        BikesContent.loadBikesFromJSON(this.context);
        return BikesContent.ITEMS;
    }

}