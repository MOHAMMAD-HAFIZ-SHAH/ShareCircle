package com.example.sharecircle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private CheckBox rememberMeCheckBox;
    private TextView forgotPasswordText, signUpText, guestLoginText;
    private Button loginButton, googleLoginButton;

    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initViews();
        setupGoogleSignIn();

        loginButton.setOnClickListener(v -> emailPasswordLogin());
        googleLoginButton.setOnClickListener(v -> googleLogin());
        signUpText.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
        guestLoginText.setOnClickListener(v -> guestLogin());
        forgotPasswordText.setOnClickListener(v -> sendPasswordReset());
    }

    private void initViews() {
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        forgotPasswordText = findViewById(R.id.forgetPassword);
        signUpText = findViewById(R.id.signUp);
        guestLoginText = findViewById(R.id.guestLoginText);
        loginButton = findViewById(R.id.loginButton);
        googleLoginButton = findViewById(R.id.googleLoginButton);
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Get this from google-services.json
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void emailPasswordLogin() {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        goToMainPage();
                    } else {
                        Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void googleLogin() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void guestLogin() {
        Toast.makeText(this, "Guest login activated. Limited access enabled.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class)); // Or use logic to disable some features
    }

    private void sendPasswordReset() {
        String email = usernameEditText.getText().toString().trim();
        if (!email.isEmpty()) {
            mAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Reset email sent", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Enter your email to reset password", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        goToMainPage();
                    } else {
                        Toast.makeText(this, "Google sign-in failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToMainPage() {
        startActivity(new Intent(this, MainActivity.class));
        finish(); // Prevent going back to login
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Log.w("LoginActivity", "Google sign in failed", e);
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
