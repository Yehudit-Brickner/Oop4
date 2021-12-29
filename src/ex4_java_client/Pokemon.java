package ex4_java_client;

public class Pokemon {

    int id;
    Geo g;


    public Pokemon(int id, Geo g){
        this.id=id;
        this.g=g;

    }

    public int getId(){
        return this.id;
    }

    public Geo getLocation(){
        return this.g;
    }


}
