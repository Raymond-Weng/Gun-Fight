package main.objects;

import jGame.core.Position;
import jGame.core.Size;
import jGame.gameObject.GameObject;
import jGame.gameObject.hitbox.Hitbox;
import jGame.main.Game;
import jGame.output.listener.KeyListenerImpl;
import main.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class IpInput extends GameObject {
    private Game game;
    private Graphics graphics;
    private String text;
    private KeyListenerImpl keyListener;
    private boolean[] keyPressed;
    private boolean backSpacePressed = false;
    private boolean enterPressed = false;
    private boolean periodPressed = false;

    public IpInput(Game game, KeyListenerImpl keyListener){
        this.game = game;
        this.keyListener = keyListener;

        keyPressed = new boolean[11];
        text = "IP : (leave this blank to connect to localhost)";
    }

    @Override
    public void update() {
        if(Main.getMain().scene.equals("join")){
            for(int i = KeyEvent.VK_0; i <= KeyEvent.VK_9; i++){
                if(keyListener.isKeyPressed(i)){
                    if(!keyPressed[Integer.parseInt(KeyEvent.getKeyText(i))]){
                        keyPressed[Integer.parseInt(KeyEvent.getKeyText(i))] = true;
                    }
                }else{
                    if(keyPressed[Integer.parseInt(KeyEvent.getKeyText(i))]){
                        if(text.equals("IP : (leave this blank to connect to localhost)")){
                            text = "IP : " + Integer.valueOf(KeyEvent.getKeyText(i));
                        }else {
                            text = text + Integer.valueOf(KeyEvent.getKeyText(i));
                        }
                        keyPressed[Integer.parseInt(KeyEvent.getKeyText(i))] = false;
                    }
                }
            }
            if(keyListener.isKeyPressed(KeyEvent.VK_PERIOD)){
                if(!periodPressed){
                    periodPressed = true;
                }
            }else{
                if(periodPressed){
                    if(text.equals("IP : (leave this blank to connect to localhost)")){
                        text = "IP : .";
                    }else {
                        text = text + ".";
                    }
                    periodPressed = false;
                }
            }
            if(keyListener.isKeyPressed(KeyEvent.VK_BACK_SPACE)){
                if(!backSpacePressed){
                    backSpacePressed = true;
                }
            }else{
                if(backSpacePressed){
                    if(!text.equals("IP : (leave this blank to connect to localhost)")) {
                        text = text.substring(0, text.length() - 1);
                        if (text.equals("IP : ")) {
                            text = "IP : (leave this blank to connect to localhost)";
                        }
                    }
                    backSpacePressed = false;
                }
            }
            if(keyListener.isKeyPressed(KeyEvent.VK_ENTER)){
                if(!enterPressed){
                    enterPressed = true;
                }
            }else{
                if(enterPressed){
                    if(text.equals("IP : (leave this blank to connect to localhost)")){
                        Main.getMain().ipEnterFinish("localhost");
                    }else {
                        Main.getMain().ipEnterFinish(text.substring(5));
                    }
                    enterPressed = false;
                }
            }
        }
    }

    @Override
    public Image render() {
        Image image = new BufferedImage(game.getCamera().getDisplayArea().getIntWidth(),
                game.getCamera().getDisplayArea().getIntHeight(),
                BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font(null, Font.PLAIN, game.FONT_SIZE));
        graphics.drawString(text,
                (-graphics.getFontMetrics().stringWidth(text) / 2) + (game.getCamera().getDisplayArea().getIntWidth()/2),
                (game.getCamera().getDisplayArea().getIntHeight()) / 2);
        graphics.dispose();
        return image;
    }

    @Override
    public Position getPosition() {
        return new Position(0, 0);
    }

    @Override
    public Hitbox getHitbox() {
        return null;
    }

    @Override
    public Size getSize() {
        return game.getCamera().getDisplayArea();
    }

    public void setIp(String ip){
        this.text = ip;
    }
}
