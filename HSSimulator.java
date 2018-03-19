public class HSSimulator {
    public void start() {
        boolean isEnd = false;
        HSProcessor leaderProcessor = null;
        Round round = null;
        while(!isEnd) {
            round = new Round();
            round.runHS();
            leaderProcessor = round.checkHSLeader();
            if (leaderProcessor != null && checkKnow() && checkTerminate()) {
                isEnd = true;
            }
        }


        int maximumNodeNumber = 0;
        for(HSProcessor processor : Main.HSProcessorList) {
            if(processor.getMyID() > maximumNodeNumber) {
                maximumNodeNumber = processor.getMyID();
            }
        }

        System.out.println("Number of maximum ID: " + maximumNodeNumber);
        System.out.println("Number of leader processor: " + leaderProcessor.getMyID());
        System.out.println("Number of node: " + Main.getProcessorNumber());
        System.out.println("Number of Terminate Node: " + calculateTerminateNodeNumber());
        System.out.println("Number of round: " + round.getRound());
        System.out.println("Number of message: " + HSProcessor.getMessageAmount());
    }


    public boolean checkKnow() {
        boolean isAllNodeKnow = true;
        for(HSProcessor processor: Main.HSProcessorList) {
            if(!processor.isKnowLeader()) {
                isAllNodeKnow = false;
            }
        }
        return isAllNodeKnow;
    }


    public boolean checkTerminate() {
        boolean isAllTerminated = true;
        for(HSProcessor processor: Main.HSProcessorList) {
            if(!processor.getIsTerminate()) {
                isAllTerminated = false;
            }
        }
        return isAllTerminated;
    }

    public int calculateTerminateNodeNumber() {
        int numberOfTerminateNode = 0;
        for (HSProcessor processor : Main.HSProcessorList) {
            if (processor.getIsTerminate()) numberOfTerminateNode++;
        }

        return numberOfTerminateNode;
    }


}
