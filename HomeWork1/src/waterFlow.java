import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

public class waterFlow {
	class NodeCompare implements Comparator<String>{

		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
		
	}
	
	class Priority implements Comparator<SelectionCost>{

		@Override
		public int compare(SelectionCost o1, SelectionCost o2) {
			if(o1.cost-o2.cost!=0){
				return o1.cost-o2.cost;
			}else{
				return o1.id.compareTo(o2.id);
			}

		}
		
	}
	
	 public class SelectionCost{
		 private String id;
		 private int cost;
		 SelectionCost(String id,int cost){
			 this.id=id;
			 this.cost=cost;
		 }
	 }
	 
	 class dataStructure {
		 class matrix {
			 class intervals {
				int begin;
				int end;
			}	
			String start;
			String end;
			int cost;
			int numberOfIntervals;
			intervals[] inter = new intervals[0];
			public void setinterSize(int size) {
		         inter = new intervals[size];
		    }
		}
		String problemType;
		int numberOfNodes;
		String startNode;
		String[] leafNodes;
		String[] restOfNodes;
		int numberOfPipes;
		String[] graph = new String[0];
		 public void setgraphSize(int size) {
	         graph = new String[size];
	    }
		matrix[] mat = new matrix[0];
		public void setmatSize(int size) {
	         mat = new matrix[size];
	    }
		int starttime;
	}
	
	 public static String DFS(dataStructure ds){
		 int count = 0;
		 int starttime = 0;
		 Queue<SelectionCost> frontier = new LinkedList<SelectionCost>();
         Stack<String> lifo = new Stack<String>();
         ArrayList<String> explored = new ArrayList<String>();
             String top = ds.startNode; 
             starttime = starttime + ds.starttime;
             lifo.push(ds.startNode);
        	 while(true){
        		 if(lifo.size()==0)
        			 return "None";
        		 top =  lifo.peek();
        		 explored.add(lifo.pop());
        		 for(int y=0;y<ds.leafNodes.length;y++){
        			 if(top.equals(ds.leafNodes[y])){
        				 //Call the output function
        				 for(SelectionCost s : frontier) { 
       					  if(s.id.equals(top)){
       						if(s.cost>=24)
           					 s.cost = s.cost%24;
       						  starttime = s.cost;
       					  }
       					}
        				 String out = top + " " + starttime; 
        				 return (out);
        			 }
        		 }
        	 ArrayList<String> toPush = new ArrayList<String>();
             int pushCount = 0;
        	 for(int g=0;g<ds.numberOfPipes;g++){
        		 if(ds.mat[g].start.trim().equals(top.trim())){
        			 int flag=1;
         			//Explored
           			 for(int u=0;u<explored.size();u++)
           			 {
           				 if((ds.mat[g].end).equals(explored.get(u))){
           					 flag=0;
               			 }
           			 }
//           			 int flagk=1;
           			 //Checking the values in Frontier
//          			 if(flag==1){
//          				 if(!frontier.isEmpty()){
//          			 for(SelectionCost s : frontier) { 
//   					  if(s.id.equals(ds.mat[g].end)){
//   						  flagk=0;
//          			 }
//          				 }
//          			 }}
          			 //End
           			 
           			 
        			 if(flag==1){
        			 toPush.add(ds.mat[g].end);
        			 pushCount++;
        			 }
        		 }
        	 }
        	 if(toPush != null) {
        	 toPush.sort(new waterFlow().new NodeCompare());
        	 for(SelectionCost s : frontier) { 
					  if(s.id.equals(top)){
						  starttime = s.cost;
					  }
					}
        	 for(int i = toPush.size()-1 ; i>= 0; i--) {
        		 lifo.add(toPush.get(i));
        		 frontier.add(new waterFlow().new SelectionCost(toPush.get(i),starttime+1));
        	 }
    	 }
        	
        	 }
	 }
	 
	 public static String BFS(dataStructure ds){
			// Create a new, empty stack
		     int starttime = 0;
		     ArrayList<String> explored = new ArrayList<String>();
		     Queue<SelectionCost> frontier = new LinkedList<SelectionCost>();
	         Queue<String> fifo = new LinkedList<String>();
	             String first = ds.startNode; 
	             frontier.add(new waterFlow().new SelectionCost(first,ds.starttime));
	             fifo.add(ds.startNode);
	        	 while(true){
	        		 if(fifo.size()==0)
	        			 return "None";
	        		 first =  fifo.peek();
	        		 explored.add(fifo.remove());
	        		 for(int y=0;y<ds.leafNodes.length;y++){
	        			 if(first.equals(ds.leafNodes[y])){
	        				 for(SelectionCost s : frontier) { 
	        					  if(s.id.equals(first)){
	        						  if(s.cost>=24)
	        		    					 s.cost = s.cost%24;
	        						  starttime = s.cost;
	        					  }
	        					}
	        				 String out = first + " " + starttime ;
	        				 return(out);
	        			 }
	        		 }
	        		 ArrayList<String> toPush = new ArrayList<String>();
	                 int pushCount = 0;
	            	 for(int g=0;g<ds.numberOfPipes;g++){
	            		 if(ds.mat[g].start.equals(first)){
	            			int flag=1;
	            			int flagk =1;
	            			//Explored
	              			 for(int u=0;u<explored.size();u++)
	              			 {
	              				 if((ds.mat[g].end).equals(explored.get(u))){
	              					 flag=0;
	                  			 }
	              			 }
	              			 //End
	              			 //Checking the values in Frontier
	              			 if(flag==1){
	              				 if(!frontier.isEmpty()){
	              			 for(SelectionCost s : frontier) { 
	       					  if(s.id.equals(ds.mat[g].end)){
	       						  flagk=0;
	              			 }
	              				 }
	              			 }}
	              			 //End
	            			 if(flag==1&&flagk==1){
	            			 toPush.add(ds.mat[g].end);
	            			 pushCount++;
	            			 }
	            		 }
	            	 }
	            	 if(toPush != null) {
	            	 toPush.sort(new waterFlow().new NodeCompare());
	            	 for(SelectionCost s : frontier) { 
   					  if(s.id.equals(first)){
   						  starttime = s.cost;
   					  }
   					}
	            	 for(int i = 0 ; i< toPush.size(); i++) {
	            		 fifo.add(toPush.get(i));
	            		 frontier.add(new waterFlow().new SelectionCost(toPush.get(i),starttime+1));
	            	 }
	        	 }
	        	 }
		 }
	 
	 public static String UCS(dataStructure ds){
		 PriorityQueue<SelectionCost> frontier = new PriorityQueue<SelectionCost>(100,new waterFlow().new Priority());
		 int starttime = 0;
		 frontier.offer(new waterFlow().new SelectionCost(ds.startNode,ds.starttime));
		 ArrayList<String> explored = new ArrayList<String>();
		 while(true){
			 if(frontier.isEmpty()){
				 return "None";
			 }
			 SelectionCost front =  frontier.poll();
			 for(int y=0;y<ds.leafNodes.length;y++){
    			 if(front.id.equals(ds.leafNodes[y])){
//    				 for(SelectionCost s : frontier) { 
//    					  if(s.id.equals(front.id)){
//    						  starttime = s.cost;
//    					  }
//    					}
    				 if(front.cost>=24)
    					 front.cost = front.cost%24;
    				 String out = front.id + " " + front.cost;
    				 return(out);
    			 }
    		 }
			 explored.add(front.id);
			 ArrayList<SelectionCost> childNodes = new ArrayList<SelectionCost>();
			 for(int g=0;g<ds.numberOfPipes;g++){
				 if(ds.mat[g].start.equals(front.id)){
					 int flagk = 1;
					 for(int h=0;h<ds.mat[g].numberOfIntervals;h++){
						 if(front.cost%24>=ds.mat[g].inter[h].begin && front.cost%24<=ds.mat[g].inter[h].end){
							 flagk=0;
						 }
					 }
					 if(flagk==1){
						 childNodes.add(new waterFlow().new SelectionCost(ds.mat[g].end,ds.mat[g].cost));
					 }
         		 }
			 }
			 for(SelectionCost c:childNodes){
				 int flag=1;
			 if(!explored.contains(c.id)){
				 for(SelectionCost f:frontier){
					 if(f.id.equals(c.id)){
						 if(f.cost>c.cost+front.cost){
							 frontier.remove(f);
							 frontier.offer(new waterFlow().new SelectionCost(c.id,front.cost+c.cost));
							 break;
						 }
						 flag=0;
					 }
				 }
				 if(flag==1){
				 frontier.offer(new waterFlow().new SelectionCost(c.id,front.cost+c.cost));
				 }
			 }
			 }
		 }
	 }
	 
	 public static void output(String outputstring) throws IOException{		 
		 File file =new File("output.txt");		
 		//if file doesnt exists, then create it
 		if(file.exists()){
 			file.delete();
 		}
 		file.createNewFile();
 		//true = append file
 		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(file.getName(),true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
 	        bufferWritter.write(outputstring);
 	        bufferWritter.newLine();
 	        bufferWritter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 }
	 
	public static String printGraph(dataStructure ds) throws IOException {
		String output = "";
		if(ds.problemType.equals("BFS")){
			output = BFS(ds);
			return(output);
			//Print BFS Data
		}else if (ds.problemType.equals("DFS")){
			//Call DFS algo
			output = DFS(ds);
			return(output);
			//Print DFS data
		}else if (ds.problemType.equals("UCS")){
			//Call UCS data
			output =UCS(ds);
			//Print UCS data
			return(output);
		}
		return output;
	}
	 
	public static void main(String[] args) throws FileNotFoundException, IOException {
		int numberOfProblems = 0;
		dataStructure[] ds = null;
		try {
			File file = null;
			if ( args.length<2) {
				 System.out.println("You need to give arguments properly" );
				 System.exit(1);
			} else {
			file = new File(args[1]);
			if(!file.exists()){
				System.out.println("File not found");
				System.exit(1);
			}
			}
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			ArrayList<String> input = new ArrayList<String>();
			String line;
			int count = 0;
			int problemNumber = 0;
			
			while ((line = bufferedReader.readLine()) != null) {
				if(count == 0) {
					numberOfProblems = Integer.parseInt(line);
					ds = new dataStructure[numberOfProblems];
				}
				input.add(line);
				count++;
			}
			if(count==0){
				System.out.println("The input file is empty");
				System.exit(1);
			}
			int i= 1;
			int number_of_results = Integer.parseInt(input.get(0));
			int offset = 0;
			while(i < count) {
				if(input.get(i).equals("BFS") || input.get(i).equals("DFS") ||input.get(i).equals("UCS")) {
					ds[problemNumber] = new waterFlow().new dataStructure();
					ds[problemNumber].problemType = input.get(i);
					offset = i;
					ds[problemNumber].startNode = input.get(++offset);
					ds[problemNumber].leafNodes = input.get(++offset).split(" ");
					if(!input.get(++offset).equals(null))
					ds[problemNumber].restOfNodes = input.get(offset).split(" ");
					ds[problemNumber].numberOfPipes = Integer.parseInt(input.get(++offset));
					int c = 0;
					//System.out.println(ds[problemNumber].graph.length);
					ds[problemNumber].setgraphSize(ds[problemNumber].numberOfPipes);
					ds[problemNumber].setmatSize(ds[problemNumber].numberOfPipes);
					//System.out.println("The new length"+ds[problemNumber].graph.length);
					for(int k=0 + offset;k< ds[problemNumber].numberOfPipes + offset;k++){
						ds[problemNumber].graph[c] = input.get(k+1);
						String lines[] = ds[problemNumber].graph[c].split(" ");
						ds[problemNumber].mat[c] = ds[problemNumber].new matrix();
						       ds[problemNumber].mat[c].start = lines[0];
						       ds[problemNumber].mat[c].end = lines[1];
						       ds[problemNumber].mat[c].cost = Integer.parseInt(lines[2]);
						       ds[problemNumber].mat[c].numberOfIntervals = Integer.parseInt(lines[3]);
						       int no_of_Intervals = Integer.parseInt(lines[3]);
						       int x = 3;
						       ds[problemNumber].mat[c].setinterSize(no_of_Intervals);
						       for(int s=0;s<no_of_Intervals;s++){
						    	   ds[problemNumber].mat[c].inter[s] = ds[problemNumber].mat[c].new intervals();
						    	   String start_end[] = lines[++x].split("-");
						    	   ds[problemNumber].mat[c].inter[s].begin = Integer.parseInt(start_end[0]);
						    	   ds[problemNumber].mat[c].inter[s].end = Integer.parseInt(start_end[1]);
						       }
						       
						c++;
					}
					offset = offset+c;
					ds[problemNumber].starttime = Integer.parseInt(input.get(++offset));
					problemNumber++;
				} else {
					offset++;
				}
				i = offset;
			}
			if(number_of_results!=problemNumber)
			{
				System.out.println("The spec does not contain specified number of test cases");
				System.exit(1);
			}
			String output = "";
			for(int j = 0; j < numberOfProblems ; j++) {
				output = output + printGraph(ds[j]);
				if(j!=numberOfProblems-1)
				output = output +"\n"; 
			}
			output(output);
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
