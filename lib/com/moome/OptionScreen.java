package com.moome;

public class OptionScreen extends MenuScreen {
    public OptionScreen() {
        TextBox b = new TextBox(MoomePane.HEIGHT * 10 - 25, 90);
        b.maxCharacters = 6;
        this.elements.add(b);
        this.elements.add(new Button("Option2", MoomePane.HEIGHT * 10 - 12, 90));
        this.elements.add(new Button("Option3", MoomePane.HEIGHT * 10 + 1, 90));
        this.elements.add(new Button("Back", MoomePane.HEIGHT * 10 + 14, 90));
    }
    
    public void buttonPress() {
        switch(selected) {
            case 3:
                ScreenManager.setScreen(1);
            break;
            default: break;
        }
    }
}
