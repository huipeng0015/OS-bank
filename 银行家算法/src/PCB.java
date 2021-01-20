

public class PCB {
    public String name;
    public String need;
    public String allocation;
    public boolean finish;
    public boolean run;


    public PCB(String name, String need, String allocation, boolean finish) {
        this.name = name;
        this.need = need;
        this.allocation = allocation;
        this.finish = finish;
        this.run = false;

    }
}
