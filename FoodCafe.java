import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class FoodCafe {
    static Scanner sc; 	//Static scanner input
    static ListQueue productionLine;// The cooking queue

    public static void main(String args[]) throws InvalidRangeInputException {
        productionLine = new ListQueue();
        Random rnd = new Random();
        sc = new Scanner(System.in);    // start setting

        System.out.println("------------ SETUP SIMULATION ENVIRONMENT-------------"); // welcome message
        int InputRunMin = returnInputMin();  // User input the mines of the program

        if(InputRunMin==0)  rnd.setSeed(ReturnUserInputSeed()); // random model,user need to input seed and system print relevant message

        int runMin = InputRunMin==0?4:InputRunMin;  // Divide the running time of random and normal, random model is 4, normal model is follow up inputRunMin

        System.out.println("----------------- START SIMULATION -------------------"); // welcome message
        int orderCount = 0; //count the number of total order
        int servedCustomerCount = 0;    //count the number of served customer
        for(int x=0;x<runMin;x++){    // start to process order
            boolean haveInsertOrder = false; // identify the user have inserted an order or not and reset the setting
            int SelectOrderNo = -1;

            while(SelectOrderNo!=0){
                SelectOrderNo = ordering(InputRunMin,rnd);
                if(SelectOrderNo!=0) {
                    if(SelectOrderNo!=2) // SelectOrderNo 2 = soft drink which would not to add enqueue because his waiting minutes is zero,
                        productionLine.enqueue(ProductNoTransferProductItem(SelectOrderNo));
                    orderCount++; // times of add product
                    haveInsertOrder = true;
                }
            }

            if(haveInsertOrder) servedCustomerCount++; // user have insert order before, add the number of servedCustomerCount
            processQueueEveryMin(productionLine);// based on requirement, process the queue every min
            printEndMessageEveryMin(x,productionLine); // print the message about the queue and minute every min
        }
        printEndMessage(runMin,servedCustomerCount,orderCount,(productionLine.count()));
    }

    public static void processQueueEveryMin(ListQueue productionLine){
        if(!productionLine.empty()) {
            ((FoodItem)productionLine.front()).reduceCookTime(); // reduce the cooktime of head
            if (((FoodItem)productionLine.front()).getRemainCookTime() == 0)  productionLine.dequeue();// if the head of cookTime is zero, the head will be removed
        }
    }

    public static void PrintHyphenLine(){
        System.out.println("------------------------------------------------------");
    }

    public static void PrintEqualSignLine(){
        System.out.println("======================================================");
    }

    public static int ReturnUserInputSeed(){
        sc = new Scanner(System.in);
        int seed;
        try {
            System.out.print("Input seed number:");
            seed = sc.nextInt();
        }
            catch (InputMismatchException x){
                System.out.println("Wrong input!");
                return ReturnUserInputSeed();
            }
        System.out.println("Max simulation time (min) is 4");
        return seed;
    }

    public static int returnInputMin() throws InvalidRangeInputException{
        System.out.print("Input simulation length (min):");
        sc = new Scanner(System.in);
        try {
            int inputNumber = sc.nextInt();
            if(inputNumber<0){throw new InvalidRangeInputException();} // only allow to input >=0
            return inputNumber;
        }
        catch (InputMismatchException e){
            System.out.println("Wrong input!");
            return returnInputMin();
        }
        catch (InvalidRangeInputException x){
            System.out.println(x.getMessage());
            return returnInputMin();
        }
    }

    public static int ordering(int InputRunMin, Random rnd) throws InvalidRangeInputException{
        sc = new Scanner(System.in);
        try {
            System.out.print("Order item [1-coffee,2-Soft Drink, 3-Hot Dog, 4-French fries, 0-Finsih]:");
            if(InputRunMin ==0) { // random model
                int randomTemp = rnd.nextInt(5);    //generate random number 0-5
                System.out.print(randomTemp+"\n");
                return randomTemp;
            }
            int inputOrderNumber = sc.nextInt();
            if(inputOrderNumber<0||inputOrderNumber>4){throw new InvalidRangeInputException();} // only allow to input 0-4
            return inputOrderNumber;
        }
        catch (InvalidRangeInputException x){
            System.out.println(x.getMessage());
            return ordering(InputRunMin, rnd);
        }
        catch (InputMismatchException e){
            System.out.println("Wrong input!");
            return ordering(InputRunMin, rnd);
        }
    }

    public static FoodItem ProductNoTransferProductItem(int ProductNo){
        return switch (ProductNo) { // According to the productNo, return the product name and cookTime
            case 1 -> new FoodItem("coffee", 1);
            case 2 -> new FoodItem("Soft Drink", 0);
            case 3 -> new FoodItem("Hot Dog", 2);
            case 4 -> new FoodItem("French fries", 3);
            default -> new FoodItem("Error", 0);
        };
    }

    public static void printEndMessageEveryMin(int x, ListQueue productionLine){
        PrintHyphenLine();
        System.out.println("After "+ (x+1) +" minute(s):");
        System.out.println("Cooking queue# " + productionLine);
        PrintEqualSignLine();
    }

    public static void printEndMessage(int min, int servedCustomerCount,int orderCount, int currentNotCompletedOrderNo){
        System.out.println("\n----------------- END OF SIMULATION ------------------");
        System.out.println("Total minute simulated: "+ min +" minutes");
        System.out.println("Number of customer served: " + servedCustomerCount +" customer(s)");
        System.out.println("Item ordered during the simulation: "+ orderCount);
        System.out.println("Outstanding item at the end of simulation: " + currentNotCompletedOrderNo);
        PrintHyphenLine();
    }
}