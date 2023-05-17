package nl.sogyo.javaopdrachten;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class DecisionTree{
    public static void main (String[] args){
        
        HashMap<String, String> originNodes = new HashMap<String, String>();
        ArrayList<ArrayList<String>> answerEdges = new ArrayList<ArrayList<String>>();
        DecisionTree readFile = new DecisionTree();

        readFile.readFile(originNodes, answerEdges);
        
        String questionsKey = "N1";
        getUserInput(questionsKey, originNodes, answerEdges);        
    }

    private static void getUserInput(String questionsKey, HashMap<String, String> originNodes, ArrayList<ArrayList<String>> answerEdges) {
        
        System.out.println(originNodes.get(questionsKey));
        
        Scanner userReplies = new Scanner(System.in);
        String userInput = userReplies.nextLine().trim();
        for(int i = 0; i < answerEdges.size(); i++){
            if(answerEdges.get(i).get(2).equalsIgnoreCase(userInput) && answerEdges.get(i).get(0).contains(questionsKey)){
                questionsKey = answerEdges.get(i).get(1);
                break;
            }
        }

        if(originNodes.get(questionsKey).contains("?")){
            getUserInput(questionsKey, originNodes, answerEdges);
        } else {
            System.out.println("De boom waar je naar staat te kijken is een" + originNodes.get(questionsKey) + ".");
        }
    }

    private static void sortLines (String nextLine, HashMap<String, String> questions, ArrayList<ArrayList<String>> replies) {
        if(nextLine.contains("?")|| !nextLine.contains(", N")){
            String[] parts = nextLine.split(",", 2);
            String originNode = parts[0];
            String question = parts[1];
            questions.put(originNode, question);
        }else{
            String[] parts = nextLine.split(", ", 3);
            String originNode = parts[0];
            String destination = parts[1];
            String reply = parts[2];
            ArrayList<String> newItem = new ArrayList<String>();
            newItem.add(originNode);
            newItem.add(destination);
            newItem.add(reply);
            replies.add(newItem);
        }
    }

    private void readFile(HashMap<String, String> questionNodes, ArrayList<ArrayList<String>> answerEdges){
        try {
            File sourceFile = new File(".\\src\\main\\resources\\intermediate\\decision-tree-data.txt");
            Scanner readFile = new Scanner(sourceFile);
            while (readFile.hasNextLine()) {
              String nextLine = readFile.nextLine();
              sortLines(nextLine, questionNodes, answerEdges);
            }
            readFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}