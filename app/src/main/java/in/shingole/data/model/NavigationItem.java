package in.shingole.data.model;

/**
 * Menu items for left nav drawer.
 */
public enum NavigationItem {
    SHEETS(false),
    SETTINGS(true),
    SEPARATOR(false),
    HELP_AND_FEEDBACK(true);

    private final boolean isSettingsItem;

    NavigationItem(boolean isSettingsItem) {
        this.isSettingsItem = isSettingsItem;
    }

    public boolean isSettingsItem() {
        return isSettingsItem;
    }
}