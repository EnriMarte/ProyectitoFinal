package com.example.a42567321.tp8;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;

import com.github.czyzby.noise4j.map.Grid;
import com.github.czyzby.noise4j.map.generator.room.dungeon.DungeonGenerator;

import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ianfr on 14/09/2017.
 */

public class Game {
    CCGLSurfaceView view;
    CCSize screen;
    Sprite playButton, character;
    Grid map;
    int currCharX, currCharY;
    Context context;
    MediaPlayer mediaPlayer;
    ArrayList<Sprite> floor, wall;
    float bpm = 92, bps;
    boolean canMove = false, alreadyMoved = false;
    Label beat = Label.label("O","Verdana",60f), offBeat = Label.label("Off beat!","Verdana",80f);

    public Game(CCGLSurfaceView view, Context context){
        this.view = view;
        this.context = context;
        mediaPlayer = MediaPlayer.create(context, R.raw.whyd_call_me);
    }

    public void start(){
        Director.sharedDirector().attachInView(view);
        screen = Director.sharedDirector().displaySize();
        Log.d("start", "Screen size: " + screen.getWidth() + " width and " + screen.getHeight() + " height");
        Director.sharedDirector().runWithScene(MainMenu());
    }

    /*
            START MAIN MENU
     */
    private Scene MainMenu(){
        Scene scene = Scene.node();

        scene.addChild(new Background(), 0);
        scene.addChild(new Items(), 1);

        return scene;
    }

    class Background extends Layer{

    }

    class Items extends Layer{
        public Items(){
            this.setIsTouchEnabled(true);
            playButton = Sprite.sprite("button_play.png");
            playButton.setPosition(screen.width/ 2, screen.height / 2);
            playButton.setScale(4f);
            super.addChild(playButton);
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            if (event.getX() > playButton.getPositionX() - playButton.getWidth()*2f &&
                    event.getX() < playButton.getPositionX() + playButton.getWidth()*2f){
                if (event.getY() > playButton.getPositionY() - playButton.getHeight()*2f &&
                        event.getY() < playButton.getPositionY() + playButton.getHeight()*2f) {
                    Log.d("playButton", "Oof! Touched!");
                    Director.sharedDirector().runWithScene(Level());
                }
            }

            return true;
        }
    }
    /*
            END MAIN MENU
     */


    /*
            START LEVEL
     */
    private Scene Level(){
        Scene scene = Scene.node();

        scene.addChild(new GameScene(), 0);
        scene.addChild(new GUI(), 1);

        bps = 60/bpm;

        offBeat.setPosition(screen.getWidth() /2,100);

        mediaPlayer.start();

        return scene;
    }

    class GameScene extends Layer{
        public GameScene(){
            createDungeon();
            placeCharacter();

            super.schedule("checkBeat", 0.4552f);

        }

        private void createDungeon(){
            map = new Grid(30);

            DungeonGenerator dungeonGenerator = new DungeonGenerator();
            dungeonGenerator.setRoomGenerationAttempts(100);
            dungeonGenerator.setTolerance(5);
            dungeonGenerator.setMaxRoomSize(9);
            dungeonGenerator.setMinRoomSize(3);
            dungeonGenerator.generate(map);

            int floorCount = 0, wallCount = 0;

            for (int x = 0; x < map.getWidth(); x++){
                for (int y = 0; y < map.getHeight(); y++){
                    if (map.get(x, y) == 1.0){
                        floorCount++;
                    } else{
                        wallCount++;
                    }
                }
            }


            Sprite sprite;

            floor = new ArrayList<>();
            wall = new ArrayList<>();

            for (int i = 0; i <= floorCount; i++){
                sprite = Sprite.sprite("wall.png");
                sprite.setPosition(0,0);
                sprite.scale(4f);
                floor.add(sprite);
            }

            for (int i = 0; i <= wallCount; i++){
                sprite = Sprite.sprite("floor.png");
                sprite.setPosition(0,0);
                sprite.scale(4f);
                wall.add(sprite);
            }

            Log.d("createDungeon", "Counted " + floor.size() + " floor tiles and " +
                    wall.size() + " wall tiles on a " + map.getHeight() + "x" + map.getWidth() + " tile dungeon!");

            int floorIterator = 0, wallIterator = 0;

            for (int x = 0; x < map.getWidth(); x++){
                for (int y = 0; y < map.getHeight(); y++) {
                    if (map.get(x, y) == 1.0){
                        if(x > 0 && y > 0 && x < map.getWidth()-1 && y < map.getHeight()-1)
                        {
                            if(map.get(x+1, y) == 1.0 &&
                                map.get(x, y+1) == 1.0 && map.get(x-1, y) == 1.0 &&
                                map.get(x, y-1) == 1.0 && map.get(x+1, y+1) == 1.0 &&
                                map.get(x-1, y-1) == 1.0 && map.get(x+1, y-1) == 1.0 && map.get(x-1, y+1) == 1.0){
                                Log.d("createDungeon","Wall is surrounded by walls, it will not be printed (" + x + ", " + y + ").");
                            } else{
                                floor.get(floorIterator).setPosition
                                        (x * floor.get(floorIterator).getWidth() * 4f,
                                                y * floor.get(floorIterator).getHeight() * 4f);
                                super.addChild(floor.get(floorIterator));
                            }
                        } else {
                            floor.get(floorIterator).setPosition
                                    (x * floor.get(floorIterator).getWidth() * 4f,
                                            y * floor.get(floorIterator).getHeight() * 4f);
                            super.addChild(floor.get(floorIterator));
                        }
                        floorIterator++;
                    } else{
                        wall.get(wallIterator).setPosition
                                (x*wall.get(wallIterator).getWidth()*4f, y*wall.get(wallIterator).getHeight()*4f);
                        super.addChild(wall.get(wallIterator));
                        wallIterator++;
                    }
                }
            }

            Log.d("createDungeon", "Finished printing!");
        }
        private void placeCharacter(){
            Random rng = new Random();
            int x, y;

            do{
                x = rng.nextInt(map.getWidth());
                y = rng.nextInt(map.getHeight());
            } while(map.get(x, y) == 1.0 || map.get(x+1, y) == 1.0 ||
                    map.get(x, y+1) == 1.0 || map.get(x-1, y) == 1.0 ||
                    map.get(x, y-1) == 1.0 || map.get(x+1, y+1) == 1.0 ||
                    map.get(x-1, y-1) == 1.0 || map.get(x+1, y-1) == 1.0 || map.get(x-1, y+1) == 1.0);

            character = Sprite.sprite("red.png");
            character.setPosition(x*character.getWidth()*4f, y*character.getHeight()*4f);
            character.scale(4f);

            currCharX = (int)(screen.getWidth() / (character.getWidth()*4f))/2;
            currCharY = (int)(screen.getHeight() / (character.getHeight()*4f))/2;

            character.runAction(MoveBy.action(0.0001f, (currCharX-x)*character.getWidth()*4f,
                    (currCharY-y)*character.getHeight()*4f));

            for (int i = 0; i < floor.size(); i++){
                floor.get(i).runAction(MoveBy.action(0.01f, (currCharX-x)*floor.get(i).getWidth()*4f,
                        (currCharY-y)*floor.get(i).getHeight()*4f));
            }
            for (int i = 0; i < wall.size(); i++){
                wall.get(i).runAction(MoveBy.action(0.01f, (currCharX-x)*wall.get(i).getWidth()*4f,
                        (currCharY-y)*wall.get(i).getHeight()*4f));
            }

            currCharY = y;
            currCharX = x;

            super.addChild(character);
        }
        public void checkBeat(float n){
            super.removeAllChildren(false);

            if (canMove) {
                canMove = false;
                super.removeChild(beat, true);

                if (alreadyMoved){
                    alreadyMoved = false;
                }
            }
            else {
                canMove = true;
                beat.setPosition(screen.getWidth() /2,screen.getHeight()/2);
                super.addChild(beat);
            }
        }
    }

    class GUI extends Layer{
        Sprite upArrow, downArrow, leftArrow, rightArrow;

        public GUI(){
            this.setIsTouchEnabled(true);

            upArrow = Sprite.sprite("up.png");
            downArrow = Sprite.sprite("down.png");
            leftArrow = Sprite.sprite("left.png");
            rightArrow = Sprite.sprite("right.png");

            upArrow.setPosition(8*upArrow.getWidth(),12*upArrow.getWidth());
            upArrow.scale(3.5f);
            upArrow.setOpacity(65);
            downArrow.setPosition(8*upArrow.getWidth(),3*upArrow.getWidth());
            downArrow.scale(3.5f);
            downArrow.setOpacity(65);
            leftArrow.setPosition((3.3f*upArrow.getWidth()),(7.5f*upArrow.getWidth()));
            leftArrow.scale(3.5f);
            leftArrow.setOpacity(65);
            rightArrow.setPosition((12.5f*upArrow.getWidth()),(7.5f*upArrow.getWidth()));
            rightArrow.scale(3.5f);
            rightArrow.setOpacity(65);

            super.addChild(upArrow).addChild(downArrow).addChild(leftArrow).addChild(rightArrow);
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            if (event.getX() > upArrow.getPositionX()-upArrow.getWidth()*1.75f &&
                    event.getX() < upArrow.getPositionX()+upArrow.getWidth()*1.75f &&
                    event.getY() > screen.getHeight()-(upArrow.getPositionY()+upArrow.getHeight()*1.75f) &&
                    event.getY() < screen.getHeight()-(upArrow.getPositionY()-upArrow.getHeight()*1.75f)){
                Log.d("ccTouchesEnded", "Up button touched!");
                if (canMove) {
                    if (!alreadyMoved){
                        if (map.get(currCharX, currCharY + 1) != 1.0) {
                            currCharY++;
                            for (int i = 0; i < floor.size(); i++) {
                                floor.get(i).runAction(MoveBy.action(0.1f, 0, floor.get(i).getHeight() * 4f * -1));
                            }
                            for (int i = 0; i < wall.size(); i++) {
                                wall.get(i).runAction(MoveBy.action(0.1f, 0, wall.get(i).getHeight() * 4f * -1));
                            }
                        }

                        alreadyMoved = true;
                    } else {
                        if(!super.getChildren().contains(offBeat)){
                            super.addChild(offBeat);
                        }
                    }
                } else {
                    if(!super.getChildren().contains(offBeat)){
                        super.addChild(offBeat);
                    }
                }
            } else if (event.getX() > rightArrow.getPositionX()-rightArrow.getWidth()*1.75f &&
                    event.getX() < rightArrow.getPositionX()+rightArrow.getWidth()*1.75f &&
                    event.getY() > screen.getHeight()-(rightArrow.getPositionY()+rightArrow.getHeight()*1.75f) &&
                    event.getY() < screen.getHeight()-(rightArrow.getPositionY()-rightArrow.getHeight()*1.75f)){
                Log.d("ccTouchesEnded", "Right button touched!");
                if (canMove) {
                    if (!alreadyMoved) {
                        if (map.get(currCharX + 1, currCharY) != 1.0) {
                            currCharX++;
                            for (int i = 0; i < floor.size(); i++) {
                                floor.get(i).runAction(MoveBy.action(0.1f, floor.get(i).getWidth() * 4f * -1, 0));
                            }
                            for (int i = 0; i < wall.size(); i++) {
                                wall.get(i).runAction(MoveBy.action(0.1f, wall.get(i).getWidth() * 4f * -1, 0));
                            }
                        }

                        alreadyMoved = true;
                    } else {
                        if(!super.getChildren().contains(offBeat)){
                            super.addChild(offBeat);
                        }
                    }
                } else{
                    if(!super.getChildren().contains(offBeat)){
                        super.addChild(offBeat);
                    }
                }
            } else if (event.getX() > downArrow.getPositionX()-downArrow.getWidth()*1.75f &&
                    event.getX() < downArrow.getPositionX()+downArrow.getWidth()*1.75f &&
                    event.getY() > screen.getHeight()-(downArrow.getPositionY()+downArrow.getHeight()*1.75f) &&
                    event.getY() < screen.getHeight()-(downArrow.getPositionY()-downArrow.getHeight()*1.75f)){
                Log.d("ccTouchesEnded", "Down button touched!");
                if (canMove) {
                    if (!alreadyMoved) {
                        if (map.get(currCharX, currCharY - 1) != 1.0) {
                            currCharY--;
                            for (int i = 0; i < floor.size(); i++) {
                                floor.get(i).runAction(MoveBy.action(0.1f, 0, floor.get(i).getHeight() * 4f));
                            }
                            for (int i = 0; i < wall.size(); i++) {
                                wall.get(i).runAction(MoveBy.action(0.1f, 0, wall.get(i).getHeight() * 4f));
                            }
                        }

                        alreadyMoved = true;
                    } else{
                        if(!super.getChildren().contains(offBeat)){
                            super.addChild(offBeat);
                        }
                    }
                } else{
                    if(!super.getChildren().contains(offBeat)){
                        super.addChild(offBeat);
                    }
                }
            } else if (event.getX() > leftArrow.getPositionX()-leftArrow.getWidth()*1.75f &&
                    event.getX() < leftArrow.getPositionX()+leftArrow.getWidth()*1.75f &&
                    event.getY() > screen.getHeight()-(leftArrow.getPositionY()+leftArrow.getHeight()*1.75f) &&
                    event.getY() < screen.getHeight()-(leftArrow.getPositionY()-leftArrow.getHeight()*1.75f)){
                Log.d("ccTouchesEnded", "Left button touched!");
                if (canMove) {
                    if (!alreadyMoved) {
                        if (map.get(currCharX - 1, currCharY) != 1.0) {
                            currCharX--;
                            for (int i = 0; i < floor.size(); i++) {
                                floor.get(i).runAction(MoveBy.action(0.1f, floor.get(i).getWidth() * 4f, 0));
                            }
                            for (int i = 0; i < wall.size(); i++) {
                                wall.get(i).runAction(MoveBy.action(0.1f, wall.get(i).getWidth() * 4f, 0));
                            }
                        }

                        alreadyMoved = true;
                    } else {
                        if(!super.getChildren().contains(offBeat)){
                            super.addChild(offBeat);
                        }
                    }
                } else{
                    if(!super.getChildren().contains(offBeat)){
                        super.addChild(offBeat);
                    }
                }
            }

            return true;
        }
    }
    /*
            END LEVEL
     */
}
