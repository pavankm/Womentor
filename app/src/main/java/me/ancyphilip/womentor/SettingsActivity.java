package me.ancyphilip.womentor;

import android.app.Activity;
import android.content.Intent;
<<<<<<< HEAD
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
=======
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
>>>>>>> 9450eff... linkedin changes
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

<<<<<<< HEAD
import com.anton46.collectionitempicker.CollectionPicker;
import com.anton46.collectionitempicker.Item;
import com.anton46.collectionitempicker.OnItemClickListener;
=======
>>>>>>> 9450eff... linkedin changes
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
<<<<<<< HEAD
import com.google.firebase.database.Query;
=======
>>>>>>> 9450eff... linkedin changes
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
<<<<<<< HEAD

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ancyphilip.womentor.Models.DomainModel;
import me.gujun.android.taggroup.TagGroup;

public class SettingsActivity extends AppCompatActivity {


    private EditText myNameField;
    private EditText mPhoneField;
    private Button mBack;
    private Button mConfirm;
=======
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {


    private EditText myNameField, mPhoneField;
    private Button mBack, mConfirm;
>>>>>>> 9450eff... linkedin changes
    private ImageView mProfileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

<<<<<<< HEAD
    private String userId;
    private String name;
    private String phone;
    private String userType;
    private String profileImgUrl;
    private Uri resultUri;
    private ArrayList<DomainModel> domainsList = new ArrayList<DomainModel>();
    private HashMap<String, String> domainMap = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
=======

    private String userId, name, phone, profileImgUrl, userType;
    private Uri resultUri;

>>>>>>> 9450eff... linkedin changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

<<<<<<< HEAD
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

=======
>>>>>>> 9450eff... linkedin changes
        // TODO: 2/24/18 more fields for profile information
        myNameField = (EditText) findViewById(R.id.name);
        mPhoneField = (EditText) findViewById(R.id.phone);
        mProfileImage = findViewById(R.id.profileImage);
        mBack = findViewById(R.id.back);
        mConfirm = findViewById(R.id.confirm);

<<<<<<< HEAD
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        domainsList = (ArrayList<DomainModel>) args.getSerializable("ARRAYLIST");
        for (DomainModel domainModel : domainsList) {
            domainMap.put(domainModel.getId(), domainModel.getName());
        }
        updateMentorsList();

=======
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
>>>>>>> 9450eff... linkedin changes

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

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
<<<<<<< HEAD
=======
        Button linkedInButton = (Button) findViewById(R.id.linkedin);
        linkedInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinkedIn();
                //computePakageHash();
            }
        });
    }
    private void computePakageHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "me.ancyphilip.womentor",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            Log.e("TAG",e.getMessage());
        }
>>>>>>> 9450eff... linkedin changes
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
                    Glide.clear(mProfileImage);
                    if (map.get("profileImageUrl") != null) {
<<<<<<< HEAD
                        switch ((String) map.get("profileImageUrl")) {
=======
                        switch ((String)map.get("profileImageUrl")) {
>>>>>>> 9450eff... linkedin changes
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

        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
        mUserDatabase.updateChildren(userInfo);

        if (resultUri != null) {
<<<<<<< HEAD
            StorageReference filePath =
                    FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
=======
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
>>>>>>> 9450eff... linkedin changes
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
<<<<<<< HEAD
=======

>>>>>>> 9450eff... linkedin changes
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageURI = data.getData();
            resultUri = imageURI;
            mProfileImage.setImageURI(resultUri);

        }
<<<<<<< HEAD
    }

    private void updateMentorsList() {

        final ArrayList<String> mentorDomains = new ArrayList<>();
        final ArrayList<String> menteeDomains = new ArrayList<>();

        final Query query = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("domains");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshot1: snapshots) {
                    if (0L == (long) dataSnapshot1.getValue()) {
                        menteeDomains.add(domainMap.get(dataSnapshot1.getKey()));
                    } else {
                        mentorDomains.add(domainMap.get(dataSnapshot1.getKey()));
                    }
                }
                TagGroup mTagGroupMentor = (TagGroup) findViewById(R.id.tag_group_mentor);
                if(menteeDomains.size() > 0) {

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
                Toast.makeText(getApplicationContext(), "clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

=======
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);

    }

    private void initializeControls(){
        Button linkedInButton = (Button) findViewById(R.id.linkedin);
        linkedInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinkedIn();
            }
        });
    }
    private void handleLinkedIn(){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Authentication was successful.  You can now do
                // other calls with the SDK.
                fetchPersonalInfo();
            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                Toast.makeText(SettingsActivity.this,"errr"+error.toString(),Toast.LENGTH_LONG).show();

            }
        }, true);
    }
    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    private void fetchPersonalInfo() {
        String url = "https://api.linkedin.com/v1/people/~?format=json";

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!
                String resp = apiResponse.getResponseDataAsString();
                Toast.makeText(SettingsActivity.this,resp,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making GET request!
                String resp = liApiError.toString();
                Toast.makeText(SettingsActivity.this,resp,Toast.LENGTH_LONG).show();
            }
        });
    }
>>>>>>> 9450eff... linkedin changes
}
