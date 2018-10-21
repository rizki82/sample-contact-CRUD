package contact.rizki.com.samplecontact.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import contact.rizki.com.samplecontact.R;
import contact.rizki.com.samplecontact.model.GetDetailContactResponse;
import contact.rizki.com.samplecontact.rest.ApiClient;
import contact.rizki.com.samplecontact.rest.ApiInterface;
import contact.rizki.com.samplecontact.utils.Tools;
import contact.rizki.com.samplecontact.utils.ViewAnimation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewContactActivity extends AppCompatActivity {

    private ImageView contactImageView;
    private TextView fullNameTextView,ageTextView;
    private FloatingActionButton editProfilFloatingActionButton;
    private LinearLayout lytProgress,lytDisplay;
    private String firstName,lastName,age,contactID,contactPhotoUrl=null;
    private ApiInterface apiInterface;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        initComponent();
    }




    private void initComponent(){
        contactImageView=(ImageView)findViewById(R.id.contact_image);
        fullNameTextView=(TextView)findViewById(R.id.fullname);
        ageTextView=(TextView)findViewById(R.id.age);
        editProfilFloatingActionButton=(FloatingActionButton)findViewById(R.id.edit_contact);
        lytDisplay = (LinearLayout) findViewById(R.id.lyt_display);
        lytProgress = (LinearLayout) findViewById(R.id.lyt_progress);
        lytDisplay.setVisibility(View.GONE);
        editProfilFloatingActionButton.setVisibility(View.GONE);
        lytProgress.setAlpha(1.0f);

        apiInterface= ApiClient.contactAPI().create(ApiInterface.class);
        bundle=getIntent().getExtras();
        if(bundle!=null){
            contactID=bundle.getString("contactID");
            getDetailContact(contactID);
        }else{
            goBack();
        }

        editProfilFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditContactActivity.class);
                bundle = new Bundle();
                bundle.putString("contactID",contactID );
                bundle.putString("firstName",firstName);
                bundle.putString("lastName",lastName);
                bundle.putString("age",age);
                bundle.putString("contactPhotoUrl",contactPhotoUrl);
                i.putExtras(bundle);
                startActivity(i);
            }
        });


    }

    private void getDetailContact(final String contactID ){

        apiInterface= ApiClient.contactAPI().create(ApiInterface.class);
        Call<GetDetailContactResponse> call = apiInterface.getContactDetail(contactID);
        call.enqueue(new Callback<GetDetailContactResponse>() {
            @Override
            public void onResponse(Call<GetDetailContactResponse> call, Response<GetDetailContactResponse> response) {

                firstName=response.body().getData().getFirstName();
                lastName=response.body().getData().getLastName();
                age=response.body().getData().getAge().toString();
                contactPhotoUrl=response.body().getData().getPhoto();

                fullNameTextView.setText(firstName+" "+lastName);
                ageTextView.setText(String.valueOf(age)+" "+"year old");
                if(!contactPhotoUrl.equalsIgnoreCase("N/A")){
                    Tools.displayImageRound(getApplicationContext(), contactImageView, contactPhotoUrl);
                }
                ViewAnimation.fadeOut(lytProgress);
                lytDisplay.setVisibility(View.VISIBLE);
                editProfilFloatingActionButton.setVisibility(View.VISIBLE);


            }

            @Override
            public void onFailure(Call<GetDetailContactResponse> call, Throwable t) {
                // handle failed request
                t.printStackTrace();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            goBack();
        }

        return super.onOptionsItemSelected(item);
    }

    private void goBack(){
        Intent i = new Intent(getApplicationContext(), ListContactActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(i);
    }


}
