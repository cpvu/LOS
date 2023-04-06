package src.los.game;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import src.los.common.MapStages;
import src.los.common.PlayerClass;
import src.los.controller.SceneController;
import javafx.scene.Scene;

/**
 * The game driver class that has all the necessary inner class.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public class SpaceDriver {
    //variables
    private static final Random RAND = new Random();
    public static PlayerClass chosenCharacter;
    public static MapStages currentLevel = MapStages.LEVEL_ONE;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 370;
    private static final int PLAYER_SIZE = 60;
    public Image PLAYER_ALIVE = new Image(chosenCharacter.getBaseImage());
    public final Image PLAYER_DEAD = new Image(chosenCharacter.getDeadImage());
    static final int EXPLOSION_STEPS = 15;
    public static Timeline gameTimeline;
    static ArrayList<String> enemyImages;
    static final Image ENEMIES_IMG[] = {
            new Image("Enemy2.png"),
            new Image("Enemy1.png"),
            new Image("Enemy3.png"),
            new Image("Enemy3.png")
    };

    final int MAX_ENEMIES = 8,  MAX_SHOTS = MAX_ENEMIES * 3;
    boolean gameOver = false;
    private GraphicsContext gc;

    Player player;
    Boss boss;
    List<Shot> shots;
    List<Universe> univ;
    List<Bomb> Bombs;

    private double mouseX;
    private int score;
    @FXML
    Label scoreLabel;
    /**
     * Creates a new Canvas object with a specified width and height,
     * sets the GraphicsContext2D object to the canvas object, creates a Timeline object with an indefinite cycle count,
     * and sets the cursor to move and the mouse listener to register the mouse movement and mouse click events.
     * @return Returns a Canvas object that represents the game scene.
     */
    public Canvas initializeGameScene() {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gameTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            try {
                run(gc);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }));
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        gameTimeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getY());
        canvas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    gameTimeline.stop();
                    score = 0;
                    SceneController.getInstance().showMainMenu();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
        Platform.runLater(canvas::requestFocus);

        canvas.requestFocus();
        canvas.setOnMouseClicked(e -> {
            if(shots.size() < MAX_SHOTS) shots.add(player.shoot());
            if(gameOver) {
                gameOver = false;
                score = 0;
                currentLevel = MapStages.LEVEL_ONE;
                try {
                    SceneController.getInstance().loadGameBg(currentLevel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                setup();
            }
        });
        setup();
        return canvas;
    }

    /**
     * Starts a new game stage.
     */
    private void startNewStage() {
        Bombs.clear();
        shots.clear();
        gameTimeline.playFromStart();
        addBombs();
    }

    // set up the necessary data structure to hold the objects.
    private void setup() {
        univ = new ArrayList<>();
        shots = new ArrayList<>();
        Bombs = new ArrayList<>();
        enemyImages = currentLevel.getEnemyImages();
        PLAYER_ALIVE = new Image(chosenCharacter.getBaseImage());
        boss = new Boss(600, HEIGHT / 2, PLAYER_SIZE + 20, new Image("Boss.png"));
        player = new Player(0, HEIGHT / 2, PLAYER_SIZE, PLAYER_ALIVE);
        addBombs();
        gameTimeline.playFromStart();
    }

    //add enemy.
    private void addBombs() {
        IntStream.range(0, MAX_ENEMIES).mapToObj(i -> this.newBomb()).forEach(Bombs::add);
    }
    //Update the score.
    private void updateScore(int score) throws IOException {
        Scene gameStage = SceneController.getInstance().getGameStage();
        scoreLabel = (Label) gameStage.lookup("#scoreLabel");
        scoreLabel.setText("" + score);
    }
    private void victory() throws IOException {
        SceneController.getInstance().showVictory();
    }
    private void bossFight() throws IOException {
        Random rand = new Random();
        boss.draw();
        if (boss.posY < 60) {
            boss.posY = boss.posY + 30;
        } else if (boss.posY > HEIGHT - 60) {
            boss.posY = boss.posY - 30;
        } else {
            boss.posY = boss.posY + rand.nextInt(0,80) - 40;
        }


        if (rand.nextInt(20) < 2 ) {
            boss.bossBombs.add(boss.bossShot());
        }

        for (int i = boss.bossBombs.size() - 1; i >= 0; i--) {
            if (boss.bossBombs.get(i).destroyed) {
                boss.bossBombs.remove(i);
            }
        }

        boss.bossBombs.stream().peek(Player::update).peek(Player::draw).forEach(e -> {
            if (player.colide(e) && !player.exploding) {
                player.explode();
            }
        });

        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw();

            for (Boss.BossShots bomb : boss.bossBombs) {
                if (shot.collide(bomb)) {
                    bomb.explode();
                    shot.toRemove = true;
                }
            }
            if (shot.collide(boss)) {
                boss.bossHP -= 1;
                shot.toRemove = true;
            }
        }
        for (int i = boss.bossBombs.size() - 1; i >= 0; i--) {
            if (boss.bossBombs.get(i).destroyed) {
                boss.bossBombs.remove(i);
            }
        }
        if (boss.bossHP == 0) {
            boss.explode();
        }
        gameOver = player.destroyed;
    }

    private void regularFight() {
        Bombs.stream().peek(Player::update).peek(Player::draw).forEach(e -> {
            if (player.colide(e) && !player.exploding) {
                player.explode();
            }
        });

        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw();
            for (Bomb bomb : Bombs) {
                if (shot.collide(bomb) && !bomb.exploding) {
                    score++;
                    bomb.explode();
                    shot.toRemove = true;
                }
            }
        }
        for (int i = Bombs.size() - 1; i >= 0; i--) {
            if (Bombs.get(i).destroyed) {
                Bombs.set(i, newBomb());
            }
        }
        gameOver = player.destroyed;
    }

    //run Graphics
    private void run(GraphicsContext gc) throws IOException {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.TRANSPARENT);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);

        updateScore(score);

        if(gameOver) {
            gc.setFill(Color.WHITE);
            double rectWidth = WIDTH - 200; // Set the width of the rectangle to WIDTH - 200
            double rectHeight = HEIGHT - 200; // Set the height of the rectangle to HEIGHT - 200
            double rectX = (WIDTH - rectWidth) / 2.0; // Calculate the x coordinate for centering the rectangle horizontally
            double rectY = (HEIGHT - rectHeight) / 2.0; // Calculate the y coordinate for centering the rectangle vertically
            gc.fillRect(rectX, rectY, rectWidth, rectHeight); // Draw the centered rectangle
            gc.setFont(Font.font("Ninja Naruto",24));
            gc.setFill(Color.BLACK);
            gc.drawImage(new Image(chosenCharacter.getGameOverImage()), 120, 100);
            gc.fillText("You've died.. \n Your Score is: " + score + " \n Click to play again", WIDTH / 2 + 25, HEIGHT / 2.5 + 25);
            boss.bossBombs.clear();
            Bombs.clear();
            gameTimeline.pause();
        }

        univ.forEach(Universe::draw);

        player.update();
        player.draw();
        player.posY = (int) mouseX;

        if (currentLevel == MapStages.LEVEL_THREE) {
            bossFight();
        } else {
            regularFight();

            if (score >= currentLevel.getNumberOfEnemies()) {
                gc.clearRect(0, 0, WIDTH, HEIGHT);
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, WIDTH, HEIGHT);

                switch (currentLevel) {
                    case LEVEL_ONE:
                        currentLevel = MapStages.LEVEL_TWO;
                        enemyImages = currentLevel.getEnemyImages();
                        break;
                    case LEVEL_TWO:
                        currentLevel = MapStages.LEVEL_THREE;
                        enemyImages = currentLevel.getEnemyImages();
                        break;
                }
                gameTimeline.stop();

                SceneController.getInstance().showDialogue(currentLevel);
                SceneController.getInstance().loadGameBg(currentLevel);
                startNewStage();
            }
        }
    }

    /**
     * The Boss class represents the enemy boss object in a game. It extends the Player class and
     * includes additional attributes such as position, hit points, attack counter, and a collection
     * of BossShots objects.
     */
    public class Boss extends Player {
        int posX;
        int posY;
        int bossHP = 5;
        int bossAttackCounter = 0;
        ArrayList<BossShots> bossBombs = new ArrayList<>();
        Image bossAttack = new Image("BossAttack.png");
        /**
         * The BossShots class represents the shots fired by the boss. It extends the Bomb class and
         * overrides its update() method.
         */
        class BossShots extends Bomb {
            int SPEED = (score / 8) + 1;
            public BossShots(int posX, int posY, int size, Image image) {
                super(posX, posY, size, image);
            }

            /**
             * Update the boss shots status.
             */
            @Override
            public void update() {
                super.update();
                if(!exploding && !destroyed) posX -= SPEED;
                if(posX > WIDTH) destroyed = true;
            }
        }
        public Boss(int posX, int posY, int size, Image image) {
            super(posX, posY, size, image);
            this.posX = posX;
            this.posY = posY;
        }

        /**
         * Draws the boss on the game canvas.
         */
        @Override
        public void draw() {
            gc.drawImage(new Image("Boss.png"), posX, posY, PLAYER_SIZE, PLAYER_SIZE);
        }

        /**
         * Creates and returns a new BossShots object at the appropriate position.
         * @return a new BossShots object
         */
        public BossShots bossShot() {
            return new BossShots(posX - 10, posY, 30 , bossAttack);
        }

        /**
         * Change the image if enemy dead, and set the explode status to true.
         */
        @Override
        public void explode() {
            Timeline dieAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
                exploding = true;
                explosionStep = -1;

                gc.drawImage(new Image("PainDead.png"), posX, posY, PLAYER_SIZE, PLAYER_SIZE);
            }));
            dieAnimation.play();
        }
    }

    /**
     * The Player class represents a player object in a game. It contains information about the player's position, size,
     * image, and whether the player is exploding or destroyed.
     */
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

        /**
         * Creates a new shot object.
         * @return A new shot object.
         */
        public Shot shoot() {
            return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
        }

        /**
         * Updates the state of the player.
         */
        public void update() {
            if(exploding) explosionStep++;
            destroyed = explosionStep > EXPLOSION_STEPS;
        }

        /**
         * Draws the player.
         */
        public void draw() {
            if(exploding) {
                gc.drawImage(PLAYER_DEAD,posX, posY, size, size);
            }
            else {
                gc.drawImage(img, posX, posY, size, size);
            }
        }

        /**
         * Checks if the player collides with another player.
         * @param other The other player to check for collision.
         * @return True if the players collide, false otherwise.
         */
        public boolean colide(Player other) {
            int d = distance(this.posX + size / 2, this.posY + size /2,
                    other.posX + other.size / 2, other.posY + other.size / 2);
            return d < other.size / 2 + this.size / 2 ;
        }

        /**
         * Starts the explosion animation.
         */
        public void explode() {

            Timeline dieAnimation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
                exploding = true;
                explosionStep = -1;

                PLAYER_ALIVE = PLAYER_DEAD;
            }));
            dieAnimation.play();
        }
    }

    /**
     * The Bomb class is a subclass of the Player class and represents a bomb that is dropped by the player in a game.
     * It moves across the screen from right to left until it reaches the left edge of the screen or explodes upon impact with an enemy.
     */
    public class Bomb extends Player {

        int SPEED = (score/5) + 2;

        public Bomb(int posX, int posY, int size, Image image) {
            super(posX, posY, size, image);
        }

        /**
         * Updates the bomb's position on the screen.
         */
        public void update() {
            super.update();
            if(!exploding && !destroyed) posX -= SPEED;
            if(posX > WIDTH) destroyed = true;
        }

        /**
         * Draws the bomb on the screen.
         */
        @Override
        public void draw() {
            if(exploding) {
                gc.drawImage(new Image("enemyDead.png"),posX, posY, size, size);
            }
            else {
                gc.drawImage(img, posX, posY, size, size);
            }
        }
        @Override
        public void explode() {
            exploding = true;
        }
    }

    /**
     * The Shot class represents a shot that can be fired by a character in a game.
     * It has a position on the game screen, a speed, and a size.
     */
    public class Shot {

        public boolean toRemove;

        int posX, posY, speed = 10;
        static final int size = 10;

        public Shot(int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
        }

        /**
         * Update the bullet.
         */
        public void update() {
            posX += speed;
        }

        /**
         * Draw the bullet in the canvas.
         */
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

        /**
         * Determines whether the shot has collided with the player's rocket.
         * @param Rocket the player's rocket
         * @return true if the shot has collided with the player's rocket, false otherwise
         */
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

    /**
     * Create enemy object.
     * @return enemy object
     */
    Bomb newBomb() {
        int enemyImageSize = enemyImages.size();
        int randomizer = RAND.nextInt(enemyImageSize);

        return new Bomb(600 + RAND.nextInt(WIDTH - 200),
                20 + RAND.nextInt(HEIGHT - 30),
                PLAYER_SIZE,
                new Image (enemyImages.get(randomizer)));
    }

    /**
     *Calculates the Euclidean distance between two points in 2D space.
     * @param x1 The x-coordinate of the first point.
     * @param y1 The y-coordinate of the first point.
     * @param x2 The x-coordinate of the second point.
     * @param y2 The y-coordinate of the second point.
     * @return The distance between the two points.
     */
    int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}