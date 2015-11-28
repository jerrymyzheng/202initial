import greenfoot.*;

public class Chaser extends QActor
{
    int dr = 0; // the current turn rate
    
    public Chaser()
    {
        setBoundedAction(Welt.actionType, Welt.actionDistance); // set bound fields
        // create image for this actor
        GreenfootImage image = new GreenfootImage(30, 30);
        image.fillOval(0, 0, 30, 30);
        image.setColor(new java.awt.Color(224, 240, 255));
        image.fillOval(7, 7, 16, 16);
        setImage(image);
    }

    /**
     * turn and move actor
     */
    public void act()
    {
        int dx = 0, dy = 0; // the differences in locational coordinates
        int rate = 0; // the rate of turn and speed
//         Actor chased = Welt.chased;
//         if (chased != null && chased.getWorld() != null)
//         { // set variable values
//             dx = getX()-chased.getX();
//             dy = getY()-chased.getY();
//             rate = 400-(int)Math.abs(dx)-(int)Math.abs(dy);
//         }
        if (rate > 100)
        { // chase
            // the next code line does what is described in the following commented lines
               // determine shortest turn direction to face chased
            // int angleDiff = getRotation()-(int)(Math.atan2(dy, dx)*180/Math.PI); // range: -359 to 359
            // int anglet = (angleDiff+360+180)%360-180; // range: -180 to 179 (sign is turn direction)
               // turn and move
            // turn(16*rate*(int)Math.signum(anglet)); 
            turn(16*rate*(int)Math.signum((getRotation()-(int)(Math.atan2(dy, dx)*180/Math.PI)+540)%360-180));
            move(rate/8, getQR()); // between 'move(0.1)' to 'move(0.4)' for a non-QActor, which is not possible
        }
        else
        { // wander
            // vary turn rate
            dr += 2-Greenfoot.getRandomNumber(5)-(int)Math.signum(dr);
            // limit turn rate to between 40 q-rotation units left and right
            if (dr < -40) dr = -40;
            if (dr > 40) dr = 40;
            // turn and move
            turn(dr);
            move(10);
        }
    }
}