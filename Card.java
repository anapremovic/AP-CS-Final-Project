public class Card
{
    //instance variables
    private char suit;
    private int value;
    private String stringValue;
    private String[] allStringNums = {"2", "3", "4", "5", "6", "7", "8", "9", 
                                      "10", "A", "J", "Q", "K"};
    
    //constants
    private int ACEVALUE = 11;
    private int PICTURECARDVALUE = 10;
    
    //constructor
    //precondition: suit must be H, C, D, or S
    //              value must be between 2 and 14
    public Card(int value, char suit)
    {
        this.suit = suit;
        
        if(value > ACEVALUE)
        {
            this.value = PICTURECARDVALUE;
        }
        else
        {
            this.value = value;   
        }
        stringValue = allStringNums[value - 2];
    }
    
    //accessors
    public int getValue()
    {
        return value;
    }
    
    public String getStringValue()
    {
        return stringValue;
    }
    
    public char getSuit()
    {
        return suit;
    }
    
    //a string representation of this Card
    public String toString()
    {
        return stringValue + suit;
    }
}
