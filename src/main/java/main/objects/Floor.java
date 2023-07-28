package main.objects;

import jGame.core.Position;
import jGame.core.Size;
import jGame.gameObject.GameObject;
import jGame.gameObject.hitbox.Hitbox;
import jGame.gameObject.hitbox.hitboxShape.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Floor extends GameObject {
    private Position position;
    private Size size;

    public Floor(Position position, Size size){
        this.position = position;
        this.size = size;
    }

    @Override
    public void update() {

    }

    @Override
    public Image render() {
        Image image = new BufferedImage(size.getIntWidth(), size.getIntHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(new Color(10, 83, 255));
        graphics.fillRect(0, 0, size.getIntWidth(), size.getIntHeight());
        graphics.dispose();
        return image;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Hitbox getHitbox() {
        return new Hitbox(new Rectangle(this.position, this.size));
    }

    @Override
    public Size getSize() {
        return this.size;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void setSize(Size size){
        this.size = size;
    }
}
