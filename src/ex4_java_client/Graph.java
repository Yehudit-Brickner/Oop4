package ex4_java_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Node;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Graph implements DirectedWeightedGraph {

    // this graph create new graph with all the parameters.
    int mc;
    public HashMap<Integer, Vertex> Nodes;
    public HashMap<Integer, HashMap<Integer, EdgeData>> Edges;
    public HashMap<String, Edge> edg;  // this hashmap save all the edges in my graph . < " src,dis" , edge> .

    //my constructor create new JSONArray from the json file.
    public Graph(String jsonName) throws ParseException {
        Nodes = new HashMap<>();
        Edges = new HashMap<>();
        edg= new HashMap<>();

        String filename = jsonName;
        try {
            JSONObject jsonObject = parseJSONFile(filename);
            JSONArray vertex = jsonObject.getJSONArray("Nodes");
            JSONArray edges = jsonObject.getJSONArray("Edges");
            saveJson(vertex, edges);

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mc = 0;
    }


    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }
    //must use the json file on and add the details in.
//this function get two JSSONArray and copy the details from them to our hashmaps.

    public void saveJson(JSONArray nodes, JSONArray edges) {

        for (int i = 0; i < nodes.length(); i++) {
            Integer key = nodes.getJSONObject(i).getInt("id");

            String a = nodes.getJSONObject(i).getString("pos");
            Geo p = new Geo(a);
            Vertex b = new Vertex(key, p, 0, 0, "");
            addNode(b);
        }
        for (int i = 0; i < edges.length(); i++) {
            Integer src = edges.getJSONObject(i).getInt("src");
            Double w = edges.getJSONObject(i).getDouble("w");
            Integer dest = edges.getJSONObject(i).getInt("dest");
            connect(src, dest, w);
        }
    }

    //this constructor copy the G' Nodes and Edges to our hashmaps
    public Graph(Graph g) {
        HashMap<Integer, Vertex> Nodes = g.Nodes;
        HashMap<Integer, HashMap<Integer, EdgeData>> Edges = g.Edges;
        HashMap<String, Edge> edg=g.edg;
        mc=g.mc;

    }
    //default constructor
    public Graph() {
        this.Nodes = new HashMap<Integer, Vertex>();
        this.Edges = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        this.edg = new HashMap<>();
        this.mc=0;
    }


    @Override

    //return the node by key
    public NodeData getNode(int key) {

        return this.Nodes.get(key);
    }

    @Override
    //return the edge by src and dest
    public EdgeData getEdge(int src, int dest) {

        return this.Edges.get(src).get(dest);
    }

    @Override
    //adding a new node to nodes hashmap and to edge hashmap
    public void addNode(NodeData n) {
        Integer key = n.getKey();
        this.Nodes.put(key, (Vertex) n);
        this.Edges.put(n.getKey(), new HashMap<>());
        mc++;
    }

    @Override
    //add a new edge to my edge hashmap , from src,dest and w.
    public void connect(int src, int dest, double w) {
        if(this.Nodes.get(src)==null||this.Nodes.get(dest)==null|| src==dest){
            System.out.println("there was a problem making a edge");
        }
        Edge a = new Edge(src, dest, w);//create bew edge from the src ,dest and w
        this.edg.put(src+","+dest,a);//add the new edge to the hashmap og edge, the kry is string.
        this.Edges.get(src).put(dest, a);//add the new edge to my hashmap
        this.Nodes.get(src).nodes.put(src,dest);//add the new edge to my nodes .
        mc++;
    }

    @Override
    //this function return iterator of NodeData.
    public Iterator<NodeData> nodeIter() {
        return new Iterator<NodeData>(){
            Iterator<Vertex> iter=Nodes.values().iterator();
            private int currentMc=mc;
            NodeData last=null;

            @Override
            public boolean hasNext() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                return iter.hasNext();
            }

            @Override
            public NodeData next() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                last=iter.next();
                return last;
            }

            @Override
            public void remove() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                if(last!=null){
                    removeNode(last.getKey());
                }
                Iterator.super.remove();
            }
        };
    }


    @Override
    // this function return the iterator of EdgeData , step by step as the last function
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<EdgeData>() {
            Iterator<Edge>iter=edg.values().iterator();
            private int currentMc=mc;
            EdgeData last=null;

            @Override
            public boolean hasNext() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                return iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                last = iter.next();
                return last;
            }

            public void remove(){
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                if(last!=null){
                    removeEdge(last.getSrc(), last.getDest());
                    currentMc=mc;
                }
            }
        };

    }

    @Override
    // this function return the iterator of EdgeData , step by step as the last function.
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterator<EdgeData>() {
            Iterator<EdgeData> iter = Edges.get(node_id).values().iterator();
            private int currentMc = mc;
            EdgeData last = null;

            @Override
            public boolean hasNext() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                return iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                last = iter.next();
                return last;
            }

            @Override
            public void remove() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                if (last !=null){
                    removeEdge(last.getSrc(), last.getDest());
                    currentMc = mc;
                }
            }
        };
    }

    @Override
    // this function remove node by node that i get in .
    public NodeData removeNode(int key) {
        ArrayList<String> keys = new ArrayList<String>();
        for (String k: edg.keySet()) //running on the hashmap of edg
        {
            if(key==Integer.parseInt((k.split(","))[0])||key==Integer.parseInt((k.split(","))[1])) {
                keys.add(k); //adding the value of key to the list
            }
        }
        keys.forEach(k->this.edg.remove(k)); //running on the list and delete the nodes with tge same key.
        Iterator<NodeData> it=nodeIter();
        while (it.hasNext()){
            Vertex v=(Vertex)it.next();
            Iterator<EdgeData> iter=edgeIter(v.getKey());
            while(iter.hasNext()){
                Edge e=(Edge)iter.next();
                if(e.getSrc()==key || e.getDest()==key){//checking if the edge have the same key as (x,x).
                    v.nodes.remove(e);
                    //remove also from the nodes' hashmap.
                }
            }

        }
        Vertex ans=this.Nodes.remove(key);
        mc++;
        return ans;
    }

    @Override
    // remove edge from the hashmap by src and dest .
    public EdgeData removeEdge(int src, int dest) {
        EdgeData edg1 = this.Edges.get(src).get(dest);

        this.Edges.get(src).remove(dest);
        //this.edg.get(src+","+dest);
        this.edg.remove(src+","+dest);
        this.Nodes.get(src).getnodes().remove(src,dest);
        mc++;
        return edg1;

    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        return edg.size();//the size of edg hashmap is the same size of the hashmap of all edges.
    }

    @Override
    public int getMC() {
        return this.mc;
    }
}