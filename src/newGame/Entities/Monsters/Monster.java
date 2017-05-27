package newGame.Entities.Monsters;

import newGame.MainGame;
import newGame.Entities.Character;
import newGame.Entities.Entity;
import newGame.Entities.Shield;
import newGame.Entities.Weapons.Melee;

public abstract class Monster extends Entity {

    private Shield shield;
    private Melee meleeWeapon;
    private boolean FindMode;
    private int Persistance;
    private int FindCount;

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
    }

    public boolean hasRangeWeapon() {
        // TODO: Work on range weapons some time
        return false;
    }

    public boolean hasMeleeWeapon() {
        return meleeWeapon != null;
    }

    @Override
    public void onEntityUpgrade() {
        onMonsterUpgrade();
    }

    public boolean playerInSight(Character character) {
        for(int x = getX();; x++) {
            if(MainGame.csi.peekChar(x, getY()) == '@')
                return true;
            else if(MainGame.csi.peekChar(x, getY()) == 'X')
                break;
        }
        for(int y = getY();; y++) {
            if(MainGame.csi.peekChar(getX(), y) == '@')
                return true;
            else if(MainGame.csi.peekChar(getX(), y) == 'X')
                return false;
        }
    }

    public void findAI(Character character,  char direction /*Direction trying to go*/, int Persistance) {
        if(playerInSight(character)) {
            FindMode = false;
            performAI(character);
        }
        else {
            if(direction == 'L') {
                if(MainGame.csi.peekChar(previewMove(1,0).x, previewMove(1,0).y) == 'X') {
                    move(1,0);
                    direction = 'D';
                }
                else {
                    // go up
                    move(0, -1);
                }
            }
            else if(direction == 'R') {
                if(MainGame.csi.peekChar(previewMove(-1,0).x, previewMove(-1,0).y) == 'X') {
                    move(-1,0);
                    direction = 'U';
                }
                else {
                    // go down
                    move(0, 1);
                }
            }
            else if(direction == 'U') {
                if(MainGame.csi.peekChar(previewMove(0,-1).x, previewMove(0,-1).y) == 'X') {
                    move(0,-1);
                    direction = 'L';
                }
                else {
                    // go right
                    move(-1, 0);
                }
            }
            else if(direction == 'D') {
                if(MainGame.csi.peekChar(previewMove(0,1).x, previewMove(0,1).y) == 'X') {
                    move(0,1);
                    direction = 'R';
                }
                else {
                    // go left
                    move(1, 0);
                }
            }
        }

        if(FindCount >= Persistance)
            FindMode = false;
        else
            FindCount++;
    }

    public void chaseAI(Character c) {
        if(FindMode)
            return;

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
