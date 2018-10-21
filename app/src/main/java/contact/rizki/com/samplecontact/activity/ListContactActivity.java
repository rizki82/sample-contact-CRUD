package contact.rizki.com.samplecontact.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import contact.rizki.com.samplecontact.R;
import contact.rizki.com.samplecontact.adapter.ContactAdapter;
import contact.rizki.com.samplecontact.model.ContactModel;
import contact.rizki.com.samplecontact.model.GetContactSResponse;
import contact.rizki.com.samplecontact.rest.ApiClient;
import contact.rizki.com.samplecontact.rest.ApiInterface;
import contact.rizki.com.samplecontact.utils.ViewAnimation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListContactActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout lytProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts");
    }
    private void initComponent() {
        lytProgress = (LinearLayout) findViewById(R.id.lyt_progress);
        FloatingActionButton addContactFloatingActionBuuton=(FloatingActionButton)findViewById(R.id.add_contact);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        lytProgress.setAlpha(1.0f);


        getContacts();

        addContactFloatingActionBuuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddContactActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getContacts();
    }

    private void getContacts(){
        ApiInterface apiInterface= ApiClient.contactAPI().create(ApiInterface.class);
        Call<GetContactSResponse> call = apiInterface.getContacts();
        call.enqueue(new Callback<GetContactSResponse>() {
            @Override
            public void onResponse(Call<GetContactSResponse> call, Response<GetContactSResponse> response) {
                List<ContactModel> contacts=response.body().getData();
                recyclerView.setAdapter(new ContactAdapter(getApplicationContext(),contacts));
                ViewAnimation.fadeOut(lytProgress);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<GetContactSResponse> call, Throwable t) {
                // handle failed request
            }
        });
    }

}
