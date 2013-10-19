package alans.recipe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static alans.recipe.Unit.grams;
import static alans.recipe.Unit.slices;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RecipeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String NAME = "grilled cheese on toast";
    private static final String ITEM = "bread";

    private static String json;

    @BeforeClass
    public static void setUp() throws IOException {
        json = new String(Files.readAllBytes(Paths.get("src/main/resources/recipes.json")));
        // json = Files.toString(new File("src/main/resources/recipes.json"), Charsets.UTF_8);
    }

    @Test
    public final void canDeserializeFromJson() throws IOException {
        final ObjectReader objectReader = MAPPER.reader(new TypeReference<List<Recipe>>() {});
        final List<Recipe> recipes = objectReader.readValue(json);
        assertNotNull(recipes);
        assertEquals(2, recipes.size());

        final Recipe recipe = recipes.get(0);
        assertEquals(NAME, recipe.getName());

        final List<Ingredient> ingredients = recipe.getIngredients();
        assertNotNull(ingredients);
        assertEquals(2, ingredients.size());

        final Ingredient ingredient = ingredients.get(0);
        assertEquals(ITEM, ingredient.getItem());
        assertEquals(2, ingredient.getAmount());
        assertEquals(slices, ingredient.getUnit());
    }

    @Test
    public final void canSerializeToJson() throws JsonProcessingException, JSONException {
        final List<Recipe> recipes = Arrays.asList(new Recipe.Builder().withName(NAME).withIngredients(Arrays
                .asList(new Ingredient.Builder().withItem(ITEM).withAmount(2).withUnit(slices)
                        .build(), new Ingredient.Builder().withItem("cheese").withAmount(2).withUnit(slices).build()))
                .build(), new Recipe.Builder().withName("salad sandwich").withIngredients(Arrays
                .asList(new Ingredient.Builder().withItem(ITEM).withAmount(2).withUnit(slices)
                        .build(), new Ingredient.Builder().withItem("mixed salad").withAmount(100).withUnit(grams)
                        .build())).build());

        final ObjectWriter objectWriter = MAPPER.writerWithType(new TypeReference<List<Recipe>>() {});
        final JSONArray actual = new JSONArray(objectWriter.writeValueAsString(recipes));
        final JSONArray expected = new JSONArray(json);
        JSONAssert.assertEquals(expected, actual, true);
    }
}
