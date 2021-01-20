import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainShow extends JPanel implements ActionListener {
    public static String available = "3 3 2";
    private static Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    private JTable table;  //表格
    private MyTableModel model = new MyTableModel();
    private JButton safeButton, availableButton, addButton;
    public static List<PCB> pcbs = new ArrayList<>();

    public MainShow() {
        initData();
        initShow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == safeButton) {

            TestShow testShow = new TestShow(pcbs, available);
            this.add(testShow);
            this.validate();
            this.repaint();

            for (int i = 0; i < data.size(); i++) {
                pcbs.add(new PCB(data.get(i).get(0).toString(), data.get(i).get(2).toString(), data.get(i).get(1).toString(), false));
            }
        } else if (e.getSource() == availableButton) {
            model.setAvailable();
        } else if (e.getSource() == addButton) {
            model.addPcb();
            this.validate();
            this.removeAll();
            this.initData();
            this.initShow();
            this.validate();
            this.repaint();

        }
    }

    private void initData() {
        Vector<Object> p0 = new Vector<Object>();
        p0.add(new String("P0"));
        p0.add(new String("0 1 0"));
        p0.add(new String("7 4 3"));

        Vector<Object> p1 = new Vector<Object>();
        p1.add(new String("P1"));
        p1.add(new String("2 0 0"));
        p1.add(new String("1 2 2"));

        Vector<Object> p2 = new Vector<Object>();
        p2.add(new String("P2"));
        p2.add(new String("3 0 2"));
        p2.add(new String("6 0 0"));

        Vector<Object> p3 = new Vector<Object>();
        p3.add(new String("P3"));
        p3.add(new String("2 1 1"));
        p3.add(new String("0 1 1"));

        Vector<Object> p4 = new Vector<Object>();
        p4.add(new String("P4"));
        p4.add(new String("0 0 2"));
        p4.add(new String("4 3 1"));

        Vector<Object> p5 = new Vector<Object>();
        p5.add(new String("P5"));
        p5.add(new String("0 0 1"));
        p5.add(new String("5 5 1"));
        if (data.size() == 0) {
            data.add(p0);
            data.add(p1);
            data.add(p2);
            data.add(p3);
            data.add(p4);
            data.add(p5);
        }
        pcbs.clear();
        for (int i = 0; i < data.size(); i++) {
            pcbs.add(new PCB(data.get(i).get(0).toString(), data.get(i).get(2).toString(), data.get(i).get(1).toString(), false));
        }
    }

    private void initShow() {
        Box baseBox = Box.createVerticalBox();
        JPanel showPanel = new JPanel();
        Box mainBox = Box.createVerticalBox();
        Box textTop = Box.createHorizontalBox();
        Box textButton = Box.createHorizontalBox();
        Box textTable = Box.createHorizontalBox();
        //标题
        JLabel bannerLabel = new JLabel("资源分配表:");

        //表格
        table = new JTable(model);                     //把模型对象作为参数构造表格对象，这样就可以用表格显示出数据
        TestShow.setColumnColor(table);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        JScrollPane scroll = new JScrollPane(table);   //把表格控件放到滚动容器里，页面不够长显示可以滚动
        scroll.setPreferredSize(new Dimension(500, 250));
        textButton.add(scroll);
        //底部按钮

        safeButton = new JButton("安全序列");
        safeButton.addActionListener(this);
        addButton = new JButton("添加作业");
        addButton.addActionListener(this);
        availableButton = new JButton("修改剩余资源");
        availableButton.addActionListener(this);

        addButton.setContentAreaFilled(false);
        safeButton.setContentAreaFilled(false);
        availableButton.setContentAreaFilled(false);
        // bannerLabel.add(safeButton);
        textTop.add(bannerLabel);

        textButton.add(scroll);
        textTable.add(Box.createHorizontalStrut(30));//创建间隔
        textTable.add(availableButton);
        textTable.add(Box.createHorizontalStrut(10));//创建间隔
        textTable.add(addButton);
        textTable.add(Box.createHorizontalStrut(10));//创建间隔
        textTable.add(safeButton);
        //-------------------容器内容------------------------------
        mainBox.add(textTop);
        mainBox.add(Box.createVerticalStrut(15));                  //创建上下空间距离
        mainBox.add(textTable);
        mainBox.add(Box.createVerticalStrut(15));                  //创建上下空间距离
        mainBox.add(textButton);

        showPanel.add(mainBox);
        baseBox.add(showPanel);

        this.add(baseBox, BorderLayout.NORTH);

    }

    static class MyTableModel extends AbstractTableModel  //模型类
    {

        Vector<String> title = new Vector<String>();// 列名

        /**
         * 构造方法，初始化二维动态数组data对应的数据
         */
        public MyTableModel()                       //构造方法
        {
            title.add("进程名");

            title.add("分配  A B C");

            title.add("需求  A B C");

        }

        /**
         * 得到列名
         */
        @Override
        public String getColumnName(int column) {
            return title.elementAt(column);
        }

        /**
         * 重写方法，得到表格列数
         */
        @Override
        public int getColumnCount() {
            return title.size();
        }

        /**
         * 得到表格行数
         */
        @Override
        public int getRowCount() {
            return data.size();
        }

        /**
         * 得到数据所对应对象
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            //return data[rowIndex][columnIndex];
            return data.elementAt(rowIndex).elementAt(columnIndex);
        }

        /**
         * 得到指定列的数据类型
         */
//        @Override
//        public Class<?> getColumnClass(int columnIndex) {
//            return data.elementAt(0).elementAt(columnIndex).getClass();
//        }

        /**
         * 指定设置数据单元是否可编辑.
         */
//        @Override
//        public boolean isCellEditable(int rowIndex, int columnIndex) {
//            return false;
//        }

        //设置剩余资源
        public static void setAvailable() {
            String inputMsg = JOptionPane.showInputDialog(null, "输入可分配资源数：", available);

            String availableInput = inputMsg;

            while (availableInput.length() <= 4) {
                JOptionPane.showMessageDialog(null, "输入错误");
                inputMsg = JOptionPane.showInputDialog(null, "输入可分配资源数：", "可分配资源", JOptionPane.PLAIN_MESSAGE);
                availableInput = inputMsg;
            }
            available = availableInput;
        }

        //添加进程
        public void addPcb() {
            String pName = JOptionPane.showInputDialog(null, "输入进程名字：", "P6");
            String pAv = JOptionPane.showInputDialog(null, "输入已分配资源：", "0 0 0");
            String pNeed = JOptionPane.showInputDialog(null, "输入需求资源：", "0 0 0");
            Vector<Object> p = new Vector<Object>();
            p.add(new String(pName));
            p.add(new String(pAv));
            p.add(new String(pNeed));
            data.add(p);
            pcbs.add(new PCB(data.get(data.size() - 1).get(0).toString(), data.get(data.size() - 1).get(2).toString(), data.get(data.size() - 1).get(1).toString(), false));

        }

    }

    public static void setColumnColor(JTable table) {
        try {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
                private static final long serialVersionUID = 1L;

                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    if (row % 2 == 0)
                        setBackground(Color.WHITE);//设置奇数行底色
                    else if (row % 2 == 1)
                        setBackground(new Color(220, 230, 241));//设置偶数行底色
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            };
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
            }
            tcr.setHorizontalAlignment(JLabel.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
