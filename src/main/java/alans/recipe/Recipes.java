package alans.recipe;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(builder = Recipes.Builder.class)
public class Recipes {

    private final List<Recipe> recipes = new ArrayList<>();

    private Recipes(final List<Recipe> recipes) {
        if (recipes != null && !recipes.isEmpty()) {
            this.recipes.addAll(recipes);
        }
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public static class Builder {

        private List<Recipe> recipes;

        public Recipes build() {
            return new Recipes(recipes);
        }

        public Builder setRecipes(final List<Recipe> recipes) {
            this.recipes = recipes;
            return this;
        }
    }
}
