package com.gildedrose;

import org.apache.commons.lang3.ArrayUtils;

public enum ItemType {
    AGED_BRIE,
    BACKSTAGE_PASSES,
    SULFURAS,
    CONJURED,
    GENERAL;

    private static final ItemType[] specialItems = {
        AGED_BRIE,
        BACKSTAGE_PASSES,
        SULFURAS,
        CONJURED,
    };

    public static ItemType of(Item item) {
        switch (item.name) {
            case "Aged Brie": return AGED_BRIE;
            case "Backstage passes to a TAFKAL80ETC concert": return BACKSTAGE_PASSES;
            case "Sulfuras, Hand of Ragnaros": return SULFURAS;
            case "Conjured item": return CONJURED;
            default: return GENERAL;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case AGED_BRIE:
                return "Aged Brie";
            case BACKSTAGE_PASSES:
                return "Backstage passes to a TAFKAL80ETC concert";
            case SULFURAS:
                return "Sulfuras, Hand of Ragnaros";
            case CONJURED:
                return "Conjured item";
            default:
                return "General item";
        }
    }

    public boolean isSpecial() {
        return ArrayUtils.contains(specialItems, toString());
    }
}
