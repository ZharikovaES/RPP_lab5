package ru.mirea.lab5.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.utils.L;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.lab5.MainActivity;
import ru.mirea.lab5.R;
import ru.mirea.lab5.api.CatApi;
import ru.mirea.lab5.api.model.PhotoDTO;
import ru.mirea.lab5.api.model.PostFavourites;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {
    private Context context;
    private List<PhotoDTO> list;
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private ItemViewHolder viewHolder;
    private Retrofit retrofit;
    private CatApi api;


    public Adapter(Context context, RecyclerView recyclerView, List<PhotoDTO> arrayList) {
        this.context = context;
        this.list = arrayList;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
            final PhotoDTO currentItem = list.get(position);
            viewHolder = (ItemViewHolder) holder;

            String imageUrl = currentItem.getImageUrl();
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.icon)
            .into(viewHolder.imageView);
        if (currentItem.isLike()) {
            holder.imageButton.setImageResource(R.drawable.dislike);
        }
            else holder.imageButton.setImageResource(R.drawable.like);
            holder.imageButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                retrofit = new Retrofit.Builder()
                        .baseUrl(Tab1.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api = retrofit.create(CatApi.class);

                // меняем изображение на кнопке
                if (currentItem.isLike()) {
                    currentItem.setLike(!currentItem.isLike());
                    holder.imageButton.setImageResource(R.drawable.like);
//                    Log.d("daniel", "onResponse " + currentItem.getUrl());
                    String s =  currentItem.getUrl();
                    PostFavourites postFavourites = new PostFavourites(s, 1, MainActivity.USER_ID);
                    Call<PostFavourites> call = api.setPostFavourites(postFavourites);
                    call.enqueue(new Callback<PostFavourites>() {
                        @Override
                        public void onResponse(Call<PostFavourites> call, Response<PostFavourites> response) {
                            if (response.isSuccessful()) {
                            }
                        }

                        @Override
                        public void onFailure(Call<PostFavourites> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                    else {
                    // возвращаем первую картинку
                    holder.imageButton.setImageResource(R.drawable.dislike);
                    currentItem.setLike(!currentItem.isLike());
                    String s =  currentItem.getUrl();
                    PostFavourites postFavourites = new PostFavourites(s, 1, MainActivity.USER_ID);
                    Call<PostFavourites> call = api.setPostFavourites(postFavourites);
                    call.enqueue(new Callback<PostFavourites>() {
                        @Override
                        public void onResponse(Call<PostFavourites> call, Response<PostFavourites> response) {
                            if (response.isSuccessful()) {
                                Log.d("daniel", "onResponse робит1 " + response.code());

                            }
                        }

                        @Override
                        public void onFailure(Call<PostFavourites> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addImages(List<PhotoDTO> photoDTOArrayList) {
        for (PhotoDTO p : photoDTOArrayList) {
            list.add(p);
        }
        notifyDataSetChanged();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ImageButton imageButton;
        public boolean flag;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            imageButton = itemView.findViewById(R.id.item_image_bottom);
        }

    }
}
