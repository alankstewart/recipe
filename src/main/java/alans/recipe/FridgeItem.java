package alans.recipe;

import java.util.Date;

public class FridgeItem {

    private final String item;
    private final int amount;
    private final Unit unit;
    private final Date useBy;

    private FridgeItem(final String item, final int amount, final Unit unit, final Date useBy) {
        this.item = item;
        this.amount = amount;
        this.unit = unit;
        this.useBy = useBy;
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

    public Date getUseBy() {
        return useBy;
    }

    public static class Builder {

        private String item;
        private int amount;
        private Unit unit;
        private Date useBy;

        public FridgeItem build() {
            return new FridgeItem(item, amount, unit, useBy);
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

        public Builder withUseBy(final Date useBy) {
            this.useBy = useBy;
            return this;
        }
    }
}
