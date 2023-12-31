package com.example.flytdream;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderReviewFragment extends Fragment {
    CoreActivity coreActivity;
    TextView passengerName,flightType,boardingTime,seatNumber,flightClass,price;
    ImageButton confirmButton;
    String names,seat;
    TextView noticeView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderReviewFragment newInstance(String param1, String param2) {
        OrderReviewFragment fragment = new OrderReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //retrieve and get data from this booking session, display it
        View view = inflater.inflate(R.layout.fragment_order_review, container, false);
        coreActivity = (CoreActivity) getActivity();

        BookingSession aBookingSession = coreActivity.getBookingSession();
        aBookingSession.nameString = "";
        aBookingSession.seatString = "";

        passengerName = view.findViewById(R.id.passenger_name);
        flightClass = view.findViewById(R.id.flight_class);
        boardingTime = view.findViewById(R.id.boarding_time);
        seatNumber = view.findViewById(R.id.seat_number);
        confirmButton = view.findViewById(R.id.confirm_order);
        flightType = view.findViewById(R.id.flight_type);
        price = view.findViewById(R.id.total_price);

        if (aBookingSession.flightType.equals("Round Trip")) {
            showAlertDialog();
        }

        names = coreActivity.getBookingSession().checkOutPassengerName(coreActivity.getBookingSession().getPassengers());
        seat = coreActivity.getBookingSession().checkOutPassengerSeats(coreActivity.getBookingSession().getSeats());

        flightType.setText(coreActivity.getBookingSession().getFlightType());
        passengerName.setText(names);
        flightClass.setText(coreActivity.getBookingSession().getClassSelected());
        boardingTime.setText(coreActivity.getBookingSession().getFlight().get(0).getDepartTime());
        seatNumber.setText(seat);
        price.setText("$"+coreActivity.getBookingSession().getTotalCost());
        confirmButton.setOnClickListener(clickListener);

        setHasOptionsMenu(true);

        return view;
    }
    // button function to pass the retrieved data for the next activity to use
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String flightCode = coreActivity.getFlightCode();
            String departCityAlias = coreActivity.getBookingSession().getDepartCity().getCityAlias();
            String departTime = coreActivity.getBookingSession().getFlight().get(0).getDepartTime();
            String flightTime = coreActivity.getBookingSession().getFlight().get(0).getFlightTime();
            String arriveCityAlias = coreActivity.getBookingSession().getArriveCity().getCityAlias();
            String arriveTime = coreActivity.getBookingSession().getFlight().get(0).getArriveTime();
            String passenger = names;
            String flightType = coreActivity.getBookingSession().getFlightType();
            String flightClass = coreActivity.getBookingSession().getClassSelected();
            String flightSeat = seat;
            String board = coreActivity.getBookingSession().getFlight().get(0).getDepartTime();
            String price = "$"+coreActivity.getBookingSession().getTotalCost();
            String departDate = coreActivity.getBookingSession().getDate();
            coreActivity.myDB.insertFlight(flightCode, departCityAlias, departTime, flightTime, arriveCityAlias, arriveTime, passenger, flightType, flightClass, flightSeat, board, price, departDate);

            Intent intent = new Intent(getActivity(),OrderConfirmActivity.class);
            intent.putExtra("flightCode", flightCode);
            intent.putExtra("passenger name",names);
            intent.putExtra("flight class",coreActivity.getBookingSession().getClassSelected());
            intent.putExtra("flight type",coreActivity.getBookingSession().getFlightType());
            intent.putExtra("boarding time",coreActivity.getBookingSession().getFlight().get(0).getDepartTime());
            intent.putExtra("price","$"+coreActivity.getBookingSession().getTotalCost());
            intent.putExtra("boarding time",coreActivity.getBookingSession().getFlight().get(0).getDepartTime());
            intent.putExtra("gate","A5");
            intent.putExtra("terminal","T2");
            intent.putExtra("seat number",seat);
            intent.putExtra("depart city alias",coreActivity.getBookingSession().getDepartCity().getCityAlias());
            intent.putExtra("depart city name",coreActivity.getBookingSession().getDepartCity().getCityName());
            intent.putExtra("depart time",coreActivity.getBookingSession().getFlight().get(0).getDepartTime());
            intent.putExtra("flight time",coreActivity.getBookingSession().getFlight().get(0).getFlightTime());
            intent.putExtra("arrival city alias",coreActivity.getBookingSession().getArriveCity().getCityAlias());
            intent.putExtra("arrival city name",coreActivity.getBookingSession().getArriveCity().getCityName());
            intent.putExtra("arrive time",coreActivity.getBookingSession().getFlight().get(0).getArriveTime());
            intent.putExtra("departDate", departDate);
            startActivity(intent);

        }
    };

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Notification");
        builder.setMessage("We Will Notify You Later For The Seat & Food Selection Of Return Flight! Extra Fee Can Be Applied Based On Your Action");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when the "Okay" button is clicked (optional)
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if (item.getItemId() == R.id.action_profile) {
            Toast.makeText(this.getActivity(), "Yo!", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == android.R.id.home) {
            ArrayList<Meal> mea = new ArrayList<>();
            coreActivity.getBookingSession().setMeals(mea);
            coreActivity.getBookingSession().setTotalCost(0);
            coreActivity.loadFragment(new MealSelectionFragment());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}