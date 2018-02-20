/**
 * Created by Jacob Cornett on 2/19/2018.
 */
public class ScheduleHelpers {

    public static void printInput(int[][] input_table){

        int i = 1;
        int j = 0;
        System.out.println("Printing the input data: ");
        for(int [] array : input_table){
            System.out.println("Person " + i + " Beginning Printing.");
            for(int n : array){
                System.out.print(n + " ");
                j++;
                if((j % Parameters.numDays) == 0){
                    System.out.println();
                }
            }
            System.out.println("Person " + i + " Finished Printing.");
            i++;
        }
        System.out.println("Input Table Finished Printing.");
    }

    public static void printChromo(Chromo chromo){
        System.out.println("Chromosome Printing: ");

        for(int [] array : chromo.chromo){
            for(int n : array){
                System.out.print(n + " ");
            }
            System.out.println();
        }
        System.out.println("Chromosome Finished Printing.");
    }

    public static void printFitness(Scheduler s){
        System.out.println("Printing The Scheduler Fitness Values: ");

        System.out.println("Count Invalid: " + s.countInvalid);
        System.out.println("Count Duplicates: (Time Slots taken)");
        for(int i = 0; i < Parameters.geneSize * Parameters.numGenes; i++){
           System.out.print(s.countDuplicates[i] + " ");
           if((i % Parameters.numGenes) == 6){
               System.out.println();
           }
        }
        System.out.println("Fitness Finished Printing.");
    }
}
