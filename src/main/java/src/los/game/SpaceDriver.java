package src.los.game;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import src.los.common.PlayerClass;
import src.los.controller.SceneController;
import javafx.scene.Scene;

/**
 * A class contain main game logic
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public class SpaceDriver {
    //variables
    private static final Random RAND = new Random();
    public static PlayerClass chosenCharacter;
    public static int currentLevel; // either make this into an enum class or..
    private static final int WIDTH = 700;
    private static final int HEIGHT = 370;
    private static final int PLAYER_SIZE = 60;
    public Image PLAYER_IMG = new Image(chosenCharacter.getBaseImage());
    public final Image EXPLOSION_IMG = new Image(chosenCharacter.getDeadImage());
    static final int EXPLOSION_W = 128;
    static final int EXPLOSION_ROWS = 4;
    static final int EXPLOSION_COL = 4;
    static final int EXPLOSION_H = 128;
    static final int EXPLOSION_STEPS = 15;

    static final Image BOMBS_IMG[] = {
            new Image("Enemy2.png"),
            new Image("Enemy1.png"),
            new Image("Enemy3.png"),
            new Image("file:./images/4.png")
    };
    final int MAX_BOMBS = 10,  MAX_SHOTS = MAX_BOMBS * 2;
    boolean gameOver = false;
    private GraphicsContext gc;

    Player player;
    List<Shot> shots;
    List<Universe> univ;
    List<Bomb> Bombs;

    private double mouseX;
    private int score;

    @FXML
    Label scoreLabel;

    /**
     * Creates a new Canvas object with a specified width and height, and sets the GraphicsContext2D object to the
     * canvas object, creates a Timeline object with an indefinite cycle count,
     * and sets the cursor to move and the mouse listener to register the mouse movement and mouse click events.
     * @return Returns a Canvas object that represents the game scene.
     */
    public Canvas initializeGameScene() {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            try {
                run(gc);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getY());
        canvas.setOnMouseClicked(e -> {
            if(shots.size() < MAX_SHOTS) shots.add(player.shoot());
            if(gameOver) {
                gameOver = false;
                setup();
            }
        });
        setup();
        return canvas;
    }

    /**
     * Create all the necessary data structure to hold the objects for the game
     */
    private void setup() {
        univ = new ArrayList<>();
        shots = new ArrayList<>();
        Bombs = new ArrayList<>();
        PLAYER_IMG = new Image(chosenCharacter.getBaseImage());
        player = new Player(0, HEIGHT / 2, PLAYER_SIZE, PLAYER_IMG);
        score = 0;
        IntStream.range(0, MAX_BOMBS).mapToObj(i -> this.newBomb()).forEach(Bombs::add);
    }

    // update the score
    private void updateScore(int score) throws IOException {
        Scene gameStage = SceneController.getInstance().gameStage;
        scoreLabel = (Label) gameStage.lookup("#scoreLabel");
        scoreLabel.setText("" + score);
    }

    //run Graphics according to the status
    private void run(GraphicsContext gc) throws IOException {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.TRANSPARENT);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);

        updateScore(score);

        if(gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", WIDTH / 2, HEIGHT /2.5);
            //	return;
        }
        univ.forEach(Universe::draw);

        player.update();
        player.draw();
        player.posY = (int) mouseX;

        Bombs.stream().peek(Player::update).peek(Player::draw).forEach(e -> {
            if(player.colide(e) && !player.exploding) {
                player.explode();
            }
        });

        for (int i = shots.size() - 1; i >=0 ; i--) {
            Shot shot = shots.get(i);
            if(shot.posY < 0 || shot.toRemove)  {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw();
            for (Bomb bomb : Bombs) {
                if(shot.collide(bomb) && !bomb.exploding) {
                    score++;
                    bomb.explode();
                    shot.toRemove = true;
                }
            }
        }

        for (int i = Bombs.size() - 1; i >= 0; i--){
            if(Bombs.get(i).destroyed)  {
                Bombs.set(i, newBomb());
            }
        }

        gameOver = player.destroyed;
        if(RAND.nextInt(10) > 2) {
            univ.add(new Universe());
        }
        for (int i = 0; i < univ.size(); i++) {
            if(univ.get(i).posY > HEIGHT)
                univ.remove(i);
        }
    }

    //player
    public class Player {

        int posX, posY, size;
        boolean exploding, destroyed;
        Image img;
        int explosionStep = 0;

        public Player(int posX, int posY, int size, Image image) {
            this.posX = posX;
            this.posY = posY;
            this.size = size;
            img = image;
        }

        public Shot shoot() {
            return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
        }

        public void update() {
            if(exploding) explosionStep++;
            destroyed = explosionStep > EXPLOSION_STEPS;
        }

        public void draw() {
            if(exploding) {
                gc.drawImage(EXPLOSION_IMG,posX, posY, size, size);
            }
            else {
                gc.drawImage(img, posX, posY, size, size);
            }
        }
        public boolean colide(Player other) {
            int d = distance(this.posX + size / 2, this.posY + size /2,
                    other.posX + other.size / 2, other.posY + other.size / 2);
            return d < other.size / 2 + this.size / 2 ;
        }
        public void explode() {

            Timeline dieAnimation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
                exploding = true;
                explosionStep = -1;

                PLAYER_IMG = EXPLOSION_IMG;
            }));

            dieAnimation.play();
        }
    }
    //computer player
    public class Bomb extends Player {

        int SPEED = (score/5)+2;

        public Bomb(int posX, int posY, int size, Image image) {
            super(posX, posY, size, image);
        }

        public void update() {
            super.update();
            if(!exploding && !destroyed) posX -= SPEED;
            if(posX > WIDTH) destroyed = true;
        }
    }

    //bullets
    public class Shot {

        public boolean toRemove;

        int posX, posY, speed = 10;
        static final int size = 10;

        public Shot(int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
        }

        public void update() {
            posX+=speed;
        }

        public void draw() {
            gc.drawImage(new Image(chosenCharacter.getCharacterAbility()), posX , posY);
            if (score >=50 && score<=70 || score>=120) {
                gc.setFill(Color.YELLOWGREEN);
                speed = 50;
                gc.fillRect(posX-5, posY-10, size+10, size+30);
            } else {
                gc.drawImage(new Image(chosenCharacter.getCharacterAbility()), posX , posY);
            }
        }

        public boolean collide (Player Rocket) {
            int distance = distance(this.posX + size / 2, this.posY + size / 2,
                    Rocket.posX + Rocket.size / 2, Rocket.posY + Rocket.size / 2);
            return distance  < Rocket.size / 2 + size / 2;
        }

    }

    //environment
    public class Universe {
        int posX, posY;
        private int h, w, r, g, b;
        private double opacity;

        public Universe() {
            posX = RAND.nextInt(WIDTH);
            posY = 0;
            w = RAND.nextInt(5) + 1;
            h =  RAND.nextInt(5) + 1;
            r = RAND.nextInt(100) + 150;
            g = RAND.nextInt(100) + 150;
            b = RAND.nextInt(100) + 150;
            opacity = RAND.nextFloat();
            if(opacity < 0) opacity *=-1;
            if(opacity > 0.5) opacity = 0.5;
        }

        public void draw() {
            if(opacity > 0.8) opacity-=0.01;
            if(opacity < 0.1) opacity+=0.01;
            gc.setFill(Color.rgb(r, g, b, opacity));
            gc.fillOval(posX, posY, w, h);
            posY+=20;
        }
    }


    Bomb newBomb() {
        return new Bomb(50 + RAND.nextInt(WIDTH - 100), 50 + RAND.nextInt(HEIGHT - 100), PLAYER_SIZE, BOMBS_IMG[RAND.nextInt(BOMBS_IMG.length)]);
    }

    int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}
