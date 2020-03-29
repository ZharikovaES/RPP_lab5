package ru.mirea.lab5.ui.main;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.lab5.MainActivity;
import ru.mirea.lab5.R;
import ru.mirea.lab5.api.CatApi;
import ru.mirea.lab5.api.model.PhotoDTO;
import ru.mirea.lab5.api.model.PostGet;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {
    private TextView textView;
    private AdapterFavourites adapterFavourites;
    private RecyclerView recyclerView;
    private ArrayList<PostGet> posts;
    private ArrayList<PhotoDTO> photos;
    private GridLayoutManager layoutManager;
    private PhotoDTO flagFavourites;
    private Retrofit retrofit;
    private CatApi api;


    public Tab2() {
        posts = new ArrayList<>();
        photos = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        recyclerView.setVisibility(View.INVISIBLE);
        photos.clear();
        posts.clear();
        textView = (TextView) view.findViewById(R.id.tab2_text);
        recyclerView = (RecyclerView) view.findViewById(R.id.tab2_recycle_view);
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(CatApi.class);
        api.getVotes(MainActivity.USER_ID).enqueue(new retrofit2.Callback<List<PostGet>>() {
            @Override
            public void onResponse(retrofit2.Call<List<PostGet>> call, retrofit2.Response<List<PostGet>> response) {
                if (response.isSuccessful()) {
                    Log.d("daniel", "lastLike " + response.body());
                    List<PostGet> responseData = response.body();
                    posts = new ArrayList<PostGet> (responseData);
                    getPhotos();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<PostGet>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return view;
    }

    public void getPhotos() {
        for (int i = 0, j = 0; i < posts.size() && j < 10; i++) {
            if (posts.get(i).getValue() == 1) {
                j++;
                api.getVotesLike(posts.get(i).getImageId()).enqueue(new retrofit2.Callback<PhotoDTO>() {
                    @Override
                    public void onResponse(retrofit2.Call<PhotoDTO> call, retrofit2.Response<PhotoDTO> response) {
                        if (response.isSuccessful()) {
                            Log.d("History", "onResponse " + response.body());
                            photos.add(response.body());
                            createRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<PhotoDTO> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    private void createRecyclerView() {
        if (photos.size() != 0) {
            textView.setText(R.string.last_like);
            layoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(layoutManager);
            adapterFavourites = new AdapterFavourites(getActivity(), photos);
            recyclerView.setAdapter(adapterFavourites);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            textView.setText(R.string.tab_text_2);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

}
