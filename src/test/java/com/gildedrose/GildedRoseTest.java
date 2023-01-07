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

    @Test
    void qualityDegradesByTwo_forGeneralItems_withValidQualityValues_afterTheSellByDate() {
        int itemQuality = faker.number().numberBetween(2, 50);
        int sellIn = faker.number().numberBetween(Integer.MIN_VALUE, -1);
        GildedRose app = new GildedRose(new Item[] {
            new Item(faker.name().name(), sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(itemQuality - 2, app.items[0].quality);
    }

    @Test
    void qualityDegradesByOne_forGeneralItems_withValidQualityValueOne_afterTheSellByDate() {
        int itemQuality = 1;
        int sellIn = faker.number().numberBetween(Integer.MIN_VALUE, -1);
        GildedRose app = new GildedRose(new Item[] {
            new Item(faker.name().name(), sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void quality_forGeneralItems_neverDegradesBelowZero() {
        int itemQuality = 0;
        int sellIn = faker.number().numberBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        GildedRose app = new GildedRose(new Item[] {
            new Item(faker.name().name(), sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void quality_forAgedBrie_increasesByOneWithAge_whenSellInIsPositive() {
        int itemQuality = faker.number().numberBetween(0, 49);
        int sellIn = faker.number().numberBetween(1, Integer.MAX_VALUE);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Aged Brie", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(itemQuality + 1, app.items[0].quality);
    }

    @Test
    void quality_forAgedBrie_increasesByTwoWithAge_whenSellInIsNotPositive() {
        int itemQuality = faker.number().numberBetween(0, 48);
        int sellIn = faker.number().numberBetween(Integer.MIN_VALUE, 0);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Aged Brie", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(itemQuality + 2, app.items[0].quality);
    }

    @Test
    void quality_forAgedBrie_doesNotIncreasePastFifty_whenSellInIsPositive() {
        int itemQuality = 50;
        int sellIn = faker.number().numberBetween(1, Integer.MAX_VALUE);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Aged Brie", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void quality_forAgedBrie_doesNotIncreasePastFifty_whenSellInIsNotPositive() {
        int itemQuality = faker.number().numberBetween(49, 50);
        int sellIn = faker.number().numberBetween(Integer.MIN_VALUE, 0);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Aged Brie", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }
}
