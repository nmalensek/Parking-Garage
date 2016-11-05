package cs414.a5.nmalensk.domain_logic;


public class PhysicalGarageGate {

    public void openGate(String direction) {
        System.out.println("******** Gate is opening ********");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("******** Gate is open, please " + direction +
                " garage (press enter) ********");
    }

    public void closeGate() {
        System.out.println("******** Gate is closing ********");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("******** Gate is closed ********\n");
    }

    public void printTicket(int currentTicketID) {
        System.out.println("-------------------------");
        System.out.println("  " + "entryTime");
        System.out.println("  TicketID:  " + currentTicketID);
        System.out.println("-------------------------");
        System.out.println();
        System.out.println("Please take your ticket (press enter). " +
                "The ticket ID must be presented upon exit.");
    }

    public void fullMessage() {
        System.out.println("No more space available in the garage, please wait for someone to exit!");
        System.out.println();
        System.out.println("Existing customers - enter 2 to exit the garage");
    }


}