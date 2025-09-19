package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    interface EditCityDialogListener {
        void onCityEdited(City city, int position);
    }
    private EditCityDialogListener listener;
    public static EditCityFragment newInstance(City city, int position) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);   //make sure City implements Serializable
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        City city = null;
        int position = -1;

        if (getArguments() != null) {
            city = (City) getArguments().getSerializable("city");
            position = getArguments().getInt("position");

            //values
            if (city != null) {
                editCityName.setText(city.getName());
                editProvinceName.setText(city.getProvince());
            }
        }
        City finalCity = city;
        int finalPosition = position;

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Edit City")
                .setPositiveButton("Save", (dialog, which) -> {
                    if (finalCity != null) {
                        finalCity.setName(editCityName.getText().toString());
                        finalCity.setProvince(editProvinceName.getText().toString());
                        listener.onCityEdited(finalCity, finalPosition);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}