package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
        }
    }

    private void updateItemQuality(Item item) {
        switch (ItemType.of(item)) {
            case GENERAL:
                updateQualityOfGeneral(item);
                break;
            case AGED_BRIE:
                updateQualityOfAgedBrie(item);
                break;
            case BACKSTAGE_PASSES:
                updateQualityOfBackstagePass(item);
                break;
            case SULFURAS:
                updateQualityOfSulfuras(item);
                break;
            case CONJURED:
                updateQualityOfConjured(item);
                break;
        }
        item.sellIn--;
    }

    private void updateQualityOfGeneral(Item item) {
        if (item.sellIn < 0) {
            item.quality -= 2;
        } else {
            item.quality--;
        }
        clipQualityRange(item);
    }

    private void updateQualityOfAgedBrie(Item item) {
        if (item.sellIn <= 0) {
            item.quality += 2;
        } else {
            item.quality++;
        }
        clipQualityRange(item);
    }

    private void updateQualityOfBackstagePass(Item item) {
        if (item.sellIn <= 0) {
            item.quality = 0;
        } else if (item.sellIn < 6) {
            item.quality += 3;
        } else if (item.sellIn < 11) {
            item.quality += 2;
        } else {
            item.quality++;
        }
        clipQualityRange(item);
    }

    private void updateQualityOfSulfuras(Item item) {
        item.quality = 80;
    }

    private void updateQualityOfConjured(Item item) {
        if (item.sellIn < 0) {
            item.quality -= 4;
        } else {
            item.quality -= 2;
        }
        clipQualityRange(item);
    }

    private void clipQualityRange(Item item) {
        if (ItemType.of(item) != ItemType.SULFURAS) {
            if (item.quality < 0) item.quality = 0;
            if (item.quality > 50) item.quality = 50;
        }
    }
}
