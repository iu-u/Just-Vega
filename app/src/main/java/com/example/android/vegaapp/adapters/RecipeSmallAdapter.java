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

public class RecipeSmallAdapter extends RecyclerView.Adapter<RecipeSmallAdapter.ViewHolder>{
    private static final String TAG = "RecipeSmallAdapter";

    private List<Recipe> mRecipes = new ArrayList<>();
    private List<Recipe> mRecipeFull = new ArrayList<>();
    private Context mContext;

    public RecipeSmallAdapter(Context mContext, List<Recipe> mRecipes) {
        this.mRecipes = mRecipes;
        this.mContext = mContext;
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

    public Filter getFilter(){
        return objectFilter;
    }

    private Filter objectFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.i(TAG, "performFiltering called");
            List<Recipe> filteredList = new ArrayList<>();

            String word = constraint.toString();

            for(Recipe a: mRecipes){
                mRecipeFull.add(a);
            }

            if(word == null || word.length() == 0){
                filteredList.clear();
            } else{
                String filterPattern = word.toLowerCase();
                Log.d(TAG, "filterPattern: " + filterPattern);

                for(Recipe item: mRecipeFull){
                    if(item.getRecipeName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
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

        }
    }
}
