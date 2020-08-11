package me.zbenjamin.tptpplugin.files;

import me.zbenjamin.tptpplugin.Methods;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WarpConfig {
    private static File file;
    private static FileConfiguration warpcFile;

    public static void setup(){
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager()
                .getPlugin("TptpPlugin")).getDataFolder(),
                "warps.yml");

        if (!file.exists()){
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            catch (IOException e){
                if (Methods.getLocaleHu()) System.out.println("Nem lehetett létrehozni a warps.yml fájlt");
                else System.out.println("Couldn't create the warps.yml file");
            }
        }
        warpcFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() { return warpcFile; }

    public static void save(){
        try{ warpcFile.save(file); }
        catch (IOException e){
            if (Methods.getLocaleHu()) System.out.println("Nem lehetett elmenteni a fájlt");
            else System.out.println("Couldn't save the file");
        }
    }

    public static void reload(){
        warpcFile = YamlConfiguration.loadConfiguration(file);
    }
}
