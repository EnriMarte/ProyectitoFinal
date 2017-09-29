package prgramacion.tp6;

import android.util.Log;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

/**
 * Created by ianfr on 01/08/2017.
 */

public class Game {
    CCGLSurfaceView view;
    CCSize screen;
    Sprite dot;

    public Game(CCGLSurfaceView view){
        this.view = view;
    }

    public void start(){
        Director.sharedDirector().attachInView(view);
        screen = Director.sharedDirector().displaySize();

        Log.d("Se encontro dispositivo", "Ancho: " + screen.width + " | Alto: " + screen.height);

        Director.sharedDirector().runWithScene(gameScene());
    }

    private Scene gameScene(){
        Scene scene;
        scene = Scene.node();

        MainLayer mainLayer = new MainLayer();
        scene.addChild(mainLayer);

        return scene;
    }

    class MainLayer extends Layer{
        public MainLayer(){
            addDot(5f);
            super.schedule("addDot", 3f);
        }

        public void addDot(float deltaTime){
            dot = Sprite.sprite("dot.png");

            // For a random value within a range, the formula is
            // min + random * (max - min);
            // Sacado de https://stackoverflow.com/questions/40431966/what-is-the-best-way-to-generate-a-random-float-value-included-into-a-specified
            Random rng = new Random();
            float w = dot.getWidth()/2 + rng.nextFloat() * (screen.width - dot.getWidth()/2);
            float h = dot.getHeight()/2 + rng.nextFloat() * (screen.height - dot.getHeight()/2);

            CCPoint initialPos = new CCPoint();
            initialPos.y = h;
            initialPos.x = w;

            dot.setPosition(initialPos.x, initialPos.y);
            dot.runAction(MoveTo.action(2.5f, getX(), getY()));

            super.addChild(dot);
        }

        // "Divide la pantalla a la mitad y se fija de que costado está más cerca"
        // Si está mas cerca del lado derecho devuelve x = 0 (comienzo del eje x)
        float getX(){
            if (screen.width - dot.getPositionX() < screen.width / 2)
                return 0f;
            else
                return screen.width;
        }

        // Igual que el de arriba pero para la altura
        float getY(){
            if (screen.height - dot.getPositionY() < screen.height / 2)
                return 0f;
            else
                return screen.height;
        }
    }
}
