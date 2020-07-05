package com.example.athleticskenya.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.athleticskenya.AcceptedProfile;
import com.example.athleticskenya.R;
import com.example.athleticskenya.getterClasses.Accepted;

import java.util.List;

public class AcceptedAdapter extends RecyclerView.Adapter<AcceptedAdapter.AcceptedViewHolder> {

    public static final String EXTRA_FIRSTNAME = "last_name";
    public static final String EXTRA_LASTNAME = "first_name";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_CONTACT = "contact";
    public static final String EXTRA_HEIGHT = "height";
    public static final String EXTRA_WEIGHT = "weight";
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_D_O_B = "d.o.b";
    public static final String EXTRA_RACE = "race";

    private Context mCtx;
    private List<Accepted> acceptedList;

    public AcceptedAdapter(Context mCtx, List<Accepted> acceptedList) {
        this.mCtx = mCtx;
        this.acceptedList = acceptedList;
    }

    @NonNull
    @Override
    public AcceptedAdapter.AcceptedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_accepted, null);
        return new AcceptedAdapter.AcceptedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AcceptedAdapter.AcceptedViewHolder holder, int position) {
        Accepted accepted = acceptedList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(accepted.getImage())
                .placeholder(R.drawable.person)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageView);
        holder.textViewFirstname.setText(accepted.getFirstname());
        holder.textViewLastname.setText(accepted.getLastname());
        holder.textViewPhone.setText(accepted.getPhone());
        holder.textViewEmail.setText(accepted.getEmail());
    }

    @Override
    public int getItemCount() {
        return acceptedList.size();
    }

    class AcceptedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewFirstname, textViewLastname, textViewPhone, textViewEmail;
        ImageView imageView;


        AcceptedViewHolder(View itemView) {
            super(itemView);

            textViewFirstname = itemView.findViewById(R.id.accepted_firstname);
            textViewLastname = itemView.findViewById(R.id.accepted_lastname);
            textViewPhone = itemView.findViewById(R.id.accepted_phone);
            textViewEmail = itemView.findViewById(R.id.accepted_email);
            imageView = itemView.findViewById(R.id.accepted_imageView);
            //reject = itemView.findViewById(R.id.reject_athlete);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v)
        {
            int i = getAdapterPosition();
            Accepted accepted = acceptedList.get(i);
            Intent intent = new Intent(mCtx, AcceptedProfile.class);
            intent.putExtra(EXTRA_ID, String.valueOf(accepted.getId()));
            intent.putExtra(EXTRA_FIRSTNAME, accepted.getFirstname());
            intent.putExtra(EXTRA_LASTNAME, accepted.getLastname());
            intent.putExtra(EXTRA_PHONE, accepted.getPhone());
            intent.putExtra(EXTRA_EMAIL, accepted.getEmail());
            intent.putExtra(EXTRA_D_O_B, accepted.getD_o_b());
            intent.putExtra(EXTRA_RACE, accepted.getRace());
            intent.putExtra(EXTRA_IMAGE, accepted.getImage());
            intent.putExtra(EXTRA_CONTACT, accepted.getContact());
            intent.putExtra(EXTRA_HEIGHT, accepted.getHeight());
            intent.putExtra(EXTRA_WEIGHT, accepted.getWeight());
            intent.putExtra(EXTRA_LOCATION, accepted.getLocation());
            mCtx.startActivity(intent);

        }
    }
}
