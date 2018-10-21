package contact.rizki.com.samplecontact.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import contact.rizki.com.samplecontact.R;
import contact.rizki.com.samplecontact.activity.EditContactActivity;
import contact.rizki.com.samplecontact.activity.ViewContactActivity;
import contact.rizki.com.samplecontact.model.ContactModel;
import contact.rizki.com.samplecontact.utils.Tools;

/**
 * Created by PERSONAL on 10/18/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<ContactModel> contact = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private Bundle bundle;

    public interface OnItemClickListener {
        void onItemClick(View view, ContactModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public ContactAdapter(Context context, List<ContactModel> contact){
        this.contact=contact;
        ctx=context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilImage;
        public TextView name,age;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            profilImage = (ImageView) v.findViewById(R.id.profil_image);
            name = (TextView) v.findViewById(R.id.name);
            age = (TextView) v.findViewById(R.id.age);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact, parent, false);
        viewHolder = new OriginalViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            final String contactID=contact.get(position).getId();
            String fullName=contact.get(position).getFirstName()+" "+contact.get(position).getLastName();
            String age=contact.get(position).getAge()+" year "+"old";
            String photo=contact.get(position).getPhoto();



            view.name.setText(fullName);
            view.age.setText(age);
            if(!photo.equalsIgnoreCase("N/A")){
                Tools.displayImageRound(ctx, view.profilImage, photo);
            }

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Intent i = new Intent(ctx, EditContactActivity.class);
                Intent i = new Intent(ctx, ViewContactActivity.class);
                bundle = new Bundle();
                bundle.putString("contactID",contactID );
                i.putExtras(bundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(i);

                }
            });
        }
    }





    @Override
    public int getItemCount() {
        return contact.size();
    }

}
