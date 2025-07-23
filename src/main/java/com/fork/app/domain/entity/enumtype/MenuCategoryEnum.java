package com.fork.app.domain.entity.enumtype;

public enum MenuCategoryEnum {
    대표메뉴("대표메뉴", 1),
    인기메뉴("인기메뉴", 2),
    세트메뉴("세트메뉴", 3),
    사이드("사이드", 4),
    음료("음료", 5);

    private final String displayName;
    private final int order;

    MenuCategoryEnum(String displayName, int order) {
        this.displayName = displayName;
        this.order = order;
    }


    public String getDisplayName() {
        return displayName;
    }

    public int getOrder() {
        return order;
    }
}
