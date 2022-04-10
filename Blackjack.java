import java.lang.Throwable;
import java.util.*;

public class Blackjack
{
    //variables
    private Deck deck;
    private Player player;
    private Player dealer;
    private double bet;
    Scanner console = new Scanner(System.in);
    
    //constants
    private double BASEBET = 100.0;
    private int HANDLIMIT = 21;
    private int DEALERSTANDS = 18; //dealer will stand if their hand value >= 18
    
    public Blackjack()
    {
        deck = new Deck();
        player = new Player(deck, "Player");
        dealer = new Player(deck, "Dealer");
    }
    
    /**
     * Method to play Blackjack, including multiple rounds if player chooses to 
     * play more than once.
    */
    public void play()
    {
        while(true)
        {
            bet();
            bothTurns();
            
            //if the player runs out of money, the game is over
            if(player.getBankroll() <= 0.0)
            {
                System.out.println();
                System.out.println("You're out of money!\nGame over. Thank you"
                + " for playing!");
                return;
            }
            
            //ask the user if they wish to play again (same bankroll, new hand)
            System.out.print("Play again (y/n)? ");
            String playAgain = console.nextLine();
            
            //if the user does not enter a valid answer, prompt again
            while(!(playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("yes")  
            || playAgain.equalsIgnoreCase("n") || playAgain.equalsIgnoreCase("no")))
            {
                System.out.print("Please try again.\nPlay again (y/n)? ");
                playAgain = console.next();
            }
            
            //if the player types "n" or "no", the game is over
            if(playAgain.equalsIgnoreCase("n") || playAgain.equalsIgnoreCase("no"))
            {
                //format to 2 decimal places for display
                String bankr = String.format("%.2f", player.getBankroll());
                
                System.out.println("\nThank you for playing!\nFinal Bankroll: "
                + bankr);
                return;
            }
            
            //reset both hands to restart the game
            player.getHand().resetHand(deck);
            dealer.getHand().resetHand(deck);
            System.out.println();
        }
    }
    
    /**
     * Method that has the player bet a certain amount of money before the game
     * starts. If they have enough, they must bet at least $100.00, and may 
     * choose to bet more. If they don't have $100.00, they can choose any 
     * amout up to however much they have to bet.
    */
    public void bet()
    {
        //format to 2 decimal places for display
        String bankr = String.format("%.2f", player.getBankroll());
        
        /**
         * display bankroll information and prompt user for additional bet if 
         * they have enough
        */
        System.out.println("Money in your bankroll: $" + bankr);
        if(player.getBankroll() < BASEBET)
        {
            System.out.print("How much would you like to bet? ");
            bet = (double) console.nextInt();
            
            //if the user enters more money than they have, prompt again
            while(bet > player.getBankroll())
            {
                System.out.print("You don't have enough money in bankroll."
                + "\nHow much would you like to bet? ");
                bet = (double) console.nextInt();
            }
        }
        else
        {
            System.out.print("Base bet: $100.00\nHow much additional would you" 
            + " like to bet? ");
            bet = (double) console.nextInt() + BASEBET;
            
            //-||- prompt again
            while(bet > player.getBankroll())
            {
                System.out.print("You don't have enough money in bankroll.\nHow" 
                + " much additional would you like to bet? ");
                bet = (double) console.nextInt() + BASEBET;
            }
        }
        
        player.loseMoney(bet);
    }
    
    /**
     * Method for both the Player's and Dealer's turn. 
     * Player's turn continues so long as the player hits, and ends when the 
     * player either stands or busts, then it is the Dealer's turn.
    */
    public void bothTurns()
    {
        System.out.println();
        
        //display part of the Dealer's hand and the Player's hand to the player
        System.out.println("Dealer's Hand: " + dealer.getDealerStringHand() +
        "\nPlayer's Hand: " + player.getStringHand());
        
        String curHitStand = hitOrStand();
        //the user enters "h" or "hit" to hit
        while(curHitStand.equalsIgnoreCase("h") || curHitStand.equalsIgnoreCase("hit"))
        {
            hit(player);
            
            //if the player's value in their hand goes above 21, they bust
            if(player.getHand().getTotalValue() > HANDLIMIT)
            {
                System.out.println("You busted!");
                break;
            }
            
            //if they hit again, this while loop repeats
            curHitStand = hitOrStand();
        }
        
        System.out.print("Enter for Dealer's turn... ");
        console.nextLine();
        console.nextLine();
        dealerTurn();
        
        //both turns are over and the bankroll information is displayed
        //format to 2 decimal places for display
        String bankr = String.format("%.2f", player.getBankroll());
        System.out.println("New bankroll: " + bankr);
    }
    
    /**
     * Method for the Dealer's turn. The Dealer will only hit on several
     * conditions. If they have 18 or higher, they will stand. If they have
     * higher than the Player or the player has busted, they will stand. Finally,
     * if the Dealer starts out with 21, they will stand. Otherwise, the Dealer
     * will keep hitting until one of these conditions are true.
     * Once the dealer stands, the winning/losing info will be displayed to the 
     * Player.
    */
    public void dealerTurn()
    {
        System.out.println();
        System.out.println("Dealer's Hand: " + dealer.getStringHand());
        
        int playerValue = player.getHand().getTotalValue();
        int dealerValue = dealer.getHand().getTotalValue();
        
        //Dealer hits while these conditions are met
        while(playerValue <= HANDLIMIT && dealerValue <= playerValue && 
        dealerValue != HANDLIMIT && dealerValue < DEALERSTANDS)
        {
            System.out.print("Enter to continue...");
            console.nextLine();
            
            System.out.println("Dealer hits.");
            hit(dealer);    //Dealer hits
            dealerValue = dealer.getHand().getTotalValue();
            
            //if the Dealer busts, the player automatically wins
            if(dealerValue > HANDLIMIT)
            {
                System.out.println("Dealer busted!");
                break;
            }
        }
        
        System.out.print("Enter to continue...");
        console.nextLine();
        
        //winning/losing information
        if(dealerValue > HANDLIMIT)
        {
            System.out.println("Player wins!");
            player.gainMoney(bet * 3);
        }
        else
        {
            System.out.println("Dealer stands.");
            
            if(playerValue > HANDLIMIT || dealerValue > playerValue)
            {
                System.out.println("Dealer wins.");
            }
            else if(playerValue > dealerValue)
            {
                System.out.println("Player wins!");
                player.gainMoney(bet * 3);
            }
            else
            {
                System.out.println("You push (it's a tie).");
                player.gainMoney(bet);
            }
        }
    }
    
    /**
     * Method called when the player "hits". The player draws a card from the deck.
     * 
     * @param Player p  the player that hits
    */
    public void hit(Player p)
    {
        p.getHand().addCard(deck);
        System.out.println(p.getName() + "'s Hand: " + p.getStringHand());
    }
    
    /**
     * Asks the user whether they want to "hit" or "stand".
     * 
     * @return hitStand  the user's answer, will be "h", "hit", "s", or "stand", 
     *                   case insensitive
    */
    public String hitOrStand()
    {
        System.out.print("Hit or Stand (h/s)? ");
        String hitStand = console.next();
        
        //if the user does not enter a valid answer, prompt them again
        while(!(hitStand.equalsIgnoreCase("h") || hitStand.equalsIgnoreCase("hit")  
        || hitStand.equalsIgnoreCase("s") || hitStand.equalsIgnoreCase("stand")))
        {
            System.out.print("Please try again.\nHit or Stand (h/s)? ");
            hitStand = console.next();
        }
        
        return hitStand;
    }
}
