package com.nest.linka.Inbox;


import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nest.linka.LoginActivity;
import com.nest.linka.R;
import com.nest.linka.Util.LoginRequest;
import com.nest.linka.Util.User;
import com.nest.linka.Util.UserRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * the app inbox
 */
public class Inbox extends Fragment {


    private ArrayList<Thread> threads = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private Bundle SI;
    private String test;

    public Inbox() {

    }
    public ArrayList<User> loadUsers() {


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonString = new JSONObject(response);
                    Iterator<String> keys = jsonString.keys();
                    while (keys.hasNext()) {
                        String user_id = keys.next();
                        String username = jsonString.getString(user_id);
                        User user = new User(username, user_id);
                        users.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }




        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inbox.this.getContext());
                builder.setMessage(error.getMessage())
                        .setNegativeButton("retry",null)
                        .create()
                        .show();
            }
        };



        UserRequest userRequest = new UserRequest(listener,error);
        User user = new User("username","user_id");
        users.add(user);

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        queue.add(userRequest);

        return users;
    }

    public ArrayList test(){
        for (User user:users) {
            threads.add(new Thread(user.getUserId()));
        }
        return threads;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_inbox, container, false);

        final TabHost tabHost =(TabHost) root.findViewById(R.id.inbox_tabhost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("tag1");
        spec.setContent(R.id.Messages);
        spec.setIndicator("Read");
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Write");
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Delete");
        tabHost.addTab(spec);
        ListView listView = (ListView) root.findViewById(R.id.inbox_listview);


        users=loadUsers();
        threads = test();


        Adapter adapter = new Adapter();

        listView.setAdapter(adapter);

        return root;
    }

    public void openThread(long userId){
        Intent i = new Intent(this.getContext(),ThreadIntent.class);

        i.putExtra("userId",userId);
        startActivity(i);
    }

   class Adapter extends BaseAdapter{

       /**
        * How many items are in the data set represented by this Adapter.
        *
        * @return Count of items.
        */
       @Override
       public int getCount() {
           return threads.size();
       }

       /**
        * Get the data item associated with the specified position in the data set.
        *
        * @param position Position of the item whose data we want within the adapter's
        *                 data set.
        * @return The data at the specified position.
        */
       @Override
       public Thread getItem(int position) {
           return threads.get(position);
       }

       /**
        * Get the row id associated with the specified position in the list.
        *
        * @param position The position of the item within the adapter's data set whose row id we want.
        * @return The id of the item at the specified position.
        */
       @Override
       public long getItemId(int position) {
           return 0;
       }

       /**
        * Get a View that displays the data at the specified position in the data set. You can either
        * create a View manually or inflate it from an XML layout file. When the View is inflated, the
        * parent View (GridView, ListView...) will apply default layout parameters unless you use
        * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
        * to specify a root view and to prevent attachment to the root.
        *
        * @param position    The position of the item within the adapter's data set of the item whose view
        *                    we want.
        * @param convertView The old view to reuse, if possible. Note: You should check that this view
        *                    is non-null and of an appropriate type before using. If it is not possible to convert
        *                    this view to display the correct data, this method can create a new view.
        *                    Heterogeneous lists can specify their number of view types, so that this View is
        *                    always of the right type (see {@link #getViewTypeCount()} and
        *                    {@link #getItemViewType(int)}).
        * @param parent      The parent that this view will eventually be attached to
        * @return A View corresponding to the data at the specified position.
        */
       @Override
       public View getView(final int position, View convertView, ViewGroup parent) {

           convertView = getLayoutInflater(new Bundle()).inflate(R.layout.message_header,parent,false);
           TextView textView = (TextView) convertView.findViewById(R.id.message_username);
           textView.setText(getItem(position).getUsername());
           convertView.setOnClickListener(
                   new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           openThread(position);
                       }
                   }
           );

           return convertView;
       }


   }




}

