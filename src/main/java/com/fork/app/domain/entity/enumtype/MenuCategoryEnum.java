package com.fork.app.domain.entity.enumtype;

public enum MenuCategoryEnum {
    대표메뉴("대표메뉴"),
    인기메뉴("인기메뉴"),
    세트메뉴("세트메뉴"),
    사이드("사이드"),
    음료("음료");

    private final String displayName;

    MenuCategoryEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
