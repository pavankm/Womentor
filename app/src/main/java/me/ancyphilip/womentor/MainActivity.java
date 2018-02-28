package me.ancyphilip.womentor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import me.ancyphilip.womentor.Cards.arrayAdapter;
import me.ancyphilip.womentor.Cards.cards;
import me.ancyphilip.womentor.Matches.MatchesActivity;

public class MainActivity extends AppCompatActivity {
    private cards cards_data[];

    private me.ancyphilip.womentor.Cards.arrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mauth;

    private String currentUId;
    private DatabaseReference usersDb;

    ListView listView;
    List<cards> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        mauth = FirebaseAuth.getInstance();
        currentUId = mauth.getCurrentUser().getUid();

        checkUserType();


        rowItems = new ArrayList<cards>();


        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems);
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();

                usersDb.child(userId).child("connections").child("nope").child(currentUId).setValue(true);

                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();

                usersDb.child(userId).child("connections").child("yup").child(currentUId).setValue(true);

                isConnectionMatch(userId);
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void isConnectionMatch(String userId) {
        final DatabaseReference currentUserConnectionsDb = usersDb.child(currentUId).child("connections").child("yup").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(MainActivity.this, "new connection", Toast.LENGTH_LONG).show();

                    String key=FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();
                    //usersDb.child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUId).setValue(true);
                    usersDb.child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUId).child("ChatId").setValue(key);

                    //usersDb.child(currentUId).child("connections").child("matches").child(dataSnapshot.getKey()).setValue(true);
                    usersDb.child(currentUId).child("connections").child("matches").child(dataSnapshot.getKey()).child("ChatId").setValue(key);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String userType;
    private String oppositeUserType;

    public void checkUserType() {
        //TODO: Add prefernces child for interest in both usertypes

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userDb = usersDb.child(user.getUid());
        userDb.addListenerForSingleValueEvent((new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("type").getValue() != null) {
                        userType = dataSnapshot.child("type").getValue().toString();
                        switch (userType) {
                            case "Mentor":
                                oppositeUserType = "Mentee";
                                break;
                            case "Mentee":
                                oppositeUserType = "Mentor";
                                break;

                        }
                        getOppositeTypeUser();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }));

    }

    public void getOppositeTypeUser() {

        usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                if (dataSnapshot.exists() && !dataSnapshot.child("connections").child("nope").hasChild(currentUId) && !dataSnapshot.child("connections").child("yup").hasChild(currentUId)
                        && dataSnapshot.child("type").getValue().toString().equals(oppositeUserType)) {
                    String profileImageUrl = "https://firebasestorage.googleapis.com/v0/b/womentor-9c7cb.appspot.com/o/profileImages%2Fic_woman-web.png?alt=media&token=f070883a-b42a-42b8-8bf4-fb2378f7e8f6";
                    if (!dataSnapshot.child("profileImageUrl").getValue().equals("default")) {
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    }
                    cards item = new cards(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), profileImageUrl);
                    rowItems.add(item);
                    arrayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void logoutUser(View view) {
        mauth.signOut();
        Intent intent = new Intent(MainActivity.this, ChooseLoginOrRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }


    public void goToSettings(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);

    }

    public void goToMatches(View view) {
        Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
        startActivity(intent);
    }
}
