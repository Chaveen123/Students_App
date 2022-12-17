package com.example.group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userLoginActivity extends AppCompatActivity {

    Button register,login;
    EditText email,password;
    TextView forgotpassword;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    boolean checkemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        email = findViewById(R.id.logemail);
        password = findViewById(R.id.logpassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.registernew);
        forgotpassword = findViewById(R.id.forgotpassword);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        //if(firebaseAuth.getCurrentUser() !=null){
        // startActivity(new Intent(getApplicationContext(),userLoginActivity.class));
        //finish();
        //}

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if(TextUtils.isEmpty(mail)){
                    email.setError("Email is Required");
                    return;
                }
                else if(TextUtils.isEmpty(pass)){
                    password.setError("Password is Required");
                    return;
                }
                else{
                    progressDialog.setMessage("Logging In User...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                }

                firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            //Toast.makeText(userLoginActivity.this,"User Logged Successfully",Toast.LENGTH_SHORT).show();
                            verifyemail();
                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(userLoginActivity.this,"Error: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your email to receive Reset link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(userLoginActivity.this,"Reset link sent to your email",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(userLoginActivity.this,"Error: " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close dialog box
                    }
                });
                passwordResetDialog.create().show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(userLoginActivity.this, userRegistrationActivity.class);
                startActivity(userIntent);
            }
        });
    }

    private void verifyemail(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        checkemail = user.isEmailVerified();

        if(checkemail){
            Toast.makeText(userLoginActivity.this,"Logging In",Toast.LENGTH_SHORT).show();
            sendToHome();
        }
        else{
            Toast.makeText(userLoginActivity.this,"Please verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
    private void sendToHome(){
        Intent intent = new Intent(userLoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}
