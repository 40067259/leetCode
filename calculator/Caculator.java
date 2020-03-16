package random;

import java.util.Arrays;
import java.util.Scanner;

import random.Tree.Node;

public class Caculator {
	
    public static boolean isBracedByParenthesis(String[]expression) {
    	if(expression.length < 2) return false;
    	if(!expression[0].contentEquals("(")) return false;
    	int counter = 0;
    	for(int i = 0; i < expression.length;i++) {
    		if(i == expression.length - 1&& expression[i].equals(")")&&
    				counter == 1)  return true;
    		if(expression[i].equals("(")) counter++;
    		if(expression[i].equals(")")) counter--;
    		if(counter == 0) return false;
    	}
    	return false;
    }
    
    public static String[] stripe(String[]expression) {
    	while(isBracedByParenthesis(expression)) {
    		expression = Arrays.copyOfRange(expression, 1,expression.length-1);
    	}
    	return expression;
    }
    
    public static int pickRootIndex(String[]expression) {
    	if(isBracedByParenthesis(expression))
    	stripe(expression);
    	if(expression.length == 1) return 0;
    	int inParenthesis = 0;
    	int currentPriority = -1;
    	int index = -1;
    	int priority = -1;
    	for(int i = expression.length - 1; i > -1; i--) {
    		if(expression[i].equals(")")) inParenthesis++;
    		if(expression[i].equals("(")) inParenthesis--;
    		if(inParenthesis == 0) {
    			if(expression[i].equals("/")||expression[i].equals("*")) 
    				currentPriority = 0;
    			else if(expression[i].equals("+")||expression[i].equals("-"))
    				currentPriority = 1;
    			
    		   if(currentPriority > priority) {
    					priority = currentPriority;
    					index = i;
    					
    				}
    			}
    	}
    	return index;	
    }
    
    public static String[] StrToArr(String str) {
    	
    	if(str.isEmpty()) return null;
    	String [] arr = str.split(" ");
    	return arr;
    	
    }
    
    public static void convertArrayToTree(String[] expression,Node root) {
    	
    	
    	String[] expression1 = stripe(expression);
    	if(expression1.length == 0) return;
    	if(expression1.length == 1) {
    		return;
    	}
    	int rootIndex = pickRootIndex(expression1);
    	//root.data = expression1[rootIndex];
    	String[]leftExpression = Arrays.copyOfRange(expression1, 0,rootIndex);
    	String[]rightExpression =Arrays.copyOfRange(expression1, rootIndex+1,expression1.length);
    	
    	String[]leftExpression1 =stripe(leftExpression);
    	String[]rightExpression1 =stripe(rightExpression);
    	
    	int leftChildIndex = pickRootIndex(leftExpression1);
    	int rightChildIndex = pickRootIndex(rightExpression1);
    	
    	String leftChild = leftExpression1[leftChildIndex];
    	String rightChild = rightExpression1[rightChildIndex];
    	
    	Node leftNode = new Node(leftChild);
    	Node rightNode = new Node(rightChild);
    	
    	root.left =leftNode;
    	root.right = rightNode;
    	
    	convertArrayToTree(leftExpression1,leftNode);
    	convertArrayToTree(rightExpression1,rightNode);
    }
    
     public static double operation(double leftOperand,String operator,double rightOperand) {
    	 switch(operator) {
    	  
    	 case "+": return leftOperand + rightOperand;
    	 case "-": return leftOperand - rightOperand;
    	 case "*": return leftOperand * rightOperand;
    	 default:
    	 {   
    		 if (leftOperand == 0) {
    			    throw new IllegalArgumentException("Argument 'divisor' is 0");
    			}
    			 double ans = leftOperand/rightOperand;
    		 
    		 return ans;
    	 }
    	 		   
    	 }
    	 
     }
     
     public static double calculator(Node root) {
    	 if(root == null) return -0.0000;
    	 if(root.left == null && root.right == null) {
    		 double data = 0;
        	 try
        	    {
        	      
        		  data = Double.parseDouble(root.data);
        	 
        	      
        	    }//need a while loop to check
        	    catch (NumberFormatException nfe)
        	    {    
        	    	 Scanner input = new Scanner(System.in);  // Create a Scanner object
        	    	 System.out.println("Instantiate a decimal for "+root.data);
        	    	 double instantiation = input.nextDouble();
        	    	 data = instantiation;
        	    } 
        	 
        	 return data;
    	 }
    	 double left = calculator(root.left);
    	 double right = calculator(root.right);
    	 return operation(left,root.data,right);
    	  
     }
     public static double calculation(String str) {
    	 Tree tree = new Tree();
    	 String[] expression = stripe(StrToArr(str));
    	 String value = expression[pickRootIndex(expression)];
    	 tree.root = new Node(value);
    	 convertArrayToTree(expression,tree.root);
    	 return calculator(tree.root);
     }
    
    
	public static void main(String[] args) {
		double t = calculation("( ( 8 * ( ( 6 - 2 ) + 2 ) ) )");
		double k = calculation("( ( ( ( 14 - 3 ) / 6 + 200 ) ) )");
		//double m = calculation(("( ( 2 * 21 ) - ( 18 + 4 ) / n )"));
		double y = calculation(("( ( 2 * x ) - ( 18 + y ) / n )"));
		System.out.println(k);
		System.out.println(t);
        System.out.println(y);
	}

}
