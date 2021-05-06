import java.util.Random;
import java.util.Scanner;

public class Blackjack {
	public static void main(String[] args) {
		int seed = Integer.parseInt(args[0]);
			
		Deck deck = new Deck(); //Created the deck.
		deck.shuffle(seed); //Shuffle the deck.
		
		House house = new House();
		Player player = new Player();
		
		int numPlayer = Integer.parseInt(args[1]) - 1;
		Computer[] com = new Computer[numPlayer];
		for(int k = 0; k < numPlayer; k++) {
			com[k]= new Computer();
		}
		
		//push card
		for(int i = 0; i < 2; i++) {
			house.cardList[i] = deck.getCardName1(deck.dealCard());
			player.cardList[i] = deck.getCardName1(deck.dealCard());
			for(int k = 0; k < numPlayer; k++) {
				com[k].cardList[i] = deck.getCardName1(deck.dealCard());
			}
		}
		//print card status
		System.out.println("House: HIDDEN, " + house.cardList[1]);
		System.out.println("Player1: " + player.cardList[0]+", "+player.cardList[1]+" ("+player.getSum()+")");
		for(int k = 0; k < numPlayer; k++)
			System.out.println("Player"+ (k+2) +": " + com[k].cardList[0]+", "+com[k].cardList[1]+" ("+com[k].getSum()+")");
		//house sum == 21 -> Output results immediately
		if(house.getSum() != 21) {
			System.out.print("\n");
			
			//player1 turn
			System.out.println("--- Player1 turn ---");
			int listnum = 2;
			while(true) {
				System.out.print("Player1: ");
				for(int c = 0; c < listnum; c++) {
					if(c==listnum-1)
						System.out.print(player.cardList[c]);
					else
						System.out.print(player.cardList[c]+", ");
				}
				if(player.getSum() > 21)
					System.out.print(" ("+player.getSum()+") - Bust!");
				else
					System.out.print(" ("+player.getSum()+")");
				System.out.print("\n");
				
				if(player.getSum() < 21) {
					Scanner scan = new Scanner(System.in);
					String str = scan.nextLine();
					if(str.equals("Hit")) {
						player.cardList[listnum] = deck.getCardName1(deck.dealCard());
						listnum++;
					}
					else if(str.equals("Stand"))
						break;
				}
				else
					break;
			}
			if(player.getSum() <= 21) {
				System.out.print("Player1: ");
				for(int c = 0; player.cardList[c] != null ; c++) {
					if(player.cardList[c+1] == null)
						System.out.print(player.cardList[c]);
					else
						System.out.print(player.cardList[c]+", ");
				}
				if(player.getSum() > 21)
					System.out.print(" ("+player.getSum()+") - Bust!");
				else
					System.out.print(" ("+player.getSum()+")");
				System.out.print("\n");
			}
			
			//other player turn
			for(int w = 0; w < numPlayer; w++) {
				System.out.print("\n");
				System.out.println("--- Player"+(w+2)+" turn ---");
				listnum = 2;
				while(true) {
					System.out.print("Player"+ (w+2) +": ");
					for(int c = 0; c < listnum; c++) {
						if(c==listnum-1)
							System.out.print(com[w].cardList[c]);
						else
							System.out.print(com[w].cardList[c]+", ");
					}
					if(com[w].getSum() > 21)
						System.out.print(" ("+com[w].getSum()+") - Bust!");
					else
						System.out.print(" ("+com[w].getSum()+")");
					System.out.print("\n");
					if(com[w].getSum() <= 21) {
						int what = com[w].doHitOrStand();
						if(what == 3) {
							com[w].cardList[listnum] = deck.getCardName1(deck.dealCard());
							listnum++;
						}
						else
							break;
					}
					else
						break;
				}
				
				if(com[w].getSum() <= 21) {
					System.out.print("Player"+ (w+2) +": ");
					for(int c = 0; com[w].cardList[c] != null ; c++) {
						if(com[w].cardList[c+1] == null)
							System.out.print(com[w].cardList[c]);
						else
							System.out.print(com[w].cardList[c]+", ");
					}
					if(com[w].getSum() > 21)
						System.out.print(" ("+com[w].getSum()+") - Bust!");
					else
						System.out.print(" ("+com[w].getSum()+")");
					System.out.print("\n");
				}
				
			}
			
			System.out.print("\n\n");
			System.out.println("--- House turn ---");
			listnum = 2;
			while(true) {
				System.out.print("House: ");
				for(int c = 0; c < listnum; c++) {
					if(c==listnum-1)
						System.out.print(house.cardList[c]);
					else
						System.out.print(house.cardList[c]+", ");
				}
				if(house.getSum() > 21)
					System.out.print(" ("+house.getSum()+") - Bust!");
				else
					System.out.print(" ("+house.getSum()+")");
				System.out.print("\n");
				if(house.getSum() <= 21) {
					int what = house.doHitOrStand();
					if(what == 3) {
						house.cardList[listnum] = deck.getCardName1(deck.dealCard());
						listnum++;
					}
					else {
						break;
					}
				}
				else
					break;
			}
			
			if(house.getSum() <= 21) {
				System.out.print("House: ");
				for(int c = 0; house.cardList[c] != null ; c++) {
					if(house.cardList[c+1] == null)
						System.out.print(house.cardList[c]);
					else
						System.out.print(house.cardList[c]+", ");
				}
				if(house.getSum() > 21)
					System.out.print(" ("+house.getSum()+") - Bust!");
				else
					System.out.print(" ("+house.getSum()+")");
				System.out.print("\n");
			}
		}
				
		System.out.print("\n");
		System.out.println("--- Game Results ---");
		
		//result of house
		System.out.print("House: ");
		for(int c = 0; house.cardList[c] != null ; c++) {
			if(house.cardList[c+1] == null)
				System.out.print(house.cardList[c]);
			else
				System.out.print(house.cardList[c]+", ");
		}
		if(house.getSum() > 21)
			System.out.print(" ("+house.getSum()+") - Bust!");
		else
			System.out.print(" ("+house.getSum()+")");
		System.out.print("\n");
		
		//result of player 1
		if(player.getSum() < 21) {
			if(player.getSum() == house.getSum())
				System.out.print("[Draw] Player1: ");
			else if(player.getSum() < house.getSum() && house.getSum() <= 21)
				System.out.print("[Lose] Player1: ");
			else if(house.getSum() > 21 || player.getSum() > house.getSum())
				System.out.print("[Win]  Player1: ");
		}
		else if(player.getSum() == 21) {
			if(player.getSum() == house.getSum())
				System.out.print("[Draw] Player1: ");
			else
				System.out.print("[Win]  Player1: ");
		}
		else {
			System.out.print("[Lose] Player1: ");
		}
		for(int c = 0; player.cardList[c] != null ; c++) {
			if(player.cardList[c+1] == null)
				System.out.print(player.cardList[c]);
			else
				System.out.print(player.cardList[c]+", ");
		}
		if(player.getSum() > 21)
			System.out.print(" ("+player.getSum()+") - Bust!");
		else
			System.out.print(" ("+player.getSum()+")");
		System.out.print("\n");
		
		//result of other player
		for(int w = 0; w < numPlayer; w++) {
			if(com[w].getSum() < 21) {
				if(com[w].getSum() == house.getSum())
					System.out.print("[Draw] Player"+(w+2)+": ");
				else if(com[w].getSum() < house.getSum() && house.getSum() <= 21)
					System.out.print("[Lose] Player"+(w+2)+": ");
				else if(house.getSum() > 21 || com[w].getSum() > house.getSum())
					System.out.print("[Win]  Player"+(w+2)+": ");
			}
			else if(com[w].getSum() == 21) {
				if(com[w].getSum() == house.getSum())
					System.out.print("[Draw] Player"+(w+2)+": ");
				else
					System.out.print("[Win]  Player"+(w+2)+": ");
			}
			else {
				System.out.print("[Lose] Player"+(w+2)+": ");
			}
			
			for(int c = 0; com[w].cardList[c] != null ; c++) {
				if(com[w].cardList[c+1] == null)
					System.out.print(com[w].cardList[c]);
				else
					System.out.print(com[w].cardList[c]+", ");
			}
			if(com[w].getSum() > 21)
				System.out.print(" ("+com[w].getSum()+") - Bust!");
			else
				System.out.print(" ("+com[w].getSum()+")");
			System.out.print("\n");
		}
		
	} // end of main
}//end of blackjack

class Card {
	String cardName;
	
	public Card() {}
	public Card(int theValue, int theSuit) {
		if(theValue > 1 && theValue < 11) {
			if(theSuit == 0) {
				cardName = Integer.valueOf(theValue) + "c";
			}
			else if(theSuit == 1) {
				cardName = Integer.valueOf(theValue) + "h";
			}
			else if(theSuit == 2) {
				cardName = Integer.valueOf(theValue) + "d";
			}
			else if(theSuit == 3) {
				cardName = Integer.valueOf(theValue) + "s";
			}
		}
		else if(theValue == 1){
			if(theSuit == 0) {
				cardName = "A" + "c";
			}
			else if(theSuit == 1) {
				cardName = "A" + "h";
			}
			else if(theSuit == 2) {
				cardName = "A" + "d";
			}
			else if(theSuit == 3) {
				cardName = "A" + "s";
			}
		}
		else { // i == 11,12,13 - J, Q, K
			if(theSuit == 0) {
				if(theValue == 11) {
					cardName = "J" + "c";
				}
				else if(theValue == 12) {
					cardName = "Q" + "c";
				}
				else if(theValue == 13) {
					cardName = "K" + "c";
				}
			}
			else if(theSuit == 1) {
				if(theValue == 11) {
					cardName = "J" + "h";
				}
				else if(theValue == 12) {
					cardName = "Q" + "h";
				}
				else if(theValue == 13) {
					cardName = "K" + "h";
				}
			}
			else if(theSuit == 2) {
				if(theValue == 11) {
					cardName = "J" + "d";
				}
				else if(theValue == 12) {
					cardName = "Q" + "d";
				}
				else if(theValue == 13) {
					cardName = "K" + "d";
				}
			}
			else if(theSuit == 3) {
				if(theValue == 11) {
					cardName = "J" + "s";
				}
				else if(theValue == 12) {
					cardName = "Q" + "s";
				}
				else if(theValue == 13) {
					cardName = "K" + "s";
				}
			}
		}
	}//end of Card constructor
}//end of Card class

class Deck {
	private Card[] deck;
	private int cardsUsed;
	
	public Deck() {
		deck = new Card[52];
		for(int i = 1; i < 14; i++) {
			for(int j = 0; j < 4; j++) {
				deck[4*(i-1)+j] = new Card(i, j);
			}
		}
	}

	public String getCardName1(Card deck1) {
		return deck1.cardName;
	}
	
	
	public void shuffle(int seed) {
		Random random = new Random(seed);
		for(int i = deck.length-1; i > 0; i--) {
			int rand = (int)(random.nextInt(i+1));
			Card temp = deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		cardsUsed = 0;
	}
	public Card dealCard() {
		if (cardsUsed == deck.length)
			throw new IllegalStateException("No cards ard left in the deck.");
		cardsUsed++;
		return deck[cardsUsed - 1];
	}
}

class Hand {
	/* Your code */
	public int cardSum;
	public String[] cardList = new String[10];
	
	public int getSum() {
		int o = 0;
		int checkOne = 0;
		cardSum = 0;
		while(cardList[o] != null) {
			String k2 = cardList[o];
			char k3 = k2.charAt(0);
			int changeNum = Character.getNumericValue(k3);
			if(k2.length() == 3)
				changeNum = 10;
			if(k3 == 'Q' || k3 == 'J' || k3 == 'K')
				changeNum = 10;
			else if(k3 == 'A') {
				checkOne = 1;
				changeNum = 11;
			}
			cardSum = cardSum + changeNum;
			o++;
		}
		
		if(checkOne == 1) {
			if(cardSum > 21)
				cardSum = cardSum - 10;
		}
		
		return cardSum;
	}
	
	
} //Set of cards in your hand

class Computer extends Hand {
	public Computer() {}
	public int doHitOrStand() {
		if(cardSum > 17) {
			System.out.println("Stand");
			return 4;
		}
		else if(cardSum <= 17 && cardSum >= 14){
			Random random = new Random();
			int is_hit = (int)(random.nextInt(2));
			if(is_hit == 1) {
				System.out.println("Hit");
				return 3;
			}
			else {
				System.out.println("Stand");
				return 4;
			}	
		}
		else {
			System.out.println("Hit");
			return 3;
		}
	}
} // Player automatically participates

class Player extends Hand {
	public Player() {}
} //Player you control

class House extends Hand {
	public House() {}
	public int doHitOrStand() {
		if(cardSum >= 17) {
			System.out.println("Stand");
			return 4;
		}
		else{
			System.out.println("Hit");
			return 3;
		}
		
	}
	
}