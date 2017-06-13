import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class GameServer implements Runnable{
    
    public void notify(int code, PrintWriter out){
        if(code==0){
            out.println("waiting for player 2...");
        }
    }
    public void run(){
        try ( 
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket clientSocket1 = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(
                new InputStreamReader(clientSocket1.getInputStream()));
                
            Socket clientSocket2 = serverSocket.accept();
            PrintWriter out2 =
                new PrintWriter(clientSocket2.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(
                new InputStreamReader(clientSocket2.getInputStream()));
        ){
            String inputLine, outputLine;

            
            
        boolean continueGame = true;
        Scanner input = new Scanner(System.in);
        String boardLine;
        int turn = 0;
        int playerColor = 0;
        String[][] board = new String[19][19];

        for(int row=0; row<board.length; row++){
                for(int column=0;column<board.length; column++){
                        board[row][column]=" - ";
                }
        }

        int row;
        int column;
            while(continueGame){

            if(turn%2 == 0){
                out.println("Player 1");
                out2.println("Player 1");
            }
            else{
                out.println("Player 2");
                out2.println("Player 2");
            }
                do{
                //row input
                        do {


                            out.println("What row?  ");
                            out2.println("What row?  ");
                            if(turn%2 == 0){
                                inputLine = in1.readLine();
                            }
                            else{
                                inputLine = in2.readLine();
                            }
                            while (tryParse(inputLine)==null) {
                                //input.nextInt();
                                out.println("Please enter a number...");
                                out2.println("Please enter a number...");
                                if(turn%2 == 0){
                                    inputLine = in1.readLine();
                                }
                                else{
                                    inputLine = in2.readLine();
                                }
                            }
                            row = tryParse(inputLine);
                        } while (!(row <= 18 && row >= 0));

                        //column input
                        do {
                            out.println("What column? ");
                            out2.println("What column? ");
                            if(turn%2 == 0){
                                inputLine = in1.readLine();
                            }
                            else{
                                inputLine = in2.readLine();
                            } 
                            while (tryParse(inputLine)==null) {
                                //input.nextInt();
                                out.println("Please enter a number...");
                                out2.println("Please enter a number...");
                                if(turn%2 == 0){
                                    inputLine = in1.readLine();
                                }
                                else{
                                    inputLine = in2.readLine();
                                } 
                            }
                            column = tryParse(inputLine);
                        } while (!(column <= 18 && column >= 0));
                        if (board[row][column] == " B " || board[row][column] == " W "){
                                out.println("That space is already in use.");
                                out2.println("That space is already in use.");
                        }
                }while (board[row][column] == " B " || board[row][column] == " W ");
                //OLD USER INPUT
/*			System.out.println("What row?");
                int row = input.nextInt();

                System.out.println("What Column?");
                int column = input.nextInt();
                */

                out.println("Row: " + row + " Column: " + column);

                if(turn %2 == 0){
                        board[row][column] = " B ";
                        playerColor = 1; 
                }else{
                        board[row][column] = " W ";
                        playerColor = 0; 
                }
                turn += 1;
                for (int r=0; r < board.length; r++){
                    boardLine = "";
                        for (int c= 0; c<board.length; c++){
                                boardLine = boardLine.concat(board[r][c] +"");	
                        }
                        out.println(boardLine);
                        out2.println(boardLine);

                }
                for (int x = 0 ; x != board.length ; x++) {
                    for (int y = 0 ; y != board[row].length ; y++) {
                        if (findFiveInARow(board, playerColor, x, y, 0, 1) != false){
                                        if(playerColor==1){
                                                out.println("The Winner is Player 1(Black)!- Horizontal");
                                                out2.println("The Winner is Player 1(Black)!- Horizontal");
                                                x=19;
                                        }else{
                                                out.println("The Winner is Player 2(White)! - Horizontal");
                                                out2.println("The Winner is Player 2(White)! - Horizontal");
                                                x=19;
                                        }
                                        continueGame = false;
                                        input.close();
                        }
                        if (findFiveInARow(board, playerColor, x, y, 1, 0) != false){
                                        if(playerColor==1){
                                                out.println("The Winner is Player 1(Black)! - Vertical");
                                                out2.println("The Winner is Player 1(Black)! - Vertical");
                                                x=19;
                                        }else{
                                                out.println("The Winner is Player 2(White)! - Vertical");
                                                out2.println("The Winner is Player 2(White)! - Vertical");
                                                x=19;
                                        }
                                        continueGame = false;
                                        input.close();
                        }
                        if (findFiveInARow(board, playerColor, x, y, 1, 1) != false){
                                        if(playerColor==1){
                                                out.println("The Winner is Player 1(Black)! - Diagonal");
                                                out2.println("The Winner is Player 1(Black)! - Diagonal");
                                                x=19;
                                        }else{
                                                out.println("The Winner is Player 2(White)! - Diagonal");
                                                out2.println("The Winner is Player 2(White)! - Diagonal");
                                                x=19;
                                        }
                                        continueGame = false;
                                        input.close();
                        }
                        if (findFiveInARow(board, playerColor, x, y, 1, -1) != false){
                                        if(playerColor==1){
                                                out.println("The Winner is Player 1(Black)! - Diagonal");
                                                out2.println("The Winner is Player 1(Black)! - Diagonal");
                                                x=19;
                                        }else{
                                                out.println("The Winner is Player 2(White) - Diagonal");
                                                out2.println("The Winner is Player 2(White) - Diagonal");
                                                x=19;
                                        }
                                        continueGame = false;
                                        input.close();
                        }
                    }
                }

        }
            
        }
        catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + "8888" + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
    
    private static boolean findFiveInARow(String[][] board, int playerColor, int x, int y, int verticalPos, int horizontalPos) {
        String color;

            for (int countToFive = 0 ; countToFive != 5 ; countToFive++) {
                    //Vertical - [x+1][y] (1,0)
                    //Horizontal - [x][y+1] (0,1)
                    //Diagonal Right - [x+1][y+1] (1,1)
                    //Diagonal Left - [x-1][y+1] (-1,1)
                    int updatedVerticalPos = countToFive*verticalPos;
                    int updatedHorizontalPos = countToFive*horizontalPos;

            int row = x + updatedVerticalPos;
            int column = y + updatedHorizontalPos;

            if (playerColor == 1){
                    color = " B ";

            }else{
                    color = " W ";
            }

            if (row < 0 || column < 0 || row >= board.length ||column >= board[row].length || board[row][column] != color) {
                return false;
            }
        }
        return true;
    }
   
    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
}





