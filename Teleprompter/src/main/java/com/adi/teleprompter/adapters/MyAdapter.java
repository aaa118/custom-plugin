package com.adi.teleprompter.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adi.teleprompter.R;
import com.adi.teleprompter.model.TextFile;
import com.adi.teleprompter.views.OpenedDocumentFragment;
import com.adi.teleprompter.views.dialogs.SavedListDialogFragment;

import java.util.ArrayList;

import static com.adi.teleprompter.views.MainActivity.BUNDLE_LAST_URI;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<TextFile> textFilesList;
    private FragmentManager fragmentManager;
    private SavedListDialogFragment savedListDialogFragment;

    public MyAdapter(ArrayList<TextFile> textFilesList, FragmentManager fragmentManager, SavedListDialogFragment savedListDialogFragment) {
        this.textFilesList = textFilesList;
        this.fragmentManager = fragmentManager;
        this.savedListDialogFragment = savedListDialogFragment;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, padding and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        TextFile textFile = textFilesList.get(position);
        final String name = textFile.getFileName();
        holder.textViewTitle.setText(name);
        holder.textViewTitle.setOnClickListener(v -> {
            savedListDialogFragment.dismissSavedListDialog();
            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_LAST_URI, textFile.getFileContent());
            OpenedDocumentFragment openedDocumentFragment = new OpenedDocumentFragment();
            openedDocumentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.container, openedDocumentFragment).addToBackStack(null).commit();
        });

    }

    @Override
    public int getItemCount() {
        return textFilesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_movie_name);
        }
    }
}
