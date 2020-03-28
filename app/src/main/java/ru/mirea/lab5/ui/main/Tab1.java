package ru.mirea.lab5.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.lab5.MainActivity;
import ru.mirea.lab5.R;
import ru.mirea.lab5.api.CatApi;
import ru.mirea.lab5.api.model.BreedDTO;
import ru.mirea.lab5.api.model.PhotoDTO;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {
    private TextView selection;
    private Spinner spinner;
    private AdapterBreed adapterBreed;
    private String selectStringName;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private ArrayList<BreedDTO> breeds;
    private ArrayList<PhotoDTO> photos;
    private ProgressBar progressBar;
    private GridLayoutManager layoutManager;

    private int pager_number = 0;
    private int item_count = 10;
    private boolean isLoading = true;
    private int pastVisibleItems, totalItemCount, visibleItemCount, previousTotal = 0;
    private int viewThreshold = 10;


    public Tab1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breeds = new ArrayList<>();
        photos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        spinner = (Spinner) view.findViewById(R.id.breeds);
        selection = (TextView) view.findViewById(R.id.selection);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.tab1_recycle_view);
        workServiceListOfSpinner();
        return view;
    }

    public void createRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        PhotoDTO.limit = Double.valueOf(item_count);
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CatApi api = retrofit.create(CatApi.class);
        api.getPhotoForBreed(PhotoDTO.breeds_id, item_count, "desc", pager_number).enqueue(new retrofit2.Callback<List<PhotoDTO>>() {
            @Override
            public void onResponse(retrofit2.Call<List<PhotoDTO>> call, retrofit2.Response<List<PhotoDTO>> response) {
                if (response.isSuccessful()) {
                    Log.d("daniel", "onResponse " + response.body());
                    List<PhotoDTO> responseData = response.body();
                    Headers headers = response.headers();
                    PhotoDTO.imagesCount = Double.parseDouble(headers.get("pagination-count"));
                    photos.addAll(responseData);
                    adapterBreed = new AdapterBreed(getActivity(), photos);
                    recyclerView.setAdapter(adapterBreed);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<PhotoDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + viewThreshold)) {
                        pager_number++;
                        performPageination();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void performPageination() {
        progressBar.setVisibility(VISIBLE);
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CatApi api = retrofit.create(CatApi.class);
        api.getPhotoForBreed(PhotoDTO.breeds_id, item_count,"desc", pager_number).enqueue(new retrofit2.Callback<List<PhotoDTO>>() {
            @Override
            public void onResponse(retrofit2.Call<List<PhotoDTO>> call, retrofit2.Response<List<PhotoDTO>> response) {
                if (response.isSuccessful()) {
                    if (pager_number  < PhotoDTO.getPageCount()) {
                        Log.d("daniel", "onResponse " + response.body());
                        List<PhotoDTO> responseData = response.body();
//                        photos.addAll(responseData);
                        adapterBreed.addImages(responseData);

                    } else {
                        Toast.makeText(getActivity(), "Фото больше нет", Toast.LENGTH_SHORT).show();
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(GONE);
            }

            @Override
            public void onFailure(retrofit2.Call<List<PhotoDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void createSpinner(ArrayList<BreedDTO> array) {
        breeds = array;
        String[] arrayList = new String[array.size() + 1];
        arrayList[0] = "Породы";
        for (int i = 0; i < array.size(); i++) {
            arrayList[i + 1] = array.get(i).getBreed();
        }
        ArrayAdapter<String> adapterArr = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arrayList);
        adapterArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterArr);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photos.clear();
                pager_number = 0;
                previousTotal = 0;
                isLoading = true;
                // Получаем выбранный объект
                selectStringName = (String) parent.getItemAtPosition(position);
                if (!selectStringName.equals("Породы")) {
                    for (int i = 0; i < breeds.size(); i++) {
                        if (breeds.get(i).getBreed().equals(selectStringName)) {
                            PhotoDTO.breeds_id = breeds.get(i).getId();
                            break;
                        }
                    }
//                    progressBar.setVisibility(VISIBLE);
                    createRecyclerView();
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                selection.setText(selectStringName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void workServiceListOfSpinner() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CatApi api = retrofit.create(CatApi.class);
        api.getBreeds().enqueue(new retrofit2.Callback<List<BreedDTO>>() {
            @Override
            public void onResponse(retrofit2.Call<List<BreedDTO>> call, retrofit2.Response<List<BreedDTO>> response) {
                if (response.isSuccessful()) {
                    Log.d("daniel", "onResponse " + response.body());
                    List<BreedDTO> responseData = response.body();
                    createSpinner((ArrayList<BreedDTO>) responseData);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<BreedDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
