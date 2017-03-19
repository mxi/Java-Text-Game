package game;

public class PickUp {
	public static void PickUp(String item)
	{
		boolean pickup = true;
		for(int x = 0; x < character.invent; x++)
		{
			if(character.equipment[x].equalsIgnoreCase(item))
			{
				System.out.print(", but you already have one, so you ignore it");
				pickup = false;
				break;
			}
		}
		if(pickup && character.invent < character.equipment.length)
		{
			character.equipment[character.invent] = item;
			character.invent++;
		}
		System.out.println();
	}
}
