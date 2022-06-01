package com.example.news.fragments.favourite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.news.Database.Database;
import com.example.news.Model.News;
import com.example.news.R;
import com.example.news.activities.DetailActivity;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment {

    ListView listView;
    List<News> newsList=new ArrayList<>();
    ArrayAdapter<News> arrayAdapter;
    Database database;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database=new Database(requireContext());

       // getActivity().getActionBar().setTitle("Favorite");
        intializeViews(view);
      //  setAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter() {

        newsList.clear();
        newsList.addAll(database.getFvrtItems());

        if (newsList.isEmpty()) {
            if(arrayAdapter!=null)
                arrayAdapter.notifyDataSetChanged();
            showToast("No Items In Favorite List");
        }
        else {
            arrayAdapter=new ArrayAdapter<News>(requireContext(), R.layout.lyt_item,R.id.txt, newsList);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    News news=newsList.get(i);
                    Intent n=new Intent(requireContext(), DetailActivity.class);
                    n.putExtra("News", news);
                    startActivity(n);
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    showDeletDialog(i);
                    return true;
                }
            });


        }
    }
    private void showDeletDialog(int position) {

        new AlertDialog.Builder(requireContext())
                .setTitle("Delete")
                .setMessage("Are you sure to delete it ?")
                .setNegativeButton("No",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.deleteFvrt(newsList.get(position).getTitle());
                        showToast("Item Deleted");
                        newsList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                    }
                })
                .show();
    }
    private void showToast(String msg) {

        Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show();
    }
    private void intializeViews(@NonNull View view) {
        listView=view.findViewById(R.id.listView);
    }
}