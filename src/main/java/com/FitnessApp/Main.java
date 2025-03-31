package com.FitnessApp;


public class Main{
    public static void main(String[] args) {
        try {
            UserInterface ui = new UserInterface(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } catch (NumberFormatException nbe) {
            UserInterface ui = new UserInterface(480, 640); //lol nyt piilotellaan virheita
        } catch (Exception e) {
            UserInterface ui = new UserInterface(480, 640);
        }
    }
}