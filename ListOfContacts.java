import java.io.Console;

import javax.sound.sampled.Line;

public class ListOfContacts {
    private Contact [] list;
    private int totalContactsInTheList;
    private int backNum;
    
    public ListOfContacts(){
        this.Contact = new Contact[4];
        this.totalContactsInTheList = 0;
        this.backNum = 0;
    }

    public ListOfContacts(Contact [] newArr){
        this.list = new Contact[newArr.length];

        for(int i =0;i<newArr.length;i++){
            this.list[i]=newArr[i];
        }
        this.totalContactsInTheList = list.length;
        this.backNum = list.length-1;
    }

    public int numberOfContacts(){
        return this.totalContactsInTheList;
    }

    public boolean isEmpty(){
        if(this.totalContactsInTheList==0){
            return true;
        }
        else{
            return false;
        }
        //end of isEmpty
    }       

    public boolean isFull(){
        
        if(this.totalContactsInTheList==this.list.length){
            return true;
        }
        else{
            return false;
        }
        //end of isFull
    }     

    public String enterContact(Contact newContact){
        if(isFull()){
            Contact [] newList = new Contact[this.list.length+1];
            for(int i=0;i< this.list.length;i++){
                newList[i]=this.list[i];
            }
            this.list = newList;
            this.list[this.list.length-1]=newContact;
            this.totalContactsInTheList = this.list.length;
            this.backNum = list.length-1;
        }
        else{
            this.list[this.backNum]=newContact;
            this.totalContactsInTheList++;
        }
    
        return newContact.getFirstName()+ " "+ newContact.getLastName();
        //end of enterContact
    }

    
    public Contact [] removeContact(Contact [] contactToRemove ){
        
        Contact [] listAfterRemoving = new Contact[this.list.length-contactToRemove.length];

        for(int i=0;i<contactToRemove.length;i++){
            for(int j=0, k=0;k<this.list.length;k++){
                if(!(contactToRemove[i].getPhoneNumber().equals(this.list[k].getPhoneNumber()))){
                    listAfterRemoving[j]=this.list[k];
                    j++;
                    
                }
            }
            
        }
        this.list = listAfterRemoving;
        return listAfterRemoving;
        //end of remove contact
    }

    public Contact [] updateContactFistName(Contact [] contactsToUpdate){
        
        for(int i=0;i<this.list.length;i++){
            for(int j=0;j<contactsToUpdate.length;j++){
                if(
                    ( this.list[i].getLastName().equals(contactsToUpdate[j].getLastName() ) ) &&
                    ( this.list[i].getPhoneNumber().equals(contactsToUpdate[j].getPhoneNumber() ) ) &&
                    ( this.list[i].getCompanyName().equals(contactsToUpdate[j].getCompanyName() ) )
                     ) {
                    this.list[i].setFirstName(contactsToUpdate[j].getFirstName());
                }
            }
        }
        return this.list;
    }


    public Contact [] updateContactLastName(Contact [] contactsToUpdate){
        
        for(int i=0;i<this.list.length;i++){
            for(int j=0;j<contactsToUpdate.length;j++){
                if(
                    ( this.list[i].getFirstName().equals(contactsToUpdate[j].getFirstName() ) ) &&
                    ( this.list[i].getPhoneNumber().equals(contactsToUpdate[j].getPhoneNumber() ) ) &&
                    ( this.list[i].getCompanyName().equals(contactsToUpdate[j].getCompanyName() ) )
                     ) {
                    this.list[i].setLastName(contactsToUpdate[j].getLastName());
                }
            }
        }
        return this.list;
    }


    public Contact [] updateContactPhoneNumber(Contact [] contactsToUpdate){
        
        for(int i=0;i<this.list.length;i++){
            for(int j=0;j<contactsToUpdate.length;j++){
                if(
                    ( this.list[i].getFirstName().equals(contactsToUpdate[j].getFirstName() ) ) &&
                    ( this.list[i].getLastName().equals(contactsToUpdate[j].getLastName() ) ) &&
                    ( this.list[i].getCompanyName().equals(contactsToUpdate[j].getCompanyName() ) )
                     ) {
                    this.list[i].setPhoneNumber(contactsToUpdate[j].getPhoneNumber());
                }
            }
        }
        return this.list;
    }


    public Contact [] updateContactCompanyName(Contact [] contactsToUpdate){
        
        for(int i=0;i<this.list.length;i++){
            for(int j=0;j<contactsToUpdate.length;j++){
                if(
                    ( this.list[i].getFirstName().equals(contactsToUpdate[j].getFirstName() ) ) &&
                    ( this.list[i].getLastName().equals(contactsToUpdate[j].getFirstName() ) ) &&
                    ( this.list[i].getPhoneNumber().equals(contactsToUpdate[j].getPhoneNumber() ) )
                     ) {
                    this.list[i].setCompanyName(contactsToUpdate[j].getCompanyName());
                }
            }
        }
        return this.list;
    }

    public String [] listAllContact(){
        String [] resAllContacts = new String [this.list.length];
        for(int i =0;i<this.list.length;i++){
            String entry = this.list[i].getFirstName()+" "+
            this.list[i].getLastName()+" "+
            this.list[i].getPhoneNumber()+" "+
            this.list[i].getCompanyName();
            resAllContacts[i]= entry;
        }
        return resAllContacts;
    }

//end of ListOfContacts
}
