package newGame.Entities.Weapons;

public class Knife extends Melee {

    public static int INIT_DURABILITY = 45;
    public static int HOUSING = 3;
    public static int DEGRADATION = 5;

    public Knife(int idamageOutput, int idamageBonus, int ilevel) {
        super("Knife Level " + ilevel, INIT_DURABILITY, DEGRADATION, HOUSING, idamageOutput, idamageBonus, ilevel);
    }

}
