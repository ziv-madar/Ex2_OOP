package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {



    @Test
    void copy() {
        dw_graph_algorithms dwg = new DWGraph_Algo();
        directed_weighted_graph ga = new DWGraph_DS ();
        dwg.init(ga);
        ga.addNode(new NodeData(0));
        ga.addNode(new NodeData(1));
        ga.addNode(new NodeData(7));

        ga.connect(1, 7, 3);
        directed_weighted_graph ga1 = dwg.copy();
        assertEquals(ga.getV().toString(), ga1.getV().toString());
        ga.addNode(new NodeData(5));
        assertNotEquals(ga.getV().toString(), ga1.getV().toString());
    }

    @Test
    void isConnected() {
        dw_graph_algorithms dwg = new DWGraph_Algo();
        directed_weighted_graph ga = new DWGraph_DS();
        dwg.init(ga);
        ga.addNode(new NodeData(0));
        ga.addNode(new NodeData(1));
        ga.addNode(new NodeData(2));
        ga.addNode(new NodeData(3));
        ga.connect(0, 1, 17);
        ga.connect(1, 0, 1);
        ga.connect(1, 2, 9);
        ga.connect(2, 1, 14);
        ga.connect(2, 3, 1.3);
        ga.connect(3, 2, 47);
        assertTrue(dwg.isConnected());
        ga.removeEdge(1, 2);
        assertFalse(dwg.isConnected());

    }

    @Test
    void shortestPathDist() {
        dw_graph_algorithms dwg = new DWGraph_Algo();
        directed_weighted_graph ga = new DWGraph_DS();
        dwg.init(ga);
        ga.addNode(new NodeData(1));
        ga.addNode(new NodeData(2));
        ga.addNode(new NodeData(3));
        ga.addNode(new NodeData(4));
        ga.addNode(new NodeData(5));
        ga.connect(1, 2, 15);
        ga.connect(1, 3, 2);
        ga.connect(1, 4, 17);
        ga.connect(1, 5, 20);
        ga.connect(3, 5, 4);
        ga.connect(5, 4, 1);
        assertEquals(dwg.shortestPathDist(1, 4), 7);

    }

    @Test
    void shortestPath() {
        dw_graph_algorithms dwg = new DWGraph_Algo();
        directed_weighted_graph ga = new DWGraph_DS();
        dwg.init(ga);
        ga.addNode(new NodeData(1));
        ga.addNode(new NodeData(2));
        ga.addNode(new NodeData(3));
        ga.addNode(new NodeData(4));
        ga.addNode(new NodeData(5));
        ga.connect(1, 2, 15);
        ga.connect(1, 3, 2);
        ga.connect(1, 4, 17);
        ga.connect(1, 5, 20);
        ga.connect(3, 5, 4);
        ga.connect(5, 4, 1);
        assertEquals(dwg.shortestPath(1, 4).toString(),"[1, 3, 5, 4]");
        System.out.println(dwg.shortestPath(1, 4).toString());
    }

    @Test
    void save() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.connect(1, 2, 15);
        g.connect(1, 3, 2);
        g.connect(1, 4, 17);
        g.connect(1, 5, 20);
        g.connect(3, 5, 4);
        g.connect(5, 4, 1);
        directed_weighted_graph g1 = new DWGraph_DS();
        g1.addNode(new NodeData(1));
        g1.addNode(new NodeData(2));
        g1.addNode(new NodeData(3));
        dw_graph_algorithms w = new DWGraph_Algo();
        dw_graph_algorithms w2 = new DWGraph_Algo();
        w2.init(g1);
        assertEquals(w2.getGraph().getV().toString(), "[1, 2, 3]");
        w.init(g);
        w.save("f1.txt");
        w2.load("f1.txt");
        assertEquals(w2.getGraph().getV().toString(), "[1, 2, 3, 4, 5]");
    }

    @Test
    void load() {
    }
}