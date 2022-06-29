package com.evoxon.mastermind.domain;

import com.evoxon.mastermind.util.KeyboardInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class MindService {

    @Autowired
    private final KeyboardInput keyboardInput;

    public MindService(KeyboardInput keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    public void initializeGame(){
        System.out.println("Would you like to play a new game of MasterMind?: (Y/N)");
        String newGame = keyboardInput.getNewInput();
        if(newGame.compareTo("Y")==0 || newGame.compareTo("y")==0){
            MindGame mindGame = createNewGame();
            displayRules();
            playMindGame(mindGame);
        }
        else{
            System.out.println("Goodbye");
        }
    }

    public void playMindGame(MindGame mindGame) {
        while(!mindGame.isFinished){
            System.out.println("Enter your prediction: ");
            //String predictionString = scanner.nextLine();
            String predictionString = keyboardInput.getNewInput();
            String[] predictionArray = parsePrediction(predictionString);
            int[] feedBack = checkPrediction(predictionArray, mindGame.getSecretCombination());
            System.out.println("You have guessed "+feedBack[0]+" colors, from which "+feedBack[1]+" in the correct place.");
            mindGame.setNumberOfTries(mindGame.getNumberOfTries()+1);
            if(feedBack[0]==5 && feedBack[1]==5){
                mindGame.setFinished(true);
            }

        }
        System.out.println("Congratulations, you have guess the combination in "+mindGame.getNumberOfTries()+" tries.");
        initializeGame();
    }

    public int[] checkPrediction(String[] predictionArray, String[] secret) {
        int numberOfMatches = 0;
        int numberOfOrderedMatches = 0;
        String[] copyOfSecret = new String[secret.length];
        System.arraycopy(secret,0,copyOfSecret,0,secret.length);

        for(int k=0 ; k < secret.length ; k++){
            if (secret[k].equals(predictionArray[k])){
                numberOfOrderedMatches++;
            }
        }
         for(int i=0 ; i < predictionArray.length; i++){
             for (int j=0 ; j < copyOfSecret.length; j++){
                 if(predictionArray[i].equals(copyOfSecret[j])){
                     numberOfMatches++;
                     copyOfSecret[j]="alreadyCounted";
                     break;
                 }
             }
         }
         return new int[]{numberOfMatches,numberOfOrderedMatches};
    }

    public String[] parsePrediction(String predictionString) {
        String[] combination = predictionString.split(" ");
        return combination;
    }

    public void displayRules() {
        System.out.println("The rules are the following: you have to guess a secret code consisting of a series of " +
                "5 colors from the following list: blue,red,yellow,black and white.");
        System.out.println("To enter a prediction write the five colors separated by a space. The game will tell you" +
                " how many colors you have guessed well and how many of them are also in the right place.");
        System.out.println("Repeat this process until you guess all of them and in the correct order. The game will then " +
                "show the number of tries you took and ask you if you want to play another round.");
    }

    public MindGame createNewGame() {

        String[] allowedColors = new String[]{"blue","red","yellow","black","white"};
        String[] randomCombination = new String[5];
        Random random = new Random();

        for(int i=0 ; i< randomCombination.length ; i++){
            randomCombination[i] = allowedColors[random.nextInt(5)];
        }
        return new MindGame(randomCombination,0,false);
    }
}
