import greenfoot.*;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * Class Stats: a world that displays the top five and the nearby scores around the total score of the user
 * 
 * Author: danpost
 * Version: 1.0.1 (modified)
 */
public class Stats extends World
{
    Actor btnWorld, btnPlayer, btnQActor, btnEnemy, btnGame;
    Actor mouseButton;
    /**
     * Constructor Stats: creates the background image that displays all the information
     */
    public Stats(int score)
    {    
        super(600, 600, 1);
        UserInfo me = UserInfo.getMyInfo(); // get info of user
        if (me.getScore() < score) me.setScore(score);
        me.store();
        ArrayList<UserInfo> tops = (ArrayList<UserInfo>) UserInfo.getTop(5); // get top five
        ArrayList<UserInfo> near = (ArrayList<UserInfo>) UserInfo.getNearby(5); // get nearby five
        GreenfootImage bg = getBackground(); // get background image
        bg.setColor(Color.gray);
        bg.fill(); // fills the background with gray for frame
        bg.setColor(new Color(208, 208, 208));
        bg.fillRect(10, 10, getWidth()-20, getHeight()-20); // fills main area with a lighter gray
        // make frames (one for top scores and one for nearby scores)
        bg.setColor(Color.black);
        bg.drawRect(10, 10, getWidth()-21, getHeight()-21);
        bg.drawRect(17, 15, getWidth()/2-25, 540);
        bg.drawRect(getWidth()/2+8, 15, getWidth()/2-25, 540);
        // add left and right header texts
        GreenfootImage leftHead = new GreenfootImage("TOP SCORES", 36, Color.red, new Color(0, 0, 0, 0));
        bg.drawImage(leftHead, getWidth()/4-leftHead.getWidth()/2, 35-leftHead.getHeight()/2);
        GreenfootImage rightHead = new GreenfootImage("NEAR YOU", 36, Color.blue, new Color(0, 0, 0, 0));
        bg.drawImage(rightHead, getWidth()*3/4-rightHead.getWidth()/2, 35-rightHead.getHeight()/2);
        addInfoBlocks(0, tops, me.getUserName());
        addInfoBlocks(1, near, me.getUserName());
        // display current final score
        GreenfootImage scoreImg = new GreenfootImage("SCORE\n"+score, 24, Color.black, new Color(192, 192, 255));
        GreenfootImage scoreDisp = new GreenfootImage(scoreImg.getWidth()+20, scoreImg.getHeight()+10);
        scoreDisp.setColor(new Color(255, 192, 192));
        scoreDisp.fill();
        scoreDisp.setColor(new Color(192, 192, 255));
        scoreDisp.fillRect(5, 5, scoreDisp.getWidth()-11, scoreDisp.getHeight()-11);
        scoreDisp.setColor(Color.red);
        scoreDisp.drawRect(0, 0, scoreDisp.getWidth()-1, scoreDisp.getHeight()-1);
        scoreDisp.drawImage(scoreImg, 10, 5);
        scoreDisp.setColor(Color.blue);
        scoreDisp.drawRect(5, 5, scoreDisp.getWidth()-11, scoreDisp.getHeight()-11);
        bg.drawImage(scoreDisp, 300-scoreDisp.getWidth()/2, 0);
        // add button objects that can be clicked to show source code of classes
        Actor button = null;
        addObject(button = getNewButton("World"), 72, 573); // sets button field and adds button to world
        button.getImage().setTransparency(80); // inactive button image
        addObject(button = getNewButton("Player"), 186, 573); // sets button field and adds button to world
        button.getImage().setTransparency(80); // inactive button image
        addObject(btnQActor = getNewButton("QActor"), 300, 573); // sets button field and adds button to world
        addObject(button = getNewButton("Enemy"), 414, 573); // sets button field and adds button to world
        button.getImage().setTransparency(80); // inactive button image
        addObject(btnGame = getNewButton("New game"), 528, 573); // sets button field and adds button to world
    }
    
    /**
     * Method addInfoBlocks: adds the user info blocks to the background of the world
     *
     * @param side the side of the background image the info blocks created are to be drawn on
     * @param list the list of UserInfo object whose info is to be shown within the block created
     * @param me the name of the current user
     */
    private void addInfoBlocks(int side, List<UserInfo> list, String me)
    {
        for (int i=0; i<list.size(); i++)
        {
            GreenfootImage img = list.get(i).getUserImage(); // get user image
            String user = list.get(i).getUserName(); // get user name
            int score = list.get(i).getScore(); // get user total score
            GreenfootImage block = new GreenfootImage(getWidth()/2-35, 90); // create image block for user info
            // highlight current users info
            if (user.equals(me))
            {
                block.setColor(side==0 ? new Color(255, 192, 192, 224) : new Color(192, 192, 255, 224));
                block.fill();
            }
            // draw frame on block
            block.setColor(side==0 ? Color.red : Color.blue);
            block.drawRect(0, 0, getWidth()/2-36, 89);
            // add user info to block
            String text = ""+list.get(i).getRank()+") "+user;
            GreenfootImage nameImg = new GreenfootImage(text, 24, Color.black, new Color(0, 0, 0, 0));
            block.drawImage(nameImg, 5, 45-nameImg.getHeight()/2);
            img.scale(80, 80);
            block.drawImage(img, getWidth()/2-165, 5);
            GreenfootImage scoreImg = new GreenfootImage(""+score, 24, Color.black, new Color(0, 0, 0, 0));
            block.drawImage(scoreImg, getWidth()/2-40-scoreImg.getWidth(), 45-scoreImg.getHeight()/2);
            // draw block on background image
            getBackground().drawImage(block, 22+side*291, i*100+60);
        }
    }

    /**
     * Method getNewButton: creates an Actor object with a button image with the caption given; the
     * image of the button is given the ability to change intensity as the mouse moves on and off it.
     *
     * @param caption the text to display on the button
     * @return the button Actor object created
     */
    private Actor getNewButton(String caption)
    {
        // create the image for the actor not yet created
        GreenfootImage base = new GreenfootImage(100, 30); // create the base image for the button
        base.fill(); // fill with black (default drawing color) to be used for the frame
        base.setColor(new java.awt.Color(192, 192, 255)); // set drawing color to a light blue
        base.fillRect(3, 3, 94, 24); // fill background of button leaving the outer frame
        GreenfootImage text = new GreenfootImage(caption, 24, java.awt.Color.black, null); // create text image
        base.drawImage(text, 50-text.getWidth()/2, 15-text.getHeight()/2); // draw text image onto base image
        base.setTransparency(160); // adjust the intensity of the image to 'mouse not over' state
        Actor button = new Actor() // create the Actor
        {
            /**
             * Method act (for button Actor): changes intensity of image for mouse hovering action
             */
            public void act()
            {
                if (getImage().getTransparency() < 100) return;
                // gaining mouse hover
                if (mouseButton == null && Greenfoot.mouseMoved(this))
                {
                    mouseButton = this;
                    getImage().setTransparency(255);
                }
                // losing mouse hover
                if (mouseButton == this && Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this))
                {
                    mouseButton = null;
                    getImage().setTransparency(160);
                }
            }
        };
        button.setImage(base); // give button its image
        return button; // return the button Actor object
    }
    
    public void act()
    {
        // if (Greenfoot.mouseClicked(btnWorld)) Greenfoot.setWorld(new TextFileViewer("Welt.txt", this));
        // if (Greenfoot.mouseClicked(btnPlayer)) Greenfoot.setWorld(new TextFileViewer("Chased.txt", this));
        if (Greenfoot.mouseClicked(btnQActor)) Greenfoot.setWorld(new TextFileViewer("QActor.txt", this));
        // if (Greenfoot.mouseClicked(btnEnemy)) Greenfoot.setWorld(new TextFileViewer("Chaser.txt", this));
        if (Greenfoot.mouseClicked(btnGame)) Greenfoot.setWorld(new Welt());
    }
}