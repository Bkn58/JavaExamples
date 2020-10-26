import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.io.IOException;

/**
 * Чтобы проследить за историей развития колонии, разместим в
 * пустыне клетки Жизни в их начальном положении. Смена поко-
 * лений будет происходить по следующим правилам.
 * 1. Соседями клетки считаются все клетки, находящиеся в
 * восьми ячейках, расположенных рядом с данной по горизон-
 * тали, вертикали или диагонали.
 * 2. Если у некоторой клетки меньше двух соседей, она погибает
 * от одиночества. Если клетка имеет больше трех соседей, она
 * погибает от тесноты.
 * 3. Если рядом с пустой ячейкой окажется ровно три соседние
 * клетки Жизни, то в этой ячейке рождается новая клетка.
 * 4. Гибель и рождение происходят в момент смены поколений.
 * Таким образом, гибнущая клетка может способствовать рож-
 * дению новой, но рождающаяся клетка не может воскресить
 * гибнущую, и гибель одной клетки, уменьшив локальную
 * плотность населения, не может предотвратить гибель дру-
 * гой.
 */
public class CellsLife  extends JFrame {
    Point curPoint; // текущая позиция курсора мыши
    boolean [][] Cells = new boolean[100][100]; // массив клеток false-клетка отсутствует, true-клетка существует
    byte [][] CellsState = new byte[100][100];  // массив состояний клеток - число находящихся по соседству клеток

    public CellsLife (){
        addMouseListener( new MouseHandler());
        ClearCells();
        ClearState();
    }
    public void ClearCells () {
        for (int i=0;i<100;i++)
            for (int j=0;j<100;j++) Cells[i][j] = false;
    }
    public void ClearState () {
        for (int i=0;i<100;i++)
            for (int j=0;j<100;j++) CellsState[i][j] = 0;
    }
    public void UpdateState (){
        for (int i=0;i<100;i++) {
            for (int j = 0; j < 100; j++) {
                CellsState[i][j] = 0;
                if ((i - 1 >= 0) && (j - 1 >= 0) && (Cells[i - 1][j - 1])) CellsState[i][j]++;
                if ((i - 1 >= 0) && (Cells[i - 1][j])) CellsState[i][j]++;
                if ((i - 1 >= 0) && (j + 1 < 100) && (Cells[i - 1][j + 1])) CellsState[i][j]++;
                if ((j - 1 >= 0) && (Cells[i][j - 1])) CellsState[i][j]++;
                if ((j + 1 < 100) && (Cells[i][j + 1])) CellsState[i][j]++;
                if ((i + 1 < 100) && (j - 1 >= 0) && (Cells[i + 1][j - 1])) CellsState[i][j]++;
                if ((i + 1 < 100) && (Cells[i + 1][j])) CellsState[i][j]++;
                if ((i + 1 < 100) && (j + 1 < 100) && (Cells[i + 1][j + 1])) CellsState[i][j]++;
            }
        }
    }
    public void NextGeneration () {
        UpdateState();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (Cells[i][j] && CellsState[i][j]>3) Cells[i][j]=false;       // гибель клетки от тесноты
                else if (Cells[i][j] && CellsState[i][j]<2) Cells[i][j]=false;  // гибель клетки от одиночества
                else if (!Cells[i][j] && CellsState[i][j]==3) Cells[i][j]=true; // рождение новой клетки
            }
        }
        repaint();
    }
    public void paint (Graphics g) {
        g.setColor(Color.BLACK);
        for (int i=0;i<100;i++)
            for (int j=0;j<100;j++) {
            if (Cells[i][j]) g.fillRect(i*10,j*10,10,10);
            else {
                g.clearRect(i*10,j*10,10,10);
                g.drawRect(i*10,j*10,10,10);
            }
        }
    }

    private  class MouseHandler extends MouseAdapter {
        public void mouseClicked (MouseEvent event) {
            curPoint = event.getPoint();
            int x = curPoint.x/10;
            int y = curPoint.y/10;
            if(Cells[x][y]) Cells[x][y] = false;
            else            Cells[x][y] = true;
            repaint();

        }
    }
    public static void  main (String args[]) {
        CellsLife bd = new CellsLife();
        bd.setSize(1000,1000);
        bd.rootPane.setSize(500,400);
        bd.setVisible(true);
        bd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bd.setLocation(300,0);
        Graphics g = bd.rootPane.getGraphics();
        g.setColor(Color.BLACK);
        g.drawLine(0,0,bd.getWidth(),0);
        bd.repaint();
        int step = 0;
        while (true) {
            try {

                int ch = System.in.read();
                if (ch=='c') bd.ClearCells();
                else if (ch=='q') System.exit(0);
                else {
                    step++;
                    bd.NextGeneration();
                    System.out.println("Шаг " + step);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
