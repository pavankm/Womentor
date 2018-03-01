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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    private ImageView mProfileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private String userId;
    private String name;
    private String phone;
    private String userType;
    private String profileImgUrl;
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

        // TODO: 2/24/18 more fields for profile information
        myNameField = (EditText) findViewById(R.id.name);
        mPhoneField = (EditText) findViewById(R.id.phone);
        mProfileImage = findViewById(R.id.profileImage);
        mBack = findViewById(R.id.back);
        mConfirm = findViewById(R.id.confirm);

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

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
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

}
