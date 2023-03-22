package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import thegame.GamePanel;
import thegame.KeyHandler;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        
        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues(){
        worldX = gp.tileSize * 35;
        worldY = gp.tileSize * 35;
        speed = 4;
        direction = "down";
    }
    
    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/u1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/u2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/d1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/d2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/l1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/l2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/r1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/r2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void update(){
        
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true || keyH.leftPressed == true){     
            if(keyH.upPressed == true){
                direction = "up";
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }
            
            //check tile collision
            collisonOn = false;
            gp.cChecker.checkTile(this);
            
            //if collision false, player move
            if(collisonOn == false){
                switch(direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            
            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        
        switch(direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
