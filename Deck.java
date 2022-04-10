import java.lang.Throwable;
import java.util.*;

public class Deck
{
    //instance variables
    private ArrayList<Card> deck = new ArrayList<Card>();
    private ArrayList<Integer> indexesUsed = new ArrayList<Integer>();
    Random rand = new Random();
    
    //constants
    private int MINCARDVALUE = 2;
    private int MAXCARDVALUE = 14;
    private int CARDSINDECK = 52;
    
    //non suit cards: 2C, 2D, 2H, 2S, ... 10C, 10D, 10H, 10S
    private int NUMOFNONSUITCARDS = 36;
    private int JINDEX = NUMOFNONSUITCARDS + 4; //index of JC
    private int QINDEX = NUMOFNONSUITCARDS + 2 * 4; //index of QC
    private int KINDEX = NUMOFNONSUITCARDS + 3 * 4; //index of KC
    
    //constructor (fills the deck with 52 cards, each deck object is identical)
    public Deck()
    {
        for(int i = MINCARDVALUE; i <= MAXCARDVALUE; i++)
        {
            //for every card value (i), add one card for each suit
            deck.add(new Card(i, 'C'));
            deck.add(new Card(i, 'D'));
            deck.add(new Card(i, 'H'));
            deck.add(new Card(i, 'S'));
        }
    }
    
    //accessors
    public ArrayList<Card> getDeck()
    {
        return deck;
    }
    
    public ArrayList<Integer> getIndexesUsed()
    {
        return indexesUsed;
    }
    
    /**
     * returns a random card from the deck arraylist
     * each card can only be returned once
     * if 52 cards have already been pulled, then this method throws an 
     * exception because there can only be 52 cards used
     * 
     * @return  the random card
    */
    public Card getRandomCard()
    {
        if(indexesUsed.size() >= CARDSINDECK)
        {
            throw new IllegalStateException("There are no more cards in the deck.");
        }
        
        //finds the index of a card that has not already been used
        int curIndex = rand.nextInt(CARDSINDECK);
        while(indexesUsed.contains(curIndex))
        {
            curIndex = rand.nextInt(CARDSINDECK);
        }
        
        Card randCard = deck.get(curIndex);
        indexesUsed.add(curIndex);
        return randCard;
    }
    
    /**
     * puts the given card back in the deck
     * if the card is already in the deck, nothing happens
     * 
     * @param Card c  the card to be put back in the deck
     * 
    */
    public void putCardBack(Card c)
    {
        int index;
        
        //gets the index for this number with the suit Clubs
        if(c.getStringValue() == "K")
        {
            index = KINDEX;
        }
        else if(c.getStringValue() == "Q")
        {
            index = QINDEX;
        }
        else if(c.getStringValue() == "J")
        {
            index = JINDEX;
        }
        else if(c.getStringValue() == "A")
        {
            index = NUMOFNONSUITCARDS;
        }
        else
        {
            index = (Integer.valueOf(c.getStringValue()) - 2) * 4;
        }
        
        //adds the correct number if the suit is not Clubs
        if(c.getSuit() == 'D')
        {
            index++;
        }
        else if(c.getSuit() == 'H')
        {
            index += 2;
        }
        else if(c.getSuit() == 'S')
        {
            index += 3;
        }
        
        indexesUsed.remove(new Integer(index));
    }
}
