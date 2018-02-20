/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

import java.io.*;
import java.sql.ParameterMetaData;
import java.util.*;
import java.text.*;
import java.util.stream.IntStream;

public class Chromo
{
/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/

	public int[][] chromo;
	public double rawFitness;
	public double sclFitness;
	public double proFitness;

/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/

	private static int randnum;

/*******************************************************************************
*                              CONSTRUCTORS                                    *
*******************************************************************************/

	public Chromo(){

		//Person to be evaluated
		//Gene Size is the number of times to evaluate

		//Chromosome representation is a list of people who in turn have
		//a list of 5 times that they will occupy. Duplicates are allowed
		//on different people, but discouraged in the fitness function
		//Number of gene points gives the number of people to evaluate
		if(Parameters.rep == 1){

			chromo = new int[Parameters.numGenes][Parameters.geneSize];

			for (int i=0; i<Parameters.numGenes; i++){
				int[] person = new int[Parameters.geneSize];
				Arrays.fill(person, -1);
				for (int j=0; j<Parameters.geneSize; j++){

					//Make sure any one person doesn't have duplicate times
					while(IntStream.of(person).anyMatch(time -> time == randnum)){
						randnum = Search.r.nextInt(35);
					}

					// Keep track of this person's time slots
					person[j] = randnum;
				}
				//Replace this person gene in the chromosome with their time slots
				chromo[i] = person;
			}

//		DEBUG
//		ScheduleHelpers.printChromo(this);

			this.rawFitness = -1;   //  Fitness not yet evaluated
			this.sclFitness = -1;   //  Fitness not yet scaled
			this.proFitness = -1;   //  Fitness not yet proportionalized
		} else if(Parameters.rep == 2){

		} else if(Parameters.rep == 3){

		}

    }


/*******************************************************************************
*                                MEMBER METHODS                                *
*******************************************************************************/

	//  Get Alpha Represenation of a Gene **************************************

	public int getGeneAlpha(int geneID){
		//TODO: Write Reprsentation Version
		return 0;
	}

	//  Get Integer Value of a Gene (Positive or Negative, 2's Compliment) ****

	public int[] getIntGeneValue(int geneID){
		//TODO: Write Representation Version
		return new int[Parameters.geneSize];
	}

	//  Get Integer Value of a Gene (Positive only) ****************************

	public int getPosIntGeneValue(int geneID){
		//TODO: Write Reprsentation Version
		return 0;
	}

	//  Mutate a Chromosome Based on Mutation Type *****************************

	public void doMutation(){
		//TODO: Write Reprsentation Version
	}

/*******************************************************************************
*                             STATIC METHODS                                   *
*******************************************************************************/

	//  Select a parent for crossover ******************************************

	public static int selectParent(){

		return Search.r.nextInt(Parameters.popSize);
	}

	//  Produce a new child from two parents  **********************************

	public static void mateParents(int pnum1, int pnum2, Chromo parent1, Chromo parent2, Chromo child1, Chromo child2){
		//TODO: Write Representation Version
	}

	//  Produce a new child from a single parent  ******************************

	public static void mateParents(int pnum, Chromo parent, Chromo child){
		//TODO: Write Representation Version
	}

	//  Copy one chromosome to another  ***************************************

	public static void copyB2A (Chromo targetA, Chromo sourceB){

		targetA.chromo = sourceB.chromo;

		targetA.rawFitness = sourceB.rawFitness;
		targetA.sclFitness = sourceB.sclFitness;
		targetA.proFitness = sourceB.proFitness;
		return;
	}

}   // End of Chromo.java ******************************************************
