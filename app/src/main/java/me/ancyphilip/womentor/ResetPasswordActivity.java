package me.ancyphilip.womentor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private Button mSendResetEmail;
    private EditText mEmail;
    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();

        mSendResetEmail = (Button) findViewById(R.id.send_password_reset_email);
        mEmail = (EditText) findViewById(R.id.password_reset_email);

        final ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
//                .setUrl(url)
//                .setIOSBundleId("com.example.ios")
                // The default for this is populated with the current android package name.
                .setAndroidPackageName("me.ancyphilip.womentor", false, null)
                .build();

        mSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                mAuth.sendPasswordResetEmail(email, actionCodeSettings);
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
