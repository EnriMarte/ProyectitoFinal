package com.example.a42567321.tp7_2;

import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.Toast;

import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCRect;
import org.cocos2d.types.CCSize;

import java.util.Random;

/**
 * Created by 42567321 on 15/8/2017.
 */

public class Game {
    CCGLSurfaceView view;
    CCSize screen;
    Sprite redDot;
    Sprite greenDot;
    boolean greenTouched;
    boolean redTouched;

    public Game(CCGLSurfaceView view){
        this.view = view;
    }

    public void start(){
        Director.sharedDirector().attachInView(view);
        screen = Director.sharedDirector().displaySize();
        Director.sharedDirector().runWithScene(gameScene());
        greenTouched = false;
        redTouched = false;
    }

    private Scene gameScene(){
        Scene scene = Scene.node();

        MainLayer mainLayer = new MainLayer();
        scene.addChild(mainLayer);

        return scene;
    }

    class MainLayer extends Layer {
        public MainLayer(){
            addDots();
            this.setIsTouchEnabled(true);
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            if (isBetween((int)event.getX(),
                    (int)(greenDot.getPositionX()-greenDot.getWidth()/2),
                    (int)(greenDot.getPositionX()+greenDot.getWidth()/2)))
                if (isBetween((int)(screen.getHeight()-event.getY()-greenDot.getHeight()),
                        (int)(greenDot.getPositionY()-greenDot.getHeight()/2),
                        (int)(greenDot.getPositionY()+greenDot.getHeight()/2)))
                    greenTouched = true;

            if (isBetween((int)event.getX(),
                    (int)(redDot.getPositionX()-redDot.getWidth()/2),
                    (int)(redDot.getPositionX()+redDot.getWidth()/2)))
                if (isBetween((int)(screen.getHeight()-event.getY()-redDot.getHeight()),
                        (int)(redDot.getPositionY()-redDot.getHeight()/2),
                        (int)(redDot.getPositionY()+redDot.getHeight()/2)))
                    redTouched = true;
            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            if (greenTouched)
                moveSprite(greenDot, event.getX(), screen.getHeight() - event.getY() - redDot.getHeight());
            if (redTouched)
                moveSprite(redDot, event.getX(), screen.getHeight() - event.getY() - redDot.getHeight());

            if (isColliding(greenDot,redDot)){
                IntervalAction intervalAction = Sequence.actions(MoveBy.action(0.2f, 50f, 0f),
                        MoveBy.action(0.2f, 0f, -50f), MoveBy.action(0.2f, -50f, 0f), MoveBy.action(0.2f, 0f, 50f));
                if (greenTouched) {
                    redDot.runAction(intervalAction);
                } else {
                    greenDot.runAction(intervalAction);
                }
            }

            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            redTouched = false;
            greenTouched = false;
            return true;
        }

        public void addDots(){
            redDot = Sprite.sprite("dot_red.png");
            greenDot = Sprite.sprite("dot_green.png");

            Random rng = new Random();
            do {
                float w = redDot.getWidth() / 2 + rng.nextFloat() * (screen.width - redDot.getWidth() / 2);
                float h = redDot.getHeight() / 2 + rng.nextFloat() * (screen.height - redDot.getHeight() / 2);

                redDot.setPosition(w, h);

                w = greenDot.getWidth() / 2 + rng.nextFloat() * (screen.width - greenDot.getWidth() / 2);
                h = greenDot.getHeight() / 2 + rng.nextFloat() * (screen.height - greenDot.getHeight() / 2);

                greenDot.setPosition(w, h);
            } while (isColliding(redDot, greenDot) || isOutOfBounds(redDot) || isOutOfBounds(greenDot));



            super.addChild(greenDot);
            super.addChild(redDot);
        }

        private boolean isColliding(Sprite sprt1, Sprite sprt2){
            int sprt1_left = (int) (sprt1.getPositionX() - sprt1.getWidth()/2);
            int sprt1_right = (int) (sprt1.getPositionX() + sprt1.getWidth()/2);
            int sprt1_bottom =(int) (sprt1.getPositionY() - sprt1.getHeight()/2);
            int sprt1_top = (int) (sprt1.getPositionY() + sprt1.getHeight()/2);
            int sprt2_left = (int) (sprt2.getPositionX() - sprt2.getWidth()/2);
            int sprt2_right = (int) (sprt2.getPositionX() + sprt2.getWidth()/2);
            int sprt2_bottom = (int) (sprt2.getPositionY() - sprt2.getHeight()/2);
            int sprt2_top = (int) (sprt2.getPositionY() + sprt2.getHeight()/2);

            //Borde izq y borde inf de Sprite 1 está dentro de Sprite 2
            if (isBetween(sprt1_left, sprt2_left, sprt2_right) &&
                    isBetween(sprt1_bottom, sprt2_bottom, sprt2_top)) {
                return true;
            }
            //Borde izq y borde sup de Sprite 1 está dentro de Sprite 2
            if (isBetween(sprt1_left, sprt2_left, sprt2_right) &&
                    isBetween(sprt1_top, sprt2_bottom, sprt2_top)) {
                return true;
            }
            //Borde der y borde sup de Sprite 1 está dentro de Sprite 2
            if (isBetween(sprt1_right, sprt2_left, sprt2_right) &&
                    isBetween(sprt1_top, sprt2_bottom, sprt2_top)) {
                return true;
            }
            //Borde der y borde inf de Sprite 1 está dentro de Sprite 2
            if (isBetween(sprt1_right, sprt2_left, sprt2_right) &&
                    isBetween(sprt1_bottom, sprt2_bottom, sprt2_top)) {
                return true;
            }
            //Borde izq y borde inf de Sprite 2 está dentro de Sprite 1
            if (isBetween(sprt2_left, sprt1_left, sprt1_right) &&
                    isBetween(sprt2_bottom, sprt1_bottom, sprt1_top)) {
                return true;
            }

            //Borde izq y borde sup de Sprite 1 está dentro de Sprite 1
            if (isBetween(sprt2_left, sprt1_left, sprt1_right) &&
                    isBetween(sprt2_top, sprt1_bottom, sprt1_top)) {
                return true;
            }
            //Borde der y borde sup de Sprite 2 está dentro de Sprite 1
            if (isBetween(sprt2_right, sprt1_left, sprt1_right) &&
                    isBetween(sprt2_top, sprt1_bottom, sprt1_top)) {
                return true;
            }
            //Borde der y borde inf de Sprite 2 está dentro de Sprite 1
            if (isBetween(sprt2_right, sprt1_left, sprt1_right) &&
                    isBetween(sprt2_bottom, sprt1_bottom, sprt1_top)) {
                return true;
            }
            return false;
        }

        private boolean isBetween(int number, int smallerNumber, int biggerNumber){
            if (biggerNumber < smallerNumber){
                int aux;
                aux = biggerNumber;
                biggerNumber = smallerNumber;
                smallerNumber = aux;
            }

            if (number >= smallerNumber && number <= biggerNumber)
                return true;
            else
                return false;
        }

        private boolean isOutOfBounds(Sprite sprite) {
            if (sprite.getPositionY() + sprite.getHeight() > screen.height ||
                    sprite.getPositionX() + sprite.getWidth() > screen.width ||
                    sprite.getPositionX() < sprite.getWidth()/2 ||
                    sprite.getPositionY() < sprite.getHeight()/2)
                return true;
            return false;
        }

        private void moveSprite(Sprite sprite, float x, float y){
            sprite.setPosition(x,y);
        }
    }
}