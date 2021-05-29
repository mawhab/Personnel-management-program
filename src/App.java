import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;

public class App {
    public static void main(String[] args) {
        Window frame = new Window();
        frame.addToAll();
        
        frame.frame.setLocation(500,100);
        frame.frame.setSize(600, 600);
        frame.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        //frame.frame.pack();
        frame.frame.setVisible(true);
    }
}


class Window implements ActionListener{
    JFrame frame;
    JPanel panelFull;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JTextField name;
    JTextField pn;
    JTextField sit;
    JTextField src;
    JTextField srcpn;
    ArrayList<String[]> data;
    String[] columns = {"Name", "Phone number", "Situation", "Source", "Source Phone number"};
    JTable t;
    JScrollPane sp;
    int row;
    
    public Window(){
        frame = new JFrame("Ramadan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        b1 = new JButton("Add");
        b2 = new JButton("Edit");
        b3 = new JButton("Delete");
        b4 = new JButton("Save");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);;


        name = new JTextField(40);
        pn = new JTextField(40);
        sit = new JTextField(40);
        src = new JTextField(40);
        srcpn = new JTextField(40);

        data = new ArrayList<String[]>();
        save();
        String[] entry = {"محمدعلي", "0155498149681", "موطف", "Amany", "0124646849"};
        read();
        data.add(entry);

        String[][] temp = new String[data.size()][5];
        temp = data.toArray(temp);
        t = new JTable(temp, columns);



        addlis(); 
    }

    public void addToAll(){
        panelFull = new JPanel(new MigLayout("", "[]", "[]"));
        panelFull.add(topPanel(), "wrap");
        panelFull.add(botPanel(), "push, grow, span, wrap");
        frame.add(panelFull);
    }

    private JPanel topPanel(){
        JPanel panel = new JPanel(new MigLayout("wrap 2", "[] []", "[] [] [] [] []"));
        panel.add(new JLabel("Name:"), "sg a, right");
        panel.add(name, "pushx, growx, span");
        
        panel.add(new JLabel("Phone number:"), "sg a, right");
        panel.add(pn, "pushx, growx, span");

        panel.add(new JLabel("Situation:"), "sg a, right");
        panel.add(sit, "pushx, growx, span");

        panel.add(new JLabel("Source:"), "sg a, right");
        panel.add(src, "pushx, growx, span");

        panel.add(new JLabel("Source Phone number:"), "sg a, right");
        panel.add(srcpn, "pushx, growx, span");

        return panel;
    }

    private JPanel botPanel(){
        JPanel panel = new JPanel(new MigLayout("wrap 2", "[grow] []", "[grow, :100:]"));
        t.setPreferredScrollableViewportSize(new Dimension(500,70));
        t.setFillsViewportHeight(true);

        sp = new JScrollPane(t);
        
        panel.add(sp, "grow");
        panel.add(b1, "sg b, split 4, flowy, top");
        panel.add(b2, "sg b");
        panel.add(b3, "sg b");
        panel.add(b4, "sg b, wrap");

        return panel;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            add();
        }else if(e.getSource()==b2){
            edit();
        }else if(e.getSource()==b3){
            delete();
            clrfields();
        }else if(e.getSource()==b4){
            save();
        }
    }

    private String[] getdata(){
        String[] data = new String[5];
        data[0] = name.getText();
        data[1] = pn.getText();
        data[2] = sit.getText();
        data[3] = src.getText();
        data[4] = srcpn.getText();
        return data;
    }


    private void update_tabl(){
        String[][] temp = new String[data.size()][5];
        temp = data.toArray(temp);
        t = new JTable(temp, columns);
        t.setPreferredScrollableViewportSize(new Dimension(500,70));
        t.setFillsViewportHeight(true);
        
        sp.setViewportView(t);
    }

    private void edit(){
        delete();
        add(row);
    }

    private void add(){
        String[] in = getdata();
        if(in[0].isBlank() && in[1].isBlank() && in[2].isBlank() && in[3].isBlank())
            return;
        data.add(in);
        update_tabl();

        clrfields();
        addlis();
    }

    private void add(int ind){
        String[] in = getdata();
        if(in[0].isBlank() && in[1].isBlank() && in[2].isBlank() && in[3].isBlank() && in[4].isBlank())
            return;
        data.add(ind, in);
        update_tabl();

        clrfields();
        addlis();
    }

    private void addlis(){
        t.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    name.setText(data.get(row)[0]);
                    pn.setText(data.get(row)[1]);
                    sit.setText(data.get(row)[2]);
                    src.setText(data.get(row)[3]);
                    srcpn.setText(data.get(row)[4]);
                }
            }
        }); 
    }


    private void delete(){
        data.remove(row);
        update_tabl();
        addlis();
    }

    private void clrfields(){
        name.setText("");
        pn.setText("");
        sit.setText("");
        src.setText("");
        srcpn.setText("");
    }




    private void read(){
        String line = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\knega\\Desktop\\Programming\\java\\Ramadan\\lib\\data.txt"));
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                data.add(lines);
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void save(){
        try{
            FileWriter w = new FileWriter("C:\\Users\\knega\\Desktop\\Programming\\java\\Ramadan\\lib\\data.txt");
            for(int i=0;i<data.size();i++){
                w.write(""+data.get(i)[0]+","+data.get(i)[1]+","+data.get(i)[2]+","+data.get(i)[3]+","+data.get(i)[4]+"\n");
            }
            w.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}