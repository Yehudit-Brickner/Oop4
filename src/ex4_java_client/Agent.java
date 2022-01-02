package ex4_java_client;

public class Agent {
    public int id;
    public Geo g;
    public double speed;

    public Agent(int id, Geo g, double s){
        this.id=id;
        this.g=g;
        this.speed=s;
    }

    public int getId(){
        return this.id;
    }

    public double getSpeed(){
        return this.speed;
    }
    public Geo getLocation(){
        return this.g;
    }

    public void setLocation(double x, double y, double z){
        Geo g1=new Geo(x,y,z);
        this.g=g1;

    }

}
