package com.example.group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class   userRegistrationActivity extends AppCompatActivity {

    Button register;
    EditText email, password, repassword;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        email = findViewById(R.id.regemail);
        password = findViewById(R.id.regpassword);
        repassword = findViewById(R.id.regrepassword);
        register = findViewById(R.id.register);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        //if(firebaseAuth.getCurrentUser() !=null){
        // startActivity(new Intent(getApplicationContext(),userLoginActivity.class));
        //finish();
        //}

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String repass = repassword.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    email.setError("Email is Required");
                    return;
                } else if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is Required");
                    return;
                } else if (password.length() < 6) {
                    password.setError("Password must have more than 6 characters");
                    return;
                } else if (TextUtils.isEmpty(repass)) {
                    repassword.setError("Re-enter Password");
                    return;
                } else if (!pass.equals(repass)) {
                    repassword.setError("Password not matching");
                    return;
                } else {
                    progressDialog.setMessage("Registering User...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                }

                firebaseAuth.createUserWithEmailAndPassword(mail, repass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(userRegistrationActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            sendEmailVerificationMessage();
                            startActivity(new Intent(getApplicationContext(), userLoginActivity.class));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(userRegistrationActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void sendEmailVerificationMessage() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(userRegistrationActivity.this, "Please check your mailbox to verify email", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(userRegistrationActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                    }
                }
            });
        }
    }
}