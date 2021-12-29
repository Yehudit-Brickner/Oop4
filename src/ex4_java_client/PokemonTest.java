package ex4_java_client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {
    Geo g1=new Geo(0,0,0);
    Geo g2=new Geo(32.151651,35.16564,0);

    Pokemon p1=new Pokemon(1, g1);
    Pokemon p2=new Pokemon(2, g2);

    @Test
    void getId() {
        assertEquals(1,p1.getId());
        assertEquals(2,p2.getId());


    }

    @Test
    void getLocation() {
        assertEquals(32.151651, p2.getLocation().x());
        assertEquals(35.16564, p2.getLocation().y());
        assertEquals(0, p2.getLocation().z());

    }

}