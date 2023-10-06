package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripListFragment extends Fragment {

    TextView textViewUpdateName;
    Button buttonGetData;
    String dataName;
    String dataDate;
    String dataDestination;

    private IClickItemListener iClickItemListener;







    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TripListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TripListFragment newInstance(String param1, String param2) {
        TripListFragment fragment = new TripListFragment();
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
    // function pass data



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        MainActivity mainActivity = (MainActivity) getActivity();



        RecyclerView rvList = view.findViewById(R.id.rvList);
        TripListAdapter tripListAdapter = new TripListAdapter(TripList.list, new IClickItemListener() {
            @Override
            public void onClickItemUser(Trip item) {
                mainActivity.gotoDetailFragment(item);

            }

        });
        rvList.setAdapter(tripListAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        // add vertical DECORATION to recyclerview
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rvList.addItemDecoration(itemDecoration);
        tripListAdapter.notifyDataSetChanged();
//
//        getParentFragmentManager().setFragmentResultListener("dataFrom1", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
//                String dataName = bundle.getString("SendDataName");
//                String dataDate = bundle.getString("SendDataDate");
//                String dataDestination = bundle.getString("SendDataDestination");
//            }
//
//        });

        // Search View Bar

        SearchView searchView = view.findViewById(R.id.SearchViewBar);
         searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }

            private void filterList(String text) {
                List<TripListFragment.Trip> filteredList = new ArrayList<>();
                for (TripListFragment.Trip item : TripList.list) {
                    if(item.Name.toLowerCase().contains(text.toLowerCase())){
                        filteredList.add(item);
                    }
                }
                tripListAdapter.setFilteredList(filteredList);
            }
        });



        return view;

    }





    public interface IClickItemListener {
        void onClickItemUser(Trip item);
    }

    // class TripList
    static class Trip implements Serializable{
        public String Name;
        public String Destination;
        public String Date;
        public String Email;
        public String PhoneNumber;
        public String Description;
        public String TickAssessment;



        public Trip(String name, String date, String destination, String email, String phoneNumber, String description, String tickAssessment){
            Name = name;
            Date = date;
            Destination = destination;
            Email = email;
            PhoneNumber = phoneNumber;
            Description = description;
            TickAssessment = tickAssessment;
        }
    }

     //class triplist adapter
    class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {

        List<Trip> _list;

        public TripListAdapter (List<Trip> list, IClickItemListener listener){
            _list = list;
            iClickItemListener = listener;
        }

        public void setFilteredList(List<Trip> filteredList){
            this._list = filteredList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.triplayoutlist, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
              final Trip item = _list.get(position);
               holder.textViewUpdateName.setText(item.Name);
               holder.textViewUpdateDate.setText(item.Date);
               holder.textViewUpdateDestination.setText(item.Destination);
               holder.linearLayoutTripList.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       iClickItemListener.onClickItemUser(item);
                   }
               });

        }

        @Override
        public int getItemCount() {
            return _list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout linearLayoutTripList;
            public TextView textViewUpdateDate;
            public TextView textViewUpdateName;
            public TextView textViewUpdateDestination;
                public ViewHolder(View itemView){
                    super(itemView);
                    linearLayoutTripList = itemView.findViewById((R.id.LinearLayoutTripList));
                    textViewUpdateName = itemView.findViewById(R.id.textViewUpdateName);
                    textViewUpdateDate = itemView.findViewById(R.id.textViewUpdateDate);
                    textViewUpdateDestination = itemView.findViewById(R.id.textViewUpdateDestination);

                }

        }


    }




}