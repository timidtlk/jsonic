package me.timidtlk.core;

import java.awt.Font;
import java.io.File;
import java.util.HashMap;

import lombok.Getter;

@Getter
public class GameFonts {
    
    private HashMap<String, Font> fonts;

    public GameFonts() {
        fonts = new HashMap<>();

        loadFonts();
    }

    private void loadFonts() {
        try {
            File[] files = new File(getClass().getClassLoader().getResource("fonts/").getPath()).listFiles();
            for (File file : files) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, file);
                fonts.put(file.getName(), font);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
