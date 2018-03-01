package me.ancyphilip.womentor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.ancyphilip.womentor.Models.DomainModel;
import me.ancyphilip.womentor.Models.UserModel;

public class DiscoverActivity extends Activity {

    private ListView lv;
    private UserAdapter mUserAdapter;
    private ArrayList<UserModel> mUserModels = new ArrayList<>();
    private ArrayList<DomainModel> mDomainModels = new ArrayList<>();
    private DatabaseReference usersDb;
    Spinner spin;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_disover);

        lv = (ListView) findViewById(R.id.usersList);
        spin = (Spinner) findViewById(R.id.domainSpinner);

        fetchDomains();

        fetchUsers(null);
    }

    private void fetchDomains() {

        DatabaseReference domainsDb = FirebaseDatabase.getInstance().getReference().child("Domains");
        final Query query = domainsDb.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    mDomainModels.add(postSnapshot.getValue(DomainModel.class));
                }
                DomainSpinnerAdapter domainSpinnerAdapter = new DomainSpinnerAdapter(getApplicationContext(),
                                                                                     mDomainModels);
                spin.setAdapter(domainSpinnerAdapter);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        DomainModel domainModel = mDomainModels.get(i);
                        fetchUsers(domainModel.getId());
                        Toast.makeText(getApplicationContext(), "clicked " + domainModel.getName(), Toast.LENGTH_SHORT)
                             .show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchUsers(final String domain) {


        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        final Query query = usersDb.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserModels.clear();
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (TextUtils.isEmpty(domain)) {
                        mUserModels.add(postSnapshot.getValue(UserModel.class));
                    } else {
                        DatabaseReference domains = usersDb.child(postSnapshot.getKey());
                        domains.child("domains").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();
                                for (DataSnapshot dataSnapshot1: snapshots) {
                                    if (dataSnapshot1.getKey().equals(domain)) {
                                        mUserModels.add(postSnapshot.getValue(UserModel.class));
                                    }
                                }
                                mUserAdapter = new UserAdapter(mUserModels, getApplicationContext());

                                lv.setAdapter(mUserAdapter);
                                mUserAdapter.notifyDataSetChanged();

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }

                }

                mUserAdapter = new UserAdapter(mUserModels, getApplicationContext());

                lv.setAdapter(mUserAdapter);
                mUserAdapter.notifyDataSetChanged();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        UserModel userModel = mUserModels.get(i);
                        Toast.makeText(getApplicationContext(), "clicked " + userModel.getName(), Toast.LENGTH_SHORT)
                             .show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class UserAdapter extends ArrayAdapter<UserModel> implements View.OnClickListener {
        private ArrayList<UserModel> dataSet;
        Context mContext;

        // View lookup cache
        private class ViewHolder {
            TextView name;
            TextView email;
            TextView phone;
        }

        public UserAdapter(ArrayList<UserModel> data, Context context) {
            super(context, R.layout.discover_row_item, data);
            this.dataSet = data;
            this.mContext = context;

        }

        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            Object object = getItem(position);
            UserModel dataModel = (UserModel) object;
            Toast.makeText(getContext(), dataModel.getName() + " .. is shown in " + position, Toast.LENGTH_SHORT)
                 .show();
        }

        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            UserModel dataModel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.discover_row_item, parent, false);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.email = (TextView) convertView.findViewById(R.id.email);
                viewHolder.phone = (TextView) convertView.findViewById(R.id.phone);

                result = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.name.setText(dataModel.getName());
            viewHolder.email.setText(dataModel.getEmail());
            viewHolder.phone.setText(dataModel.getPhone());
            // Return the completed view to render on screen
            return convertView;
        }
    }


    public class DomainSpinnerAdapter extends BaseAdapter {
        Context context;
        ArrayList<DomainModel> domains;
        LayoutInflater inflater;

        public DomainSpinnerAdapter(Context applicationContext, ArrayList<DomainModel> domainModels) {
            this.context = applicationContext;
            this.domains = domainModels;
            inflater = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return domains.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.domain_spinner_item, null);
            TextView names = (TextView) view.findViewById(R.id.domainText);
            names.setText(domains.get(i).getName());
            return view;
        }
    }

}
