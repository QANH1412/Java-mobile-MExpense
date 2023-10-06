package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
class ExpensesList {
    public static List<DetailFragment.Expenses> list = new ArrayList<DetailFragment.Expenses>();

}
public class DetailFragment extends Fragment {


    int position =-1;
    public static final String DetailFragmentTag = DetailFragment.class.getName();
    TripListFragment.Trip item;

    TextView textViewNameDetail;
    TextView textViewDestinationDetail;
    TextView textViewDateDetail;
    TextView textViewEmailDetail;
    TextView textViewPhoneDetail;
    TextView textViewDescriptionDetail;
    TextView textViewTickAssessmentDetail;
    Button buttonEditDetail;
    Button buttonDeleteDetail;

    TextInputEditText textInputNameDialogDetail;
    TextInputEditText textInputDestinationDialogDetail;
    TextInputEditText textInputDateDialogDetail;
    TextInputEditText textInputEmailDialogDetail;
    TextInputEditText textInputPhoneDialogDetail;
    TextInputEditText textInputDescriptionDialogDetail;
    CheckBox checkBoxRiskAssessmentDialogDetail;


    Button buttonEditDialogDetail;
    Button buttonCancelDialogDetail;

    // Expenses
    Button buttonCreateExpenses;
    // Expenses dialog
    TextInputEditText textInputEditTextTypeExpenses;
    TextInputEditText textInputEditTextAmountExpenses;
    TextInputEditText textInputEditTextDateExpenses;
    TextInputEditText textInputEditTextCommentExpenses;
    Button buttonCancelDialogExpenses;
    Button buttonAddDialogExpenses;

    // Database
 int TripId;
    public List<Expenses> listExpenses = new ArrayList<Expenses>();
    ListExpensesAdapter listExpensesAdapter;











    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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


    public void SetTextDetailFragment(TripListFragment.Trip item){
         textViewNameDetail.setText(item.Name);
        textViewDestinationDetail.setText(item.Destination);
        textViewDateDetail.setText(item.Date);
        textViewEmailDetail.setText(item.Email);
        textViewPhoneDetail.setText(item.PhoneNumber);
        textViewTickAssessmentDetail.setText(item.TickAssessment);
        textViewDescriptionDetail.setText(item.Description);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        MainActivity mainActivity = (MainActivity) getActivity();


        textViewNameDetail = view.findViewById(R.id.textViewNameTripDetail);
        textViewDestinationDetail = view.findViewById(R.id.textViewDestinationDetail);
        textViewDateDetail = view.findViewById(R.id.textViewDateDetail);
        textViewEmailDetail = view.findViewById(R.id.textViewEmailDetail);
        textViewPhoneDetail = view.findViewById(R.id.textViewPhoneDetail);
        textViewDescriptionDetail = view.findViewById(R.id.textViewDescriptionDetail);
        textViewTickAssessmentDetail = view.findViewById(R.id.textViewTickAssessmentDetail);
        buttonEditDetail = view.findViewById(R.id.ButtonEditDetail);
        buttonDeleteDetail = view.findViewById(R.id.ButtonDeleteDetail);

        // bundle receive information
         Bundle bundleReceive = getArguments();
         if (bundleReceive != null) {
              item = (TripListFragment.Trip) bundleReceive.get("object_user");
             position = TripList.list.indexOf(item);
             if (item != null){
                 SetTextDetailFragment(item);
             }
         }
        // Get ID trip from database
        TripId = mainActivity.GetIDTrip(textViewNameDetail.getText().toString(), textViewDestinationDetail.getText().toString(),
                textViewDateDetail.getText().toString());

         // Expenses list
        mainActivity.AddExpenseFromDB(TripId, listExpenses);

        RecyclerView rvListExpenses = view.findViewById(R.id.rvListExpenses);
        listExpensesAdapter = new ListExpensesAdapter(listExpenses);
        rvListExpenses.setAdapter(listExpensesAdapter);
        rvListExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
        listExpensesAdapter.notifyDataSetChanged();

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rvListExpenses.addItemDecoration(itemDecoration);

        buttonDeleteDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialog Alert
              DialogAlertDetailDelete();
            }
            private void DialogAlertDetailDelete(){
                 Dialog dialogAlert = new Dialog(getContext());
                 dialogAlert.setContentView(R.layout.dialog_alert_delete_detail_custom);

                 //get id
                  Button buttonCancelAlertDialog = dialogAlert.findViewById(R.id.ButtonCancelDialogAlert);
                Button buttonDeleteAlertDialog = dialogAlert.findViewById(R.id.ButtonDeleteDialogAlert);

                buttonDeleteAlertDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Remove all data trip from database by ID
                        mainActivity.DeleteTripDB(String.valueOf(TripId));

                        // remove all data expenses from database expensesList
                        mainActivity.DeleteExpenseDB(TripId);

                        //remove all data from list
                        TripList.list.remove(position);



                        //Back to Trip List Fragment
                        if (getParentFragmentManager() != null){
                            getParentFragmentManager().popBackStack();
                        }

                        dialogAlert.dismiss();
                    }
                });

                buttonCancelAlertDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAlert.dismiss();
                    }
                });
                dialogAlert.show();
            }
        });




         buttonEditDetail.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                   DialogDetailLogin();
             }

             private void DialogDetailLogin(){
                 Dialog dialogDetail = new Dialog(getContext());
                 dialogDetail.setContentView(R.layout.custom_dialog_detail);

                 // get id
                 textInputNameDialogDetail = dialogDetail.findViewById(R.id.TextInputNameDetailDialog);
                 textInputDestinationDialogDetail = dialogDetail.findViewById(R.id.TextInputDestinationDetailDialog);
                 textInputDateDialogDetail = dialogDetail.findViewById(R.id.TextInputDateDetailDialog);
                 textInputEmailDialogDetail = dialogDetail.findViewById(R.id.TextInputEmailDetailDialog);
                 textInputPhoneDialogDetail = dialogDetail.findViewById(R.id.TextInputPhoneDetailDialog);
                 textInputDescriptionDialogDetail = dialogDetail.findViewById(R.id.TextInputDescriptionDetailDialog);
                 checkBoxRiskAssessmentDialogDetail = dialogDetail.findViewById(R.id.checkBoxRiskAssessmentDialogDetail);
                 buttonEditDialogDetail = dialogDetail.findViewById(R.id.ButtonEditDialogDetail);
                 buttonCancelDialogDetail = dialogDetail.findViewById(R.id.ButtonCancelDialogDetail);


                 // pass data from detail to detail_dialog
                 textInputNameDialogDetail.setText(textViewNameDetail.getText().toString());
                 textInputDestinationDialogDetail.setText(textViewDestinationDetail.getText().toString());
                 textInputDateDialogDetail.setText(textViewDateDetail.getText().toString());
                 textInputEmailDialogDetail.setText(textViewEmailDetail.getText().toString());
                 textInputPhoneDialogDetail.setText(textViewPhoneDetail.getText().toString());
                 textInputDescriptionDialogDetail.setText(textViewDescriptionDetail.getText().toString());
                 String TickAssessmentDetail =  textViewTickAssessmentDetail.getText().toString();


                 if( TickAssessmentDetail == "Yes"){
                       checkBoxRiskAssessmentDialogDetail.setChecked(!checkBoxRiskAssessmentDialogDetail.isChecked());
                 }



                 buttonCancelDialogDetail.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         dialogDetail.dismiss();
                     }
                 });

                 buttonEditDialogDetail.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         String NameUpdateInput = textInputNameDialogDetail.getText().toString();
                         String DestinationUpdateInput = textInputDestinationDialogDetail.getText().toString();
                         String DateUpdateInput = textInputDateDialogDetail.getText().toString();
                         String EmailUpdateInput = textInputEmailDialogDetail.getText().toString();
                         String PhoneUpdateInput = textInputPhoneDialogDetail.getText().toString();
                         String DescriptionUpdateInput = textInputDescriptionDialogDetail.getText().toString();
                         String TickAssessmentUpdateInput;
                         if (checkBoxRiskAssessmentDialogDetail. isChecked() == true){
                              TickAssessmentUpdateInput = "Yes";
                         } else {
                              TickAssessmentUpdateInput = "No";
                         }

                         mainActivity.UpdateTripDB(NameUpdateInput, DateUpdateInput,DestinationUpdateInput,EmailUpdateInput,PhoneUpdateInput,
                                 DescriptionUpdateInput,TickAssessmentUpdateInput, String.valueOf(TripId));

                         TripListFragment.Trip trip = new TripListFragment.Trip(NameUpdateInput, DestinationUpdateInput, DateUpdateInput, EmailUpdateInput, PhoneUpdateInput, DescriptionUpdateInput, TickAssessmentUpdateInput);
                         TripList.list.set(position, trip);
                         SetTextDetailFragment(trip);
                         listExpensesAdapter.notifyDataSetChanged();
                         dialogDetail.dismiss();
                         //Back to Trip List Fragment




                     }
                 });





                 //End of dialog
                 dialogDetail.show();
             }
         });


         // EXPENSES



        buttonCreateExpenses = view.findViewById(R.id.buttonCreateExpenses);


        buttonCreateExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogExpenses();
            }

        private void DialogExpenses(){
            Dialog dialogExpenseCreate = new Dialog(getContext());
            dialogExpenseCreate.setContentView(R.layout.dialog_custom_expenses_input);
            //get id
            textInputEditTextTypeExpenses = dialogExpenseCreate.findViewById(R.id.TextInputTypeExpenses);
            textInputEditTextAmountExpenses = dialogExpenseCreate.findViewById(R.id.TextInputAmountExpenses);
            textInputEditTextDateExpenses = dialogExpenseCreate.findViewById(R.id.TextInputDateExpenses);
            textInputEditTextCommentExpenses = dialogExpenseCreate.findViewById(R.id.TextInputCommentExpenses);
            buttonCancelDialogExpenses = dialogExpenseCreate.findViewById(R.id.ButtonCancelDialogExpenses);
            buttonAddDialogExpenses = dialogExpenseCreate.findViewById(R.id.ButtonAddDialogExpenses);

            buttonCancelDialogExpenses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogExpenseCreate.dismiss();
                }
            });

            buttonAddDialogExpenses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String Type = textInputEditTextTypeExpenses.getText().toString();
                    String Amount = textInputEditTextAmountExpenses.getText().toString();
                    String Date = textInputEditTextDateExpenses.getText().toString();
                    String Comment = textInputEditTextCommentExpenses.getText().toString();
                    Expenses expenses = new Expenses(Type, Amount, Date, Comment);
                    listExpenses.add(expenses);

                    mainActivity.InsertExpenseDB(Type,Amount, Date, Comment, TripId);
                    listExpensesAdapter.notifyDataSetChanged();
                    dialogExpenseCreate.dismiss();

                }
            });

            dialogExpenseCreate.show();

        }

        });













        return view;
    }

     static class Expenses{
            public String TypeExpenses;
            public String AmountExpenses;
            public String DateExpenses;
            public String CommentExpenses;

            public Expenses(String type, String amount, String date, String comment){
                TypeExpenses = type;
                AmountExpenses = amount;
                DateExpenses = date;
                CommentExpenses = comment;
            }
     }


    class ListExpensesAdapter extends RecyclerView.Adapter<ListExpensesAdapter.ViewHolder> {

        List<Expenses> list_Expenses;

        public ListExpensesAdapter (List<Expenses> list){
            list_Expenses = list;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.expenseslayoutlist,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public int getItemCount() {
            return list_Expenses.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
               Expenses expenses = list_Expenses.get(position);
               holder.textViewExpensesType.setText(expenses.TypeExpenses);
            holder.textViewExpensesAmount.setText(expenses.AmountExpenses);
            holder.textViewExpensesDate.setText(expenses.DateExpenses);
            holder.textViewExpensesComment.setText(expenses.CommentExpenses);
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            public TextView textViewExpensesType;
            public TextView textViewExpensesAmount;
            public TextView textViewExpensesDate;
            public TextView textViewExpensesComment;
            public ViewHolder(View expensesView){
                  super(expensesView);
                textViewExpensesType = expensesView.findViewById(R.id.textViewType);
                textViewExpensesAmount = expensesView.findViewById(R.id.textViewAmount);
                textViewExpensesDate = expensesView.findViewById(R.id.textViewExpensesDate);
                textViewExpensesComment = expensesView.findViewById(R.id.textViewComment);

            }
        }
    }
}