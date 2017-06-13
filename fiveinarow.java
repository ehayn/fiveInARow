import java.util.Scanner;
import java.net.*;
import java.io.*;
public class fiveinarow {
    
    public static void main (String[] args){
        Scanner input = new Scanner(System.in);
        int hostGame;
        int player;//tracks which player the client is
        int currentTurn = 1;
        
        System.out.println("Host Game?");
        hostGame = input.nextInt();//type 1 to host
        
        if(hostGame == 1){
            //Set up game server thread and start it
            GameServer gameServer = new GameServer();
            new Thread(gameServer).start();
            player = 1;//Player 1 always hosts the game
        }
        else{
            player = 2;
        }
        
        

        try (
            Socket clientSocket = new Socket("192.168.1.101", 8888);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));
        ){
            BufferedReader stdIn =
            new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            boolean skipInput;//tracks whether the client needs to input or not

            while ((fromServer = in.readLine()) != null) {
                skipInput = true;
                
                if(fromServer.equals("Player 1")){
                    currentTurn = 1;
                }
                else if(fromServer.equals("Player 2")){
                    currentTurn = 2;
                }
                if(currentTurn==player){//it is the players turn, will need to input at some point
                    if(fromServer.equals("Please enter a number...")){
                        skipInput = false;
                    }
                    else if(fromServer.contains("What")){
                        skipInput=false;
                    }
                }
                
                System.out.println("Server: " + fromServer);
                /*if (fromServer.equals("Bye."))
                    break;*/

                if(skipInput==true){
                    fromUser = null;
                }
                else{
                    fromUser = stdIn.readLine();
                }
                //fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } 
        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + "192.168.1.101");
            System.exit(1);
        } 
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                "192.168.1.101");
            System.exit(1);
        }

        
        

    }
	
}
