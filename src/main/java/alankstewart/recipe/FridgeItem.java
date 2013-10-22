package alankstewart.recipe;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.joda.time.DateTime;

public class FridgeItem implements Comparable<FridgeItem> {

    private final String item;
    private final int amount;
    private final Unit unit;
    private final DateTime useBy;

    private FridgeItem(final String item, final int amount, final Unit unit, final DateTime useBy) {
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

    public DateTime getUseBy() {
        return useBy;
    }

    @Override
    public int compareTo(final FridgeItem o) {
        return ComparisonChain.start().compare(useBy, o.getUseBy()).compare(item, o.getItem()).result();
    }

    @Override
    public String toString() {
        return String.format("FridgeItem [item=%s, amount=%s, unit=%s, useBy=%s]", item, amount, unit, useBy);
    }

    public static class Builder {

        private String item;
        private int amount;
        private Unit unit;
        private DateTime useBy;

        public FridgeItem build() {
            return new FridgeItem(item, amount, unit, useBy);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this).add("item", item).add("amount", amount).add("unit", unit)
                    .add("useBy", useBy).toString();
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

        public Builder withUseBy(final DateTime useBy) {
            this.useBy = useBy;
            return this;
        }
    }
}
