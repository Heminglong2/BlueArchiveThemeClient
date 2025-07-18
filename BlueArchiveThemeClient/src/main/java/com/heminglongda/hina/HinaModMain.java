package com.heminglongda.hina;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(
    modid = "hina_pvp",  // 保持原modid不变
    name = "BlueArchiveThemeClient",  // 新增name属性
    version = "1.0",
    acceptedMinecraftVersions = "[1.8.9]"
)
public class HinaModMain {
    public static final String MODID = "hina_pvp";
    public static final String VERSION = "1.0";
    public static final int MENU_KEY = Keyboard.KEY_RSHIFT;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        HinaConfig.loadConfig();
        HinaTextureManager.registerTextures();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new HinaCombatSystem());
        MinecraftForge.EVENT_BUS.register(new HinaAntiCheat());
        HinaVoiceSystem.initialize();
    }
    
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(MENU_KEY)) {
            Minecraft.getMinecraft().displayGuiScreen(new HinaMenu());
        }
    }
}