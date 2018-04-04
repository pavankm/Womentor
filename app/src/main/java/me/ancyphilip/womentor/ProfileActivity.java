package me.ancyphilip.womentor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    private TextView myNameField;
    private TextView mPhoneField;
    private TextView mLocationField;
    private TextView mJobTitleField;
    private TextView mCompanyField;
    private TextView mBioField;
    private TextView mFBField;
    private TextView mTwitterField;
    private TextView mLinkedInField;
    private TextView mSkillsField;

    private ImageView mProfileImage;

    private String userId;
    private String name;
    private String phone;
    private String location;
    private String jobTitle;
    private String company;
    private String bio;
    private String facebookUsername;
    private String twitterHandle;
    private String linkedinUsername;
    private List<String> skills;
    private String profileImgUrl;

    private String userType;

    private Button mBack;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private Uri resultUri;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        userId = getIntent().getStringExtra("userID");

        myNameField = (TextView) findViewById(R.id.profile_name);
        mPhoneField = (TextView) findViewById(R.id.profile_phone);
        mLocationField = (TextView) findViewById(R.id.profile_location);
        mJobTitleField = (TextView) findViewById(R.id.profile_job_title);
        mCompanyField = (TextView) findViewById(R.id.profile_company);
        mBioField = (TextView) findViewById(R.id.profile_bio);
        mFBField = (TextView) findViewById(R.id.profile_facebook_username);
        mTwitterField = (TextView) findViewById(R.id.profile_twitter_username);
        mLinkedInField = (TextView) findViewById(R.id.profile_linkedin_username);
        mSkillsField = (TextView) findViewById(R.id.profile_skills);

        mProfileImage = findViewById(R.id.profile_profile_image);
//        mBack = findViewById(R.id.back);
        mBack = findViewById(R.id.profile_back);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
//        updateMentorsList();


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        getUserInfo();
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    private void getUserInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null) {
                        name = map.get("name").toString();
                        myNameField.setText(name);
                    }
                    if (map.get("phone") != null) {
                        phone = map.get("phone").toString();
                        mPhoneField.setText(phone);
                    }
                    if (map.get("type") != null) {
                        userType = map.get("type").toString();
                    }
                    if (map.get("location") != null) {
                        location = map.get("location").toString();
                        mLocationField.setText(location);
                    }
                    if (map.get("jobTitle") != null) {
                        jobTitle = map.get("jobTitle").toString();
                        mJobTitleField.setText(jobTitle);
                    }
                    if (map.get("company") != null) {
                        company = map.get("company").toString();
                        mCompanyField.setText(company);
                    }
                    if (map.get("bio") != null) {
                        bio = map.get("bio").toString();
                        mBioField.setText(bio);
                    }
                    if (map.get("facebook_username") != null) {
                        facebookUsername = map.get("facebook_username").toString();
                        mFBField.setText(facebookUsername);
                    }
                    if (map.get("twitter_handle") != null) {
                        twitterHandle = map.get("twitter_handle").toString();
                        mTwitterField.setText(twitterHandle);
                    }
                    if (map.get("linkedin_username") != null) {
                        linkedinUsername = map.get("linkedin_username").toString();
                        mLinkedInField.setText(linkedinUsername);
                    }
                    if (map.get("skills") != null) {
                        skills = (List<String>) map.get("skills");
                        mSkillsField.setText(TextUtils.join(", ", skills));
                    }

                    Glide.clear(mProfileImage);
                    if (map.get("profileImageUrl") != null) {
                        switch ((String) map.get("profileImageUrl")) {
                            case "default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(mProfileImage);
                                break;
                            default:

                                Glide.with(getApplication()).load(map.get("profileImageUrl")).into(mProfileImage);
                                break;
                        }
                        profileImgUrl = map.get("profileImageUrl").toString();
                        Glide.with(getApplication()).load(profileImgUrl).into(mProfileImage);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageURI = data.getData();
            resultUri = imageURI;
            mProfileImage.setImageURI(resultUri);

        }
    }

    public void openFacebookURL(View view) {
        if (facebookUsername != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + facebookUsername));
            startActivity(browserIntent);
        }
    }

    public void openTwitterURL(View view) {
        if (twitterHandle != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/" + twitterHandle));
            startActivity(browserIntent);
        }
    }

    public void openLinkedInURL(View view) {
        if (linkedinUsername != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/" + linkedinUsername));
            startActivity(browserIntent);
        }
    }


}
