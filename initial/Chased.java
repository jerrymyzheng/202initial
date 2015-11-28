import greenfoot.*;
import java.awt.Color;

/**
 * Class Chased (subclass of Actor): a user-controlled object with a mouse-controlled gun
 */
public class Chased extends Actor
{
    Gun gun = new Gun(); // the gun for this actor
    
    /**
     * creates image for actor
     */
    public Chased()
    {
        // the image for this actor
        GreenfootImage image = new GreenfootImage(50, 50);
        image.fillOval(0, 0, 50, 50);
        image.setColor(Color.green);
        image.fillOval(7, 7, 36, 36);
        setImage(image);
    }
    
    /**
     * adds the gun for this actor into the world
     *
     * @param world the world this actor was added into
     */
    public void addedToWorld(World world)
    {
        world.addObject(gun, getX(), getY()); // add the gun belonging to this actor into the world
    }

    /**
     * move user-controlled actor and its gun; also, check for game over
     */
    public void act()
    {
        // user-controlled movement
        int dx = 0, dy = 0;
        if (Greenfoot.isKeyDown("a")) dx--;
        if (Greenfoot.isKeyDown("d")) dx++;
        if (Greenfoot.isKeyDown("w")) dy--;
        if (Greenfoot.isKeyDown("s")) dy++;
        setLocation(getX()+3*dx, getY()+3*dy);
        // limit to world bounds
        if (getX() < 25) setLocation(25, getY());
        if (getY() < 25) setLocation(getX(), 25);
        if (getX() > getWorld().getWidth()-25) setLocation(getWorld().getWidth()-25, getY());
        if (getY() > getWorld().getHeight()-25) setLocation(getX(), getWorld().getHeight()-25);
        // position gun
        gun.setLocation(getX(), getY());
        // check game over
        if (!getObjectsInRange(40, Chaser.class).isEmpty())
        {
            World world = getWorld();
            world.removeObjects(world.getObjects(null));
            if (UserInfo.isStorageAvailable())
                Greenfoot.setWorld(new Stats(((Welt)world).score));
            else
                Greenfoot.stop();
        }
    }
    
    /**
     * Class: Gun (subclass of Actor -- inner class of Chased): the gun for this actor
     */
    private class Gun extends Actor
    {
        /**
         * creates image for actor
         */
        public Gun()
        {
            // the image for this actor
            GreenfootImage image = new GreenfootImage(50, 10);
            image.setColor(Color.black);
            for (int i=0; i<4; i++) image.fillRect(20, i, 9+i*4, 10-i*2);
            setImage(image);
        }
            
        /**
         * responds to mouse movement and mouse button clicks
         */
        public void act()
        {
            // turn towards mouse when mouse moves
            if (Greenfoot.mouseMoved(null) || Greenfoot.mouseDragged(null))
            {
                MouseInfo mouse = Greenfoot.getMouseInfo();
                if (mouse != null) turnTowards(mouse.getX(), mouse.getY());
            }
            // detect mouse clicks to fire shot
            if (Greenfoot.mouseClicked(null)) getWorld().addObject(new Shot(), getX(), getY());
        }
        
        /**
         * Class Shot (subclass of QActor -- inner class of Chased.Gun): the shots from this gun
         */
        private class Shot extends QActor
        {
            /**
             * sets bounds fields and creates the image for this actor
             */
            public Shot()
            {
                setBoundedAction(QActor.REMOVE, 5); // set bounds fields
                // create image for this actor
                GreenfootImage image = new GreenfootImage(5, 2);
                image.fill();
                setImage(image);
            }
            
            /**
             * initializes rotation and position of actor
             *
             * @param world the world this actor was added into
             */
            public void addedToWorld(World world)
            {
                setRotation(Gun.this.getRotation()); // set rotation (to that of gun)
                move(Gun.this.getImage().getWidth()/2); // set position (at end of gun)
            }
            
            /**
             * moves actor and checks for removal of actor
             */
            public void act()
            {
                setVX(0);
                setVY(0);
                addForce(500, getRotation()*QVAL);
                move(); // moving (equivalent to 'move(5)' for a non-QActor)
                if (hits(Chaser.class) || atWorldEdge()) getWorld().removeObject(this); // removing
            }
            
            /**
             * internal method to detect object collision; returns true if collision detected, else false
             *
             * @param cls the class of object to check for collision with
             * @return a flag indicating whether an object of given class was detected or not
             */
            private boolean hits(Class cls)
            {
                // get intersecting object and return result
                Actor clsActor = getOneIntersectingObject(cls);
                if (clsActor != null)
                {
                    // remove intersector and bump score
                    getWorld().removeObject(clsActor);
                    ((Welt)getWorld()).adjustScore(1);
                    return true; 
                }
                return false;
            }
            
            /**
             * internal method that returns a flag indicating world edge encroachment
             *
             * @return a flag indicating whether the actor has encroached on a world edge or not
             */
            private boolean atWorldEdge()
            {
                // return state of encroachment on world edge
                return getX() <= 0 || getY() <= 0 ||
                    getX() >= getWorld().getWidth()-1 ||
                    getY() >= getWorld().getHeight()-1;
            }
        }
    }
}