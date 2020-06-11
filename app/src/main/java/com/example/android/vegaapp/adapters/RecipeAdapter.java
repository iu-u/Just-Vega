package com.example.android.vegaapp.adapters;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
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
    private List<Recipe> mRecipeFull;
    private Context mContext;
    private final RecipeOnClickHandler recipeOnClickHandler;

    public RecipeAdapter(Context mContext, List<Recipe> mRecipes, RecipeOnClickHandler recipeOnClickHandler, List<Recipe> all) {
        this.mRecipes = mRecipes;
        this.mContext = mContext;
        this.recipeOnClickHandler = recipeOnClickHandler;
        this.mRecipeFull = all;
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
                .asBitmap().load(mRecipes.get(position).getImage())
                .into(holder.image);
        holder.category.setText(mRecipes.get(position).getCategory());
        holder.recipeName.setText(mRecipes.get(position).getRecipeName());

        holder.allergy.setText(mRecipes.get(position).getAllergies().toString().replace('[',' ').replace(']',' ').trim());

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public Filter getFilter(){
        return objectFilter;
    }

    private Filter objectFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.i(TAG, "performFiltering called");
            List<Recipe> filteredList = new ArrayList<>();

            String word = constraint.toString();
            String[] filters = word.split(",");


            if(word == null || word.length() == 0){
                filteredList.clear();
            } else{
                String filterPattern = word.toLowerCase();
                Log.d(TAG, "filterPattern: " + filterPattern);

                for(Recipe item: mRecipeFull){
                    Log.d(TAG, "item allergen: " + item.getAllergies());
                    for(String s: filters){
                        if(item.getCategory().toLowerCase().contains(s.toLowerCase())){
                            filteredList.add(item);
                        }
                    }
                    //case-method change set de incoming String to dutch filterPattern


                        Log.i(TAG, "Recipe search called");
                    }
                }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();

            Log.i(TAG, "performFiltering List returned: " + results.values + " | Amount of items: " + results.count);

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.i(TAG, "publishResults called");
            mRecipes.clear();
            mRecipes.addAll((List)results.values);

            notifyDataSetChanged();
        }
    };




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