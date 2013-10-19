package alans.recipe;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(builder = Recipe.Builder.class)
public class Recipe {

    private final String name;
    private final List<Ingredient> ingredients = new ArrayList<>();

    private Recipe(final String name, final List<Ingredient> ingredients) {
        this.name = name;
        if (ingredients != null && !ingredients.isEmpty()) {
            this.ingredients.addAll(ingredients);
        }
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public static class Builder {

        private String name;
        private List<Ingredient> ingredients;

        public Recipe build() {
            return new Recipe(name, ingredients);
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withIngredients(final List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }
    }
}
