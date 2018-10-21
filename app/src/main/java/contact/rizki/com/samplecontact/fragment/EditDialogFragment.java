package contact.rizki.com.samplecontact.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import contact.rizki.com.samplecontact.R;


public class EditDialogFragment extends DialogFragment {


    private String firstName,lastName,age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root_view;
        AutoCompleteTextView txtFirstName,txtLastName,txtAge;
        Button btnEdit,btnCancel;
        ImageButton imgClose;


        root_view=inflater.inflate(R.layout.fragment_edit_dialog, container, false);

        firstName = this.getArguments().getString("firstName");
        lastName = this.getArguments().getString("lastName");
        age = this.getArguments().getString("age");

        imgClose=(ImageButton)root_view.findViewById(R.id.img_close);
        txtFirstName=(AutoCompleteTextView)root_view.findViewById(R.id.first_name);
        txtLastName=(AutoCompleteTextView)root_view.findViewById(R.id.last_name);
        txtAge=(AutoCompleteTextView)root_view.findViewById(R.id.age);
        btnEdit=(Button)root_view.findViewById(R.id.btn_edit);
        btnCancel=(Button)root_view.findViewById(R.id.btn_cancel);

        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtAge.setText(age);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return root_view;
    }


    public interface CallbackResult {

    }



}
