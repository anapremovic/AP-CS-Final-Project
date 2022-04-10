import java.util.*;

public class Player
{
    //variables
    private double bankroll;
    private Hand hand;
    private String name;
    
    //constants
    private double BASEBANKROLL = 1000.0;
    
    //constructor
    public Player(Deck d, String name)
    {
        this.bankroll = BASEBANKROLL;
        this.hand = new Hand(d);
        this.name = name;
    }
    
    //accessors
    public double getBankroll()
    {
        return bankroll;
    }
    
    public Hand getHand()
    {
        return hand;
    }
    
    public String getStringHand()
    {
        return hand.toString() + "(" + hand.getTotalValue() + ")";
    }
    
    public String getDealerStringHand()
    {
        return "X " + hand.getCurHand().get(1);
    }
    
    public String getName()
    {
        return name;
    }
    
    /**
     * removes the given amount from the bankroll
     * 
     * @param double amount  the given amount
    */
    public void loseMoney(double amount)
    {
        bankroll -= amount;
    }
    
    /**
     * adds the given amount to the bankroll
     * 
     * @param double amount  the given amount
    */
    public void gainMoney(double amount)
    {
        bankroll += amount;
    }
}