package com.example.android.vegaapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.android.vegaapp.R;
import com.example.android.vegaapp.domain.Recipes;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>  {

    private final static String TAG = RecipeAdapter.class.getName();
    private List<Recipes> recipes;
    private final RecipeOnClickHandler recipeOnClickHandler;


    public RecipeAdapter(List<Recipes> recipes, RecipeOnClickHandler recipeOnClickHandler) {
        this.recipes = recipes;
        this.recipeOnClickHandler = recipeOnClickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v(TAG, "++++++++ onCreateViewHolder - viewGroup-class: " + parent.getClass().getSimpleName());
        Log.v(TAG, "++++++++ onCreateViewHolder - viewGroup-resourceName: " + parent.getContext().getResources().getResourceName(parent.getId()));
        Log.v(TAG, "++++++++ onCreateViewHolder - viewGroup-resourceEntryName: " + parent.getContext().getResources().getResourceEntryName(parent.getId()));
        //TODO moet gedaan worden
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);

        /* maakt een nieuwe view aan
           set xml view*/
        View recipieListItem = inflator.inflate(R.layout.recipe_show_in_recyclerview, parent, false);
        RecipeAdapter.ViewHolder viewHolder = new ViewHolder(recipieListItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //TODO moet gedaan worden
        Recipes recipes = this.recipes.get(position);


        holder.sortFood.setText(this.recipes.get(position).getTypeOfFood());

        //TODO correct image must be set.
        //holder.imageFood.setText(mSeeList.get(position).getReview());
        holder.titleFood.setText(this.recipes.get(position).getRecipeName());
        holder.ingredients.setText(this.recipes.get(position).getIngredients());
    }



    @Override
    public int getItemCount() {
        Log.v(TAG, "item count in Adapter = " + recipes.size());
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //TODO moet gedaan worden
        private TextView sortFood;
        private ImageView imageFood;
        private TextView titleFood;
        private TextView ingredients;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sortFood = (TextView) itemView.findViewById(R.id.typeOfFood);
            imageFood = (ImageView) itemView.findViewById(R.id.image);
            titleFood = (TextView) itemView.findViewById(R.id.titleFood);
            ingredients = (TextView) itemView.findViewById(R.id.ingredients);

            sortFood.setOnClickListener(this);
            imageFood.setOnClickListener(this);
            titleFood.setOnClickListener(this);
            ingredients.setOnClickListener(this);
//
//            rating.setOnClickListener(this);
//            movie.setOnClickListener(this);
//            review.setOnClickListener(this);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.v(RecipeAdapter.ViewHolder.class.getName(), "clicked on item");
            int itemIndex = getAdapterPosition();
            recipeOnClickHandler.onElementClick(v, itemIndex);
        }
    }

}