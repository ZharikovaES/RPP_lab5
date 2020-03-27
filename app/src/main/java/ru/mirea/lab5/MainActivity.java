package ru.mirea.lab5;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.lab5.api.CatApi;
import ru.mirea.lab5.api.model.PhotoDTO;
import ru.mirea.lab5.api.model.PostFavourites;
import ru.mirea.lab5.ui.main.SectionsPagerAdapter;
import ru.mirea.lab5.ui.main.Tab1;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabs;
    private ViewPager viewPager;
    private TabItem tab1, tab2;
    private SectionsPagerAdapter pagerAdapter;
    public static String USER_ID ="your-user-1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tab1 = (TabItem)findViewById(R.id.tab1);
        tab2 = (TabItem)findViewById(R.id.tab2);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabs.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Tab1.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CatApi api = retrofit.create(CatApi.class);
        api.getVotes(USER_ID).enqueue(new retrofit2.Callback<List<PostFavourites>>() {
            @Override
            public void onResponse(retrofit2.Call<List<PostFavourites>> call, retrofit2.Response<List<PostFavourites>> response) {
                if (response.isSuccessful()) {
                    List<PostFavourites> responseData = response.body();


                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<PostFavourites>> call, Throwable t) {
                t.printStackTrace();
            }
        });


        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
}
}