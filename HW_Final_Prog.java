//Peter Boldyrev, CSS 142, Final program
//PhoneBook program is a cli application utilizing crud (create/read/update/delete) approach 
//PhoneBook has 6 functions: List all contacts from the file
//Search by any contact parameter
//Add a new Contact to the file
//Delete a contact 
//Update a contact info
//Exit app

import java.util.Scanner;
import java.util.Arrays;
import java.util.LanguageRange;
import java.util.NoSuchElementException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;


public class HW_Final_Prog {
    public static void main(String[] args){
        System.out.println("Hello! Welcome to the phoneBook app where you can "+
        "create, read, update and delete entries using cli.");
        //method contains the program
        overall();
        //end of main
    }

       public static void overall(){
        String line;
        //reading from user defined file
        String userFileName = "nameBook.txt";
        System.out.println("Please add filename to read from on line 32 -- i.e. nameBook.txt."+
        " Currently reading from "+userFileName);
        //count the lines method. Input - String of txt file where to read from;
        //output - integer with number of lines
        int linesInTheFile = countLinesInTheFile(userFileName);
        //creating arrays for values in the file
         String [] firstName = new String [linesInTheFile];
         String [] lastName = new String [linesInTheFile];
         String [] phoneNumber = new String [linesInTheFile];
         String [] companyData = new String [linesInTheFile];
        //creating array of contacts
         Contact [] arrOfContacts = new Contact[linesInTheFile];
        //adding scanner to use for file Input //NUMBER 5 FROM FEATURES TXT = FILE I/O
        Scanner inputStream = null;
        try {
            inputStream = new Scanner (new FileInputStream(userFileName));
        } catch (FileNotFoundException e) {
            System.out.println("Here is a filenotfound exception");
            System.exit(0);
        }
        //adding values to respective string arrays
        int i = 0;
        //line 57 -73 can lead to grading 2 Looping with Repetition Control Structures
        //there is a while loop and a for loop
        while(inputStream.hasNextLine()){
            line = inputStream.nextLine();
            Scanner lineScanner = new Scanner (line);
                while(lineScanner.hasNext()){
                    firstName[i]=lineScanner.next();
                    lastName[i] = lineScanner.next();
                    phoneNumber[i] = lineScanner.next();
                    companyData[i] = lineScanner.next();
            }
                lineScanner.close();
                i++;
       }
       inputStream.close();
       //creating an array of Contact class
       for(int j =0, k=0;j<arrOfContacts.length;j++){
        arrOfContacts[k]= new Contact(firstName[j],lastName[j],phoneNumber[j],companyData[j]);
        k++;
      }
      //creating a new list class // GRADING FEATURE 6 // Using Multiple Classes
      ListOfContacts list1 = new ListOfContacts(arrOfContacts);
      //passing list, array of contacts and user file name to proceed with handling user choices
      //no output from this method but basically driving the program
      handleUserOptions(list1, arrOfContacts, userFileName);
        //end of overall
    }

    public static void handleUserOptions(ListOfContacts list1, Contact [] arrOfContacts, String userFileName){
      //preparing to write to the file - GRADING FEATURE 5 - File I/O
        PrintWriter outputStream = null;
        Scanner sc = new Scanner(System.in);
        //mathod display main menu takes a scanner as an input
        //displays options and returns user selection as an integer
        int userChoice = displayMainMenu(sc);

        //handling user choices
       if(userChoice ==1){
       //list all contacts is a method that takes array of strings and displays all contacts
       //that were read from user file - basically showing what is currently in the file
        listAllContacts(list1.listAllContact());
        //nextStep is a method that doesn't take any inputs/outputs
        //and doesn't return anything but asks user if user wants to proceed or exit
        nextStep();
       }
        //handling user choices
       if(userChoice ==2){
        System.out.println("Please enter first / last / phone number"+
        " / company to get all relevant results >");
        String userInputFirst = sc.next();
        //search method takes array of contacts and string user input as an input
        // returns a array of strings as search results
        String [] searchRes = searchMethod(arrOfContacts, userInputFirst);
        for(int i=0;i<searchRes.length;i++){
            System.out.println(searchRes[i]);
        }
        nextStep();
        //end of userChoice2
       }

       //handling user choices
       if(userChoice ==3){
           //asking user parameters to create a new Contact object
        System.out.println("Please enter first name as one word ");
        String userFistName = sc.next();
        System.out.println("Please enter last name as one word ");
        String userLastName = sc.next();
        System.out.println("Please enter phone number without any chars ");
        String userPhoneNumber = sc.next();
        System.out.println("Please enter comany name as one word ");
        String userCompanyName = sc.next();
        //creating a new contact object and adding it to the list class
        Contact userContact = new Contact(userFistName,userLastName,userPhoneNumber,userCompanyName);
        list1.enterContact(userContact);
       //re-writing the file to avoid formatting problems. I coudl also append the last added item 
       //but after testing on mac and pc program had different behavior
       //for the purpose of this program it seems ok to re-write but in general of it's resource intensive
        try {
            outputStream = new PrintWriter(new FileOutputStream(userFileName));
        } catch (FileNotFoundException e) {
            System.out.println("Error creating/accessing the file stats.txt."); 
            System.exit(0);
        }
        System.out.println("Writing to file");

        for(int i =0;i<list1.listAllContact().length;i++){
            outputStream.println(list1.listAllContact()[i]);
        }
        outputStream.close();
        nextStep();
        //end of option 3
       }

       if(userChoice == 4){
        System.out.println("Please enter phonenumber >");
        String userInputPhoneNum = sc.next();
        //method findbyPhone takes array of contacts and user entry of phone number and returns
        //array of results that need to be removed
        Contact [] contactsToRemove = findByPhone(arrOfContacts, userInputPhoneNum);
        System.out.println("Here are the entries that you are planning to remove:");
        //handling the emply array case
        //techincally this can be graded as a GRADING FEATURE 4 Branching with Selection Control Structures:
        if(contactsToRemove.length==0){
            System.out.println("Nothing was found...");
            nextStep();
        }
        else{
            for(int i=0;i<contactsToRemove.length;i++){
                System.out.println(contactsToRemove[i].toString());
             }
             //asking the user final time to make sure no mistake was made
             System.out.println("Continue? Enter 1 to continue or anything else to go back to main menu");
             int userSelection = sc.nextInt();
             if(userSelection == 1){
                    //calling the list1 class method of removing the contact//
                    //takes array of contacts as input, re-creates a list without them
                    //please see ListOfContacts.java for details on the method and to grade the
                    // GRADING FEATURE 3  Nested Loops
                     list1.removeContact(contactsToRemove);
                     try {
                         outputStream = new PrintWriter(new FileOutputStream(userFileName));
                     } catch (FileNotFoundException e) {
                         System.out.println("Error creating/accessing the file stats.txt."); 
                         System.exit(0);
                     }
                     System.out.println("Writing to file");
                     //re-writing the file again as I mentioned for styling purposes
                     for(int i =0;i<list1.listAllContact().length;i++){
                         outputStream.println(list1.listAllContact()[i]);
                     }
                     outputStream.close();
                     nextStep();
             }
             else{
                 nextStep();
             }
        }
        // end of user choice 4
    }

    if(userChoice ==5){
        System.out.println("Please enter phonenumber to find a contact>");
        String userAddedPhoneNum = sc.next();
        //find by phone is a method that takes array of contacts and user added phone number and 
        //returns an array of contacts to update 
        //this can grade 1 Functional Decomposition
        //wondering if I ever get the good grade for styling!
        //also next line can grade  7 One-dimensional arrays:
        Contact [] contactsToUpdate = findByPhone(arrOfContacts, userAddedPhoneNum);
        System.out.println("Here are the entries that you are planning to update:");
        for(int i=0;i<contactsToUpdate.length;i++){
            System.out.println(contactsToUpdate[i].toString());
         }
         //making sure user is in agreeement with self
         System.out.println("Continue? Enter 1 to continue or anything else to go back to main menu");
        int userNextStep = sc.nextInt();
        if(userNextStep == 1){
            System.out.println("Select what would you like to update: 1- first name, 2- last name, 3- phone number, 4-company name");
            int userSelectionToUpdate = sc.nextInt();
            if(userSelectionToUpdate ==1){
                System.out.println("Please enter new first name");
                String userNewFirst = sc.next();
                for(int i=0;i<contactsToUpdate.length;i++){
                    //GRADING FEATURE - Class Design using Access Modifiers:
                    contactsToUpdate[i].setFirstName(userNewFirst);
                 }
                 list1.updateContactFistName(contactsToUpdate);
                 try {
                    outputStream = new PrintWriter(new FileOutputStream(userFileName));
                } catch (FileNotFoundException e) {
                    System.out.println("Error creating/accessing the file stats.txt."); 
                    System.exit(0);
                }
                System.out.println("Writing to file");
                //again, due to performance issues maac vs pc I had to perform re-writing vs appending. 
                //if I wanted to append, as I did at first, I would add TRUE to FileOutputStream below
                //after fileName
                for(int i =0;i<list1.listAllContact().length;i++){
                    outputStream.println(list1.listAllContact()[i]);
                }
                outputStream.close();
                nextStep();
            }
            if(userSelectionToUpdate ==2){
                System.out.println("Please enter new last name");
                String userNewLast = sc.next();
                for(int i=0;i<contactsToUpdate.length;i++){
                    contactsToUpdate[i].setLastName(userNewLast);
                 }
                 list1.updateContactLastName(contactsToUpdate);
                 try {
                    outputStream = new PrintWriter(new FileOutputStream(userFileName));
                } catch (FileNotFoundException e) {
                    System.out.println("Error creating/accessing the file stats.txt."); 
                    System.exit(0);
                }
                System.out.println("Writing to file");
        
                for(int i =0;i<list1.listAllContact().length;i++){
                    outputStream.println(list1.listAllContact()[i]);
                }
                outputStream.close();
                nextStep();
            }
            if(userSelectionToUpdate ==3){
                System.out.println("Please enter new phone name");
                String userNewPhone = sc.next();
                for(int i=0;i<contactsToUpdate.length;i++){
                    contactsToUpdate[i].setPhoneNumber(userNewPhone);
                 }
                 //self-explanatory method. please see ListOfContacts class 
                 //method takes array of Contact objects and updates phone number
                 list1.updateContactPhoneNumber(contactsToUpdate);

                 try {
                    outputStream = new PrintWriter(new FileOutputStream(userFileName));
                } catch (FileNotFoundException e) {
                    System.out.println("Error creating/accessing the file "+userFileName); 
                    System.exit(0);
                }
                System.out.println("Writing to file"+userFileName);
        
                for(int i =0;i<list1.listAllContact().length;i++){
                    outputStream.println(list1.listAllContact()[i]);
                }
                outputStream.close();
                nextStep();
            }
            if(userSelectionToUpdate ==4){
                System.out.println("Please enter new company name");
                String userNewCompany = sc.next();
                for(int i=0;i<contactsToUpdate.length;i++){
                    contactsToUpdate[i].setCompanyName(userNewCompany);
                 }
                 list1.updateContactCompanyName(contactsToUpdate);
                 try {
                    outputStream = new PrintWriter(new FileOutputStream(userFileName));
                } catch (FileNotFoundException e) {
                    System.out.println("Error creating/accessing the file"+userFileName); 
                    System.exit(0);
                }
                System.out.println("Writing to file");
        
                for(int i =0;i<list1.listAllContact().length;i++){
                    outputStream.println(list1.listAllContact()[i]);
                }
                outputStream.close();
                nextStep();
            }
        }
        else{
            nextStep();
            }
        }
        //handling final option to exit
       if(userChoice == 0){
           System.out.println("Thank you! See you later!");
       }
      //end of handleUserOptions method
    }

    public static int displayMainMenu(Scanner keyboard){
        int userInput;
        System.out.println("Hello! Welcome to the contacts book. Please select an option to continue:\n"+
        "1: List all contacts\n "+
        "2: Search contact by first OR last OR phone number \n "+
        "3: Add new contact to the list \n "+
        "4: Delete a contact using phonenumber \n "+
        "5: Update a contact using phonenumber \n "+
        "0: Exit the app >>");
       
        userInput = keyboard.nextInt();
        while(userInput!=1 && userInput!=2 && userInput!=3 && userInput!=4  && userInput!=5 && userInput!=0){
            
        System.out.println("Please select a valid option 1-5 or 0 to exit");
            
        userInput = keyboard.nextInt();
        }
        return userInput;

    }

    public static void nextStep(){
        Scanner scNext = new Scanner(System.in);
        System.out.println("Do you want to return to main menu or exit? 1 to return to main menu, 0 to exit");
        int userChoice = scNext.nextInt();

        if(userChoice == 1){
            overall();
        }
        else{
            System.out.println("Thank you! Goodbye!");
        }
        //end of nextStep
    }

    public static void listAllContacts(String [] someArr){
        System.out.println("Here are all entered contacts in your file: ");
        for(int i=0;i<someArr.length;i++){
            System.out.println(someArr[i]);
        
        }
        //end of list all contacts
    }

    public static String[] searchMethod(Contact [] arrOfContacts, String userFirst){
        String [] res = new String [arrOfContacts.length];
        for(int i=0, j=0;i<arrOfContacts.length;i++){
            if(userFirst.toLowerCase().equals(arrOfContacts[i].getFirstName().toLowerCase())||
            userFirst.toLowerCase().equals(arrOfContacts[i].getLastName().toLowerCase())||
            userFirst.toLowerCase().equals(arrOfContacts[i].getPhoneNumber().toLowerCase())||
            userFirst.toLowerCase().equals(arrOfContacts[i].getCompanyName().toLowerCase())
            ){
                res[j++]= arrOfContacts[i].toString();
            }
        }

       if(res[0]!=null){
           int counter = 0;
        for(int k=0;k<res.length;k++){
            if(res[k]!=null){
                counter++ ;
            }
        }

        String [] resNotEmpty = new String[counter];
        for(int z=0, a=0;z<res.length;z++){
            if(res[z]!=null){
                resNotEmpty[a++]=res[z];
            }
        }
        return resNotEmpty;
       }
       else {
           String [] resEmpty = new String[1];
           resEmpty[0] = "No such entry found... ";
           return resEmpty;
       }
        
        //end of search method
    }

    public static Contact [] findByPhone(Contact [] arrOfContacts, String phoneNum){
        
        Contact [] results = new Contact[arrOfContacts.length];

        for(int i=0,j=0;i<arrOfContacts.length;i++){
            if(phoneNum.toLowerCase().equals(arrOfContacts[i].getPhoneNumber().toLowerCase())){
                results[j++]=arrOfContacts[i];
            }
        }
        if(results[0]!=null){
            int counter = 0;
            for(int k=0;k<results.length;k++){
                if(results[k]!=null){
                    counter++;
                }
            }

            Contact [] finalRes = new Contact [counter];
            for(int z=0,a=0;z<results.length;z++){
                if(results[z]!=null){
                    finalRes[a++]=results[z];
                }
            }
            return finalRes;
        }
        else{
           Contact [] finalRes2 = new Contact [0];
           return finalRes2;
        }
        //end find by phone
    }

    public static int countLinesInTheFile(String userFileNameSelected){
        Scanner lineCounter = null;
        int counter=0;
        try {
            lineCounter = new Scanner (new FileInputStream(userFileNameSelected));
        } catch (FileNotFoundException e) {
            System.out.println("Here is a filenotfound exception");
            System.exit(0);
        }
        while(lineCounter.hasNextLine()){
            counter++;
            lineCounter.nextLine();
        }
        return counter;
        //end of count lines in the file
    }
    //end of class
}
