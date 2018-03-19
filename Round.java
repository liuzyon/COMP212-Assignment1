public class Round {

    public static int round = 0;

    public Round() {
        round++;
    }

    public void runLCR() {
        for(LCRProcessor processor : Main.LCRProcessorList) {
            if(processor.getIsFlooding()) {
                processor.floodLeaderInformation();
            }
            if(!processor.getIsTerminate()) {
                processor.run(round);
            }
        }

        for(LCRProcessor processor : Main.LCRProcessorList) {
            if (processor.getReceivedMessage() != null) {
                if(processor.getReceivedMessage().getLeaderInformation().equals("")) {
                    processor.setInID(processor.getReceivedMessage().getID());
                    processor.setReceivedMessage(null);
                }
                else {
                    processor.setAcceptLeaderInformation(processor.getReceivedMessage().getLeaderInformation());
                    processor.setReceivedMessage(null);
                    processor.setIsFlooding(true);
                }
            }
        }

    }

    public void runHS() {
        for(HSProcessor processor : Main.HSProcessorList) {
            if(processor.getIsFlooding()) {
                processor.floodLeaderInformation();
            }
            if(!processor.getIsTerminate()) {
                processor.run(round);
            }
        }

        for(HSProcessor processor : Main.HSProcessorList) {
            if(processor.getReceivedCounterClockwiseBuffer() != null && !processor.getReceivedCounterClockwiseBuffer().getLeaderInformation().equals("")) {
                processor.setReceivedCounterClockwise(processor.getReceivedCounterClockwiseBuffer());
                processor.setAcceptLeaderInformation(processor.getReceivedCounterClockwise().getLeaderInformation());
                processor.setReceivedCounterClockwiseBuffer(null);
                processor.setFlooding(true);
            }
            else {
                if(processor.getReceivedClockwiseBuffer()!=null) {
                    processor.setReceivedClockwise(processor.getReceivedClockwiseBuffer());
                    processor.setReceivedClockwiseBuffer(null);
                }
                if(processor.getReceivedCounterClockwiseBuffer()!=null) {
                    processor.setReceivedCounterClockwise(processor.getReceivedCounterClockwiseBuffer());
                    processor.setReceivedCounterClockwiseBuffer(null);
                }
            }
        }
    }

    public LCRProcessor checkLCRLeader() {
        LCRProcessor leaderProcessor = null;
        for (LCRProcessor processor: Main.LCRProcessorList) {
            if (processor.getStatus().equals("leader")) {
                leaderProcessor = processor;
            }
        }
        return leaderProcessor;
    }

    public HSProcessor checkHSLeader() {
        HSProcessor leaderProcessor = null;
        for (HSProcessor processor: Main.HSProcessorList) {
            if (processor.getStatus().equals("leader")) {
                leaderProcessor = processor;
            }
        }
        return leaderProcessor;
    }

    public int getRound() {
        return round;
    }
}

