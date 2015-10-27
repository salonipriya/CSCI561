import java.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class mancala {
	
	
     
	//public static gameSetup next_move = new mancala().new gameSetup();
	static int val=0;
	static int actualPlayer;
	
	class returnclass{
		int val;
		gameSetup returnstate;
	}
	class gameSetup {
		 boolean replay = false;
         private int[] boardstatePlayer2 = new int[0];
         private int[] boardstatePlayer1 = new int[0];
         private int stone2; 
         private int stone1;
         public void setboardSize(int size) {
        	 boardstatePlayer2 = new int[size];
        	 boardstatePlayer1 = new int[size];
	    }
         public gameSetup(String[] val2,String[] val1,int stone_2, int stone_1) {
        	 setboardSize(val1.length);
        	 for(int i = 0;i < val1.length;i++)
      	    {
         		 boardstatePlayer1[i] = Integer.parseInt(val1[i]);
      	    }
        	 for(int i = 0;i <val2.length;i++)
      	    {
        		 boardstatePlayer2[i] = Integer.parseInt(val2[i]);
      	    }
        	 stone1 =stone_1;
        	 stone2 =stone_2;
		}
         
         public gameSetup(){
         }
     }
	
	
	
	
	//Initialize Data
	public static gameSetup Initialize(Scanner input) {
	    String[] val2 =input.nextLine().split(" ");
	    String[] val1 =input.nextLine().split(" ");
	    int stone2 = Integer.parseInt((String) input.nextLine());
	    int stone1 = Integer.parseInt((String) input.nextLine());
	    gameSetup g = new mancala().new gameSetup(val2,val1, stone2,stone1);
	    return g;
   	  }
	
	public static int eval(gameSetup g, int Player){
		int val =0;
		if(Player == 1){
			val = g.stone1 - g.stone2;
		}else{
			val = g.stone2 - g.stone1;
		}
		return actualPlayer == Player ? val:-1*val; 
	}	
	
	public static gameSetup getState(gameSetup g, int i, int player){
		gameSetup tempobj = copyParent(g);
	int boardSize = tempobj.boardstatePlayer1.length-1;
	 if (player==1) {
	int stoneInPit = tempobj.boardstatePlayer1[i];
	tempobj.boardstatePlayer1[i] = 0;
	int index = i;
	while(stoneInPit!=0){
		tempobj.replay = false;
		for(int j=index+1;j<=boardSize;j++){
			if(stoneInPit>0){
				tempobj.boardstatePlayer1[j]++;
				stoneInPit--;
				
			if(stoneInPit==0 && tempobj.boardstatePlayer1[j]==1){
				tempobj.stone1++;
				tempobj.boardstatePlayer1[j] = 0;
				tempobj.stone1 += tempobj.boardstatePlayer2[j];
				tempobj.boardstatePlayer2[j] = 0;
			}
			}
			
		}
		if(stoneInPit > 0){
			tempobj.stone1++;
			stoneInPit--;
			if(stoneInPit==0){
				tempobj.replay = true;
			}
		}
		if(stoneInPit>0){
			for(int k=boardSize;k>=0;k--){
			if(stoneInPit>0){
				tempobj.boardstatePlayer2[k]++;
			stoneInPit--;
			}
			}
		}
		if(stoneInPit>0){
			index = -1;
		}
	      }
		}else{
		int stoneInPit = tempobj.boardstatePlayer2[i];
		tempobj.boardstatePlayer2[i] = 0;
		int index = i;
		while(stoneInPit!=0){
			tempobj.replay = false;
			for(int j=index-1;j>=0;j--){
				if(stoneInPit>0){
					tempobj.boardstatePlayer2[j]++;
				stoneInPit--;
				if(stoneInPit==0 && tempobj.boardstatePlayer2[j]==1){
					tempobj.stone2++;
					tempobj.boardstatePlayer2[j] = 0;
					tempobj.stone2 += tempobj.boardstatePlayer1[j];
					tempobj.boardstatePlayer1[j] = 0;
				}
				}
				
			}
			if(stoneInPit > 0){
				tempobj.stone2++;
				stoneInPit--;
				if(stoneInPit==0){
					tempobj.replay = true;
				}
			}
			if(stoneInPit>0){
				for(int k=0;k<=boardSize;k++){
				if(stoneInPit>0){
					tempobj.boardstatePlayer1[k]++;
				stoneInPit--;
				}
				}
			}
			if(stoneInPit>0){
				index = boardSize+1;
			}
		      
	        }
		}
	 
	 //Check for Game Over
	 int countB1=0;
	 int countB2=0;
	 if(isgameOver(tempobj, player)){
		 for(int u=0;u<=boardSize;u++){
			 countB1 += tempobj.boardstatePlayer1[u];
			 tempobj.boardstatePlayer1[u] = 0;
			 countB2 += tempobj.boardstatePlayer2[u];
			 tempobj.boardstatePlayer2[u]=0;
		 } 
		 if(player==1){
			 tempobj.stone2 += countB1+countB2;
		 }else{
			 tempobj.stone1 += countB1+countB2;
		 }
	 }
	return tempobj;	
	}
	
	//Profit Value
	public static String profitValue(int value){
        if(value == Integer.MAX_VALUE)
            return "Infinity";
        if(value == Integer.MIN_VALUE)
            return "-Infinity";
        return String.valueOf(value);
    }
	
	public static void writeLog(String child,int level, int profit,PrintWriter traverse_log,int task,Integer alpha,Integer beta) throws IOException{
		if(task == 2){
		traverse_log.print("\n"+child+","+level+","+profitValue(profit));
		}else if (task == 3){
			traverse_log.print("\n"+child+","+level+","+profitValue(profit)+","+profitValue(alpha)+","+profitValue(beta));
		}
	}
	
	
	//Get Legal Moves
	public static ArrayList<Integer> getChildrenPits(gameSetup g, int player){
		ArrayList<Integer> return_result = new ArrayList<Integer>();
		if(player == 1){
			for(int i = 0;i < g.boardstatePlayer1.length;i++){
				if(g.boardstatePlayer1[i]!=0)
				{
					return_result.add(i);
				}
			}
		}else {
			for(int i = 0;i<g.boardstatePlayer2.length;i++){
				if(g.boardstatePlayer2[i]!=0)
				{
					return_result.add(i);
				}
			}
		}
		return return_result;
	}
	
	//END
	
	//Check for Game Over
	public static boolean isgameOver(gameSetup g,int player){
		gameSetup tempobj = copyParent(g);
		ArrayList<Integer> children = getChildrenPits(tempobj,player);
		return children.size()==0;
	}
	//END
	
	
	public static gameSetup copyParent(gameSetup originalgame){
		    gameSetup tempBoard = new mancala().new gameSetup();
		    tempBoard.setboardSize(originalgame.boardstatePlayer1.length);
		    int[] b1 = originalgame.boardstatePlayer1.clone();
		    for(int o=0;o<originalgame.boardstatePlayer1.length;o++){
				tempBoard.boardstatePlayer1[o] = b1[o];
				}
		    b1 = originalgame.boardstatePlayer2.clone();
			for(int o=originalgame.boardstatePlayer2.length-1;o>=0;o--){
				tempBoard.boardstatePlayer2[o] = b1[o];
					}
			int valB1 = originalgame.stone2;
			tempBoard.stone2 = valB1;
			valB1 = originalgame.stone1;
			tempBoard.stone1 = valB1;
			tempBoard.replay = originalgame.replay;
			return tempBoard;
	}
	
	
	public static returnclass playGame(gameSetup pobj,int index_child,int player,int level,int cutofdepth,String current,PrintWriter traverse_log,int task,Integer alpha,Integer beta) throws IOException{
		gameSetup currentstate = new mancala().new gameSetup();
        gameSetup finalstate = new mancala().new gameSetup();
	 	gameSetup savedstate = new mancala().new gameSetup();
		returnclass obj = new mancala().new returnclass();
     	int return_val;
		//New Level
		int new_level;
		//New Player
		int next_Player;
		boolean is_Max = false;
		if(level == 0){
			if(task == 3){
			traverse_log.print("Node,Depth,Value,Alpha,Beta");
			}
			if(task == 2){
				traverse_log.print("Node,Depth,Value");
			}
		}
		if(index_child!=-1){
		currentstate = getState(pobj, index_child, player);
		}
		//Replay Condition
		if(currentstate.replay){
			if(isgameOver(currentstate,player)){
				 if(player==actualPlayer)
					 val = Integer.MIN_VALUE;
				 else
					 val = Integer.MAX_VALUE;
	            writeLog(current, level, val,traverse_log,task,alpha,beta);
	            val = eval(currentstate,player);
	 			writeLog(current, level, val,traverse_log,task,alpha,beta);
	 			obj.val = val;
	 			obj.returnstate = currentstate;
	 			return obj;
	         }
		new_level = level;
		next_Player = player;
		is_Max = false;
		if(actualPlayer == player)
			is_Max = true;
		}else{
		if(level == 0){
			currentstate = copyParent(pobj);
			is_Max = true;
			next_Player = player;
		}else{
			next_Player = (player==1)?2:1;
		}
		if(isgameOver(currentstate,player)){
            if(level != cutofdepth){
            	if(player==actualPlayer)
   				 val = Integer.MAX_VALUE;
   			 else
   				 val = Integer.MIN_VALUE;
               writeLog(current, level, val,traverse_log,task,alpha,beta);
            }
            val = eval(currentstate,player);
 			writeLog(current, level, val,traverse_log,task,alpha,beta);
 			obj.val = val;
 			obj.returnstate = currentstate;
 			return obj;
        }
		if(cutofdepth == level){
			val = eval(currentstate,player);
			writeLog(current, level, val,traverse_log,task,alpha,beta);
			obj.val = val;
			obj.returnstate = currentstate;
			return obj;
		}
		new_level = level+1;
		if(level!=0){
		is_Max = true;
		if(actualPlayer == player)
		is_Max = false;
		}
			}
		
		ArrayList<Integer> children = getChildrenPits(currentstate, next_Player);
		if(children.size()==0){
			val = eval(currentstate,player);
			obj.val = val;
			obj.returnstate = currentstate;
			return obj;
		}
		
		
		if(is_Max){
			int max = Integer.MIN_VALUE;
			writeLog(current, level, max,traverse_log,task,alpha,beta);
			for (Integer i : children) {
				String buf="";
				if(next_Player==1){
					buf = "B"+(i+2);
				}else{
					buf = "A"+(i+2);
				}
                returnclass ret;
				ret = playGame(currentstate,i,next_Player,new_level,cutofdepth,buf,traverse_log,task,alpha,beta);
				if(ret.val>max){
					max = ret.val;
					finalstate = copyParent(ret.returnstate);
					obj.returnstate = finalstate;
					
				}
				
				if(task==3){
					if (max >= beta) {
                        writeLog(current, level, max,traverse_log,task,alpha,beta);
                        obj.val = max;
                        obj.returnstate = currentstate;
                        return obj;
                    }
                    alpha = Math.max(alpha, max);
				}
				writeLog(current, level, max,traverse_log,task,alpha,beta);
			}
			obj.val = max;
		}else {
			int min = Integer.MAX_VALUE;
			writeLog(current, level, min,traverse_log,task,alpha,beta);
			for (Integer i : children) {
					String buf="";
				if(next_Player==1){
					buf = "B"+(i+2);
				}else{
					buf ="A"+(i+2);
				}
				returnclass retobj;
			    retobj =playGame(currentstate,i,next_Player,new_level,cutofdepth,buf,traverse_log,task,alpha,beta);
			    if(retobj.val<min){
					min = retobj.val;
					obj.returnstate = currentstate;
				}
			    if(task == 3) {
                    if (min <= alpha) {
                    	writeLog(current, level, min,traverse_log,task,alpha,beta);
                    	obj.val = min;
                    	obj.returnstate = currentstate;
                        return obj;
                    }
                    beta = Math.min(beta,min);
                }
			    writeLog(current, level, min,traverse_log,task,alpha,beta);
			}
			obj.val = min;
		}
		return obj;
	}
	
	//Output the next move
	public static void printNextMove(PrintWriter p, returnclass next_move){
		for(int o=0;o<next_move.returnstate.boardstatePlayer2.length;o++){
		p.print(next_move.returnstate.boardstatePlayer2[o]+" ");
		}
		p.println();
		for(int o=0;o<next_move.returnstate.boardstatePlayer1.length;o++){
			p.print(next_move.returnstate.boardstatePlayer1[o]+" ");
			}
		p.println();
		p.println(next_move.returnstate.stone2);
		p.println(next_move.returnstate.stone1);
	}

	
	
	//Read the input File
		public static void getInput(String[] args) throws IOException{
			File file = null;
//			if ( args.length<1) {
//				 System.out.println("You need to give arguments properly" );
//				 System.exit(1);
//			} else {
			file = new File("/Users/salonipriya/Desktop/Java/Java/Exercise/mancala2/src/input.txt");
//			if(!file.exists()){
//				System.out.println("File not found");
//				System.exit(1);
//			}
//			}
			//Creating log
			PrintWriter traverse_log = null;
		    PrintWriter output_next_move = null;
			try {
	            traverse_log = new PrintWriter(new BufferedWriter(new FileWriter("traverse_log.txt", false)));
	            output_next_move = new PrintWriter(new BufferedWriter(new FileWriter("output_next_move.txt", false)));
	       }
	        catch (IOException e) {
	           // Handle the exception here
	        }
			Scanner input = new Scanner(new FileReader(file));
			   int task = Integer.parseInt((String) input.nextLine());
			   int player = Integer.parseInt((String) input.nextLine());
			   actualPlayer = player;
			   int cutofdepth = Integer.parseInt((String) input.nextLine());
			   gameSetup g = Initialize(input);
			   returnclass next_move = new mancala().new returnclass();
			   if(task == 1){
			   next_move = playGame(g,-1,player,0,1,"root",traverse_log,task,null,null);    
			   }else if(task ==2){
			   next_move = playGame(g,-1,player,0,cutofdepth,"root",traverse_log,task,null,null);    
			   }else {
			   next_move = playGame(g,-1,player,0,cutofdepth,"root",traverse_log,task,Integer.MIN_VALUE,Integer.MAX_VALUE); 
			   }
			printNextMove(output_next_move,next_move);
			traverse_log.flush();
	        output_next_move.flush();
			return;
		}

//Main function
	public static void main(String[] args) throws FileNotFoundException, IOException{
	 getInput(args);
	}
	
	
	
	
}