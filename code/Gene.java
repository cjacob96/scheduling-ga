
//@mylist
public class Gene {
    int person;
    int timeslot;

    public Gene(){
        //randomly generated a person's id: [1, 7]
        this.person = Search.r.nextInt(7) + 1;
        //randomly generated a time slot's id: [1, 35]
        this.timeslot = Search.r.nextInt(35) + 1;
    }

    public Gene(int person, int timeslot){
        this.person = person;
        this.timeslot = timeslot;
    }
}
