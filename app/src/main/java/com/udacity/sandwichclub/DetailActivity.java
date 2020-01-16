package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich currentSandwich) {

        //Also Known as
        TextView alsoKnownAsTV = findViewById(R.id.alsoKnownAsTV);
        TextView alsoKnownAsLabel = findViewById(R.id.alsoKnownAsLabel);
        List<String> alsoKnownAsList = currentSandwich.getAlsoKnownAs();
        Log.d("aka", "populateUI: ");
        //if there is no also known as then do not show textviews
        if (alsoKnownAsList == null || alsoKnownAsList.get(0) == "") {
            alsoKnownAsTV.setVisibility(View.GONE);
            alsoKnownAsLabel.setVisibility(View.GONE);
        }
        //if there is a list of also known as
        else {
            alsoKnownAsTV.setVisibility(View.VISIBLE);
            alsoKnownAsLabel.setVisibility(View.VISIBLE);

            String alsoKnownAsString = "";
            for (String temp : alsoKnownAsList) {
                alsoKnownAsString = alsoKnownAsString + temp + "\n";
            }
            //take last newline away
            alsoKnownAsString = alsoKnownAsString.substring(0, alsoKnownAsString.length() - 1);
            alsoKnownAsTV.setText(alsoKnownAsString);
        }

        //Place of Origin
        TextView placeOfOriginTV = findViewById(R.id.originTV);
        TextView placeOfOriginLabel = findViewById(R.id.originLabel);
        String currentOrigin = currentSandwich.getPlaceOfOrigin();
        Log.v("place of origin is ", currentOrigin);
        //if place of origin is not available then make views be gone
        if (currentOrigin.isEmpty()) {
            placeOfOriginTV.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);
        } else {
            placeOfOriginTV.setVisibility(View.VISIBLE);
            placeOfOriginLabel.setVisibility(View.VISIBLE);
            placeOfOriginTV.setText(currentOrigin);
        }

        //description
        TextView descriptionTV = findViewById(R.id.descriptionTV);
        TextView descriptionLabel = findViewById(R.id.descriptionLabel);
        String currentDescription = currentSandwich.getDescription();
        if (currentDescription.isEmpty()) {
            descriptionTV.setVisibility(View.GONE);
            descriptionLabel.setVisibility(View.GONE);
        } else {
            descriptionLabel.setVisibility(View.VISIBLE);
            descriptionTV.setVisibility(View.VISIBLE);
            descriptionTV.setText(currentDescription);
        }

        //ingredients
        TextView ingredientsTV = findViewById(R.id.ingredientsTV);
        TextView ingredientsLabel = findViewById(R.id.ingredientsLabel);
        List<String> ingredientsList = currentSandwich.getIngredients();
        //if there is no also known as then do not show textviews
        if (ingredientsList == null || ingredientsList.get(0)==""){
            ingredientsTV.setVisibility(View.GONE);
            ingredientsLabel.setVisibility(View.GONE);
        }
        //if there is a list of also known as
        else {
            ingredientsTV.setVisibility(View.VISIBLE);
            ingredientsLabel.setVisibility(View.VISIBLE);

            String ingredientsString = "";
            for (String temp : ingredientsList){
                ingredientsString = ingredientsString + temp + "\n";
            }
            //take last newline away
            ingredientsString = ingredientsString.substring(0, ingredientsString.length() - 1);
            ingredientsTV.setText(ingredientsString);
        }

    }
}
