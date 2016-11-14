package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.*;

import javax.swing.*;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import static cs414.a5.nmalensk.client.TextInput.pressEnter;
import static cs414.a5.nmalensk.client.TextInput.userInput;

public class CustomerUI {
    private ParkingGarageInterface pGI;
    private GarageGateInterface gGI;
    private OccupancySignInterface oSI;
    private TransactionLogInterface tLI;
    private BigDecimal ticketPrice = BigDecimal.ZERO;

    public CustomerUI(ParkingGarageInterface pGI, GarageGateInterface gGI) throws RemoteException {
        this.pGI = pGI;
        this.gGI = gGI;
        oSI = pGI.getSign();
        tLI = pGI.getTLog();
    }

    PhysicalGarageGate physicalGate = new PhysicalGarageGate();
    PaymentHandler handler = new PaymentHandler();

    public void enterGarage(GateGUIInterface menu) throws RemoteException {
        int newTicket = gGI.createTicket(tLI, ticketPrice);
        physicalGate.printTicket(newTicket, tLI.retrieveEntryTime(newTicket));
        physicalGate.openGate(menu);
//        JOptionPane.showMessageDialog(null, "Gate is open, please enter");
        gGI.admitCustomer(oSI);
        tLI.updateGates();
        physicalGate.closeGate(menu);
    }

    private void exitGarage(GateGUIInterface menu) throws RemoteException {
        System.out.println("Please enter your ticket ID or 1 for a lost/unavailable ticket:");
        checkTicketValidity();
        physicalGate.openGate(menu);
        pressEnter();
        physicalGate.closeGate(menu);
    }

    private void checkTicketValidity() throws RemoteException {
        while (true) {
            try {
                int exitTicket = Integer.parseInt(userInput());
                if (exitTicket == 1) {
                    int lostTicket = gGI.createAndUpdateLostTicket(tLI);
                    handler.promptForTotal(tLI, lostTicket);
                    gGI.expelCustomer(oSI);
                    break;
                } else if(gGI.checkTicket(tLI, exitTicket)) {
                    System.out.println("Ticket accepted!");
                    handler.promptForTotal(tLI, exitTicket);
                    gGI.expelCustomer(oSI);
                    break;
                } else {
                    System.out.println("Not a valid ticket ID, please re-enter or enter 1 for " +
                            "a lost/unavailable ticket:");
                }
            } catch (NumberFormatException e){
                System.out.println("Please enter a valid ticket ID format (numbers only):");
            }
        }
    }

    private boolean thereAreStillOpenSpaces() throws RemoteException {
        if (oSI.getOpenSpaces() > 0) return true;
        return false;
    }

}