package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

    @Test
    void getNode() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.connect(0, 1, 5);
        g.connect(0, 2, 7);
        node_data n = g.getNode(0);
        assertEquals(n.toString(),"0");
        node_data n2 = g.getNode(3);
        assertNull(n2);
    }

    @Test
    void getEdge() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(125));
        g.addNode(new NodeData(8));
        g.addNode(new NodeData(17));
        g.connect(17, 125, 52.784);
        assertEquals(g.getEdge(17, 125).toString(), "17->125(52.784)");
        assertNull(g.getEdge(17, 50));
        assertNull(g.getEdge(17, 8));
    }

    @Test
    void addNode() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(125));
        g.addNode(new NodeData(8));
        g.addNode(new NodeData(170000));
        g.connect(8, 170000, 45);
        g.connect(170000, 125, 2);
        assertEquals(g.getV().toString(), "[170000, 8, 125]");
    }

    @Test
    void connect() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.connect(1, 2, 45);
        g.connect(2, 1, 45);
        dw_graph_algorithms dwg = new DWGraph_Algo();
        dwg.init(g);
        assertTrue(dwg.isConnected());
        g.removeEdge(1,2);
        assertFalse(dwg.isConnected());

    }

    @Test
    void getV() {
        directed_weighted_graph g = new DWGraph_DS();
        assertEquals(g.getV().toString(), "[]");
        g.addNode(new NodeData(125));
        g.addNode(new NodeData(8));
        g.addNode(new NodeData(170000));
        assertEquals(g.getV().toString(), "[170000, 8, 125]");

    }

    @Test
    void getE() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(125));
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(8));
        g.addNode(new NodeData(5));
        g.connect(0,5,7.5);
        g.connect(8, 5, 45);
        g.connect(5, 125, 2);
        g.connect(5,1,4);
        assertEquals(g.getE(5).toString(), "[5->1(4.0), 5->125(2.0)]");

    }

    @Test
    void removeNode() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(125));
        g.addNode(new NodeData(8));
        g.addNode(new NodeData(5));
        g.connect(8, 5, 45);
        g.connect(5, 125, 2);
        g.connect(8, 125, 0.25);
        assertEquals(g.getE(8).toString(),"[8->5(45.0), 8->125(0.25)]");
        g.removeNode(5);
        assertNull(g.getNode(5));
        assertEquals(g.getE(8).toString(),"[8->125(0.25)]");

    }

    @Test
    void removeEdge() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(125));
        g.addNode(new NodeData(8));
        g.addNode(new NodeData(5));
        g.connect(8, 5, 45);
        g.connect(5, 125, 2);
        g.connect(8, 125, 0.25);
        g.removeEdge(8, 5);
        g.removeEdge(8, 5);
        g.removeEdge(8, 6);
        assertEquals(g.getE(5).toString(), "[5->125(2.0)]");
        assertEquals(g.getE(8).toString(), "[8->125(0.25)]");
    }



    @Test
    void edgeSize() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(125));
        g.addNode(new NodeData(8));
        g.addNode(new NodeData(5));
        g.addNode(new NodeData(38));
        g.addNode(new NodeData(45));
        g.connect(8, 5, 45);
        g.connect(5, 125, 2);
        g.connect(8, 125, 0.25);
        g.connect(45, 5, 4);
        g.connect(38, 5, 3);
        g.removeEdge(8, 5);
        g.removeEdge(8, 5);
        g.removeEdge(8, 6);
        g.removeNode(5);
        assertEquals(g.edgeSize(), 1);
    }


}