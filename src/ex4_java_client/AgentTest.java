package ex4_java_client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentTest {



    Geo g1=new Geo(0,0,0);
    Geo g2=new Geo(32.151651,35.16564,0);

    Agent a1=new Agent(1, g1,1);
    Agent a2=new Agent(2, g2,1.5);


    @Test
    void getId() {
        assertEquals(1,a1.getId());
        assertEquals(2,a2.getId());
    }

    @Test
    void getLocation() {
        assertEquals(32.151651, a2.getLocation().x());
        assertEquals(35.16564, a2.getLocation().y());
        assertEquals(0, a2.getLocation().z());
    }

    @Test
    void getSpeed() {
        assertEquals(1,a1.getSpeed());
        assertEquals(1.5,a2.getSpeed());
    }

    @Test
    void setLocation() {
        a2.setLocation(32.2516151,35.26564,0.0);
        assertEquals(32.2516151, a2.getLocation().x());
        assertEquals(35.26564, a2.getLocation().y());
        assertEquals(0, a2.getLocation().z());

    }

}