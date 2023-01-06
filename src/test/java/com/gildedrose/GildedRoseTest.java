package com.gildedrose;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    // General items are anything without special rules per the GildedRose Spec
    // Eg items except for "Aged Brie", "Sulfuras", "Backstage passes", and "Conjured" items

    private final Faker faker = new Faker();

    @Test
    void qualityDegradesByOne_forGeneralItems_withValidQualityValues_throughTheSellByDate() {
        int itemQuality = faker.number().numberBetween(1, 50);
        int sellIn = faker.number().numberBetween(0, Integer.MAX_VALUE);
        GildedRose app = new GildedRose(new Item[] {
            new Item(faker.name().name(), sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(itemQuality - 1, app.items[0].quality);
    }
}
