package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;


import com.example.myapplication.TripDBInput;
import com.example.myapplication.TripDBHelper;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    public TripDBHelper tripDBHelper = new TripDBHelper(this, "Trip", 1);
    public SQLiteDatabase dbread, dbwrite;
    FragmentContainerView fmv;

    protected FirebaseFirestore FireBaseDB = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbread = tripDBHelper.getReadableDatabase();
        dbwrite = tripDBHelper.getWritableDatabase();


        fmv = findViewById(R.id.fragmentContainerView);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.bottomnavigationview);


        // NAVIGATION
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.newTripFragment, R.id.tripListFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //

        // When I open App,get trip data in database and show in trip list
        AddTripFromDB();


    }


    public void InsertTripDB(String tripName, String date, String destination, String email, String phoneNumber, String description, String risk) {
        ContentValues values = new ContentValues();

        values.put(TripDBInput.COL_NAME, tripName);
        values.put(TripDBInput.COL_DESTINATION, destination);
        values.put(TripDBInput.COL_DATE, date);
        values.put(TripDBInput.COL_DESCRIPTION, description);
        values.put(TripDBInput.COL_RISKASSESSMENT, risk);
        values.put(TripDBInput.COL_EMAIL, email);
        values.put(TripDBInput.COL_PHONENUMBER, phoneNumber);

        dbwrite.insert(TripDBInput.TABLE_NAME, null, values);

    }

    public void AddTripFromDB() {
        Cursor cursor = dbread.query(TripDBInput.TABLE_NAME, null, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_NAME));
            String destination = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_DESTINATION));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_DATE));
            String risk = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_RISKASSESSMENT));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_PHONENUMBER));
            TripListFragment.Trip trip = new TripListFragment.Trip(name, date, destination, email, phone, description, risk);
            TripList.list.add(trip);

        }
    }

    //Function Delete Trip DB
    public void DeleteTripDB(String id) {
        //Delete URL in Database
        String where = TripDBInput.COL_ID + " = ?";
        String[] whereArgs = {id};
        dbread.delete(TripDBInput.TABLE_NAME, where, whereArgs);

    }

    //Function Update Trip Database
    public void UpdateTripDB(String Name, String date, String destination, String email, String phoneNumber,
                             String description, String risk, String id) {
        ContentValues values = new ContentValues();

        values.put(TripDBInput.COL_NAME, Name);
        values.put(TripDBInput.COL_DESTINATION, destination);
        values.put(TripDBInput.COL_DATE, date);
        values.put(TripDBInput.COL_DESCRIPTION, description);
        values.put(TripDBInput.COL_RISKASSESSMENT, risk);
        values.put(TripDBInput.COL_EMAIL, email);
        values.put(TripDBInput.COL_PHONENUMBER, phoneNumber);

        String where = TripDBInput.COL_ID + " = ?";
        String[] args = {id};

        dbwrite.update(TripDBInput.TABLE_NAME, values, where, args);

    }


    //Get id Trip in Database
    public int GetIDTrip(String name, String destination, String date) {
        String selection = TripDBInput.COL_NAME + " = ? AND " + TripDBInput.COL_DESTINATION + " = ? AND " +
                TripDBInput.COL_DATE + " = ?";
        String[] args = {name, destination, date};
        int id = 0;

        Cursor cursor = dbread.query(TripDBInput.TABLE_NAME, null, selection, args, null,
                null, null);

        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(TripDBInput.COL_ID));
        }
        return id;
    }


    public void gotoDetailFragment(TripListFragment.Trip item) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user", item);

        detailFragment.setArguments(bundle);


        fragmentTransaction.replace(R.id.fragmentContainerView, detailFragment)
                .addToBackStack(DetailFragment.DetailFragmentTag)
                .addToBackStack(null)
                .commit();
    }


    //Insert expenseData To Expense Database
    public void InsertExpenseDB(String type, String amount, String date, String comment, int fkId) {
        ContentValues values = new ContentValues();
        values.put(ExpensesDBInput.COL_TYPE, type);
        values.put(ExpensesDBInput.COL_AMOUNT, amount);
        values.put(ExpensesDBInput.COL_DATE, date);
        values.put(ExpensesDBInput.COL_COMMENT, comment);
        values.put(ExpensesDBInput.COL_FK_ID_TRIP, fkId);

        dbwrite.insert(ExpensesDBInput.TABLE_NAME, null, values);
    }

    // Add ExpenseData from database to the location
    public void AddExpenseFromDB(int TripId, List<DetailFragment.Expenses> list) {
        String selection = ExpensesDBInput.COL_FK_ID_TRIP + " = ?";
        String[] args = {"" + TripId};

        Cursor cursor = dbread.query(ExpensesDBInput.TABLE_NAME, null, selection, args,
                null, null, null);
        while (cursor.moveToNext()) {
            String type = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_TYPE));
            String amount = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_AMOUNT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_DATE));
            String comment = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_COMMENT));

            DetailFragment.Expenses expenses = new DetailFragment.Expenses(type, comment, date, amount);
            list.add(expenses);
        }
    }


    public void DeleteExpenseDB(int id) {
        String where = ExpensesDBInput.COL_FK_ID_TRIP + "= ?";
        String[] args = {"" + id};


        dbread.delete(ExpensesDBInput.TABLE_NAME, where, args);

    }


    public void UploadTripToCloud() {
        Cursor cursor = dbread.query(TripDBInput.TABLE_NAME, null, null, null,
                null, null, null);

        String id = "0";
        String name = null;
        String destination = null;
        String description = null;
        String email = null;
        String phoneNumber = null;
        String risk = null;
        String date = null;


        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_ID));
            name = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_NAME));
            destination = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_DESTINATION));
            description = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_DESCRIPTION));
            email = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_EMAIL));
            risk = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_RISKASSESSMENT));
            date = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_DATE));
            phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(TripDBInput.COL_PHONENUMBER));

            if (id != "0") {
                Map<String, Object> data = new HashMap<>();
                data.put("id", id);
                data.put("name", name);
                data.put("description", description);
                data.put("email", email);
                data.put("risk", risk);
                data.put("phoneNumber", phoneNumber);
                data.put("destination", destination);
                data.put("date", date);

                FireBaseDB.collection("trip").add(data);
            }
        }
    }

    public void UploadExpenseToCloud() {
        Cursor cursor = dbread.query(ExpensesDBInput.TABLE_NAME, null, null, null,
                null, null, null);

        String id = null;
        String trip_id = null;
        String type = null;
        String amount = null;
        String dateExpenses = null;
        String comment = null;

        while (cursor.moveToNext()) {
            type = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_TYPE));
            dateExpenses = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_DATE));
            amount = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_AMOUNT));
            comment = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_COMMENT));
            id = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_ID));
            trip_id = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesDBInput.COL_FK_ID_TRIP));

            if (id != "0") {
                Map<String, Object> data = new HashMap<>();
                data.put("expenseType", type);
                data.put("amount", amount);
                data.put("time", dateExpenses);
                data.put("expenseComment", comment);
                data.put("id", id);
                data.put("trip_id", trip_id);

                FireBaseDB.collection("expense").add(data);

            }
        }
    }
}

//    public void CleanData(){
//        //delete all data in Database
////        long i = dbread.delete(TripDBInput.TABLE_NAME, null, null);
////        dbread.delete(TripDBInput.TABLE_NAME, null, null);
////
////        //Remove all trip from list
////        TripList.list.clear();
////
////        if (i > 0) {
////            Snackbar.make(mDrawerLayout, "Data Reset Successfully", Snackbar.LENGTH_SHORT).show();
////        } else {
////            Snackbar.make(mDrawerLayout, "Data Is Currently Empty", Snackbar.LENGTH_SHORT).show();
////        }
////        dialog.dismiss();
////        replaceFragment(new FragmentHome(), R.id.menu_home);
//    }


