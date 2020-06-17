package com.arb.movieguideapp.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    private EditText newPassword, confirmPassword;
    private CardView cvChangePassword;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mFirebaseAuth = FirebaseAuth.getInstance();

        newPassword = findViewById(R.id.txtPassword);
        confirmPassword = findViewById(R.id.txtChangePassword);
        cvChangePassword = findViewById(R.id.cv_change_password);
        dialog = new ProgressDialog(ChangePasswordActivity.this);

        cvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = newPassword.getText().toString();
                String confPass = confirmPassword.getText().toString();

                if (!newPass.equals(confPass)) {
                    confirmPassword.setError("Password does not match");
                    confirmPassword.requestFocus();
                } else {
                    if (!newPass.isEmpty()) {
                        dialog.setMessage("Changing password, please wait!");
                        dialog.show();

                        user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            user.updatePassword(newPass)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                dialog.dismiss();
                                                Toast.makeText(ChangePasswordActivity.this,
                                                        "Your password has been changed", Toast.LENGTH_SHORT).show();

                                                mFirebaseAuth.signOut();
                                                ChangePasswordActivity.this.finish();

                                                startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                                            }
                                            else {
                                                dialog.dismiss();
                                                Toast.makeText(ChangePasswordActivity.this,
                                                        "Password could not be changed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                }
            }
        });
    }
}
