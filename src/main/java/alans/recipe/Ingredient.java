package alans.recipe;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Ingredient.Builder.class)
public class Ingredient {

    private final String item;
    private final int amount;
    private final Unit unit;

    private Ingredient(final String item, final int amount, final Unit unit) {
        this.item = item;
        this.amount = amount;
        this.unit = unit;
    }

    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public static final class Builder {

        private String item;
        private int amount;
        private Unit unit;

        public Ingredient build() {
            return new Ingredient(item, amount, unit);
        }

        public Builder withItem(final String item) {
            this.item = item;
            return this;
        }

        public Builder withAmount(final int amount) {
            this.amount = amount;
            return this;
        }

        public Builder withUnit(final Unit unit) {
            this.unit = unit;
            return this;
        }
    }
}
