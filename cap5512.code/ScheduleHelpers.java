/**
 * Created by Jacob Cornett on 2/19/2018.
 */
public class ScheduleHelpers {

    public static void printInput(int[][] input_table){

        System.out.println();
        for(int [] array : input_table){
            for(int n : array){
                System.out.print(n + " ");
            }
            System.out.println();
        }

    }
}
