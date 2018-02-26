import java.io.FileWriter;
import java.util.Arrays;

/**
 * Created by Jacob Cornett on 2/19/2018.
 */
public class Scheduler extends FitnessFunction{

/*******************************************************************************
 *                            INSTANCE VARIABLES                                *
 *******************************************************************************/

    //Counters to keep track of whether we've seen duplicates across all time slots
    //Or if we've seen a slot taken by a person that had a 0 in that time slot

    int[] countDuplicates = new int[Parameters.geneSize * Parameters.numGenes];
    int countInvalid;

    int rep;

    //The input table which contains preferences
    int[][] table;

/*******************************************************************************
 *                            STATIC VARIABLES                                  *
 *******************************************************************************/


/*******************************************************************************
 *                              CONSTRUCTORS                                    *
 *******************************************************************************/

    public Scheduler(int [][] input_table) {

        this.table = input_table;
        this.countDuplicates = new int[Parameters.geneSize * Parameters.numGenes];
        this.name = "Scheduler Problem";
    }

/*******************************************************************************
 *                                MEMBER METHODS                                *
 *******************************************************************************/


//  COMPUTE A CHROMOSOME'S RAW FITNESS *************************************


    //TODO: Change Chromo to work with new fitness function
    //TODO: Write new fitness function
    @Override
    public void doRawFitness(Chromo X){

        if(Parameters.rep == 1) {
        }
        if(Parameters.rep == 2) {
        }
        //@mylist
        if(Parameters.rep == 3) {
            int[] track_person = new int[7];
            Arrays.fill(track_person, 0);
            int[] track_timeslot = new int[35];
            Arrays.fill(track_timeslot, 0);
            //evaluate items
            for (int i = 0; i < X.chromo.size(); i++){
                int x = X.chromo.get(i).person - 1;
                int y = X.chromo.get(i).timeslot - 1;
                //check if a person has more than 5 time slot assigned
                track_person[X.chromo.get(i).person - 1]++;
                if (track_person[X.chromo.get(i).person - 1] > 5){
                    X.rawFitness += 50;
                }
                //check person's preference
                if (this.table[x][y] == 0){
                    X.rawFitness += 50;
                }
                else {
                    track_timeslot[X.chromo.get(i).timeslot - 1]++;
                    X.rawFitness += this.table[x][y];
                }
                //check if a time slot is assigned to no one or more than 1 person
                if (track_timeslot[X.chromo.get(i).timeslot - 1] == 0 || track_timeslot[X.chromo.get(i).timeslot - 1] > 1){
                    X.rawFitness += 50;
                }
            }
        }
    }

//  PRINT OUT AN INDIVIDUAL GENE TO THE SUMMARY FILE *********************************

    @Override
    public void doPrintGenes(Chromo X, FileWriter output) throws java.io.IOException {

            for (int i=0; i<Parameters.numGenes; i++){
                Hwrite.right(X.getGeneAlpha(i),11,output);
            }
            output.write("   RawFitness");
            output.write("\n        ");
            for (int i=0; i<Parameters.numGenes; i++){
                Hwrite.right(X.getPosIntGeneValue(i),11,output);
            }
            Hwrite.right((int) X.rawFitness,13,output);
            output.write("\n\n");
    }


/*******************************************************************************
 *                             STATIC METHODS                                   *
 *******************************************************************************/

}  // End of Scheduler.java ******************************************************


