package operatingsystems;

public class OperatingSystems {

    static final int SCREEN_WIDTH = 700, SCREEN_HEIGHT = 280;
    static final int rectangleUpperPadding = 50, rectangleHeight = 100;
    static int sumCPUBurstTime;
    static int lengthOfEachBlock;
    static int numberOfProcesses;
    static int CPUBurstTime[], priority[];

    public static void main(String[] args) {
        ProjectFrame pf = new ProjectFrame();
        pf.setVisible(true);
    }

}
