package newGame.Entities;

public enum CharacterType {
    Fighter("Fighter"),
    Ranger("Ranger"),
    Wizard("Wizard");

    public String Type;

    CharacterType(String type) {
        Type = type;
    }
}