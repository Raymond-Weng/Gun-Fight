package main;

import jGame.core.Position;
import jGame.core.Size;
import jGame.loop.render.CameraImpl;
import jGame.loop.render.RenderImpl;
import jGame.loop.update.UpdateImpl;
import jGame.main.Game;
import jGame.output.Frame;
import jGame.output.listener.KeyListenerImpl;
import jGame.output.listener.MouseListenerImpl;
import main.objects.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static Main main;

    private Frame frame;
    private KeyListenerImpl keyListener;
    private MouseListenerImpl mouseListener;
    private Game game;
    private UpdateImpl update;
    private RenderImpl render;
    private CameraImpl camera;

    private CreateButton createButton;
    private JoinButton joinButton;
    private IpDisplay ipDisplay;
    private PortDisplay portDisplay;
    private StartButton startButton;
    private IpInput ipInput;
    private PortInput portInput;

    public volatile String scene;

    public static void main(String[] args) {
        main = new Main();
    }

    public Main() {
        keyListener = new KeyListenerImpl();
        mouseListener = new MouseListenerImpl();
        frame = new Frame.Builder()
                .setFrameTitle("Gun Fight")
                .setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
                .setSize(new Size(1000, 1000))
                .setFullScreen(true)
                .setKeyListener(keyListener)
                .setMouseListener(mouseListener)
                .setMouseMotionListener(mouseListener)
                .setNumBufferStrategy(3)
                .build();

        update = new UpdateImpl(30);
        render = new RenderImpl(30);
        camera = new CameraImpl(new Position(0, 0), new Size(800,
                800 * Toolkit.getDefaultToolkit().getScreenSize().height / Toolkit.getDefaultToolkit().getScreenSize().width));

        game = new Game.Builder()
                .setGameStartDelay(5)
                .setBackgroundColor(Color.black)
                .setDebug(true)
                .setCamera(camera)
                .setFontSize(20)
                .setOutput(frame)
                .setRender(render)
                .setUpdate(update)
                .setLoadingTimeOut(5)
                .setOnlyRenderAfterUpdate(false)
                .setThreadCount(3)
                .build();

        frame.setGame(game);
        frame.showFrame();

        scene = "menu";
        camera.setGame(game);
        render.setGame(game);
        update.setGame(game);

        game.build();

        createButton = new CreateButton(game, mouseListener);
        joinButton = new JoinButton(game, mouseListener);

        game.addObject(createButton, 1);
        game.addObject(joinButton, 1);

        game.run();
    }

    public static Main getMain() {
        return main;
    }

    public void joinGame() {
        //TODO join game
        System.out.println("join");
        scene = "join";
        game.removeObject(createButton, 1);

        ipInput = new IpInput(game, keyListener);
        portInput = new PortInput(game, keyListener);
    }

    public void joinReady() {
        game.addObject(ipInput, 1);
    }

    public void createGame() {
        //TODO create game
        System.out.println("create");
        scene = "create";
        game.removeObject(joinButton, 1);
        ipDisplay = new IpDisplay(game, "IP : loading...");
        portDisplay = new PortDisplay(game, "Port : loading...");
        startButton = new StartButton(game, mouseListener);
    }

    public void createReady() {
        game.addObject(ipDisplay, 1);
        game.addObject(portDisplay, 1);
        game.addObject(startButton, 1);
    }
}
