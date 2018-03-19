import java.util.ArrayList;
import java.util.Random;

public class HSProcessor extends Processor{
    private static int messageAmount;
    private HSProcessor clockwiseNeighbor;
    private HSProcessor counterClockwiseNeighbor;
    private int myID;
    private Message sendClock;
    private Message sendCounterClock;
    private Message receivedClockwise;
    private Message receivedCounterClockwise;
    private Message receivedClockwiseBuffer;
    private Message receivedCounterClockwiseBuffer;
    private boolean isTerminated;
    private boolean isFlooding;
    private String acceptLeaderInformation;
    private String status;
    private int phase;

    public HSProcessor(int processorNumber, ArrayList<HSProcessor> processorList) {
        this.myID = allocateID(processorNumber, processorList);
        this.sendClock = new Message(myID, "out", 1);
        this.sendCounterClock = new Message(myID, "out", 1);
        this.status = "unknown";
        this.phase = 0;
        this.acceptLeaderInformation = "";
        this.isTerminated = false;
        this.isFlooding = false;
    }

    public void run(int round) {
        if(round == 1) {
            send();
        }
        else{
            receive();
            send();
        }
    }

    public void setReceivedClockwiseBuffer(Message message) {
        this.receivedClockwiseBuffer = message;
    }

    public void setReceivedCounterClockwiseBuffer(Message message) {
        this.receivedCounterClockwiseBuffer = message;
    }

    public Message getReceivedClockwiseBuffer() {
        return receivedClockwiseBuffer;
    }

    public Message getReceivedCounterClockwiseBuffer() {
        return receivedCounterClockwiseBuffer;
    }

    private void send() {
        if(sendClock!=null) {
            sendClockwise(clockwiseNeighbor);
            sendClock = null;
        }
        if(sendCounterClock!=null) {
            sendCounterClockwise(counterClockwiseNeighbor);
            sendCounterClock = null;
        }
    }

    private void sendClockwise(HSProcessor processor) {
        processor.setReceivedCounterClockwiseBuffer(sendClock);
        messageAmount++;
    }

    private void sendCounterClockwise(HSProcessor processor) {
        processor.setReceivedClockwiseBuffer(sendCounterClock);
        messageAmount++;
    }


    public void floodLeaderInformation() {
        sendLeaderInformation(clockwiseNeighbor, acceptLeaderInformation);
        messageAmount++;
        isTerminated = true;
    }

    public void sendLeaderInformation(HSProcessor processor, String leaderInformation) {
        processor.setReceivedCounterClockwiseBuffer(new Message(leaderInformation));
    }

    public void receive() {
        if (receivedCounterClockwise != null) {
            processMesFromCounterClockwise(receivedCounterClockwise);
        }

        if (receivedClockwise != null) {
            processMesFromClockwise(receivedClockwise);
        }

        if(receivedClockwise != null && receivedCounterClockwise != null) {
            if (receivedClockwise.getID() == myID && receivedClockwise.getDirection().equals("in") && receivedClockwise.getHopCount() == 1 && receivedCounterClockwise.getID() == myID && receivedCounterClockwise.getDirection().equals("in") && receivedCounterClockwise.getHopCount() == 1) {
                phase++;
                sendClock = new Message(myID, "out", (int)Math.pow(2, phase));
                sendCounterClock = new Message(myID, "out", (int)Math.pow(2, phase));
            }
        }
        receivedClockwise = null;
        receivedCounterClockwise = null;
    }

    public void setFlooding(boolean isFlooding) {
        this.isFlooding = isFlooding;
    }



    public void processMesFromClockwise(Message message) {
        int inID = message.getID();
        String direction = message.getDirection();
        int hopCount = message.getHopCount();

        if (direction.equals("out")) {
            if (inID > myID && hopCount > 1) {
                sendCounterClock = new Message(inID, "out", hopCount-1);
            }
            else if (inID > myID && hopCount == 1) {
                sendClock = new Message(inID, "in", 1);
            }
            else if (inID == myID) {
                status = "leader";
                acceptLeaderInformation = Integer.toString(myID);
                isFlooding = true;
            }
        }

        if (direction.equals("in") && hopCount==1 && inID != myID) {
            sendCounterClock = new Message(inID,"in", 1);
        }
    }

    public void processMesFromCounterClockwise(Message message) {
        int inID = message.getID();
        String direction = message.getDirection();
        int hopCount = message.getHopCount();

        if (direction.equals("out")) {
            if (inID > myID && hopCount > 1) {
                sendClock = new Message(inID, "out", hopCount-1);
            }
            else if (inID > myID && hopCount ==1 ) {
                sendCounterClock = new Message(inID, "in", 1);
            }
            else if (inID == myID) {
                status = "leader";
                acceptLeaderInformation = Integer.toString(myID);
                isFlooding = true;
            }
        }

        if (direction.equals("in") && hopCount==1 && inID != myID) {
            sendClock = new Message(inID, "in", 1);
        }
    }

    private static int allocateID(int processorNumber, ArrayList<HSProcessor> processorList) {
        boolean isUnique = false;
        Random rm = new Random();
        int allocatedID = -1;
        while(!isUnique) {
            allocatedID = rm.nextInt(3 * processorNumber);
            if(isIDUnique(allocatedID, processorList)){
                isUnique = true;
            }
        }
        return allocatedID;
    }

    private static boolean isIDUnique(int allocatedID, ArrayList<HSProcessor> processorList) {
        for(HSProcessor processor: processorList){
            if (processor.myID == allocatedID)
                return false;
        }
        return true;
    }


    public void setReceivedClockwise(Message message) {
        this.receivedClockwise = message;
    }

    public void setReceivedCounterClockwise(Message message) {
        this.receivedCounterClockwise = message;
    }

    public void setClockwiseNeighbor(HSProcessor processor) {
        this.clockwiseNeighbor = processor;
    }

    public void setCounterClockwiseNeighbor(HSProcessor processor) {
        this.counterClockwiseNeighbor = processor;
    }

    public String getStatus() {
        return status;
    }

    public int getMyID() {
        return myID;
    }

    public static int getMessageAmount() {
        return messageAmount;
    }

    public boolean getIsTerminate() {
        return isTerminated;
    }

    public boolean getIsFlooding() {
        return isFlooding;
    }

    public Message getReceivedCounterClockwise() {
        return receivedCounterClockwise;
    }

    public void setAcceptLeaderInformation(String leaderInformation) {
        acceptLeaderInformation = leaderInformation;
    }

    public boolean isKnowLeader() {
        return !acceptLeaderInformation.equals("");
    }

}
