import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SudokuGUI {

    public void initGridGUI() {
        //TODO: create JFrame and grid of JPanels within the JFrame
        //TODO: loop through each JPanel and add either a label or a TextField based on the value of that location in
        // the solution (SudokuModel.game[i][j]).
        //if value is zero, in addition to the JTextField, include the code below
        JLabel jl = new JLabel(Integer.toString(0));
        jl.setVisible(false);
        //TODO: create keyListener for the 'enter' key
        //when the enter key is pressed the following code should be run (note: this assumes jt is the name of the
        // JTextField variable)

        JFrame frame = new JFrame("Sudoku");
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLayout(new GridLayout(9, 9));
        JPanel[][] panels = new JPanel[9][9];

        Border border= BorderFactory.createLineBorder(Color.black);



        for (int i = 0; i < 9; i++) {
            for (int c = 0; c < 9; c++){
                panels[i][c] = new JPanel();
                panels[i][c].setBorder(border);
                panels[i][c].setSize(40, 40);

                int x = i;
                int y = c;
                if (SudokuModel.game[x][y] != 0){
                    JLabel label = new JLabel(String.valueOf(SudokuModel.game[x][y]));
                    panels[i][c].add(label);
                } else {
                    JLabel label = new JLabel();
                    JTextField field = new JTextField(null, 3);
                    panels[i][c].add(field);
                    field.addKeyListener(new KeyAdapter() {  //add key listener
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_ENTER){  //when enter key is pressed
                                int in = Integer.parseInt(field.getText().trim());

                                if (checkValid(x, y, in)) {
                                    field.setVisible(false);
                                    label.setText(Integer.toString(in));


                                    panels[x][y].add(label);  //add label to panel
                                    label.setVisible(true);
                                    panels[x][y].setVisible(true);
                                }

                            }
                        }
                    });
                    panels[i][c].add(field);
                    panels[i][c].add(label);

                }

                frame.add(panels[i][c]);  //add panel
            }
        }

        frame.setVisible(true);

        /*

                    int in = Integer.parseInt(jt.getText());
                    if (checkValid(x, y, in)) {
                        jt.setVisible(false);
                        jl.setText(Integer.toString(in));
                        jl.setVisible(true);
                        gridGUI[x][y].add(jl);
                    }

         */
    }

    private boolean checkValid(int x, int y, int in){
        if (SudokuModel.solution[x][y]==in){
            return true;
        }return false;
    }

    public static void main(String[] args) {
        SudokuGUI gui = new SudokuGUI();
        SudokuModel sm = new SudokuModel();
        gui.initGridGUI();
    }


}
