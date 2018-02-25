/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

import java.io.*;
import java.util.*;
import java.text.*;
import java.util.stream.IntStream;

public class Chromo
{

	//Debug variables
	public final boolean DEBUG = true;

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
			//In representation 2, each gene (slot) has a choice of 7 persons
			//numGenes = 35, geneSize = 1
			chromo = new int[Parameters.numGenes][Parameters.geneSize];

			//Generate a random permutation of indices
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < Parameters.numGenes; i++){
				list.add(i);
			}
			Collections.shuffle(list);

			/*if (DEBUG){
				System.out.println("Shuffled indices: ");
				for (int i = 0; i < 35; i++){
					System.out.println(list.get(i));
				}
			}*/
			
			for (int i = 0; i < 7; i++){
				for (int j = 0; j < 5; j++){
					chromo[list.get(i * 5 + j)][0] = i;
				}
			}

			this.rawFitness = -1;
			this.sclFitness = -1;
			this.proFitness = -1;

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

		switch(Parameters.mutationType){
			/*case 1:
				for(int i = 0; i < Parameters.numGenes; i++){
					for(int j = 0; j < Parameters.geneSize; j++) {
						double rand = Search.r.nextDouble();
						if(rand < Parameters.mutationRate){
							rand = Search.r.nextDouble();
							randnum = Search.r.nextInt(5);
							if(rand < 0.7){
								this.chromo[i][j] = (randnum * (this.chromo[i][j] % 7));
							}else{
								this.chromo[i][j] = (randnum * ((int)Math.floor(this.chromo[i][j] / 7)));
							}


                            randnum = Search.r.nextInt(35);
							this.chromo[i][j] = randnum;
						}
					}
				}
				break;*/
			case 1:
				for (int i = 0; i < Parameters.numGenes; i++){
					for (int j = 0; j < Parameters.geneSize; j++){
						double rand = Search.r.nextDouble();
						if (rand < Parameters.mutationRate){
							//assign this seat randomly to another preson
							int rand_person = Search.r.nextInt(7);
							this.chromo[i][j] = rand_person;
						}
					}
				}
				break;
			default:
				System.out.println("ERROR - No mutation method selected");
		}
	}

/*******************************************************************************
*                             STATIC METHODS                                   *
*******************************************************************************/

	//  Select a parent for crossover ******************************************

	public static int selectParent(){

		double rWheel = 0;
		int j = 0;
		double k = 0.9;

		double rand;

		switch(Parameters.selectType){
			case 1:
				rand = Search.r.nextDouble();
				for(j = 0; j < Parameters.popSize; j++){
					rWheel = rWheel + Search.member[j].proFitness;
					if(rand < rWheel) return j;
				}
				break;
			case 3:
				rand = Search.r.nextDouble();
				j = (int) (rand * Parameters.popSize);
				return j;
			case 2:
				rand = Search.r.nextDouble();
				int randComp1 = Search.r.nextInt(Parameters.popSize);
				int randComp2 = Search.r.nextInt(Parameters.popSize);
				if(rand < k){
					if(Search.member[randComp1].proFitness < Search.member[randComp2].proFitness){
						return randComp2;
					} else{
						return randComp1;
					}
				}else{
					if(Search.member[randComp1].proFitness < Search.member[randComp2].proFitness) {
						return randComp1;
					}else{
						return randComp2;
					}
				}
			case 4:
				//To do
				//Add rank selection method
				//To implement rank selection, scale type must be 3 or 4. 
				if (Parameters.scaleType == 0 || Parameters.scaleType == 1){
					System.out.println("Error - to use rank selection, please change scale type to rank 2 or 3");
					return -1;
				} else {
					//Normal proportional selection
					rand = Search.r.nextDouble();
					for (j = 0; j < Parameters.popSize; j++){
						rWheel = rWheel + Search.member[Search.memberIndex[j]].proFitness;
						if (rand < rWheel)
							return Search.memberIndex[j];
					}
				}
				break;
			default:
				System.out.println("ERROR - No selection method selected");
		}
		return -1;
	}

	//  Produce a new child from two parents  **********************************

	public static void mateParents(int pnum1, int pnum2, Chromo parent1, Chromo parent2, Chromo child1, Chromo child2){
		//TODO: Write Representation Version

		int xoverPoint1;
		int xoverPoint2;

		switch(Parameters.xoverType){
			//one-point crossover
			case 1:
				xoverPoint1 = Search.r.nextInt(35);

                for(int i = 0; i < Parameters.numGenes; i++){
                    for(int j = 0; j < Parameters.geneSize; j++){
                        if(i < xoverPoint1){
                            child1.chromo[i][j] = parent1.chromo[i][j];
                            child2.chromo[i][j] = parent2.chromo[i][j];
                        }else{
                            child1.chromo[i][j] = parent2.chromo[i][j];
                            child2.chromo[i][j] = parent1.chromo[i][j];
                        }
                    }
                }
				break;
			default:
				System.out.println("ERROR - Bad crossover method selected");
		}

		child1.rawFitness = -1;
		child1.sclFitness = -1;
		child1.proFitness = -1;
		child2.rawFitness = -1;
		child2.sclFitness = -1;
		child2.proFitness = -1;

	}


	//  Produce a new child from a single parent  ******************************

	public static void mateParents(int pnum, Chromo parent, Chromo child){
		child.chromo = parent.chromo;

		child.rawFitness = -1;
		child.sclFitness = -1;
		child.proFitness = -1;
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
