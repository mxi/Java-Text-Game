package newGame.Entities.Monsters;

import newGame.MainGame;
import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Melee;

public abstract class Monster extends Entity {

    private Shield shield;
    private Melee meleeWeapon;

    protected int findPersistence = 15;
    protected int findCount = 0;
    protected int findDirection = 0;
    protected boolean findMode = false;

    public Monster() {

    }

    public Shield getShield() {
        return shield;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public Melee getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(Melee meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
        meleeWeapon.setOwner(this);
    }

    public boolean hasRangeWeapon() {
        return false;
    }

    public boolean hasMeleeWeapon() {
        return meleeWeapon != null;
    }

    @Override
    public void onEntityUpgrade() {
        onMonsterUpgrade();
    }

    /*
    up 0 (0,-1)  goes right
    right 1 (1,0) goes down
    down 2 (0,1) goes left
    left 3 (-1,0) goes up
     */
    
    public void findAI(Character character) {
        if(inSight(character)) {
            findMode = false;
            findCount = 0;
            performAI(character);
        }
        else {
            if(findDirection == 3) { //left
                if(MainGame.csi.peekChar(previewMove(-1,0).x, previewMove(-1,0).y) == '.') {
                    // go left
                    move(-1,0);
                    findDirection = 2;
                }
                else if(MainGame.csi.peekChar(previewMove(0,-1).x, previewMove(0,-1).y) == 'X')
                {
                    // try up
                    findDirection = 0;
                }
                else {
                    // go up
                    move(0, -1);
                }
            }
            else if(findDirection == 1) { //right
                if(MainGame.csi.peekChar(previewMove(1,0).x, previewMove(1,0).y) == '.') {
                    // go right
                    move(1,0);
                    findDirection = 0;
                }
                else if(MainGame.csi.peekChar(previewMove(0,1).x, previewMove(0,1).y) == 'X')
                {
                    // try down
                    findDirection = 2;
                }
                else {
                    // go down
                    move(0, 1);
                }
            }
            else if(findDirection == 0) { //up
                if(MainGame.csi.peekChar(previewMove(0,-1).x, previewMove(0,-1).y) == '.') {
                    // go up
                    move(0,-1);
                    findDirection = 3;
                }
                else if(MainGame.csi.peekChar(previewMove(1,0).x, previewMove(1,0).y) == 'X')
                {
                    // try right
                    findDirection = 1;
                }
                else {
                    // go right
                    move(1, 0);
                }
            }
            else if(findDirection == 2) { //down
                if(MainGame.csi.peekChar(previewMove(0,1).x, previewMove(0,1).y) == '.') {
                    // go down
                    move(0,1);
                    findDirection = 1;
                }
                else if(MainGame.csi.peekChar(previewMove(-1,0).x, previewMove(-1,0).y) == 'X')
                {
                    // try left
                    findDirection = 3;
                }
                else {
                    // go left
                    move(-1, 0);
                }
            }
        }

        if(findCount >= findPersistence)
        {
            findMode = false;
        	findCount = 0;
        }
        else
            findCount++;
    }

    public void chaseAI(Character c) {
        if(findMode)
            return;

        if(!inSight(c)) {
            findMode = true;
            return;
        }

        int deltaX = getX() - c.getX();
        int deltaY = getY() - c.getY();

        int absX = Math.abs(deltaX);
        int absY = Math.abs(deltaY);

        if(absX > absY) {
            if (MainGame.map.getCharacter(previewMove(deltaX > 0 ? -1 : 1, 0)) != 'X')
                move(deltaX > 0 ? -1 : 1, 0);
            else
                move(0, deltaY > 0 ? -1 : 1);
        } else if(absY > absX) {
            if(MainGame.map.getCharacter(previewMove(0, deltaY > 0 ? -1 : 1)) != 'X')
                move(0, deltaY > 0 ? -1 : 1);
            else
                move(deltaX > 0 ? -1 : 1, 0);
        }
        else {
            if(MainGame.random.nextInt(1) == 0) {
                if (MainGame.map.getCharacter(previewMove(deltaX > 0 ? -1 : 1, 0)) != 'X')
                    move(deltaX > 0 ? -1 : 1, 0);
                else
                    move(0, deltaY > 0 ? -1 : 1);
            }
            else {
                if(MainGame.map.getCharacter(previewMove(0, deltaY > 0 ? -1 : 1)) != 'X')
                    move(0, deltaY > 0 ? -1 : 1);
                else
                    move(deltaX > 0 ? -1 : 1, 0);
            }
        }
    }

    protected abstract void onMonsterUpgrade();

    public abstract void performAI(Character character);
}
