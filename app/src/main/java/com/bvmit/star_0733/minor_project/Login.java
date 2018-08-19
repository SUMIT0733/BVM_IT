package com.bvmit.star_0733.minor_project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Login extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.bvmit.star_0733.minor_project.R.layout.activity_login);

            fragmentManager = getSupportFragmentManager();

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

            // If savedinstnacestate is null then replace login fragment
            if (null == savedInstanceState) {
                fragmentManager
                        .beginTransaction()
                        .replace(com.bvmit.star_0733.minor_project.R.id.frameContainer, new Login_Fragment(),
                                Utils.Login_Fragment).commit();
            }

            // On close icon click finish activity
            findViewById(com.bvmit.star_0733.minor_project.R.id.close_activity).setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            finish();

                        }
                    });

        }

        // Replace Login Fragment with animation
        protected void replaceLoginFragment() {
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(com.bvmit.star_0733.minor_project.R.anim.left_enter, com.bvmit.star_0733.minor_project.R.anim.right_out)
                    .replace(com.bvmit.star_0733.minor_project.R.id.frameContainer, new Login_Fragment(),
                            Utils.Login_Fragment).commit();
        }

        @Override
        public void onBackPressed() {

            // Find the tag of signup and forgot password fragment
            Fragment SignUp_Fragment = fragmentManager
                    .findFragmentByTag(Utils.SignUp_Fragment);
            Fragment ForgotPassword_Fragment = fragmentManager
                    .findFragmentByTag(Utils.ForgotPassword_Fragment);

            // Check if both are null or not
            // If both are not null then replace login fragment else do backpressed
            // task

            if (SignUp_Fragment != null)
                replaceLoginFragment();
            else if (ForgotPassword_Fragment != null)
                replaceLoginFragment();
            else
                super.onBackPressed();
    }
}
