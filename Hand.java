import java.util.*;

public class Hand
{
    //variables
    private ArrayList<Card> curHand;
    
    //constants
    private int HANDLIMIT = 21;
    
    //constructor
    public Hand(Deck d)
    {
        curHand = new ArrayList<Card>();
        curHand.add(d.getRandomCard());
        curHand.add(d.getRandomCard());
        
        //incase 2 aces are pulled
        while(getTotalValue() > HANDLIMIT)
        {
            d.putCardBack(curHand.get(1));
            curHand.remove(1);
            curHand.add(d.getRandomCard());
        }
    }
    
    //accessors
    public ArrayList<Card> getCurHand()
    {
        return curHand;
    }
    
    //returns the total value of all the cards in the hand
    public int getTotalValue()
    {
        int total = 0;
        for(Card c : curHand)
        {
            total += c.getValue();
        }
        
        return total;
    }
    
    /**
     * adds a random card to the hand
     * 
     * @param Deck d  the deck that the card will be pulled from
    */
    public void addCard(Deck d)
    {
        curHand.add(d.getRandomCard());
    }
    
    /**
     * reshuffles the deck and resets the hand to a new 2 random cards
     * 
     * @param Deck d  the deck that is being reshuffled/pulled from
    */
    public void resetHand(Deck d)
    {
        //remove each card from the arraylist
        for(Card c : curHand)
        {
            d.putCardBack(c);
        }
        curHand.clear();
        
        curHand.add(d.getRandomCard());
        curHand.add(d.getRandomCard());
    }
    
    //string representation of this Hand
    public String toString()
    {
        String result = "";
        for(Card c : curHand)
        {
            result += c + " ";   
        }
        
        return result;
    }
}
