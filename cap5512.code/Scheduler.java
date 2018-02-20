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

        if(Parameters.rep == 1){
            //Initialize counters to 0
            countInvalid = 0;
            Arrays.fill(countDuplicates, 0);

            //Initialize raw fitness to 0
            //Best fitness: Parameters.numGenes * Paremeters.geneSize (35)
            //High fitness values indicate invalid solutions
            //Attempt to minimize
            X.rawFitness = 0;

            //Count how many duplicates and invalid time slots are in the current chromosome
            for(int i = 0; i < Parameters.numGenes; i++){
                for(int j = 0; j < Parameters.geneSize; j++){

                    //The current chromosome time slot we're looking at
                    int curChromoTimeSlot = X.chromo[i][j];

                    //If we find duplicates, harshly penalize having multiple people occupying
                    //the same time slot
                    //Otherwise, default increase the value by 1 since this time slot is taken
                    countDuplicates[curChromoTimeSlot]++;
                    if(countDuplicates[curChromoTimeSlot] == 1){
                        X.rawFitness += 1;
                    }
                    else if(countDuplicates[curChromoTimeSlot] > 1){
                        X.rawFitness += countDuplicates[curChromoTimeSlot] * 50;
                    }

                    //Check to see if this chromosome time slot is available for this person
                    if((this.table[i][curChromoTimeSlot] == 0)){
                        countInvalid++;
                    }
                }
            }

            //If someone was placed in time slots that they cannot make, harshly penalize them
            if(this.countInvalid > 0){
                X.rawFitness += 50 * countInvalid;
            }

            //DEBUG
//        ScheduleHelpers.printFitness(this);

            //The raw fitness of this chromo
            System.out.println(X.rawFitness);

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


