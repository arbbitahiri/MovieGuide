package com.arb.movieguideapp.ui.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.arb.movieguideapp.login.LoginActivity;
import com.arb.movieguideapp.R;
import com.arb.movieguideapp.ui.PageViewModel;
import com.arb.movieguideapp.ui.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MoreFragment extends Fragment {

    private TextView txtSignOut, txtVerify, txtChangePassword, txtDeactivateAccount;
    private Switch notificationSwitch;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationSwitch = view.findViewById(R.id.switch_notification);
        txtSignOut = view.findViewById(R.id.txt_sign_out);
        txtVerify = view.findViewById(R.id.txtVerify);
        txtChangePassword = view.findViewById(R.id.txt_update_password);
        txtDeactivateAccount = view.findViewById(R.id.txt_cancel_account);

        progressDialog = new ProgressDialog(getActivity());

        mFirebaseAuth = FirebaseAuth.getInstance();

        user = mFirebaseAuth.getCurrentUser();

        if (user != null){
            if (!user.isEmailVerified()) {
                txtVerify.setVisibility(View.VISIBLE);

                txtVerify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "A verification email has been sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Log.v("TAG", "Checked");
                    String title = "Movie Guide Notification";
                    String message = "Go search movies...";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),
                            "personal notifications")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    Intent intent = new Intent(getContext(), MainActivity.class);

                    PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);

                    NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder.build());
                }
            }
        });

        txtChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapFragment(new ChangePasswordFragment());
            }
        });

        txtDeactivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    final AlertDialog.Builder deactivateDialog = new AlertDialog.Builder(v.getContext());
                    deactivateDialog.setTitle("Deactivate Account");
                    deactivateDialog.setMessage("Are you sure you want to deactivate your account?");

                    deactivateDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.setMessage("Deactivating, please wait...");
                            progressDialog.show();

                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Account deactivated", Toast.LENGTH_SHORT).show();

                                        finishActivity();
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Account could not be deactivated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    deactivateDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishActivity();
                        }
                    });

                    deactivateDialog.create().show();
                }
            }
        });

        txtSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    private void displayNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "personal notifications");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Movie Guide Notification");
        builder.setContentText("Go search movies...");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(001, builder.build());
    }

    private void getFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.more_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void swapFragment(Fragment fragment){
        getParentFragmentManager()
                .beginTransaction()
                .add(R.id.more_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }
}
