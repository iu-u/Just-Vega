package com.example.android.vegaapp.adapters;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.vegaapp.R;
import com.example.android.vegaapp.domain.Recipe;
import com.facebook.appevents.suggestedevents.ViewOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeSmallAdapter extends RecyclerView.Adapter<RecipeSmallAdapter.ViewHolder>{
    private static final String TAG = "RecipeSmallAdapter";

    private List<Recipe> mRecipes = new ArrayList<>();
    private Context mContext;
    private final RecipeOnClickHandler recipeOnClickHandler;

    public RecipeSmallAdapter(Context mContext, List<Recipe> mRecipes, RecipeOnClickHandler recipeOnClickHandler) {
        this.mRecipes = mRecipes;
        this.mContext = mContext;
        this.recipeOnClickHandler = recipeOnClickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_small, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext)
                .asBitmap().load("https://firebasestorage.googleapis.com/v0/b/justvega-aacaa.appspot.com/o/image.png?alt=media&token=a19b6709-8799-4a49-a1a2-1d0498b5111a")
                .into(holder.imageView);
        holder.recipeTitle.setText(mRecipes.get(position).getRecipeName());
        holder.recipeCategory.setText(mRecipes.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView recipeTitle;
        TextView recipeCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageSmall);
            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeCategory = itemView.findViewById(R.id.recipeType);

            //set onclick
//            parentLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Log.v(ViewHolder.class.getName(), "clicked on item");
            int itemIndex = getAdapterPosition();
            recipeOnClickHandler.onElementClick(view, itemIndex);
        }
    }
}
