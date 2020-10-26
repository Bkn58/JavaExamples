import org.junit.Test;

import static org.junit.Assert.*;

public class CellsLifeTest {

    @org.junit.Test
    public void updateState() {
        CellsLife bd = new CellsLife();
        bd.Cells[1][1]=true;
        bd.Cells[1][2]=true;
        bd.Cells[1][3]=true;
        bd.UpdateState();
        assertEquals(bd.CellsState[1][1],1);
        assertEquals(bd.CellsState[1][2],2);
        assertEquals(bd.CellsState[1][3],1);
        assertEquals(bd.CellsState[0][0],1);
        assertEquals(bd.CellsState[0][1],2);
        assertEquals(bd.CellsState[0][2],3);
        assertEquals(bd.CellsState[0][3],2);
        assertEquals(bd.CellsState[0][4],1);
        assertEquals(bd.CellsState[0][5],0);
        assertEquals(bd.CellsState[1][0],1);
        assertEquals(bd.CellsState[1][4],1);
        assertEquals(bd.CellsState[2][0],1);
        assertEquals(bd.CellsState[2][1],2);
        assertEquals(bd.CellsState[2][2],3);
        assertEquals(bd.CellsState[2][3],2);
        assertEquals(bd.CellsState[2][4],1);
    }

    @Test
    public void nextGeneration() {
        CellsLife bd = new CellsLife();
        bd.Cells[1][1]=true;
        bd.Cells[1][2]=true;
        bd.Cells[1][3]=true;
       //bd.UpdateState();
        bd.NextGeneration();
        assertTrue(bd.Cells[0][2]);
        assertTrue(bd.Cells[2][2]);
        assertFalse(bd.Cells[1][1]);
        assertFalse(bd.Cells[1][3]);
    }
}