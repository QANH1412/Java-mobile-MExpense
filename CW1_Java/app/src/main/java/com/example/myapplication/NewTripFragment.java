package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
class TripList {
    public static List<TripListFragment.Trip> list = new ArrayList<TripListFragment.Trip>();

}

public class NewTripFragment extends Fragment {

    TextInputEditText dateOfTrip;
    Calendar calender;
    TextInputEditText nameOfTrip;
    TextInputEditText tripDestination;
    TextInputEditText email;
    TextInputEditText phoneNumber;
    TextInputEditText description;
    CheckBox tickAssessment;
    Button createNewTrip;
    TextView textviewNameTrip;
    TextView textviewDestination;
    TextView textviewDateTrip;
    TextView textviewEmail;
    TextView textviewPhoneNumber;
    TextView textviewDescription;
    TextView textviewTickAssessment;
    Button buttonDialogConfirm;
    Button buttonDialogCancel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewTripFragment newInstance(String param1, String param2) {
        NewTripFragment fragment = new NewTripFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_trip, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();


        // get id
        TextInputEditText nameofTrip = view.findViewById(R.id.TextInputNameTrip);
        TextInputEditText tripDestination = view.findViewById(R.id.TextInputDestinationTrip);
        TextInputEditText email = view.findViewById(R.id.TextInputEmail);
        TextInputEditText phoneNumber = view.findViewById(R.id.TextInputPhoneNumber);
        TextInputEditText description = view.findViewById(R.id.TextInputDescription);
        CheckBox tickAssessment = view.findViewById(R.id.checkBoxRiskAssessment);
        Button createNewTrip = view.findViewById(R.id.buttonCreateTrip);


        // calendar and date picker
        TextInputEditText dateOfTrip = view.findViewById(R.id.TextInputDateTrip);

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                updateCalendar();
            }

            private void updateCalendar(){
                String Format="MM/dd/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format, Locale.US);

                dateOfTrip.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        dateOfTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // End of calendar and date picker
        
        //Button check condition and create dialog
        createNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameofTrip.length()== 0)
                {
                    nameofTrip.setError("Enter name");
                }
                else if(tripDestination.length()== 0)
                {
                    tripDestination.setError("Enter destination");
                }
                else if(dateOfTrip.length()== 0)
                {
                    dateOfTrip.setError("Enter date");
                }
                else if(email.length()== 0)
                {
                    email.setError("Enter Email");
                }
                else if(phoneNumber.length()== 0)
                {
                    phoneNumber.setError("Enter destination");
                }
                else if(description.length()== 0)
                {
                    description.setError("Enter Description");
                }
                else
                {
                    DialogLogin();
                }
            }

            private void DialogLogin() {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_custom_confirm);
                // get id from all textview
                TextView textviewNameTrip = dialog.findViewById(R.id.textViewNameTrip);
                TextView textviewDestination = dialog.findViewById(R.id.textViewDestination);
                TextView textviewDateTrip = dialog.findViewById(R.id.textViewDateTrip);
                TextView textviewEmail = dialog.findViewById(R.id.textViewEmail);
                TextView textviewPhoneNumber = dialog.findViewById(R.id.textViewPhoneNumber);
                TextView textviewDescription = dialog.findViewById(R.id.textViewDescription);
                TextView textviewTickAssessment = dialog.findViewById(R.id.textViewRiskAsessment);

                Button buttonDialogConfirm = dialog.findViewById(R.id.ButtonDialogConfirm);
                Button buttonDialogCancel = dialog.findViewById(R.id.ButtonDialogCancel);



                // convert all input to dialog
                String nameTripInput = nameofTrip.getText().toString();
                textviewNameTrip.setText(nameTripInput);

                String tripDestinationInput = tripDestination.getText().toString();
                textviewDestination.setText(tripDestinationInput);

                String dateOfTripInput = dateOfTrip.getText().toString();
                textviewDateTrip.setText(dateOfTripInput);

                String emailInput = email.getText().toString();
                textviewEmail.setText(emailInput);

                String phoneNumberInput = phoneNumber.getText().toString();
                textviewPhoneNumber.setText(phoneNumberInput);

                String descriptionInput = description.getText().toString();
                textviewDescription.setText(descriptionInput);

                if(tickAssessment.isChecked() == true)
                {
                    textviewTickAssessment.setText("Yes");

                } else{
                    textviewTickAssessment.setText("No");
                }
                String tickAssessmentInput = textviewTickAssessment.getText().toString();

                // Button click in dialog

                buttonDialogConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // object trip add into list
                        TripListFragment.Trip trip = new TripListFragment.Trip(nameTripInput, dateOfTripInput, tripDestinationInput, emailInput, phoneNumberInput,descriptionInput, tickAssessmentInput);
                           TripList.list.add(trip);
                       // Insert to database
                        ((MainActivity)getActivity()).InsertTripDB(nameTripInput, dateOfTripInput, tripDestinationInput , emailInput, phoneNumberInput, descriptionInput, tickAssessmentInput);

                        dialog.dismiss();
                    }



                });

                buttonDialogCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                //End of dialog
                dialog.show();

            }

        });

         return view;
    }





}