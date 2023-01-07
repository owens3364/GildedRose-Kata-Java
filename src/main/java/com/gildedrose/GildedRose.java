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

        if (!item.name.equals(ItemType.AGED_BRIE.toString())
            && !item.name.equals(ItemType.BACKSTAGE_PASSES.toString())) {
            if (item.quality > 0) {
                if (!item.name.equals(ItemType.SULFURAS.toString())) {
                    item.quality = item.quality - 1;
                }
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                if (item.name.equals(ItemType.BACKSTAGE_PASSES.toString())) {
                    if (item.sellIn < 11) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }
        }

        if (!item.name.equals(ItemType.SULFURAS.toString())) {
            item.sellIn = item.sellIn - 1;
        }

        if (item.sellIn < 0) {
            if (!item.name.equals(ItemType.AGED_BRIE.toString())) {
                if (!item.name.equals(ItemType.BACKSTAGE_PASSES.toString())) {
                    if (item.quality > 0) {
                        if (!item.name.equals(ItemType.SULFURAS.toString())) {
                            item.quality = item.quality - 1;
                        }
                    }
                } else {
                    item.quality = 0;
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            }
        }
    }

    private void updateQualityOfGeneral(Item item) {

    }

    private void updateQualityOfAgedBrie(Item item) {

    }

    private void updateQualityOfBackstagePass(Item item) {

    }

    private void updateQualityOfSulfuras(Item item) {

    }

    private void updateQualityOfConjured(Item item) {

    }
}
