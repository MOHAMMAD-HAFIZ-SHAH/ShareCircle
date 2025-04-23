package com.example.sharecircle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.*;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullName, email, password, phone;
    private TextView getOtpEmail, getOtpPhone, loginRedirect;
    private Button btnSignUp;

    private FirebaseAuth mAuth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        initViews();

        loginRedirect.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

        btnSignUp.setOnClickListener(v -> registerUser());

        getOtpPhone.setOnClickListener(v -> sendPhoneVerification());

        getOtpEmail.setOnClickListener(v ->
                Toast.makeText(this, "Email OTP not supported directly. Use verification email.", Toast.LENGTH_LONG).show()
        );
    }

    private void initViews() {
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        getOtpEmail = findViewById(R.id.getOtpEmail);
        getOtpPhone = findViewById(R.id.getOtpPhone);
        btnSignUp = findViewById(R.id.btnSignUp);
        loginRedirect = findViewById(R.id.loginRedirect);
    }

    private void registerUser() {
        String emailStr = email.getText().toString().trim();
        String passStr = password.getText().toString().trim();

        if (emailStr.isEmpty() || passStr.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && !user.isEmailVerified()) {
                            user.sendEmailVerification();
                            Toast.makeText(this, "Verification email sent", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendPhoneVerification() {
        String phoneNumber = phone.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+91" + phoneNumber) // Change country code if needed
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        mAuth.signInWithCredential(credential)
                                .addOnSuccessListener(authResult ->
                                        Toast.makeText(SignUpActivity.this, "Phone Verified", Toast.LENGTH_SHORT).show()
                                )
                                .addOnFailureListener(e ->
                                        Toast.makeText(SignUpActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(SignUpActivity.this, "OTP Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String id, PhoneAuthProvider.ForceResendingToken token) {
                        verificationId = id;
                        Toast.makeText(SignUpActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                        // Navigate to OTP verification screen if desired
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
