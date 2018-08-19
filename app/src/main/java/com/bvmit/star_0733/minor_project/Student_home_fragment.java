package com.bvmit.star_0733.minor_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;

public class Student_home_fragment extends Fragment {
    View view;
    LinearLayout chat,dept_info;
    private SliderLayout mDemoSlider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(com.bvmit.star_0733.minor_project.R.layout.student_home_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDemoSlider = view.findViewById(com.bvmit.star_0733.minor_project.R.id.slider);

        ArrayList<Integer> image = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        image.add(com.bvmit.star_0733.minor_project.R.drawable.one);
        listName.add("JPG - Github");
        image.add(com.bvmit.star_0733.minor_project.R.drawable.two);
        listName.add("PNG - Android Studio");
        image.add(com.bvmit.star_0733.minor_project.R.drawable.three);
        listName.add("GIF - Disney");
        image.add(com.bvmit.star_0733.minor_project.R.drawable.four);
        listName.add("WEBP - Mountain");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        for (int i = 0; i < image.size(); i++) {
            TextSliderView sliderView = new TextSliderView(getContext());
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(image.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mDemoSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);


        chat = view.findViewById(com.bvmit.star_0733.minor_project.R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Student_chat.class));
            }
        });

        dept_info = view.findViewById(R.id.dept_info);
        dept_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Dept_info.class));

            }
        });
    }
}
