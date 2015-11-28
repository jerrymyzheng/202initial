import greenfoot.*;
import java.awt.Color;
import java.awt.Font;

public class Welt extends World
{
    public static int actionType, actionDistance; // the world bounds action fields for the chasers
    public static Chased chased; // the player
    
    public int score; // the score
    
    public Welt()
    {
        super(800, 600, 1, false);
        Greenfoot.setSpeed(60);
        // create background texts
        GreenfootImage bg = getBackground();
        bg.setFont(new Font("SERIF", Font.BOLD, 28));
        bg.setColor(Color.black);
        bg.drawString("Score:", 600, 40);
        adjustScore(0);
        bg.setColor(Color.gray);
        bg.setFont(new Font("SERIF", Font.BOLD, 32));
        bg.drawString("Use mouse to aim gun and fire shots", 50, 400);
        bg.drawString("Use w-a-s-d keys to move player", 50, 430);
        bg.drawString("Press 'e' to change edge action", 50, 480);
        bg.drawString("Log onto site for high scores and code viewing", 50, 510);
        bg.drawString("Code view buttons at bottom of high score screen", 50, 540);
        bg.drawString("Press 'x' or 'escape' to return from code viewing", 50, 570);
        bg.setFont(new Font("SERIF", Font.BOLD, 36));
        bg.drawString("EDGE ACTION: ", 50, 44);
        // create player and reset game
        chased = new Chased();
        setAction(0);
    }
    
    public void act()
    {
        // changing edge action
        String key = Greenfoot.getKey();
        if (key != null)
        {
            if ("e".equals(key)) setAction(actionType+1);
            if ("q".equals(key)) Greenfoot.setWorld(new TextFileViewer("QActor.txt", this));
        }
        // adding chasers to world
        // if (chased.getWorld() == null) return;
        if (numberOfObjects() >= 20+score/10) return;
        if (Greenfoot.getRandomNumber(1000) > 30+score/10) return;
        int x = Greenfoot.getRandomNumber(getWidth());
        int y = Greenfoot.getRandomNumber(getHeight());
        int rand = Greenfoot.getRandomNumber(4);
        if (rand < 2) x = (getWidth()-3+actionDistance*2)*rand+1-actionDistance;
        else y = (getHeight()-3+actionDistance*2)*(rand-2)+1-actionDistance;
        // if (Math.abs(x-chased.getX())+Math.abs(y-chased.getY()) < 200) return;
        Chaser chaser = new Chaser();
        addObject(chaser, x, y);
        chaser.turnTowards(400, 300);
    }
    
    public void adjustScore(int adjustment)
    {
        score += adjustment; // adjust score
        // adjust score display
        GreenfootImage bg = getBackground();
        bg.setColor(Color.white);
        bg.fillRect(684, 15, 100, 30);
        bg.setColor(Color.black);
        bg.setFont(new Font("SERIF", Font.BOLD, 28));
        bg.setColor(Color.black);
        bg.drawString(""+score, 684, 40);
    }
    
    private void setAction(int action)
    {
        removeObjects(getObjects(Chaser.class)); // remove old chasers
        actionType = action%5; // set bound action
        int[] distances = { 0, 0, -10, 0, -15 }; // bound ranges
        actionDistance = distances[actionType]; // the range for this action
        String[] texts = { "UNBOUND", "LIMIT", "REMOVAL", "WRAPPING", "BOUNCE" }; // the bounds text
        // adjust bounds display
        GreenfootImage bg = getBackground();
        bg.setFont(new Font("SERIF", Font.BOLD, 36));
        bg.setColor(Color.white);
        bg.fillRect(350, 0, 200, 50);
        bg.setColor(Color.gray);
        bg.drawString(texts[actionType], 350, 44);
        // reset score
        score = 0;
        // ensure player is in world
        if (chased.getWorld() == null) addObject(chased, 400, 300);
    }

    public void getActorCounts()
    {
        java.util.List<Object> objects = getObjects(null);
        int n = 0;
        while (!objects.isEmpty())
        {
            String name = objects.get(n).getClass().getName();
            int count = 1;
            int index = 1;
            while (index < objects.size())
            {
                if (name.equals(objects.get(index).getClass().getName()))
                {
                    count++;
                    objects.remove(index);
                }
                else index++;
            }
            System.out.println(name+": "+count);
            objects.remove(0);
        }
    }
}