package com.example.placement_desk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.placement_desk.Adapter.StudentAdapter;
import com.example.placement_desk.Adapter.YearAdapter;
import com.example.placement_desk.Model.Student;
import com.example.placement_desk.Model.Year;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements YearAdapter.OnYearClickListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dbref;
    RecyclerView recyclerView;

    private YearAdapter yearAdapter;
    private List<Year> yearList;

    public ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        yearList = new ArrayList<>();
        yearAdapter = new YearAdapter(yearList, this);
        recyclerView.setAdapter(yearAdapter);
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child("2024");
//        Student24();
        getdata();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void getdata() {
        DatabaseReference yearRef = FirebaseDatabase.getInstance().getReference("Student");
        yearRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yearList.clear();
                for (DataSnapshot yearSnapshot : dataSnapshot.getChildren()) {
                    String year = yearSnapshot.getKey();
                    yearList.add(new Year(year));
                }
                yearAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
   public void onYearClick(String year) {
       List<Student> studentDetailList = new ArrayList<>();

       // Handle year click, e.g., navigate to a new activity to display students of that year
       DatabaseReference yearDetailRef = FirebaseDatabase.getInstance().getReference("Student").child(year);
       StudentAdapter studentAdapter = new StudentAdapter(studentDetailList);
       recyclerView.setAdapter(studentAdapter);
       yearDetailRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                   String ID = studentSnapshot.getKey();
//                   String id = studentSnapshot.child("ID").getValue(String.class);
                   String Name = studentSnapshot.child("Name").getValue(String.class);
                   String Remark = studentSnapshot.child("Remark").getValue(String.class);
                   String Gender = studentSnapshot.child("Gender").getValue(String.class);
                   String Company = studentSnapshot.child("Company").getValue(String.class);
                   Student student = new Student();
//                   Log.d("TAG1010", "onDataChange: "+ id +" "+ name+ " "+ company + " "+ gender);
                   student.setID(ID);
                   student.setName(Name);
                   student.setCompany(Company);
                   student.setGender(Gender);
                   student.setRemark(Remark);
                   // Add the Student object to the list
                   studentDetailList.add(student);
                   back.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           onBackPressed();
                       }
                   });

               }
               studentAdapter.notifyDataSetChanged();
               // Populate another RecyclerView with studentDetailList
               // You can use a new adapter for this RecyclerView
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               // Handle error
           }
       });
   }
}