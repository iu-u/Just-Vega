package com.example.android.vegaapp.adapters;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.vegaapp.R;
import com.example.android.vegaapp.domain.Recipe;
import com.example.android.vegaapp.domain.TypeOfFood;
import com.facebook.appevents.suggestedevents.ViewOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeSmallAdapter extends RecyclerView.Adapter<RecipeSmallAdapter.ViewHolder>{
    private static final String TAG = "RecipeSmallAdapter";

    private List<Recipe> mRecipes;
    private List<Recipe> mRecipeFull;
    private Context mContext;
    private final RecipeSmallOnClickHandler recipeSmallOnClickHandler;
    int typeOfFilter = 0;

    public RecipeSmallAdapter(Context mContext, List<Recipe> mRecipes, List<Recipe> all, RecipeSmallOnClickHandler recipeSmallOnClickHandler) {
        this.mRecipes = mRecipes;
        this.mRecipeFull = all;
        this.mContext = mContext;
        this.recipeSmallOnClickHandler = recipeSmallOnClickHandler;
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
                .asBitmap().load(mRecipes.get(position).getImage())
                .into(holder.imageView);
        holder.recipeTitle.setText(mRecipes.get(position).getRecipeName());
        holder.recipeCategory.setText(mRecipes.get(position).getCategory());
    }

    public void setTypeOfFilter(String string){
        if(string.equalsIgnoreCase("recipe")){
            typeOfFilter = 0;
        } else if(string.equalsIgnoreCase("ingredient")){
            typeOfFilter = 1;
        }
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
            Log.d(TAG, "word to String performfiltering: " + word);
            String[] ingredients = word.split(",");

            if(word == null || word.length() == 0){
                filteredList.clear();
            } else{
                String filterPattern = word.toLowerCase();
                Log.d(TAG, "filterPattern: " + filterPattern);

                for(Recipe item: mRecipeFull){
                    if(typeOfFilter == 0){
                        Log.i(TAG, "Search on recipe called");
                        if(item.getRecipeName().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if(typeOfFilter == 1){
                        Log.i(TAG, "Search on ingredient called");
                        Log.d(TAG, "Recipe name: " + item.getRecipeName());
                        for(TypeOfFood t: item.getTof()){
                            Log.i(TAG, "Type of food:" + t.getName());
                            ArrayList<String> lowerCase = new ArrayList();
                            for(String s:t.getIngredients()) {
                                if(s.startsWith("gr,")|| s.startsWith("st,")){
                                    s = s.substring(3);
                                    }
                                lowerCase.add(s.toLowerCase());
                            }
                            if(lowerCase.contains(ingredients[0].toLowerCase())){
                                Log.i(TAG, "Item added:" + item.getRecipeName() + "Type: " + t.getName());
                                filteredList.add(item);
                                break;
                            }
                        }



                    }

                    }
                }
//            List<Recipe> secondFiltered = filteredList;
////            Log.d(TAG, "sceondfiltered 1: " + secondFiltered.get(0).getRecipeName());
////            Log.d(TAG, "sceondfiltered 2: " + secondFiltered.get(1).getRecipeName());
//            for(int i=0;i<secondFiltered.size();i++){
//                Log.d(TAG, "secondfilterd: " + secondFiltered.get(i).getRecipeName());
//            }
//
//            if(ingredients.length>1){
//                Log.d(TAG, "amount of ingredients: " + ingredients.length);
//                for(int i = 1; i<ingredients.length;i++){
//                    Log.d(TAG, "inggreident: " + ingredients[i]);
//                    for(Recipe r: secondFiltered){
//                        Log.d(TAG, "Recipe called " + r.getRecipeName());
//                        List<String> allIngredients = new ArrayList<>();
//                        for(TypeOfFood t: r.getTof()){
//                            Log.d(TAG, "Type of food:" + t.getName());
//                            ArrayList<String> ingredientLowerCase = new ArrayList();
//                            for(String s:t.getIngredients()) {
//                                if(s.startsWith("gr,")|| s.startsWith("st,")){
//                                    s = s.substring(3);
//                                }
//                                ingredientLowerCase.add(s.toLowerCase());
//                                allIngredients.add(s.toLowerCase());
//                            }
//                            if(ingredientLowerCase.contains(ingredients[i].toLowerCase())){
//                                Log.d(TAG, "ingredientLowercase contains " + ingredients[i]);
//                                Log.i(TAG, "Item added:" + r.getRecipeName() + "Type: " + t.getName());
//                                filteredList.add(r);
//
//                            } else{
//                                Log.d(TAG, "ingredientLowercase does not contain " + ingredients[i]);
//                            }
//
//                        }
//
//                    }
//                }
//            }


            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();

//            Log.i(TAG, "performFiltering List returned: " + results.values + " | Amount of items: " + results.count);

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.i(TAG, "publishResults called");
//                mRecipes.clear();
//                mRecipes.addAll((List)results.values);
            if(typeOfFilter == 0){
                Log.i(TAG, "typeOfFilter publishResult = 0");
                mRecipes.clear();
                mRecipes.addAll((List)results.values);
            } else if(typeOfFilter == 1){
                Log.i(TAG, "typeOfFilter publishResult = 1");
                mRecipes.clear();
                mRecipes.addAll((List)results.values);
            }


            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView recipeTitle;
        TextView recipeCategory;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageSmall);
            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeCategory = itemView.findViewById(R.id.recipeType);
            parentLayout = itemView.findViewById(R.id.smallRecipe_parent);

            //set onclick
            parentLayout.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Log.v(ViewHolder.class.getName(), "clicked on item");
            int itemIndex = getAdapterPosition();
            recipeSmallOnClickHandler.onElementClick(view, itemIndex);
        }
    }
}
