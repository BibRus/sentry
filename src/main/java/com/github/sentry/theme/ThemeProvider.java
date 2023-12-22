package com.github.sentry.theme;

import atlantafx.base.theme.Theme;
import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;

import java.util.Map;
import java.util.HashMap;

public class ThemeProvider {

    public static final Map<String, Theme> THEMES = new HashMap<>();

    public static final Theme DRACULA = new Dracula();
    public static final Theme CUPERTINO_DARK = new CupertinoDark();
    public static final Theme CUPERTINO_LIGHT = new CupertinoLight();
    public static final Theme NORD_DARK = new NordDark();
    public static final Theme NORD_LIGHT = new NordLight();
    public static final Theme PRIMER_DARK = new PrimerDark();
    public static final Theme PRIMER_LIGHT = new PrimerLight();

    public static Map<String, Theme> getThemes() {
        return THEMES;
    }

    static {
        THEMES.put(DRACULA.getName(), DRACULA);
        THEMES.put(CUPERTINO_DARK.getName(), CUPERTINO_DARK);
        THEMES.put(CUPERTINO_LIGHT.getName(), CUPERTINO_LIGHT);
        THEMES.put(NORD_DARK.getName(), NORD_DARK);
        THEMES.put(NORD_LIGHT.getName(), NORD_LIGHT);
        THEMES.put(PRIMER_DARK.getName(), PRIMER_DARK);
        THEMES.put(PRIMER_LIGHT.getName(), PRIMER_LIGHT);
    }

}
