package alans.recipe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

public class RecipeFinder {

    private static final Logger LOGGER = Logger.getLogger(RecipeFinder.class.getName());
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(final String[] args) {
        RecipeFinder recipeFinder = new RecipeFinder();
        try {
            final List<Recipe> recipes = recipeFinder.readRecipes();
            //    LOGGER.info("Recipes size = " + recipes.size());
            final List<FridgeItem> fridgeItems = recipeFinder.readFridgeItems();
            //    LOGGER.info("Fridge items size = " + fridgeItems.size());
            Recipe recipe = recipeFinder.findRecipe(recipes, fridgeItems);
            if (recipe == null) {
                LOGGER.info("Order takeout");
            } else {
                LOGGER.info("Recipe found is " + recipe.getName());
            }
        } catch (final IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private Recipe findRecipe(final List<Recipe> recipes, final List<FridgeItem> fridgeItems) {
        SortedSet<FridgeItem> sortedItems = new TreeSet<>(new Comparator<FridgeItem>() {
            @Override
            public int compare(final FridgeItem o1, final FridgeItem o2) {
                return o1.getUseBy().compareTo(o2.getUseBy());

            }
        });
        Date now = new Date();
        for (FridgeItem fridgeItem : fridgeItems) {
            if (fridgeItem.getUseBy().after(now)) {
                sortedItems.add(fridgeItem);
           }
        }

        for (Recipe recipe : recipes) {
        }

        for (FridgeItem fridgeItem : sortedItems) {
            LOGGER.info(fridgeItem.getItem() + ": " + fridgeItem.getUseBy());
            recipe: for (Recipe recipe : recipes) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    if (ingredient.getItem().equals(fridgeItem.getItem()) && fridgeItem.getUseBy().before(now)) {
                         continue recipe;
                    }
                    if (ingredient.getItem().equals(fridgeItem.getItem()) ) {
                        return recipe;
                    }
                }
            }
        }

        return null;
    }

    List<Recipe> readRecipes() throws IOException {
        try (final InputStream inputStream = getClass().getResourceAsStream("/recipes.json")) {
            final ObjectReader objectReader = MAPPER.reader(new TypeReference<List<Recipe>>() {});
            return objectReader.readValue(inputStream);
        }
    }

    List<FridgeItem> readFridgeItems() throws IOException, ParseException {
        try (final InputStream inputStream = getClass().getResourceAsStream("/fridge.csv");
             final Scanner scanner = new Scanner(inputStream)) {
            final List<FridgeItem> fridgeItems = new ArrayList<>();
            while (scanner.hasNextLine()) {
                final String[] row = scanner.nextLine().split(",");
                fridgeItems.add(getFridgeItem(row));
            }
            return fridgeItems;
        }
    }

    private FridgeItem getFridgeItem(final String[] row) throws ParseException {
        final String item = row[0];
        final int amount = Integer.parseInt(row[1]);
        final Unit unit = Unit.valueOf(row[2]);
        final DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        final Date userBy = format.parse(row[3]);
        return new FridgeItem.Builder().withItem(item).withAmount(amount).withUnit(unit).withUseBy(userBy).build();
    }
}
