package com.bvmit.star_0733.minor_project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp_Fragment extends Fragment implements OnClickListener {
    private View view;
    private EditText fullName, emailId, mobileNumber, batch,
            password, confirmPassword,enroll;
    private TextView login;
    private Button signUpButton;
    private CheckBox terms_conditions;
    FirebaseAuth auth;
    DatabaseReference ref;
    ProgressDialog dia;


    public SignUp_Fragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(com.bvmit.star_0733.minor_project.R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("student_login");
        dia = new ProgressDialog(getContext());
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        batch = (EditText) view.findViewById(R.id.batch);
        enroll = view.findViewById(R.id.enroll);
        password = (EditText) view.findViewById(com.bvmit.star_0733.minor_project.R.id.password);
        confirmPassword = (EditText) view.findViewById(com.bvmit.star_0733.minor_project.R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(com.bvmit.star_0733.minor_project.R.id.signUpBtn);
        login = (TextView) view.findViewById(com.bvmit.star_0733.minor_project.R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(com.bvmit.star_0733.minor_project.R.id.terms_conditions);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(com.bvmit.star_0733.minor_project.R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) { }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                dia.setMessage("Registering...");
                dia.setCancelable(false);
                dia.show();
                checkValidation();
                break;

            case R.id.already_user:
                new Login().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        final String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getBatch = batch.getText().toString();
        final String getEnroll = enroll.getText().toString();
        final String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0 || getMobileNumber.length() != 10
                || getBatch.equals("") || getBatch.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getEnroll.equals("")
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0) {
            dia.dismiss();
            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");
        }
            // Check if email id valid or not
        else if (!m.find()) {
            dia.dismiss();
            new CustomToast().Show_Toast(getActivity(), view, "Your Email Id is Invalid.");
        }
            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword)) {
            dia.dismiss();
            new CustomToast().Show_Toast(getActivity(), view, "Both password doesn't match.");
        }
            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked()) {
            dia.dismiss();
            new CustomToast().Show_Toast(getActivity(), view, "Please select Terms and Conditions.");
        }

        else if (getEnroll.length() != 12) {
            dia.dismiss();
            new CustomToast().Show_Toast(getActivity(), view, "Please Enter 12 digit number.");
        }

        else if (getPassword.length() < 6) {
            dia.dismiss();
            new CustomToast().Show_Toast(getActivity(), view, "Password must contain 6 letter.");
        }
            // Else do signup or do your stuff
        else
//            Toast.makeText(getActivity(), "Do SignUp.", Toast.LENGTH_SHORT)
//                    .show();
            ref.child(getEnroll).setValue(new Stu_Reg_model(getFullName,getEmailId,getPassword,getBatch,getMobileNumber,getEnroll)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //Toast.makeText(getContext(), "Register success", Toast.LENGTH_SHORT).show();
                    auth.createUserWithEmailAndPassword(getEmailId,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dia.dismiss();
                            Toast.makeText(getContext(), "Registartion successful", Toast.LENGTH_SHORT).show();
                            new Login().replaceLoginFragment();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                            ref.child(getEnroll).removeValue();
                            Toast.makeText(getActivity(), "node delete", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
    }
}
