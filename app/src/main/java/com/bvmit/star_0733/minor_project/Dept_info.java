package com.bvmit.star_0733.minor_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dept_info extends AppCompatActivity {
    TextView more_info,pso_1,peo_1,vision_lable,vision_text,mission_text,mission_lable;
    TextView scope,major_achivements,pso,peo;
    LinearLayout scope_layout,major_layout;
    Toolbar toolbar;
    int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_info);

        pso_1=findViewById(R.id.pso_1);
        peo_1=findViewById(R.id.peo_1);
        scope_layout=findViewById(R.id.scope_layout);
        major_layout=findViewById(R.id.major_layout);
        pso=findViewById(R.id.pso);
        peo=findViewById(R.id.peo);
        scope=findViewById(R.id.scope);
        major_achivements=findViewById(R.id.major_achivements);

        vision_lable = findViewById(R.id.vision_label);
        vision_text = findViewById(R.id.vision_text);
        mission_lable = findViewById(R.id.mission_lable);
        mission_text = findViewById(R.id.mission_text);

        more_info = findViewById(R.id.more_info);
        more_info.setClickable(true);
        more_info.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "For more information <a href='http://www.bvmengineering.ac.in/dashboard.aspx?branch_id=9'> Click Here </a>";
        more_info.setText(Html.fromHtml(text));

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Department information");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        scope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                major_layout.setVisibility(View.GONE);
                flag2=0;
                peo_1.setVisibility(View.GONE);
                flag4=0;
                pso_1.setVisibility(View.GONE);
                flag3=0;
                vision_text.setVisibility(View.GONE); flag5=0;
                mission_text.setVisibility(View.GONE); flag6=0;
                if(flag1==0)
                {
                    scope_layout.setVisibility(View.VISIBLE);
                    flag1=1;

                }
                else
                {
                    scope_layout.setVisibility(View.GONE);
                    flag1=0;
                }
            }
        });

        major_achivements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scope_layout.setVisibility(View.GONE);
                flag1=0;
                peo_1.setVisibility(View.GONE);
                flag4=0;
                pso_1.setVisibility(View.GONE);
                flag3=0;
                vision_text.setVisibility(View.GONE); flag5=0;
                mission_text.setVisibility(View.GONE); flag6=0;
                if(flag2==0)
                {
                    major_layout.setVisibility(View.VISIBLE);
                    flag2=1;
                }
                else
                {
                    major_layout.setVisibility(View.GONE);
                    flag2=0;
                }
            }
        });

        pso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                major_layout.setVisibility(View.GONE);
                flag2=0;
                scope_layout.setVisibility(View.GONE);
                flag1=0;
                peo_1.setVisibility(View.GONE);
                flag4=0;
                vision_text.setVisibility(View.GONE); flag5=0;
                mission_text.setVisibility(View.GONE); flag6=0;
                if(flag3==0)
                {
                    pso_1.setVisibility(View.VISIBLE);
                    flag3=1;
                }
                else
                {
                    pso_1.setVisibility(View.GONE);
                    flag3=0;
                }
            }
        });

        peo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                major_layout.setVisibility(View.GONE);
                flag2=0;
                scope_layout.setVisibility(View.GONE);
                flag1=0;
                pso_1.setVisibility(View.GONE);
                flag3=0;
                vision_text.setVisibility(View.GONE); flag5=0;
                mission_text.setVisibility(View.GONE); flag6=0;
                if(flag4==0)
                {
                    peo_1.setVisibility(View.VISIBLE);
                    flag4=1;
                }
                else
                {
                    peo_1.setVisibility(View.GONE);
                    flag4=0;
                }
            }
        });
        vision_lable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                major_layout.setVisibility(View.GONE);
                flag2=0;
                scope_layout.setVisibility(View.GONE);
                flag1=0;
                pso_1.setVisibility(View.GONE);
                flag3=0;
                peo_1.setVisibility(View.GONE);
                flag4=0;
                mission_text.setVisibility(View.GONE);
                flag6=0;
                if(flag5==0)
                {
                    vision_text.setVisibility(View.VISIBLE);
                    flag5=1;
                }
                else
                {
                    vision_text.setVisibility(View.GONE);
                    flag5=0;
                }
            }
        });
        mission_lable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                major_layout.setVisibility(View.GONE);
                flag2=0;
                scope_layout.setVisibility(View.GONE);
                flag1=0;
                pso_1.setVisibility(View.GONE);
                flag3=0;
                vision_text.setVisibility(View.GONE); flag5=0;
                peo_1.setVisibility(View.GONE); flag4=0;
                if(flag6==0)
                {
                    mission_text.setVisibility(View.VISIBLE);
                    flag6=1;
                }
                else
                {
                    mission_text.setVisibility(View.GONE);
                    flag6=0;
                }
            }
        });
    }
}
