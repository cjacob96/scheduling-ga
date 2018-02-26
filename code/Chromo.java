/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

//import com.sun.org.apache.xpath.internal.SourceTree;

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

    //@mylist
	public ArrayList<Gene> chromo;
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

		} else if(Parameters.rep == 2){

		} else if(Parameters.rep == 3){
			//@mylist
            //list starts with 35 items (ideally); however size of list can grow or shrink during crossover or mutation
            chromo = new ArrayList<Gene>();
            for (int i = 0; i < 35; i++){
                chromo.add(new Gene());
            }

            this.rawFitness = -1;
            this.sclFitness = -1;
            this.proFitness = -1;
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
			case 1:
            {
                //@mylist
                double rand_double = 0.0;
                //mutation on person's id or timeslot's id
                for (int i = 0; i < chromo.size(); i++){
                    rand_double = Search.r.nextDouble();
                    if (rand_double < Parameters.mutationRate){
                        int rand_person = Search.r.nextInt(7) + 1;
                        chromo.get(i).person = rand_person;
                    }
                    rand_double = Search.r.nextDouble();
                    if (rand_double < Parameters.mutationRate){
                        int rand_timeslot = Search.r.nextInt(35) + 1;
                        chromo.get(i).timeslot = rand_timeslot;
                    }
                }
                //mutation on either adding or removing item
				rand_double = Search.r.nextDouble();
                if (rand_double < Parameters.mutationRate) {
					rand_double = Search.r.nextDouble();
					//mutation on adding a new item
					if (rand_double < Parameters.mutationRate) {
						//order of items in list does not matter
						chromo.add(new Gene());
					}
					else if (chromo.size() > 0) {
						//mutation on removing an existing item
						rand_double = Search.r.nextDouble();
						if (rand_double < Parameters.mutationRate) {
							int rand_int = Search.r.nextInt(chromo.size());
							chromo.remove(rand_int);
						}
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
		int k = 0;

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
				if(rand < 0.9){
					if(Search.member[randComp1].rawFitness < Search.member[randComp2].rawFitness){
						return randComp2;
					} else{
						return randComp1;
					}
				}else{
					if(Search.member[randComp1].rawFitness < Search.member[randComp2].rawFitness) {
						return randComp1;
					}else{
						return randComp2;
					}
				}
			default:
				System.out.println("ERROR - No selection method selected");
		}
		return -1;
	}

	//  Produce a new child from two parents  **********************************

    public static void mateParents(int pnum1, int pnum2, Chromo parent1, Chromo parent2, Chromo child1, Chromo child2){
        //TODO: Write Representation Version

        int xoverPoint1;

        switch (Parameters.xoverType) {
            case 1:
            {
                //@mylist
                if (parent1.chromo.size() > 0 && parent2.chromo.size() > 0) {
                    int t1 = Search.r.nextInt(parent1.chromo.size());
                    int t2 = Search.r.nextInt(parent2.chromo.size());
                    xoverPoint1 = (t1 < t2) ? t1 : t2;
                    child1 = new Chromo();
                    child1.chromo.clear();
                    child2 = new Chromo();
                    child2.chromo.clear();
                    //order of items in list does not matter
                    for (int i = 0; i < xoverPoint1; i++) {
                        child1.chromo.add(new Gene(parent1.chromo.get(i).person, parent1.chromo.get(i).timeslot));
                        child2.chromo.add(new Gene(parent2.chromo.get(i).person, parent2.chromo.get(i).timeslot));
                    }
                    for (int i = xoverPoint1; i < parent2.chromo.size(); i++) {
                        child1.chromo.add(new Gene(parent2.chromo.get(i).person, parent2.chromo.get(i).timeslot));
                    }
                    for (int i = xoverPoint1; i < parent1.chromo.size(); i++) {
                        child2.chromo.add(new Gene(parent1.chromo.get(i).person, parent1.chromo.get(i).timeslot));
                    }
                }
                break;
            }
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
		//child.chromo = parent.chromo;

		//@mylist
        child = new Chromo();
        child.chromo.clear();
        child.chromo.addAll(parent.chromo);

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
