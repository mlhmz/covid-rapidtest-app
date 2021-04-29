package xyz.mlhmz.rapidTestApp.gui;

public enum SearchModes {
    BY_TEST_ID("By Test ID"),
    BY_NAME("By Name"),
    BY_PERSON_ID("By Person ID");
    private final String display;
    private SearchModes(String s) {
        display = s;
    }

    @Override
    public String toString() {
        return display;
    }
}
