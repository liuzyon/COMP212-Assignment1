import java.util.ArrayList;

public class LCRSimulator {

    public void start() {
        boolean isEnd = false;
        LCRProcessor leaderProcessor = null;
        Round round = null;
        while(!isEnd) {
            round = new Round();
            round.runLCR();
            leaderProcessor = round.checkLCRLeader();
            if (leaderProcessor != null && checkTerminate() && checkKnow()) {
                isEnd = true;
            }
        }

        int maximumNodeNumber = 0;
        for(LCRProcessor processor : Main.LCRProcessorList) {
            if(processor.getMyID() > maximumNodeNumber) {
                maximumNodeNumber = processor.getMyID();
            }
        }

        System.out.println("Number of maximum ID: " + maximumNodeNumber);
        System.out.println("Number of leader processor: " + leaderProcessor.getMyID());
        System.out.println("Number of node: " + Main.getProcessorNumber());
        System.out.println("Number of Terminate Node: " + calculateTerminateNodeNumber());
        System.out.println("Number of round: " + round.getRound());
        System.out.println("Number of message: " + LCRProcessor.messageAmount);

    }


    public boolean checkKnow() {
        boolean isAllNodeKnow = true;
        for(LCRProcessor processor: Main.LCRProcessorList) {
            if(!processor.isKnowLeader()) {
                isAllNodeKnow = false;
            }
        }
        return isAllNodeKnow;
    }

    public boolean checkTerminate() {
        boolean isAllTerminated = true;
        for(LCRProcessor processor: Main.LCRProcessorList) {
            if(!processor.getIsTerminate()) {
                isAllTerminated = false;
            }
        }
        return isAllTerminated;
    }

    public int calculateTerminateNodeNumber() {
        int numberOfTerminateNode = 0;
        for (LCRProcessor processor : Main.LCRProcessorList) {
            if (processor.getIsTerminate()) numberOfTerminateNode++;
        }

        return numberOfTerminateNode;
    }
}
