package com.bvmit.star_0733.minor_project;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class Student_Home extends AppCompatActivity implements DuoMenuView.OnMenuClickListener{

    private com.bvmit.star_0733.minor_project.MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    ImageView notice_icon;
    TextView nav_title,nav_enroll;
    Button logoff;
    FirebaseAuth auth;
    DatabaseReference ref;
    FirebaseUser user;

    private ArrayList<String> mTitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.bvmit.star_0733.minor_project.R.layout.activity_student_home);

        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(com.bvmit.star_0733.minor_project.R.array.menuOptions)));
        mMenuAdapter = new com.bvmit.star_0733.minor_project.MenuAdapter(mTitles);
        mViewHolder = new ViewHolder();
        handleToolbar();
        handleMenu();
        handleDrawer();
        setTitle(mTitles.get(0));
        goToFragment(new Student_home_fragment(),false);
        mMenuAdapter.setViewSelected(0,true);

        notice_icon = findViewById(com.bvmit.star_0733.minor_project.R.id.notice_icon);
        notice_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_Home.this,Student_notice.class));
            }
        });

        nav_title = findViewById(R.id.header_name);
        nav_enroll = findViewById(R.id.header_enroll);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("student_login");
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null)
        {
            finish();
            startActivity(new Intent(Student_Home.this, Login.class));
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot obj : dataSnapshot.getChildren())
                {
                    Stu_Reg_model model = obj.getValue(Stu_Reg_model.class);
                    if(model.getEmail().equals(user.getEmail()))
                    {
                        nav_title.setText(model.getName());
                        nav_enroll.setText(model.getEnroll());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        logoff = findViewById(R.id.logoff);
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Toast.makeText(Student_Home.this, "log out", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(Student_Home.this, Login.class));
            }
        });
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
        mViewHolder.mToolbar.setOverflowIcon(getResources().getDrawable(com.bvmit.star_0733.minor_project.R.drawable.ic_notifications_black_24dp));
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                com.bvmit.star_0733.minor_project.R.string.navigation_drawer_open,
                com.bvmit.star_0733.minor_project.R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {

        mViewHolder.mDuoMenuView.setOnMenuClickListener((DuoMenuView.OnMenuClickListener) this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.add(com.bvmit.star_0733.minor_project.R.id.container, fragment).commit();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        setTitle(mTitles.get(position));

        mMenuAdapter.setViewSelected(position, true);

        switch (position) {
            default:
//                goToFragment(new MainFragment(), false);
//                break;
        }
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        public Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(com.bvmit.star_0733.minor_project.R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(com.bvmit.star_0733.minor_project.R.id.toolbar);
        }
    }


}