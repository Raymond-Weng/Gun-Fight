package main.objects;

import jGame.core.Position;
import jGame.core.Size;
import jGame.gameObject.GameObject;
import jGame.gameObject.hitbox.Hitbox;
import jGame.main.Game;
import jGame.output.listener.MouseListenerImpl;
import main.Main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class StartButton extends GameObject {
    private Game game;
    private MouseListenerImpl mouseListener;

    private boolean hit = false;
    private Graphics graphics;

    private int y;

    private String text;
    private boolean ready;

    public StartButton(Game game, MouseListenerImpl mouseListener) {
        this.game = game;
        this.mouseListener = mouseListener;
        this.text = "Waiting for second player...";
    }

    @Override
    public void update() {
        if(graphics != null) {
            y = game.getCamera().getDisplayArea().getIntHeight() - ((-graphics.getFontMetrics().getFont().getSize()/2) + (game.getCamera().getDisplayArea().getIntWidth()/2) - game.getCamera().getDisplayArea().getIntHeight() / 2);
            if (hit) {
                if (mouseListener.isMousePressed(MouseEvent.BUTTON1)) {
                    if (!new Rectangle((-graphics.getFontMetrics().stringWidth(text) / 2) + (game.getCamera().getDisplayArea().getIntWidth() / 2),
                            y,
                            graphics.getFontMetrics().stringWidth(text),
                            graphics.getFontMetrics().getFont().getSize())
                            .contains(mouseListener.getMousePos().getX() * (game.getCamera().getDisplayArea().getWidth() / game.getOutput().getSize().getIntWidth()),
                                    mouseListener.getMousePos().getY() * (game.getCamera().getDisplayArea().getHeight() / game.getOutput().getSize().getHeight()))) {
                        hit = false;
                    }
                } else {
                    if (new Rectangle((-graphics.getFontMetrics().stringWidth(text) / 2) + (game.getCamera().getDisplayArea().getIntWidth() / 2),
                            y,
                            graphics.getFontMetrics().stringWidth(text),
                            graphics.getFontMetrics().getFont().getSize())
                            .contains(mouseListener.getMousePos().getX() * (game.getCamera().getDisplayArea().getWidth() / game.getOutput().getSize().getIntWidth()),
                                    mouseListener.getMousePos().getY() * (game.getCamera().getDisplayArea().getHeight() / game.getOutput().getSize().getHeight()))) {
                        ready();
                    }
                    hit = false;
                }
            } else if (mouseListener.isMousePressed(MouseEvent.BUTTON1)) {
                if (new Rectangle((-graphics.getFontMetrics().stringWidth(text) / 2) + (game.getCamera().getDisplayArea().getIntWidth() / 2),
                        y,
                        graphics.getFontMetrics().stringWidth(text),
                        graphics.getFontMetrics().getFont().getSize())
                        .contains(mouseListener.getMousePos().getX() * (game.getCamera().getDisplayArea().getWidth() / game.getOutput().getSize().getIntWidth()),
                                mouseListener.getMousePos().getY() * (game.getCamera().getDisplayArea().getHeight() / game.getOutput().getSize().getHeight()))) {
                    hit = true;
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
        this.ready = ready;
        this.text = "> Start Game <";
    }
}
