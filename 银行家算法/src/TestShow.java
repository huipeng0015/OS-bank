import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class TestShow extends JPanel implements ActionListener {
    private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    public static Queue<String> safe = new LinkedList<String>();
    private JTable table;  //表格

    private TestShow.MyTableModel model = new TestShow.MyTableModel();  //声明模型对象
    private List<PCB> process = new ArrayList<>();
    private String available;
    private JButton requestButton;
    private JButton openButton;

    private String XGavailable;

    public TestShow(List<PCB> pcbs, String available) {
        this.process = pcbs;
        this.available = available;
        this.XGavailable = available;
        Test(pcbs, available);
        initView();

    }

    //初始化窗口
    private void initView() {
        Box baseBox = Box.createVerticalBox();     //根盒子

        //-------------------容器内容------------------------------
        JPanel showPanel = new JPanel();
        Box mainBox = Box.createVerticalBox();
        Box textTop = Box.createHorizontalBox();
        Box textTop2 = Box.createHorizontalBox();

        Box textCenter = Box.createHorizontalBox();
        Box textLast = Box.createHorizontalBox();
        //标题
        JLabel bannerLabel = new JLabel("安全序列表:" + "初始剩余资源为  " + available);
        textTop.add(bannerLabel);
        //表格
        table = new JTable(model);                     //把模型对象作为参数构造表格对象，这样就可以用表格显示出数据
        setColumnColor(table);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        JScrollPane scroll = new JScrollPane(table);   //把表格控件放到滚动容器里，页面不够长显示可以滚动
        scroll.setPreferredSize(new Dimension(500, 250));
        textCenter.add(scroll);

        textLast.add(Box.createHorizontalStrut(30));//创建间隔

        //输出打印标题2——————————————————————————————————————————
        String s = new String(" ");
        for (int i = 0; i < data.size(); i++) {
            if ((Boolean) data.get(i).get(5) == false) {
                return;
            }
            s += data.get(i).get(0).toString();
            safe.add(new String(data.get(i).get(0).toString()));
            if (i < 10) {
                s += " ";
            }
        }
        s += " ";
        String textMessage = "存在安全序列";
        if (s.length() <= 2) textMessage = "没有安全序列";
        textMessage = textMessage + s;
        JLabel bannerLabe2 = new JLabel(textMessage);
        textTop2.add(bannerLabe2);
        requestButton = new JButton("请求资源");
        requestButton.addActionListener(this);
        requestButton.setContentAreaFilled(false);

        openButton = new JButton("释放资源");
        openButton.addActionListener(this);
        openButton.setContentAreaFilled(false);

        //-------------------容器内容------------------------------
        mainBox.add(textTop);
        mainBox.add(requestButton);
        mainBox.add(openButton);
        mainBox.add(Box.createVerticalStrut(5));
        mainBox.add(textTop2);
        mainBox.add(Box.createVerticalStrut(7));
        mainBox.add(textLast);
        mainBox.add(Box.createVerticalStrut(10));                  //创建上下空间距离
        mainBox.add(textCenter);

        showPanel.add(mainBox);
        baseBox.add(showPanel);

        this.add(baseBox, BorderLayout.NORTH);
    }

    private void Test(List<PCB> process, String available) {
        for (int i = 0; i < process.size(); i++) {
            if (process.get(i).finish == false) {
                if (compare(available, process.get(i).need)) {
                    Vector<Object> v = new Vector<Object>();
                    v.add(process.get(i).name);
                    v.add(available);
                    v.add(process.get(i).need);
                    v.add(process.get(i).allocation);
                    available = add(available, process.get(i).allocation);
                    process.get(i).finish = true;
                    v.add(available);
                    v.add(process.get(i).finish);
                    data.add(v);
                    process.remove(i);
                    Test(process, available);
                }
            }
        }

    }

    private void requetTest(List<PCB> process, String available, String scName) {
        for (int i = 0; i < process.size(); i++) {
            if (process.get(i).name.equals(scName)) {
                if (process.get(i).run == false) {
                    if (compare(available, process.get(i).need)) {
                        available = delete(available, process.get(i).allocation);
                        process.get(i).run = true;
                        JOptionPane.showMessageDialog(null, "申请成功");
                    } else {
                        JOptionPane.showMessageDialog(null, "申请失败");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "资源已经申请");
                }
            }
        }

    }

    private void openTest(List<PCB> process, String available, String scName) {
        for (int i = 0; i < process.size(); i++) {
            if (process.get(i).name.equals(scName)) {
                if (process.get(i).run == true) {
                    available = add(available, process.get(i).allocation);
                    JOptionPane.showMessageDialog(null, "释放成功");
                } else {
                    JOptionPane.showMessageDialog(null, "释放失败");
                }
            }
        }

    }

    private boolean compare(String a, String b) {
        String a_temp[] = a.split(" ");
        String b_temp[] = b.split(" ");
        //i<4
        for (int i = 0; i < 3; i++) {
            if (Integer.parseInt(a_temp[i]) < Integer.parseInt(b_temp[i])) {
                return false;
            }
        }
        return true;
    }

    private String add(String a, String b) {
        String a_temp[] = a.split(" ");
        String b_temp[] = b.split(" ");
        String c = new String();
        //i<4
        for (int i = 0; i < 3; i++) {
            int temp = Integer.parseInt(a_temp[i]) + Integer.parseInt(b_temp[i]);
            c += Integer.toString(temp);
            //i<3
            if (i < 2) {
                c += " ";
            }
        }
        return c;
    }

    private String delete(String a, String b) {
        String a_temp[] = a.split(" ");
        String b_temp[] = b.split(" ");
        String c = new String();
        //i<4
        for (int i = 0; i < 3; i++) {
            int temp = Integer.parseInt(a_temp[i]) - Integer.parseInt(b_temp[i]);
            c += Integer.toString(temp);
            //i<3
            if (i < 2) {
                c += " ";
            }
        }
        return c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == requestButton) {
            compSafe();
        } else if (e.getSource() == openButton) {
            openSafe();
        }
    }

    class MyTableModel extends AbstractTableModel  //模型类
    {

        Vector<String> title = new Vector<String>();// 列名

        /**
         * 构造方法，初始化二维动态数组data对应的数据
         */
        public MyTableModel()                       //构造方法
        {
            title.add("进程名");

            title.add("工作向量  A B C");

            title.add("需求  A B C");

            title.add("分配  A B C");

            title.add("工作向量+可分配  A B C");

            title.add("完成");

        }

        // 以下为继承自AbstractTableModle的方法，可以自定义

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
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return data.elementAt(0).elementAt(columnIndex).getClass();
        }

        /**
         * 指定设置数据单元是否可编辑.
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

    }

    public void compSafe() {
        String pName = JOptionPane.showInputDialog(null, "输入要申请资源的进程名：", "P6");
        requetTest(process, XGavailable, pName);
    }

    public void openSafe() {
        String pName = JOptionPane.showInputDialog(null, "输入要释放资源的进程名：", "P6");
        openTest(process, XGavailable, pName);
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

