
import java.util.ArrayList;
import java.util.Random;

public class LCRProcessor extends Processor{
    public static int messageAmount = 0;
    private LCRProcessor clockwiseNeighbor;
    private LCRProcessor CounterClockwiseNeighbor;
    private int myID;
    private int sendID;
    private int inID;
    private String status;
    private String acceptLeaderInformation;
    private Message receivedMessage;
    private boolean isTerminated;
    private boolean isFlooding;
    public LCRProcessor(int processorNumber, ArrayList<LCRProcessor> processorList) {
        this.myID = allocateID(processorNumber, processorList);
        this.sendID = myID;
        this.status = "unknown";
        this.acceptLeaderInformation = "";
        this.isTerminated = false;
        this.isFlooding = false;
    }

    public void run(int round) {
        if(round == 1) {
            sendMessage(clockwiseNeighbor);
        }
        else {
            if (inID > 0) {
                if(inID > myID) {
                    sendID = inID;
                    sendMessage(clockwiseNeighbor);
                }
                else if(inID == myID) {
                    status = "leader";
                    acceptLeaderInformation = Integer.toString(myID);
                    isFlooding = true;
                }
                inID = -1;
            }
        }
    }

    public void floodLeaderInformation() {
        sendLeaderInformation(clockwiseNeighbor, acceptLeaderInformation);
        isTerminated = true;
        isFlooding = false;
    }

    public void sendLeaderInformation(LCRProcessor processor, String leaderInformation) {
        processor.setReceivedMessage(new Message(leaderInformation));
        messageAmount++;
    }

    public void sendMessage(LCRProcessor processor) {
        processor.setReceivedMessage(new Message(sendID));
        messageAmount++;
    }

    private static boolean isIDUnique(int allocatedID, ArrayList<LCRProcessor> processorList) {
        for(LCRProcessor processor: processorList){
            if (processor.myID == allocatedID)
                return false;
        }
        return true;
    }

    private static int allocateID(int processorNumber, ArrayList<LCRProcessor> processorList) {
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

    public int getMyID() {
        return myID;
    }

    public void setInID(int inID) {
        this.inID = inID;
    }

    public void setAcceptLeaderInformation(String leaderInformation) {
        acceptLeaderInformation = leaderInformation;
    }

    public void setReceivedMessage(Message message) {
        this.receivedMessage = message;
    }

    public String getStatus() {
        return status;
    }

    public void setClockwiseNeighbor(LCRProcessor clockwiseNeighbor) {
        this.clockwiseNeighbor = clockwiseNeighbor;
    }

    public void setCounterClockwiseNeighbor(LCRProcessor CounterClockwiseNeighbor) {
        this.CounterClockwiseNeighbor = CounterClockwiseNeighbor;
    }

    public Message getReceivedMessage() {
        return  receivedMessage;
    }

    public boolean getIsTerminate() {
        return isTerminated;
    }

    public boolean getIsFlooding() {
        return isFlooding;
    }

    public void setIsFlooding(boolean isFlooding) {
        this.isFlooding = isFlooding;
    }

    public boolean isKnowLeader() {
        return !acceptLeaderInformation.equals("");
    }

}

