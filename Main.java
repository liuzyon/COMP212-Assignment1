
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<LCRProcessor> LCRProcessorList = new ArrayList<LCRProcessor>();
    public static ArrayList<HSProcessor> HSProcessorList = new ArrayList<HSProcessor>();

    private static int processorNumber;
    private static int selectedAlgorithm;


    public static void main(String[] args) {
        setSelectedAlgorithm();
        setProcessorNumber();
        initialise(selectedAlgorithm);
        startSimulator(selectedAlgorithm);

//
//        printProcessorConfiguration();
//
//        printProccessorInformation();
    }

    private static void setProcessorNumber() {
        Scanner kb = new Scanner(System.in);
        boolean isValid = false;
        int inputNumber;

        while (!isValid) {
            System.out.print("Please input the number of processor\n");
            inputNumber = kb.nextInt();

            if (inputNumber > 0) {
                processorNumber = inputNumber;
                isValid = true;
            }
            else {
                System.out.println("Invalid input");
            }
        }
    }

    private static void setSelectedAlgorithm() {
        Scanner kb = new Scanner(System.in);
        boolean isValid = false;
        int inputNumber;

        while (!isValid) {
            System.out.print("Please select the algorithm you want to simulate:\n1. LCR Algorithm\n2. HS Algorithm\n");
            inputNumber = kb.nextInt();
            if (inputNumber > 0) {
                selectedAlgorithm = inputNumber;
                isValid = true;
            }
            else {
                System.out.println("Invalid input");
            }
        }
    }

    private static void initialise(int selectedAlgorithm) {

        if (selectedAlgorithm == 1) {
            for(int i=0; i<processorNumber; i++) {
                LCRProcessor newProcessor = new LCRProcessor(processorNumber, LCRProcessorList);
                LCRProcessorList.add(newProcessor);
            }


            for(int i=1; i<processorNumber-1; i++) {
                LCRProcessor processor = LCRProcessorList.get(i);
                processor.setClockwiseNeighbor(LCRProcessorList.get(i+1));
                processor.setCounterClockwiseNeighbor(LCRProcessorList.get(i-1));
            }

            LCRProcessor firstProcessor = LCRProcessorList.get(0);
            firstProcessor.setClockwiseNeighbor(LCRProcessorList.get(1));
            firstProcessor.setCounterClockwiseNeighbor(LCRProcessorList.get(processorNumber-1));

            LCRProcessor lastProcessor = LCRProcessorList.get(processorNumber-1);
            lastProcessor.setClockwiseNeighbor(LCRProcessorList.get(0));
            lastProcessor.setCounterClockwiseNeighbor(LCRProcessorList.get(processorNumber-2));
        }
        else if (selectedAlgorithm == 2){
            for(int i=0; i<processorNumber; i++) {
                HSProcessor newProcessor = new HSProcessor(processorNumber, HSProcessorList);
                HSProcessorList.add(newProcessor);
            }

            for(int i=1; i<processorNumber-1; i++) {
                HSProcessor processor = HSProcessorList.get(i);
                processor.setClockwiseNeighbor(HSProcessorList.get(i+1));
                processor.setCounterClockwiseNeighbor(HSProcessorList.get(i-1));
            }

            HSProcessor firstProcessor = HSProcessorList.get(0);
            firstProcessor.setClockwiseNeighbor(HSProcessorList.get(1));
            firstProcessor.setCounterClockwiseNeighbor(HSProcessorList.get(processorNumber-1));

            HSProcessor lastProcessor = HSProcessorList.get(processorNumber-1);
            lastProcessor.setClockwiseNeighbor(HSProcessorList.get(0));
            lastProcessor.setCounterClockwiseNeighbor(HSProcessorList.get(processorNumber-2));
        }
    }



    public static void startSimulator(int selectedAlgorithm) {
        if (selectedAlgorithm == 1) {
            LCRSimulator simulator = new LCRSimulator();
            simulator.start();
        }
        else if (selectedAlgorithm == 2) {
            HSSimulator simulator = new HSSimulator();
            simulator.start();
        }
    }

    public static int getProcessorNumber() {
        return processorNumber;
    }

}
