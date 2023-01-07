package com.gildedrose;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    // General items are anything without special rules per the GildedRose Spec
    // Eg items except for "Aged Brie", "Sulfuras", "Backstage passes", and "Conjured" items

    // These tests make the following assumptions
    // Sulfuras always starts out at 80
    // Items are never null or have null values
    // The length of the items array never changes
    // The behavior of the GildedRose::updateQuality method is the same regardless of item index
    // The GildedRose::updateQuality method treats items in the array is unrelated and isolated
    // The GildedRose::updateQuality method has no side effects aside from mutating the quality of each item

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

    @Test
    void quality_forSulfuras_alwaysStaysAt80() {
        int itemQuality = 80;
        int sellIn = faker.number().numberBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void quality_forConjuredItems_degradesByTwo() {
        int itemQuality = faker.number().numberBetween(2, 50);
        int sellIn = faker.number().numberBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Conjured item", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(itemQuality - 2, app.items[0].quality);
    }

    @Test
    void quality_forConjuredItems_doesNotDegradeBelowZero() {
        int itemQuality = faker.number().numberBetween(0, 1);
        int sellIn = faker.number().numberBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Conjured item", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void quality_forBackStagePasses_dropsToZero_whenSellInIsNotPositive() {
        int itemQuality = faker.number().numberBetween(0, 50);
        int sellIn = faker.number().numberBetween(Integer.MIN_VALUE, 0);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void quality_forBackStagePasses_increasesByThree_whenSellInIsBetweenOneAndFive() {
        int itemQuality = faker.number().numberBetween(0, 47);
        int sellIn = faker.number().numberBetween(1, 5);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(itemQuality + 3, app.items[0].quality);
    }

    @Test
    void quality_forBackStagePasses_increasesByTwo_whenSellInIsBetweenSixAndTen() {
        int itemQuality = faker.number().numberBetween(0, 48);
        int sellIn = faker.number().numberBetween(6, 10);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(itemQuality + 2, app.items[0].quality);
    }

    @Test
    void quality_forBackStagePasses_increasesByOne_whenSellInIsElevenOrHigher() {
        int itemQuality = faker.number().numberBetween(0, 49);
        int sellIn = faker.number().numberBetween(11, Integer.MAX_VALUE);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(itemQuality + 1, app.items[0].quality);
    }

    @Test
    void quality_forBackStagePasses_neverExceedsFifty() {
        int itemQuality = 50;
        int sellIn = faker.number().numberBetween(1, Integer.MAX_VALUE);
        GildedRose app = new GildedRose(new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, itemQuality)
        });
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }
}
