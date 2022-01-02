package ex4_java_client;

public class Pokemon {

    public double value;
    public int type;
    public Geo g;


    public Pokemon(double val, Geo g, int t){
        this.value=val;
        this.g=g;
        this.type=t;

    }

    public double getValue(){
        return this.value;
    }

    public Geo getLocation(){
        return this.g;
    }
    public int getType(){
        return this.type;
    }


}
