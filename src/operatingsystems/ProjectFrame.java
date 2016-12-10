package operatingsystems;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

class Process {

    int process_no;
    int process_arrival_time;
    int process_burst_time;
    int process_priority;
    int process_waiting_time;
    int process_burstRobin;

    public Process(int p_n, int p_a_t, int p_b_t) {
        this.process_no = p_n;
        this.process_burst_time = p_b_t;
        this.process_arrival_time = p_a_t;
        this.process_priority = 0;
        this.process_burstRobin = this.process_burst_time;
    }

    public Process(int p_n, int p_a_t, int p_b_t, int priority) {
        this.process_no = p_n;
        this.process_burst_time = p_b_t;
        this.process_arrival_time = p_a_t;
        this.process_priority = priority;
        this.process_burstRobin = this.process_burst_time;
    }

    public void set_arrival_time(int a_t) {
        this.process_arrival_time = a_t;
    }

    public void set_waiting_time(int w_t) {
        this.process_waiting_time = w_t;
    }

    public void set_piority(int p) {
        this.process_priority = p;
    }

    public void set_no(int p) {
        this.process_no = p;
    }

    public void set_burst_time(int b_t) {
        this.process_burst_time = b_t;
    }

    public int get_arrival_time() {

        return this.process_arrival_time;
    }

    public int get_waiting_time() {

        return this.process_waiting_time;
    }

    public int get_burst_time() {
        return this.process_burst_time;
    }

    public int get_priority() {
        return this.process_priority;
    }

    public int get_process_number() {
        return this.process_no;
    }

    public void calculate_waiting_time(int endTime) {
        this.process_waiting_time = endTime - this.process_burst_time;
    }

}

class Operations {
    
    
       public static void sort_list_by_burst_time( List<Process> p) {

        for (int i = 0; i < p.size() - 1; i++) {
            for (int j = (i + 1); j < (p.size()); j++) {
                if (p.get(j).process_burst_time < p.get(i).process_burst_time) {
                    Process temp = p.get(i);
                    p.set(i, p.get(j));
                    p.set(j, temp);
                }
            }
        }}

    public static void sort_by_arrival_time(Process[] p) {
        for (int i = 0; i < p.length - 1; i++) {
            for (int j = (i + 1); j < (p.length); j++) {
                if (p[j].process_arrival_time < p[i].process_arrival_time) {
                    Process temp = p[i];
                    p[i] = p[j];
                    p[j] = temp;
                }
            }
        }
    }

    public static void sort_by_burst_time(Process[] p) {

        for (int i = 0; i < p.length - 1; i++) {
            for (int j = (i + 1); j < (p.length); j++) {
                if (p[j].process_burst_time < p[i].process_burst_time) {
                    Process temp = p[i];
                    p[i] = p[j];
                    p[j] = temp;
                }
            }
        }

    }

    public static void sort_by_priority(Process[] p) {

        for (int i = 0; i < p.length - 1; i++) {
            for (int j = (i + 1); j < (p.length); j++) {
                if (p[j].get_priority() < p[i].get_priority()) {
                    Process temp = p[i];
                    p[i] = p[j];
                    p[j] = temp;
                }
            }
        }

    }

    public static void copy_process(Process[] p1, Process[] p2) {
        for (int i = 0; i < p1.length; i++) {
            p2[i] = p1[i];
        }
    }

    public static boolean p_exists(Process[] p, Process pp) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == pp) {
                return true;
            }
        }
        return false;
    }

    public static int find(Process[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].get_process_number() == value) {
                return i;
            }
        }
        return -1;
    }

}

class Scheduling {

    Process[] processes;
    List<Process> processesList;
    int quantum_time;
    int processes_number;
    ArrayList<Integer> times = new ArrayList<>();

    public void setProcesses(Process[] p) {
        this.processes = p;
    }

    public void setQuantum(int q) {
        this.quantum_time = q;
    }

    public void roundRobin() {

        int q = 0;
        int total_time = 0;
        for (int i = 0; i < processes_number; i++) {
            total_time += processes[i].get_burst_time();
        }

        Process[] p = new Process[processes_number];
        Operations.copy_process(processes, p);

        int i = 0;
        while (q < total_time) {
            if (p[i].process_burstRobin > 0) {
                processesList.add(p[i]);
                if (p[i].process_burstRobin > quantum_time) {
                    p[i].process_burstRobin -= quantum_time;
                    q += quantum_time;
                    if (p[i].process_burstRobin == 0) {
                        p[i].process_waiting_time = q - p[i].process_burstRobin;
                    }
                } else {
                    q += p[i].process_burstRobin;
                    p[i].process_burstRobin = 0;
                    p[i].process_waiting_time = q - p[i].process_burstRobin;
                }
            }
            i++;
            if (i == (processes_number)) {
                i = 0;
            }
        }

        for (Process processesList1 : processesList) {
            System.out.println(processesList1.get_process_number() + " , " + processesList1.get_burst_time() + "  ,  " + processesList1.get_waiting_time());
        }

    }

    public void fcfs() {

        Operations.sort_by_arrival_time(processes);
        int sum = processes[0].get_burst_time();
        for (int i = 1; i < processes_number; i++) {
            processes[i].process_waiting_time = sum - processes[i].get_arrival_time();
            sum += processes[i].get_burst_time();
        }

        for (int i = 0; i < processes_number; i++) {
            System.out.println(processes[i].get_process_number() + " , ");
        }
    }

}

public class ProjectFrame extends javax.swing.JFrame {

    static int sumCPUBurstTime;
    static int lengthOfEachBlock;
    static int numberOfProcesses;
    static int CPUBurstTime[], priority[];
    static BufferedReader br;
    static OperatingSystems obj;
    static FrameForPriorityScheduling frame;

    public ProjectFrame() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        schedularType = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        preemptive = new javax.swing.JRadioButton();
        nonpreemptive = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        processes_no = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ConfirmBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        quantumTime = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        waitingT = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        data = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        prioritiesD = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        gaps = new javax.swing.JRadioButton();
        nogaps = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Schedular Type : ");

        jLabel2.setText("Preemptive :");

        preemptive.setText("Yes");

        nonpreemptive.setText("No");

        jLabel3.setText("Processes Number:");

        processes_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processes_noActionPerformed(evt);
            }
        });

        jLabel4.setText("Processes Data");

        ConfirmBtn.setText("Confirm");
        ConfirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmBtnActionPerformed(evt);
            }
        });

        jLabel8.setText("Quantum time :");
        jLabel8.setToolTipText("");

        jLabel10.setText("Waiting Time :");

        waitingT.setText("....");

        data.setColumns(20);
        data.setRows(5);
        jScrollPane4.setViewportView(data);

        jLabel5.setText("Priorties :");

        prioritiesD.setColumns(20);
        prioritiesD.setRows(5);
        jScrollPane1.setViewportView(prioritiesD);

        jLabel6.setFont(new java.awt.Font("Sitka Small", 3, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("CPU Scheduling Algorithms");

        jLabel7.setText("Have Gaps");

        gaps.setText("Yes");

        nogaps.setText("No");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(ConfirmBtn))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(121, 121, 121)
                                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel3)
                                                            .addComponent(jLabel1))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(schedularType, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(processes_no, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addComponent(jLabel4))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel7)
                                                .addGap(35, 35, 35)))
                                        .addComponent(gaps)))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(preemptive))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addGap(37, 37, 37)
                                            .addComponent(quantumTime, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(nogaps))
                                        .addGap(60, 60, 60)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(1, 1, 1)
                        .addComponent(nonpreemptive)
                        .addGap(135, 135, 135))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel10)
                        .addGap(41, 41, 41)
                        .addComponent(waitingT)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(gaps)
                            .addComponent(nogaps, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(151, 151, 151)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(processes_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel8)
                                    .addComponent(quantumTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(schedularType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(preemptive)
                                    .addComponent(nonpreemptive))))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)))
                .addComponent(ConfirmBtn)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(waitingT))
                .addGap(162, 162, 162))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void processes_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processes_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_processes_noActionPerformed

    /**
     *
     * @param processesList
     * @param times
     */
    static void drawGanttChartForPriorityScheduling(List<Process> processesList, ArrayList<Integer> times) {
        FrameForPriorityScheduling.set_processes(processesList, times);
        FrameForPriorityScheduling f = new FrameForPriorityScheduling();
    }

    public static List<Process> processesList;
    ArrayList<Integer> times;
    private void ConfirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmBtnActionPerformed

        String schedular = schedularType.getText().toString();
        String num = processes_no.getText().toString();
        int processesNo = Integer.parseInt(num);
        times = new ArrayList<>();
        boolean p = false;
        Process[] processes = new Process[processesNo];
        String pdata = data.getText().toString();
        String lines[] = pdata.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) {
            String temp = lines[i];
            String[] values = temp.split(",");
            String p_n = values[0];
            String p_a = values[1];
            String p_b = values[2];
            int pn = Integer.parseInt(p_n);
            int pa = Integer.parseInt(p_a);
            int pb = Integer.parseInt(p_b);
            processes[i] = new Process(pn, pa, pb);
        }

        if (gaps.isSelected()) {
            Process empty = new Process(-1, -1, -1);
            switch (schedular) {
                case "fcfs": {
                    Operations.sort_by_arrival_time(processes);
                    int sum = 0;
                    int j = 0;
                    int timeline = 0;
                    processesList = new ArrayList<>();
                    while (true) {

                        if (j == processesNo) {
                            break;
                        } else {
                            if (processes[j].get_arrival_time() <= timeline) {
                                processesList.add(processes[j]);
                                times.add(processes[j].process_burst_time);

                                sum += processes[j].get_burst_time();
                                processes[j].process_waiting_time = (sum - processes[j].get_arrival_time()) - processes[j].get_burst_time();
                                timeline += processes[j].get_burst_time();
                                j++;
                            } else {
                                processesList.add(empty);
                                timeline++;
                                times.add(1);
                                sum++;
                            }
                        }

                    }
                }
                break;

                case "priority": {

                    if (preemptive.isSelected()) {
                        p = true;
                    } else if (nonpreemptive.isSelected()) {
                        p = false;
                    }

                    Process[] temp1 = new Process[processesNo];
                    Process[] temp2 = new Process[processesNo];
                    Process[] done = new Process[processesNo];

                    Operations.copy_process(processes, temp1);
                    Operations.copy_process(processes, temp2);
                    Operations.sort_by_arrival_time(temp1);
                    Operations.sort_by_priority(temp2);

                    int current_time = 0;
                    int burst = 0;
                    int arrival = 0;
                    int j = 0;
                    int timeline = 0;
                    processesList = new ArrayList<>();
                    while (true) {

                        if (j == processesNo) {
                            break;
                        }

                        if (burst < processesNo) {
                            if (Operations.p_exists(done, temp2[burst])) {
                                burst++;
                            }
                            if ((!Operations.p_exists(done, temp2[burst])) && temp2[burst].get_arrival_time() <= current_time) {
                                done[j] = temp2[burst];
                                processesList.add(done[j]);

                                current_time += temp2[burst].get_burst_time();
                                times.add(temp2[burst].get_burst_time());
                                for (int k = 0; k < processesNo; k++) {
                                    if (processes[k].process_no == done[j].process_no) {
                                        processes[k].process_waiting_time = current_time - processes[k].process_burst_time - processes[k].process_arrival_time;
                                    }
                                }
                                burst++;
                                j++;
                            } else {
                                while ((arrival < processesNo) && Operations.p_exists(done, temp1[arrival])) {
                                    arrival++;
                                }
                                if (!Operations.p_exists(done, temp1[arrival]) && temp1[arrival].get_arrival_time() <= current_time) {
                                    done[j] = temp1[arrival];
                                    processesList.add(done[j]);

                                    current_time += temp1[arrival].get_burst_time();
                                    times.add(temp1[burst].get_burst_time());
                                    for (int k = 0; k < processesNo; k++) {
                                        if (processes[k].process_no == done[j].process_no) {
                                            processes[k].process_waiting_time = current_time - processes[k].process_burst_time - processes[k].process_arrival_time;
                                        }
                                    }
                                    arrival++;
                                    j++;
                                } else {

                                    processesList.add(empty);
                                    timeline++;
                                    times.add(1);
                                    current_time++;
                                }

                            }
                        }
                    }

                }
                break;

                case "SJF": {

                    if (preemptive.isSelected()) {
                        p = true;
                    } else if (nonpreemptive.isSelected()) {
                        p = false;
                    }

                    Process[] temp1 = new Process[processesNo];
                    Process[] temp2 = new Process[processesNo];
                    Process[] done = new Process[processesNo];

                    Operations.copy_process(processes, temp1);
                    Operations.copy_process(processes, temp2);
                    Operations.sort_by_arrival_time(temp1);
                    Operations.sort_by_burst_time(temp2);

                    int current_time = 0;
                    int burst = 0;
                    int arrival = 0;
                    int j = 0;
                    int timeline = 0;
                    processesList = new ArrayList<>();
                    while (true) {

                        if (j == processesNo) {
                            break;
                        }

                        if (burst < processesNo) {
                            if (Operations.p_exists(done, temp2[burst])) {
                                burst++;
                            }
                            if ((!Operations.p_exists(done, temp2[burst])) && temp2[burst].get_arrival_time() <= current_time) {
                                done[j] = temp2[burst];
                                processesList.add(done[j]);

                                current_time += temp2[burst].get_burst_time();
                                times.add(temp2[burst].get_burst_time());
                                for (int k = 0; k < processesNo; k++) {
                                    if (processes[k].process_no == done[j].process_no) {
                                        processes[k].process_waiting_time = current_time - processes[k].process_burst_time - processes[k].process_arrival_time;
                                    }
                                }
                                burst++;
                                j++;
                            } else {
                                while ((arrival < processesNo) && Operations.p_exists(done, temp1[arrival])) {
                                    arrival++;
                                }
                                if (!Operations.p_exists(done, temp1[arrival]) && temp1[arrival].get_arrival_time() <= current_time) {
                                    done[j] = temp1[arrival];
                                    processesList.add(done[j]);

                                    current_time += temp1[arrival].get_burst_time();
                                    times.add(temp1[burst].get_burst_time());
                                    for (int k = 0; k < processesNo; k++) {
                                        if (processes[k].process_no == done[j].process_no) {
                                            processes[k].process_waiting_time = current_time - processes[k].process_burst_time - processes[k].process_arrival_time;
                                        }
                                    }
                                    arrival++;
                                    j++;
                                } else {

                                    processesList.add(empty);
                                    timeline++;
                                    times.add(1);
                                    current_time++;
                                }

                            }
                        }
                    }

                }
                break;

            }

        } else {
            switch (schedular) {
                case "fcfs": {
                    Operations.sort_by_arrival_time(processes);
                    int sum = 0;
                    for (int i = 0; i < processesNo; i++) {
                        times.add(processes[i].process_burst_time);
                        sum += processes[i].get_burst_time();
                        processes[i].process_waiting_time = (sum - processes[i].get_arrival_time()) - processes[i].get_burst_time();
                    }
                    processesList = new ArrayList<>(Arrays.asList(processes));
                }
                break;

                case "round-robin": {

                    int quantum = Integer.parseInt(quantumTime.getText().toString());
                    int q = 0;
                    int total_time = 0;
                    processesList = new ArrayList<>();
                    for (int i = 0; i < processesNo; i++) {
                        total_time += processes[i].get_burst_time();
                    }
                    Process[] pro = new Process[processesNo];
                    Operations.copy_process(processes, pro);

                    int i = 0;

                    while (q < total_time) {
                        if (pro[i].process_burstRobin > 0) {
                            processesList.add(pro[i]);

                            if (pro[i].process_burstRobin > quantum) {
                                pro[i].process_burstRobin -= quantum;
                                q += quantum;
                                times.add(quantum);
                                if (pro[i].process_burstRobin == 0) {
                                    pro[i].process_waiting_time = q - pro[i].process_burst_time;
                                }
                            } else {
                                q += pro[i].process_burstRobin;

                                times.add(pro[i].process_burstRobin);
                                pro[i].process_burstRobin = 0;
                                pro[i].process_waiting_time = q - pro[i].process_burst_time;
                            }

                        }
                        i++;
                        if (i == (processesNo)) {
                            i = 0;
                        }
                    }
                }
                break;

                       case "priority": {
                    String pri = prioritiesD.getText().toString();
                    String p_lines[] = pri.split("\\r?\\n");
                    for (int i = 0; i < p_lines.length; i++) {
                        String temp = p_lines[i];
                        int pn = Integer.parseInt(temp);
                        processes[i].set_piority(pn);
                    }
                    if (preemptive.isSelected()) {
                        p = true;
                    } else if (nonpreemptive.isSelected()) {
                        p = false;
                    }

                    if (p) {

                        int timer = 0;
                        List<Process> listl3 = new LinkedList<Process>();

                        Process[] process_by_arrival = new Process[processesNo];
                        Process[] process_by_burst = new Process[processesNo];
                        int[] bursts = new int[processesNo];

                        Operations.copy_process(processes, process_by_burst);

                        Operations.sort_by_priority(process_by_burst);
                        List<Process> listl4 = new LinkedList<Process>(Arrays.asList(process_by_burst));
                        int total_time = 0;
                        for (int i = 0; i < processesNo; i++) {
                            bursts[i] = processes[i].get_burst_time();
                            total_time += processes[i].get_burst_time();
                        }

                        while (timer < total_time) {
                            int array_counter = 0;
                            while (listl4.get(array_counter).process_arrival_time > timer) {
                                array_counter++;
                            }

                            if (listl4.get(array_counter).process_burst_time > 0) {
                                listl3.add(listl4.get(array_counter));
                                listl4.get(array_counter).process_burst_time--;
                                times.add(1);
                                if (listl4.get(array_counter).process_burst_time == 0) {

                                    Process x = listl4.get(array_counter);
                                    for (int i = 0; i < processesNo; i++) {
                                         
                                            if (x.process_no==(processes[i].process_no))
                                        {
                                            int finish=times.size();
                                            int burst=bursts[i];
                                            int arr=processes[i].process_arrival_time;
                                            processes[i].process_waiting_time= finish-burst-arr;
                                            
                                              for (int z = 0; z < processesNo; z++) {
                                                  
                                                    if (listl3.get(z).process_no ==   processes[i].process_no) {
                                            listl3.get(z).process_waiting_time = processes[i].process_waiting_time;
                                        }
                                                  
                                              }
                                            
                                  
                                        }
                                     
                                    }
                                    
                                       
                                    boolean e = listl4.contains(x);
                                    if (e) {
                                        listl4.remove(x);
                                    }

                                }
                         

                            }
                                   timer++;
                        }
                        processesList = listl3;
                    } else {

                        Process[] temp1 = new Process[processesNo];
                        Process[] temp2 = new Process[processesNo];
                        Process[] done = new Process[processesNo];

                        Operations.copy_process(processes, temp1);
                        Operations.copy_process(processes, temp2);
                        Operations.sort_by_arrival_time(temp1);
                        Operations.sort_by_priority(temp2);

                        int current_time = 0;
                        int burst = 0;
                        int arrival = 0;
                        for (int i = 0; i < processesNo; i++) {
                            if (burst < processesNo) {
                                if (Operations.p_exists(done, temp2[burst])) {
                                    burst++;
                                }
                                if ((!Operations.p_exists(done, temp2[burst])) && temp2[burst].get_arrival_time() <= current_time) {
                                    done[i] = temp2[burst];
                                    current_time += temp2[burst].get_burst_time();
                                    times.add(temp2[burst].get_burst_time());
                                    for (int k = 0; k < processesNo; k++) {
                                        if (processes[k].process_no == done[i].process_no) {
                                            processes[k].process_waiting_time = current_time - processes[k].process_burst_time - processes[k].process_arrival_time;
                                        }
                                    }
                                    burst++;
                                } else {
                                    while ((arrival < processesNo) && Operations.p_exists(done, temp1[arrival])) {
                                        arrival++;
                                    }
                                    if (!Operations.p_exists(done, temp1[arrival])) {
                                        done[i] = temp1[arrival];
                                        current_time += temp1[arrival].get_burst_time();
                                        times.add(temp1[burst].get_burst_time());
                                        for (int k = 0; k < processesNo; k++) {
                                            if (processes[k].process_no == done[i].process_no) {
                                                processes[k].process_waiting_time = current_time - processes[k].process_burst_time - processes[k].process_arrival_time;
                                            }
                                        }
                                        arrival++;
                                    }

                                }
                            }
                        }
                        processesList = new ArrayList<>(Arrays.asList(done));

                    }

                }
                break;

                case "SJF": {

                    if (preemptive.isSelected()) {
                        p = true;
                    } else if (nonpreemptive.isSelected()) {
                        p = false;
                    }
                    if (p) 
                    {

                        int timer = 0;
                        List<Process> listl3 = new LinkedList<Process>();

                        Process[] process_by_arrival = new Process[processesNo];
                        Process[] process_by_burst = new Process[processesNo];
                        int[] bursts = new int[processesNo];

                        Operations.copy_process(processes, process_by_burst);

                        Operations.sort_by_burst_time(process_by_burst);
                        List<Process> listl4 = new LinkedList<Process>(Arrays.asList(process_by_burst));
                        int total_time = 0;
                        for (int i = 0; i < processesNo; i++) {
                            bursts[i] = processes[i].get_burst_time();
                            total_time += processes[i].get_burst_time();
                        }

                        while (timer < total_time) {
                            
                            int array_counter = 0;
                            while (listl4.get(array_counter).process_arrival_time > timer) {
                                array_counter++;
                            }

                            if (listl4.get(array_counter).process_burst_time > 0) {
                                listl3.add(listl4.get(array_counter));
                                listl4.get(array_counter).process_burst_time--;
                                Operations.sort_list_by_burst_time(listl4);
                                
                                times.add(1);
                                
                                if (listl4.get(array_counter).process_burst_time == 0) {

                                    Process x = listl4.get(array_counter);
                                         for (int i = 0; i < processesNo; i++) {
                                         
                                            if (x.process_no==(processes[i].process_no))
                                        {
                                            int finish=times.size();
                                            int burst=bursts[i];
                                            int arr=processes[i].process_arrival_time;
                                            processes[i].process_waiting_time= finish-burst-arr;
                                            
                                              for (int z = 0; z < processesNo; z++) {
                                                  
                                                    if (listl3.get(z).process_no ==   processes[i].process_no) {
                                            listl3.get(z).process_waiting_time = processes[i].process_waiting_time;
                                        }
                                                  
                                              }
                                            
                                  
                                        }
                                     
                                    }
                                    boolean e = listl4.contains(x);
                                    if (e) {
                                        listl4.remove(x);
                                    }

                                }
                                timer++;

                            }
                        }
                        processesList = listl3;

                    } else {

                        Process[] temp1 = new Process[processesNo];
                        Process[] temp2 = new Process[processesNo];
                        Process[] done = new Process[processesNo];

                        Operations.copy_process(processes, temp1);
                        Operations.copy_process(processes, temp2);
                        Operations.sort_by_arrival_time(temp1);
                        Operations.sort_by_burst_time(temp2);

                        int current_time = 0;
                        int burst = 0;
                        int arrival = 0;
                        for (int i = 0; i < processesNo; i++) {
                            if (burst < processesNo) {
                                if (Operations.p_exists(done, temp2[burst])) {
                                    burst++;
                                }
                                if ((!Operations.p_exists(done, temp2[burst])) && temp2[burst].get_arrival_time() <= current_time) {
                                    done[i] = temp2[burst];
                                    current_time += temp2[burst].get_burst_time();
                                    times.add(temp2[burst].get_burst_time());
                                    for (int k = 0; k < processesNo; k++) {
                                        if (processes[k].process_no == done[i].process_no) {
                                            processes[k].process_waiting_time = current_time - processes[k].process_burst_time - processes[k].process_arrival_time;
                                        }
                                    }
                                    burst++;
                                } else {
                                    while ((arrival < processesNo) && Operations.p_exists(done, temp1[arrival])) {
                                        arrival++;
                                    }
                                    if (!Operations.p_exists(done, temp1[arrival])) {
                                        done[i] = temp1[arrival];
                                        current_time += temp1[arrival].get_burst_time();
                                        times.add(temp1[burst].get_burst_time());
                                        for (int k = 0; k < processesNo; k++) {
                                            if (processes[k].process_no == done[i].process_no) {
                                                processes[k].process_waiting_time = current_time - processes[k].process_burst_time - processes[k].process_arrival_time;
                                            }
                                        }
                                        arrival++;
                                    }

                                }
                            }
                        }
                        processesList = new ArrayList<>(Arrays.asList(done));

                    }

                }
                break;

            }
        }

        double count = 0;

        for (int i = 0; i < processes.length; i++) {
            count += processes[i].get_waiting_time();
        }
        double avg_waiting_time = count / processes.length;
        waitingT.setText(avg_waiting_time + "");

        drawGanttChartForPriorityScheduling(processesList, times);
    }//GEN-LAST:event_ConfirmBtnActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProjectFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConfirmBtn;
    private javax.swing.JTextArea data;
    private javax.swing.JRadioButton gaps;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JRadioButton nogaps;
    private javax.swing.JRadioButton nonpreemptive;
    private javax.swing.JRadioButton preemptive;
    private javax.swing.JTextArea prioritiesD;
    private javax.swing.JTextField processes_no;
    private javax.swing.JTextField quantumTime;
    private javax.swing.JTextField schedularType;
    private javax.swing.JLabel waitingT;
    // End of variables declaration//GEN-END:variables
}

class FrameForPriorityScheduling extends JFrame {

    OperatingSystems obj;
    int priority[];
    public static List<Process> processesList;
    public static ArrayList<Integer> times;

    FrameForPriorityScheduling() {
        super("Scheduling");
        this.setVisible(true);
        this.setSize(500, 200);
        repaint();
    }

    public static void set_processes(List<Process> processesList, ArrayList<Integer> times) {
        FrameForPriorityScheduling.processesList = processesList;
        FrameForPriorityScheduling.times = times;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int currentTime = 0;
        int leftStart = 50;
        g = this.getContentPane().getGraphics();
        g.drawString(0 + "" + currentTime, leftStart, 50 + 50 + 20);

        for (int j = 0; j < processesList.size(); j++) {
            g = this.getContentPane().getGraphics();
            g.drawRect(leftStart, 50, 50, 50);
            int p_number = processesList.get(j).process_no;
            if (p_number == -1) {
                g.drawString("N/P", leftStart + 5, 50 + 50);

            } else {
                g.drawString("P" + (p_number), leftStart + 5, 50 + 50);
            }
            leftStart += 50;
            currentTime += times.get(j);
            g.drawString("" + currentTime, leftStart, 50 + 50 + 20);
        }
    }
}