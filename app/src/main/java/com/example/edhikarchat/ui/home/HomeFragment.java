package com.example.edhikarchat.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edhikarchat.TalkActivity;
import com.example.edhikarchat.UserAdapter;
import com.example.edhikarchat.R;
import com.example.edhikarchat.User;
import com.example.edhikarchat.UserAdapter;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;




//    private HomeViewModel homeViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//}

public class HomeFragment extends Fragment implements UserAdapter.OnNoteListener {
    String TAG = getClass().getSimpleName();

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    List<User> arraylist;


    FirebaseDatabase database;
    UserAdapter mAdapter;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.friends_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        arraylist = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new UserAdapter(arraylist, getActivity(),this); 
        mRecyclerView.setAdapter(mAdapter);
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);

                for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){

//                    String value2 = dataSnapshot2.getValue(String.class);
//                    Log.d(TAG, "Value is: " + value2);
                    User user = dataSnapshot2.getValue(User.class);

                    // [START_EXCLUDE]
                    // Update RecyclerView
                    arraylist.add(user);
//                    mAdapter.notifyItemInserted(arraylist.size() - 1);
                }
                mAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return v;


    }


    @Override
    public void onNoteClick(int postion) {
//        arraylist.get(postion);
//        Intent intent = new Intent(this,TakingActivity.class);
//        startActivity(intent);
        Log.d(TAG, "onNoteClick: clicked.");
        Intent intent = new Intent(getActivity(), TalkActivity.class);
//        intent.putExtra("id".id);
        intent.putExtra("userName", arraylist.get(postion).getUserName());
        intent.putExtra("id", arraylist.get(postion).getId());
        startActivity(intent);
    }
}
