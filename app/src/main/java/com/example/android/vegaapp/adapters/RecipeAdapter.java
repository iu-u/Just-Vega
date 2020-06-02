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

public class RecipeAdapter  extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private static final String TAG = "RecipeAdapter";

    private List<Recipe> mRecipes = new ArrayList<>();
    private Context mContext;
    private final RecipeOnClickHandler recipeOnClickHandler;

    public RecipeAdapter(Context mContext, List<Recipe> mRecipes, RecipeOnClickHandler recipeOnClickHandler) {
        this.mRecipes = mRecipes;
        this.mContext = mContext;
        this.recipeOnClickHandler = recipeOnClickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_show_in_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext)
                .asBitmap().load("https://firebasestorage.googleapis.com/v0/b/justvega-aacaa.appspot.com/o/image.png?alt=media&token=a19b6709-8799-4a49-a1a2-1d0498b5111a")
                .into(holder.image);
        holder.category.setText(mRecipes.get(position).getCategory());
        holder.recipeName.setText(mRecipes.get(position).getRecipeName());
        holder.allergy.setText("asdas");
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView category;
        TextView recipeName;
        TextView allergy;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.image);
            category = itemView.findViewById(R.id.category);
            recipeName = itemView.findViewById(R.id.recipeName);
            allergy = itemView.findViewById(R.id.allergy);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            //set onclick
            parentLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Log.v(ViewHolder.class.getName(), "clicked on item");
            int itemIndex = getAdapterPosition();
            recipeOnClickHandler.onElementClick(view, itemIndex);
        }
    }
}