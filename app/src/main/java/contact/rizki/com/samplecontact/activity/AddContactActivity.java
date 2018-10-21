package contact.rizki.com.samplecontact.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.asksira.bsimagepicker.BSImagePicker;
import com.asksira.bsimagepicker.Utils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import contact.rizki.com.samplecontact.R;
import contact.rizki.com.samplecontact.model.ContactModel;
import contact.rizki.com.samplecontact.model.GetDetailContactResponse;
import contact.rizki.com.samplecontact.rest.ApiClient;
import contact.rizki.com.samplecontact.rest.ApiInterface;
import contact.rizki.com.samplecontact.utils.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactActivity extends AppCompatActivity implements BSImagePicker.OnSingleImageSelectedListener {
    private AutoCompleteTextView txtFirstName,txtLastName,txtAge;
    private Button btnAdd,btnCancel;
    private ImageView profilImage,addImage;
    private ApiInterface apiInterface;
    private String selectedPhoto,responseMessage,firstName,lastName,age=null;
    private View lytProgress,lytDisplay;
    private AlertDialog.Builder confirmationDialog;
    private Uri selectedPhotoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Contact");
    }

    private void initComponent(){
        txtFirstName=(AutoCompleteTextView)findViewById(R.id.first_name);
        txtLastName=(AutoCompleteTextView)findViewById(R.id.last_name);
        txtAge=(AutoCompleteTextView)findViewById(R.id.age);
        profilImage=(ImageView)findViewById(R.id.profil_image);
        addImage=(ImageView)findViewById(R.id.add_image);
        btnAdd=(Button)findViewById(R.id.btn_add);
        btnCancel=(Button)findViewById(R.id.btn_cancel);
        lytDisplay = (View) findViewById(R.id.lyt_display);
        lytProgress = (View) findViewById(R.id.lyt_progress);
        lytProgress.setVisibility(View.GONE);



        profilImage.setDrawingCacheEnabled(true);
        profilImage.buildDrawingCache();

        apiInterface= ApiClient.contactAPI().create(ApiInterface.class);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAddContact();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


    }

    private void processAddContact(){

        firstName= txtFirstName.getText().toString().trim();
        lastName=txtLastName.getText().toString().trim();
        age=txtAge.getText().toString().trim();

        if(firstName!=null&&lastName!=null&&age!=null&&!firstName.equalsIgnoreCase("")&&!lastName.equalsIgnoreCase("")&&!age.equalsIgnoreCase("")){
            lytDisplay.setVisibility(View.GONE);
            lytProgress.setVisibility(View.VISIBLE);
            lytProgress.setAlpha(1.0f);

            if(selectedPhotoUri!=null){
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());

                ref.putFile(selectedPhotoUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            selectedPhoto= task.getResult().toString();
                            sendAddContact();



                        } else {
                            Toast.makeText(getApplicationContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        selectedPhoto=null;
                    }
                });
            }else{
                selectedPhoto="N/A";
                sendAddContact();

            }
        }else {
            Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
        }




    }

    private void sendAddContact(){


        ContactModel contactModel= new ContactModel();
        contactModel.setFirstName(firstName);
        contactModel.setLastName(lastName);
        contactModel.setAge(Integer.parseInt(age));
        contactModel.setPhoto(selectedPhoto);

        Call<GetDetailContactResponse> call = apiInterface.addContact(contactModel);
        call.enqueue(new Callback<GetDetailContactResponse>() {
            @Override
            public void onResponse(Call<GetDetailContactResponse> call, Response<GetDetailContactResponse> response) {
                responseMessage=String.valueOf(response.message());
                Toast.makeText(getApplicationContext(), responseMessage, Toast.LENGTH_SHORT).show();
                goBack();
            }

            @Override
            public void onFailure(Call<GetDetailContactResponse> call, Throwable t) {
                t.printStackTrace();
                responseMessage=t.getMessage();
                Toast.makeText(getApplicationContext(), responseMessage, Toast.LENGTH_SHORT).show();

                goBack();
            }
        });


    }

    private void imagePicker(){
        BSImagePicker imagePickerDialog = new BSImagePicker.Builder("contact.rizki.com.samplecontact.fileprovider")
                .setMaximumDisplayingImages(Integer.MAX_VALUE)
                .setSpanCount(4)
                .setGridSpacing(Utils.dp2px(2))
                .setPeekHeight(Utils.dp2px(360))
                .build();
        imagePickerDialog.show(getSupportFragmentManager(), "Image picker");
    }

    @Override
    public void onSingleImageSelected(Uri uri) {
        selectedPhotoUri=uri;
        Tools.displayImageRound(getApplicationContext(), profilImage, uri.toString());
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
