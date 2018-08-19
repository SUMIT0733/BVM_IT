package com.bvmit.star_0733.minor_project;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Fragment extends Fragment implements OnClickListener {
    private  View view;

    private EditText emailid, password;
    private Button loginButton;
    private TextView forgotPassword, signUp;
    private CheckBox show_hide_password;
    private LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog dia;

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(com.bvmit.star_0733.minor_project.R.layout.login_layout, container, false);
        initViews();
        setListeners();
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(getContext(),Student_Home.class));
        }
        dia = new ProgressDialog(getContext());
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(com.bvmit.star_0733.minor_project.R.id.login_emailid);
        password = (EditText) view.findViewById(com.bvmit.star_0733.minor_project.R.id.login_password);
        loginButton = (Button) view.findViewById(com.bvmit.star_0733.minor_project.R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(com.bvmit.star_0733.minor_project.R.id.forgot_password);
        signUp = (TextView) view.findViewById(com.bvmit.star_0733.minor_project.R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(com.bvmit.star_0733.minor_project.R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(com.bvmit.star_0733.minor_project.R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                com.bvmit.star_0733.minor_project.R.anim.shake);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(com.bvmit.star_0733.minor_project.R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        if (isChecked) {

                            show_hide_password.setText(com.bvmit.star_0733.minor_project.R.string.hide_pwd);

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());
                        } else {
                            show_hide_password.setText(com.bvmit.star_0733.minor_project.R.string.show_pwd);

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.bvmit.star_0733.minor_project.R.id.loginBtn:
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                dia.setCancelable(false);
                dia.setMessage("Authenticating...");
                dia.show();
                checkValidation();
                break;

            case com.bvmit.star_0733.minor_project.R.id.forgot_password:

                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(com.bvmit.star_0733.minor_project.R.anim.right_enter, com.bvmit.star_0733.minor_project.R.anim.left_out)
                        .replace(com.bvmit.star_0733.minor_project.R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                Utils.ForgotPassword_Fragment).commit();
                break;
            case com.bvmit.star_0733.minor_project.R.id.createAccount:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(com.bvmit.star_0733.minor_project.R.anim.right_enter, com.bvmit.star_0733.minor_project.R.anim.left_out)
                        .replace(com.bvmit.star_0733.minor_project.R.id.frameContainer, new SignUp_Fragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }

    }

     //Check Validation before login
    private void checkValidation() {
        // Get email id and password
        final String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            dia.dismiss();
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        }
        // Check if email id is valid or not
        else if (!m.find()) {
            dia.dismiss();
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
        }
            // Else do login and do your stuff
        else {
            dia.dismiss();
            auth.signInWithEmailAndPassword(getEmailId,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(getContext(), "Log in success", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    startActivity(new Intent(getContext(),Student_Home.class));
                }
            });

        }
    }
}
