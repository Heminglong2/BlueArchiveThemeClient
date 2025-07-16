package com.heminglongda.hina;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import java.util.List;

public class HinaMenu extends GuiScreen {
    @Override
    public void initGui() {
        buttonList.clear();
        int buttonY = 50;
        
        // 添加功能开关按钮
        addFeatureButton("attack_guide", "攻击指引系统", buttonY);
        buttonY += 24;
        addFeatureButton("combo_counter", "连击计数器", buttonY);
        buttonY += 24;
        addFeatureButton("distance_indicator", "攻击距离提示", buttonY);
        buttonY += 24;
        addFeatureButton("anti_cheat", "反作弊系统", buttonY);
        buttonY += 24;
        addFeatureButton("potion_trajectory", "药水轨迹预测", buttonY);
        buttonY += 24;
        addFeatureButton("damage_beautify", "伤害数字美化", buttonY);
        buttonY += 24;
        addFeatureButton("voice", "语音系统", buttonY);
        buttonY += 24;
        addFeatureButton("hina_sanction", "日奈制裁特效", buttonY);
        
        // 添加控制按钮
        buttonList.add(new GuiButton(100, width/2 - 100, height - 30, 90, 20, "全部开启"));
        buttonList.add(new GuiButton(101, width/2 + 10, height - 30, 90, 20, "全部关闭"));
    }
    
    private void addFeatureButton(String featureId, String name, int y) {
        boolean enabled = HinaConfig.getFeatureState(featureId);
        buttonList.add(new GuiToggleButton(
            buttonList.size(),
            width/2 - 100,
            y,
            200,
            20,
            name + ": " + (enabled ? "§aON" : "§cOFF"),
            featureId
        ));
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // 绘制日奈主题背景
        HinaUIEngine.drawHinaBackground(this, width, height);
        
        // 绘制标题
        HinaUIEngine.drawHinaTitle(this, "§l风纪委员作战系统 §dVer 1.8.9", 20);
        
        // 绘制按钮
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof GuiToggleButton) {
            handleToggleButton((GuiToggleButton) button);
        } else if (button.id == 100) {
            enableAllFeatures(true);
        } else if (button.id == 101) {
            enableAllFeatures(false);
        }
    }
    
    private void handleToggleButton(GuiToggleButton button) {
        String featureId = button.featureId;
        boolean newState = !HinaConfig.getFeatureState(featureId);
        HinaConfig.setFeatureState(featureId, newState);
        button.displayString = button.baseText + ": " + (newState ? "§aON" : "§cOFF");
        HinaConfig.saveConfig();
    }
    
    private void enableAllFeatures(boolean enable) {
        for (Object btn : buttonList) {
            if (btn instanceof GuiToggleButton) {
                GuiToggleButton toggle = (GuiToggleButton) btn;
                HinaConfig.setFeatureState(toggle.featureId, enable);
                toggle.displayString = toggle.baseText + ": " + (enable ? "§aON" : "§cOFF");
            }
        }
        HinaConfig.saveConfig();
    }
    
    // 自定义切换按钮
    static class GuiToggleButton extends GuiButton {
        public final String featureId;
        public final String baseText;
        
        public GuiToggleButton(int id, int x, int y, int width, int height, String text, String featureId) {
            super(id, x, y, width, height, text);
            this.featureId = featureId;
            this.baseText = text.substring(0, text.lastIndexOf(':'));
        }
    }
}