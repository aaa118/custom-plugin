package com.adi.teleprompter.views.dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adi.teleprompter.R;
import com.adi.teleprompter.adapters.MyAdapter;
import com.adi.teleprompter.model.TextFile;

import java.util.ArrayList;

import static com.adi.teleprompter.views.MainActivity.FILES_LIST;

public class SavedListDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.custom_dialog_fragment, container, false);
        RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //setadapter
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<TextFile> textFileArrayList = bundle.getParcelableArrayList(FILES_LIST);
            MyAdapter adapter = new MyAdapter(textFileArrayList, getFragmentManager(), this);
            mRecyclerView.setAdapter(adapter);
        }

        //get your recycler view and populate it.
        return v;
    }

    public void dismissSavedListDialog() {
        dismiss();
    }
}
