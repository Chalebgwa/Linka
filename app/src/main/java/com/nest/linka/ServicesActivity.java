package com.nest.linka;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        final List<String> services = Arrays.asList(
                "Ride Hailing",
                "Food Delivery",
                "Mobile Wallet"
        );

        ListView listView = findViewById(R.id.services_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, services);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String service = services.get(position);
            Toast.makeText(this, service + " coming soon!", Toast.LENGTH_SHORT).show();
        });
    }
}
