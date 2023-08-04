package main.objects;

import jGame.core.Position;
import jGame.core.Size;
import jGame.gameObject.GameObject;
import jGame.gameObject.hitbox.Hitbox;
import jGame.main.Game;
import jGame.output.listener.MouseListenerImpl;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class InputHint extends GameObject {
    private Game game;

    private Graphics graphics;

    private boolean ready = false;
    private int y;

    private String text;

    public InputHint(Game game) {
        this.game = game;
        this.text = "Press enter to continue...";
    }

    @Override
    public void update() {
    }

    @Override
    public Image render() {
        Image image = new BufferedImage(game.getCamera().getDisplayArea().getIntWidth(),
                game.getCamera().getDisplayArea().getIntHeight(),
                BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();
        y = (-graphics.getFontMetrics().getFont().getSize()/2) + (game.getCamera().getDisplayArea().getIntHeight()/4) + (game.getCamera().getDisplayArea().getIntHeight() / 2);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font(null, Font.PLAIN, game.FONT_SIZE));
        graphics.drawString(text,
                (-graphics.getFontMetrics().stringWidth(text) / 2) + (game.getCamera().getDisplayArea().getIntWidth()/2),
                y);
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

    public void ready(){
        this.ready = true;
        this.text = "Press enter to connect...";
    }
}
