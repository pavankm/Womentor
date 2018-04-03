package me.ancyphilip.womentor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.anton46.collectionitempicker.CollectionPicker;
import com.anton46.collectionitempicker.Item;
import com.anton46.collectionitempicker.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ancyphilip.womentor.Models.DomainModel;
import me.gujun.android.taggroup.TagGroup;

public class SettingsActivity extends AppCompatActivity {


    private EditText myNameField;
    private EditText mPhoneField;
    private EditText mLocationField;
    private EditText mJobTitleField;
    private EditText mCompanyField;
    private EditText mBioField;
    private EditText mFBField;
    private EditText mTwitterField;
    private EditText mLinkedInField;
    private EditText mSkillsField;

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
    private Button mConfirm;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private Uri resultUri;
    private ArrayList<DomainModel> domainsList = new ArrayList<DomainModel>();
    private HashMap<String, String> domainMap = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        myNameField = (EditText) findViewById(R.id.settings_name);
        mPhoneField = (EditText) findViewById(R.id.settings_phone);
        mLocationField = (EditText) findViewById(R.id.settings_location);
        mJobTitleField = (EditText) findViewById(R.id.settings_job_title);
        mCompanyField = (EditText) findViewById(R.id.settings_company);
        mBioField = (EditText) findViewById(R.id.settings_bio);
        mFBField = (EditText) findViewById(R.id.settings_facebook_username);
        mTwitterField = (EditText) findViewById(R.id.settings_twitter_username);
        mLinkedInField = (EditText) findViewById(R.id.settings_linkedin_username);
        mSkillsField = (EditText) findViewById(R.id.settings_skills);

        mProfileImage = findViewById(R.id.settings_profile_image);
//        mBack = findViewById(R.id.back);
        mConfirm = findViewById(R.id.settings_confirm);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        domainsList = (ArrayList<DomainModel>) args.getSerializable("ARRAYLIST");
        for (DomainModel domainModel : domainsList) {
            domainMap.put(domainModel.getId(), domainModel.getName());
        }
        updateMentorsList();


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

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
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

    private void saveUserInformation() {
        name = myNameField.getText().toString();
        phone = mPhoneField.getText().toString();
        location = mLocationField.getText().toString();
        jobTitle = mJobTitleField.getText().toString();
        company = mCompanyField.getText().toString();
        bio = mBioField.getText().toString();
        facebookUsername = mFBField.getText().toString();
        twitterHandle = mTwitterField.getText().toString();
        linkedinUsername = mLinkedInField.getText().toString();
        skills = Arrays.asList(TextUtils.split(mSkillsField.getText().toString().trim(), "\\s*,\\s*"));


        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
        userInfo.put("location", location);
        userInfo.put("jobTitle", jobTitle);
        userInfo.put("company", company);
        userInfo.put("bio", bio);
        userInfo.put("facebookUsername", facebookUsername);
        userInfo.put("twitterHandle", twitterHandle);
        userInfo.put("linkedinUsername", linkedinUsername);
        userInfo.put("skills", skills);


        mUserDatabase.updateChildren(userInfo);

        if (resultUri != null) {
            StorageReference filePath =
                    FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = filePath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Map userInfo = new HashMap();
                    userInfo.put("profileImageUrl", downloadUrl.toString());
                    mUserDatabase.updateChildren(userInfo);

                    finish();
                    return;
                }
            });
        } else {
            finish();
        }

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

    private void updateMentorsList() {

        final ArrayList<String> mentorDomains = new ArrayList<>();
        final ArrayList<String> menteeDomains = new ArrayList<>();

        final Query query = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("domains");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshot1 : snapshots) {
                    if (0L == (long) dataSnapshot1.getValue()) {
                        menteeDomains.add(domainMap.get(dataSnapshot1.getKey()));
                    } else {
                        mentorDomains.add(domainMap.get(dataSnapshot1.getKey()));
                    }
                }
                TagGroup mTagGroupMentor = (TagGroup) findViewById(R.id.tag_group_mentor);
                if (menteeDomains.size() > 0) {

                    String[] mentorDomainStrings = new String[mentorDomains.size()];
                    mentorDomainStrings = mentorDomains.toArray(mentorDomainStrings);

                    mTagGroupMentor.setTags(mentorDomainStrings);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final List<Item> items = new ArrayList<>();
        for (DomainModel domainModel : domainsList) {
            items.add(new Item(domainModel.getId(), domainModel.getName()));
        }
        CollectionPicker mentor = findViewById(R.id.collection_item_picker_mentor);
        mentor.setItems(items);
        mentor.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(Item item, int position) {
//                Toast.makeText(getApplicationContext(), "clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
