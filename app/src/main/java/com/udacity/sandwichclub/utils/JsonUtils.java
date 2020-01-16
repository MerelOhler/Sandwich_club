package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName;
        ArrayList<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        ArrayList<String> ingredients = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);

            //get main name
            JSONObject joName = jsonObject.getJSONObject("name");
            mainName = joName.getString("mainName");

            //get also known as arraylist
            JSONArray jaAlsoKnownAs = joName.getJSONArray("alsoKnownAs");
            if (jaAlsoKnownAs.length() == 0){
                alsoKnownAs = null;
            }
            else {
                for (int i = 0; i < jaAlsoKnownAs.length(); i++) {
                    alsoKnownAs.add(jaAlsoKnownAs.getString(i));
                }
            }

            //get place of origin
            placeOfOrigin = jsonObject.getString("placeOfOrigin");

            //get description
            description = jsonObject.getString("description");

            //get image
            image = jsonObject.getString("image");

            //get ingredients
            JSONArray joIngredients = jsonObject.getJSONArray("ingredients");
            for (int i = 0; i<joIngredients.length(); i++){
                ingredients.add(joIngredients.getString(i));
            }

            Sandwich currentSandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            return currentSandwich;

        }catch (JSONException err){
            Log.d("Error", err.toString());
            return null;
        }

    }
}
