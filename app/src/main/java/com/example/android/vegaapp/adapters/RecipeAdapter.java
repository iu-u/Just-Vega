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
import com.example.android.vegaapp.domain.Recipies;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>  {

    private final static String TAG = RecipeAdapter.class.getName();
    private List<Recipies> recipies;
    private final RecipeOnClickHandler RecipeClicker;


    public RecipeAdapter(List<Recipies> recipies, RecipeOnClickHandler recipeClicker) {
        this.recipies = recipies;
        this.RecipeClicker = recipeClicker;
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
        RecipeAdapter.ViewHolder viewHolder2 = new ViewHolder(recipieListItem);

        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //TODO moet gedaan worden


        holder.sortFood.setText(recipies.get(position).getKindOfFood());

        //TODO correct image must be set.
        //holder.imageFood.setText(mSeeList.get(position).getReview());
        holder.titleFood.setText(recipies.get(position).getRecipeName());
        holder.ingredients.setText(recipies.get(position).getIngredients());
    }



    @Override
    public int getItemCount() {
        Log.v(TAG, "item count in Adapter = " + recipies.size());
        return recipies.size();
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
            RecipeClicker.onElementClick(v, itemIndex);
        }
    }

}