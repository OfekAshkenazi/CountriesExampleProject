package com.ofek.countries.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofek.countries.R;
import com.ofek.countries.presentation.objects.UiCountry;

import java.util.List;

public class CountriesListAdapter extends RecyclerView.Adapter<CountriesListAdapter.ViewHolder> {

    private final List<UiCountry> uiCountries;

    public CountriesListAdapter(List<UiCountry> uiCountries) {
        this.uiCountries = uiCountries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // as it's an example app I'm not adding the text as string resource
        holder.englishNameTv.setText("English Name: " + uiCountries.get(position).getEnglishName());
        holder.nativeNameTv.setText("English Name: " + uiCountries.get(position).getNativeName());
    }

    @Override
    public int getItemCount() {
        return uiCountries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView englishNameTv, nativeNameTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            englishNameTv = itemView.findViewById(R.id.country_english_name);
            nativeNameTv = itemView.findViewById(R.id.country_native_name);

        }

    }
}
