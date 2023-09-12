package com.example.flytdream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DownloadInvoiceActivity extends AppCompatActivity {
    ImageButton download;
    String flightType,flightCode,boardingTime,gate,terminal,departCityAlias,departCityName,departTime,flightTime,arriveCityAlias,arriveCityName,arriveTime,passengers,seats;
    TextView flightType1,flightCode1,boardingTime1,gate1,terminal1,departCityAlias1,departCityName1,departTime1,flightTime1,arriveCityAlias1,arriveCityName1,arriveTime1,passengers1,seats1,bookingDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_invoice);

        download = findViewById(R.id.download_button);
        download.setOnClickListener(clickListener);

        Intent intent = getIntent();
        passengers = intent.getStringExtra("passenger name");
        flightType = intent.getStringExtra("flight type");
        flightCode = intent.getStringExtra("flight code");
        boardingTime = intent.getStringExtra("boarding time");
        gate = intent.getStringExtra("gate");
        terminal = intent.getStringExtra("terminal");
        seats = intent.getStringExtra("seat number");
        departCityAlias = intent.getStringExtra("depart city alias");
        departCityName = intent.getStringExtra("depart city name");
        departTime = intent.getStringExtra("depart time");
        flightTime = intent.getStringExtra("flight time");
        arriveCityAlias = intent.getStringExtra("arrival city alias");
        arriveCityName = intent.getStringExtra("arrival city name");
        arriveTime = intent.getStringExtra("arrive time");

        departCityAlias1 = findViewById(R.id.depart_alias);
        departTime1 = findViewById(R.id.depart_time);
        flightTime1 = findViewById(R.id.flight_time);
        arriveCityAlias1 = findViewById(R.id.arrive_alias);
        arriveTime1 = findViewById(R.id.arrive_time);
        passengers1 = findViewById(R.id.names);
        flightType1 = findViewById(R.id.type);
        seats1 = findViewById(R.id.seat);
        boardingTime1 = findViewById(R.id.board_time);

        departCityAlias1.setText(departCityAlias);
        departTime1.setText(departTime);
        flightTime1.setText(flightTime);
        arriveCityAlias1.setText(arriveCityAlias);
        arriveTime1.setText(arriveTime);
        passengers1.setText(passengers);
        flightType1.setText(flightType);
        seats1.setText(seats);
        boardingTime1.setText(boardingTime);

        bookingDate = findViewById(R.id.booking_date);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        String monthString = monthFormat.format(calendar.getTime());
        String formatDate = day + " " + monthString + " " + year;
        bookingDate.setText(formatDate);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(DownloadInvoiceActivity.this,"Invoice downloaded",Toast.LENGTH_LONG).show();
        }
    };
}