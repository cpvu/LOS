package src.los.game;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
import src.los.common.MapStages;
import src.los.common.PlayerClass;
import src.los.controller.DialogueController;
import src.los.controller.SceneController;
import javafx.scene.Scene;

public class SpaceDriver {
    //variables
    private static final Random RAND = new Random();
    public static PlayerClass chosenCharacter;
    public static MapStages currentLevel = MapStages.LEVEL_THREE;
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
    public static Timeline gameTimeline;

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
    Boss boss;
    List<Shot> shots;
    List<Universe> univ;
    List<Bomb> Bombs;

    private double mouseX;
    private int score;

    @FXML
    Label scoreLabel;

    //start
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

    public void startNewStage() {
        Bombs.clear();
        shots.clear();
        gameTimeline.playFromStart();
        addBombs();
    }

    //setup the game
    private void setup() {
        univ = new ArrayList<>();
        shots = new ArrayList<>();
        Bombs = new ArrayList<>();
        boss = new Boss(600, 100, PLAYER_SIZE, new Image("SasukeSprite.png"));
        PLAYER_IMG = new Image(chosenCharacter.getBaseImage());
        player = new Player(0, HEIGHT / 2, PLAYER_SIZE, PLAYER_IMG);
        addBombs();
    }

    private void addBombs() {
        IntStream.range(0, MAX_BOMBS).mapToObj(i -> this.newBomb()).forEach(Bombs::add);
    }

    private void updateScore(int score) throws IOException {
        Scene gameStage = SceneController.getInstance().gameStage;
        scoreLabel = (Label) gameStage.lookup("#scoreLabel");
        scoreLabel.setText("" + score);
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
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", WIDTH / 2, HEIGHT /2.5);
            //	return;
        }

        univ.forEach(Universe::draw);

        player.update();
        player.draw();
        player.posY = (int) mouseX;

        if (currentLevel == MapStages.LEVEL_THREE) {
            Random rand = new Random();
            boss.draw();
            boss.posY = boss.posY + rand.nextInt(-40, 40);
            boss.bossBombs.add(boss.bossShot());

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
                for (Bomb bomb : Bombs) {
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

            if (boss.bossHP == 0) {
                System.out.println("Dead!!!1");
            }
        } else {
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
            if (RAND.nextInt(10) > 2) {
                univ.add(new Universe());
            }
            for (int i = 0; i < univ.size(); i++) {
                if (univ.get(i).posY > HEIGHT)
                    univ.remove(i);
            }

            if (score >= currentLevel.getNumberOfEnemies()) {
                gc.clearRect(0, 0, WIDTH, HEIGHT);
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, WIDTH, HEIGHT);

                switch (currentLevel) {
                    case LEVEL_ONE:
                        currentLevel = MapStages.LEVEL_TWO;
                        break;
                    case LEVEL_TWO:
                        currentLevel = MapStages.LEVEL_THREE;
                        break;
                }
                gameTimeline.stop();

                SceneController.getInstance().showDialogue(currentLevel);
                SceneController.getInstance().loadGameBg(currentLevel);
                startNewStage();
            }
        }
    }

    public class Boss extends Player {
        int posX;
        int posY;
        int bossHP = 5;
        ArrayList<BossShots> bossBombs = new ArrayList<>();

        Image bossAttack = new Image("Fireball.png");
        class BossShots extends Bomb {
            int SPEED = (score/5) + 2;
            public BossShots(int posX, int posY, int size, Image image) {
                super(posX, posY, size, image);
            }
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

        @Override
        public void draw() {
            gc.drawImage(new Image("SasukeSprite.png"), posX, posY, PLAYER_SIZE, PLAYER_SIZE);
        }

        public BossShots bossShot() {
            return new BossShots(posX - 5, posY, 10, bossAttack);
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

        int SPEED = (score/5) + 2;

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
            posX += speed;
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
        return new Bomb(600 + RAND.nextInt(WIDTH - 200), 20 + RAND.nextInt(HEIGHT - 30), PLAYER_SIZE, BOMBS_IMG[RAND.nextInt(BOMBS_IMG.length)]);
    }

    int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}
