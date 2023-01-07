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
                return;
            case AGED_BRIE:
                updateQualityOfAgedBrie(item);
                return;
            case BACKSTAGE_PASSES:
                updateQualityOfBackstagePass(item);
                return;
            case SULFURAS:
                updateQualityOfSulfuras(item);
                return;
            case CONJURED:
                updateQualityOfConjured(item);
                return;
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
        if (item.sellIn < 0) {
            item.quality -= 2;
        } else {
            item.quality--;
        }
        clipQualityRange(item);
        item.sellIn--;
    }

    private void updateQualityOfAgedBrie(Item item) {
        if (item.sellIn <= 0) {
            item.quality += 2;
        } else {
            item.quality++;
        }
        clipQualityRange(item);
        item.sellIn--;
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
        item.sellIn--;
    }

    private void updateQualityOfSulfuras(Item item) {
        item.quality = 80;
        item.sellIn--;
    }

    private void updateQualityOfConjured(Item item) {
        if (item.sellIn < 0) {
            item.quality -= 4;
        } else {
            item.quality -= 2;
        }
        clipQualityRange(item);
        item.sellIn--;
    }

    private void clipQualityRange(Item item) {
        if (ItemType.of(item) != ItemType.SULFURAS) {
            if (item.quality < 0) item.quality = 0;
            if (item.quality > 50) item.quality = 50;
        }
    }
}
