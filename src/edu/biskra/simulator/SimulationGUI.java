package edu.biskra.simulator;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.IAxisScalePolicy;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.axis.scalepolicy.AxisScalePolicyManualTicks;
import info.monitorenter.gui.chart.labelformatters.LabelFormatterNumber;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyFixedViewport;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.util.Range;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import java.awt.SystemColor;
import javax.swing.JProgressBar;
import java.text.Format;

/***
 * Graphical User Interface of our Service Rate management Sumulator.
 */
public class SimulationGUI extends JFrame {

	private JPanel contentPane;
	Simulator sm;
	float [][][]originReputation ;
	float [][][] assessdReputation ;
	int numberOfDays =0;
	private JPanel panel_A,panel_B,panel_C,panel_D,panel_E;
	private JFormattedTextField txt_ServiceNumber;
	private JLabel lblNumberOfUsers;
	private JFormattedTextField txt_UserNumber;
	private JLabel lblNumberOfDays;
	private JTextField txt_DaysNumber;
	private JTextField txt_UserRate;
	private JLabel lblHonestUsersRate;
	private JLabel label_2;
	private JTextField txt_nbrofrates;
	private JLabel lblNumberOfRates;
	private JTextField txt_execTime;
	private JFormattedTextField txt_lambda;
	private  JFormattedTextField txt_nbrOfIteration;
	private int iterNbr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimulationGUI frame = new SimulationGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SimulationGUI() {
		setTitle("WS Reputation Assessment Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1316, 878);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_Data = new JPanel();
		panel_Data.setBackground(new Color(70, 130, 180));
		panel_Data.setBounds(23, 27, 293, 795);
		contentPane.add(panel_Data);
		panel_Data.setLayout(null);
		
		txt_ServiceNumber = new JFormattedTextField(NumberFormat.getNumberInstance());
		txt_ServiceNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		txt_ServiceNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txt_ServiceNumber.setText("100");
		txt_ServiceNumber.setBounds(191, 172, 86, 29);
		panel_Data.add(txt_ServiceNumber);
		txt_ServiceNumber.setColumns(10);
		
		JLabel lblNumberOfServices = new JLabel("Number of Services");
		lblNumberOfServices.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumberOfServices.setForeground(new Color(255, 255, 255));
		lblNumberOfServices.setBounds(10, 171, 150, 29);
		panel_Data.add(lblNumberOfServices);
		
		lblNumberOfUsers = new JLabel("Number of Users");
		lblNumberOfUsers.setForeground(Color.WHITE);
		lblNumberOfUsers.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumberOfUsers.setBounds(10, 221, 150, 29);
		panel_Data.add(lblNumberOfUsers);
		
		txt_UserNumber = new JFormattedTextField();
		txt_UserNumber.setText("1000");
		txt_UserNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		txt_UserNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txt_UserNumber.setColumns(10);
		txt_UserNumber.setBounds(191, 222, 86, 29);
		panel_Data.add(txt_UserNumber);
		
		lblNumberOfDays = new JLabel("Number of Days");
		lblNumberOfDays.setForeground(Color.WHITE);
		lblNumberOfDays.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumberOfDays.setBounds(10, 324, 150, 29);
		panel_Data.add(lblNumberOfDays);
		
		txt_DaysNumber = new JFormattedTextField();
		txt_DaysNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		txt_DaysNumber.setText("100");
		txt_DaysNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txt_DaysNumber.setColumns(10);
		txt_DaysNumber.setBounds(191, 325, 86, 29);
		panel_Data.add(txt_DaysNumber);
		
		txt_UserRate = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		txt_UserRate.setText("75");
		txt_UserRate.setFont(new Font("Tahoma", Font.BOLD, 11));
		txt_UserRate.setHorizontalAlignment(SwingConstants.CENTER);
		txt_UserRate.setColumns(10);
		txt_UserRate.setBounds(191, 275, 66, 29);
		panel_Data.add(txt_UserRate);
		
		lblHonestUsersRate = new JLabel("Honest Users Rate");
		lblHonestUsersRate.setForeground(Color.WHITE);
		lblHonestUsersRate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHonestUsersRate.setBounds(10, 274, 150, 29);
		panel_Data.add(lblHonestUsersRate);
		
		label_2 = new JLabel("%");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_2.setBounds(258, 274, 19, 29);
		panel_Data.add(label_2);
		
		txt_nbrofrates = new JFormattedTextField();
		txt_nbrofrates.setText("1000");
		txt_nbrofrates.setFont(new Font("Tahoma", Font.BOLD, 11));
		txt_nbrofrates.setHorizontalAlignment(SwingConstants.CENTER);
		txt_nbrofrates.setColumns(10);
		txt_nbrofrates.setBounds(191, 380, 86, 29);
		panel_Data.add(txt_nbrofrates);
		
		lblNumberOfRates = new JLabel("Number of Rates per Day");
		lblNumberOfRates.setForeground(Color.WHITE);
		lblNumberOfRates.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumberOfRates.setBounds(10, 371, 160, 44);
		panel_Data.add(lblNumberOfRates);
		
		JButton btnStart = new JButton("Start Simulation");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
				//	progressBar.setIndeterminate(true);
					
					
					sm= new Simulator();
					int val = 0;
					
					//number of iterations
					if (!txt_nbrOfIteration.getText().equals(""))
					{ val = Integer.parseInt(txt_nbrOfIteration.getText());}
					if (val!=0)
						sm.setNumberOfIterations(val);
					else
						{String vals=""+sm.getNumberOfIterations();  txt_nbrOfIteration.setText(vals);}
					
					
					//number of days
					if (!txt_DaysNumber.getText().equals(""))
					{ val = Integer.parseInt(txt_DaysNumber.getText());}
					if (val!=0)
						sm.setNumberOfDays((short)val);
					else
						{String vals=""+sm.getNumberOfDays();  txt_DaysNumber.setText(vals);}
					
					// number of rates
					val=0;
					if (!txt_nbrofrates.getText().equals(""))
					{ val = Integer.parseInt(txt_nbrofrates.getText());}
					if (val!=0)
						sm.setNumberOfRatesPerDay((short)val);
					else
						{String vals=""+sm.getNumberOfRatesPerDay();  txt_nbrofrates.setText(vals);}
					
					// service number
					val=0;
					if (!txt_ServiceNumber.getText().equals(""))
					{ val = Integer.parseInt(txt_ServiceNumber.getText());}
					if (val!=0)
						sm.setNumberOfService((short)val);
					else
						{String vals=""+sm.getNumberOfService();  txt_ServiceNumber.setText(vals);}
					
					
					//number of user 
					val=0;
					if (!txt_UserNumber.getText().equals(""))
					{ val = Integer.parseInt(txt_UserNumber.getText());}
					if (val!=0)
						sm.setNumberOfUsers((short)val);
					else
						{String vals=""+sm.getNumberOfUsers();  txt_UserNumber.setText(vals);}
					
					
					// honest users rate
					val=0;
					if (!txt_UserRate.getText().equals(""))
					{ val = Integer.parseInt(txt_UserRate.getText());}
					if (val!=0)
						sm.setHonestUserRate((short)val);
					else
						{String vals=""+sm.getHonestUserRate();  txt_UserRate.setText(vals);}
					
					// lambda
					val=0;
					if (!txt_lambda.getText().equals(""))
					{ val = Integer.parseInt(txt_lambda.getText());}
					if (val!=0)
						sm.setLamda(val/100);
					else
						{String vals=""+(sm.getLamda()*100);  txt_lambda.setText(vals);}
					
					sm.start_simulation();
					originReputation=sm.getOriginReputation();
					assessdReputation=sm.getAssessdReputation();
					iterNbr = sm.getNumberOfIterations();
					numberOfDays= sm.getNumberOfDays();
					txt_execTime.setText((sm.getExecutionTime())+" ms");
					plot(0);plot(1);plot(2);plot(3);plot(4);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnStart.setBounds(66, 540, 160, 44);
		panel_Data.add(btnStart);
		
		JLabel lblSimulationParameter = new JLabel("Simulation Parameters");
		lblSimulationParameter.setHorizontalAlignment(SwingConstants.CENTER);
		lblSimulationParameter.setForeground(new Color(255, 255, 255));
		lblSimulationParameter.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSimulationParameter.setBounds(41, 72, 216, 27);
		panel_Data.add(lblSimulationParameter);
		
		txt_lambda = new JFormattedTextField();
		txt_lambda.setText("75");
		txt_lambda.setHorizontalAlignment(SwingConstants.CENTER);
		txt_lambda.setFont(new Font("Tahoma", Font.BOLD, 11));
		txt_lambda.setColumns(10);
		txt_lambda.setBounds(191, 433, 66, 29);
		panel_Data.add(txt_lambda);
		
		JLabel lblInclusionFactorlambda = new JLabel("Inclusion Factor (Lambda)");
		lblInclusionFactorlambda.setForeground(Color.WHITE);
		lblInclusionFactorlambda.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInclusionFactorlambda.setBounds(10, 432, 171, 29);
		panel_Data.add(lblInclusionFactorlambda);
		
		JLabel label_1 = new JLabel("%");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(258, 432, 19, 29);
		panel_Data.add(label_1);
		
		JLabel lblNumberOfIterations = new JLabel("Number of Iterations");
		lblNumberOfIterations.setForeground(Color.WHITE);
		lblNumberOfIterations.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumberOfIterations.setBounds(10, 122, 150, 29);
		panel_Data.add(lblNumberOfIterations);
		
		txt_nbrOfIteration = new JFormattedTextField((Format) null);
		txt_nbrOfIteration.setText("10");
		txt_nbrOfIteration.setHorizontalAlignment(SwingConstants.CENTER);
		txt_nbrOfIteration.setFont(new Font("Tahoma", Font.BOLD, 11));
		txt_nbrOfIteration.setColumns(10);
		txt_nbrOfIteration.setBounds(191, 123, 86, 29);
		panel_Data.add(txt_nbrOfIteration);
		
		panel_A = new JPanel();
		panel_A.setBackground(new Color(255, 255, 255));
		panel_A.setBounds(347, 27, 447, 250);
		contentPane.add(panel_A);
		panel_A.setLayout(null);
		
	/*	Chart2D chartA = new Chart2D();
		chartA.setBounds(0, 0, 447, 245);
		panel_A.add(chartA);*/
		
		
		panel_B = new JPanel();
		panel_B.setBackground(new Color(255, 255, 255));
		panel_B.setBounds(829, 27, 447, 250);
		contentPane.add(panel_B);
		panel_B.setLayout(null);
		
		/*Chart2D chartB = new Chart2D();
		chartB.setBounds(0, 0, 447, 245);
		panel_B.add(chartB);*/
		
		panel_C = new JPanel();
		panel_C.setBackground(Color.WHITE);
		panel_C.setBounds(347, 300, 447, 250);
		contentPane.add(panel_C);
		panel_C.setLayout(null);
		
		/*Chart2D chartC = new Chart2D();
		chartC.setBounds(0, 0, 447, 245);
		panel_C.add(chartC);*/
		
		panel_D = new JPanel();
		panel_D.setBackground(Color.WHITE);
		panel_D.setBounds(829, 300, 447, 250);
		contentPane.add(panel_D);
		panel_D.setLayout(null);
		
		/*Chart2D chartD = new Chart2D();
		chartD.setBounds(0, 0, 447, 245);
		panel_D.add(chartD);*/
		
		panel_E = new JPanel();
		panel_E.setBackground(Color.WHITE);
		panel_E.setBounds(347, 572, 447, 250);
		contentPane.add(panel_E);
		panel_E.setLayout(null);
		
	/*	Chart2D chartE = new Chart2D();
		chartE.setBounds(0, 0, 447, 245);
		panel_E.add(chartE);*/
		
		JLabel lblA = new JLabel("Class 2 : Low Performance Web Services");
		lblA.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblA.setBounds(919, 0, 293, 22);
		contentPane.add(lblA);
		
		JLabel lblClass_3 = new JLabel("Class 1 : High Performance Web Services");
		lblClass_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass_3.setBounds(440, 0, 282, 22);
		contentPane.add(lblClass_3);
		
		JLabel lblClass_1 = new JLabel("Class 4: Low to High Performance");
		lblClass_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass_1.setBounds(921, 279, 263, 22);
		contentPane.add(lblClass_1);
		
		JLabel lblClass = new JLabel("Class 3: from High to Low Performance");
		lblClass.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass.setBounds(440, 279, 263, 22);
		contentPane.add(lblClass);
		
		JLabel lblClass_2 = new JLabel("Class 5 : Web services with oscillate Performance");
		lblClass_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass_2.setBounds(427, 553, 309, 22);
		contentPane.add(lblClass_2);
		
		JPanel panel_R = new JPanel();
		panel_R.setBackground(new Color(245, 255, 250));
		panel_R.setBounds(829, 572, 447, 215);
		contentPane.add(panel_R);
		panel_R.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Execution Time :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(86, 47, 124, 27);
		panel_R.add(lblNewLabel);
		
		txt_execTime = new JTextField();
		txt_execTime.setEditable(false);
		txt_execTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_execTime.setHorizontalAlignment(SwingConstants.CENTER);
		txt_execTime.setBounds(198, 47, 94, 24);
		panel_R.add(txt_execTime);
		txt_execTime.setColumns(10);
		
		JButton btnNewButton = new JButton("Mean & MAE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				RecallGUI frame = new RecallGUI(sm.getRecall());
				frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(198, 111, 94, 27);
		panel_R.add(btnNewButton);
		
		JLabel lblBiskraUniversity = new JLabel("Biskra University @ 2014");
		lblBiskraUniversity.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBiskraUniversity.setBounds(1115, 798, 159, 27);
		contentPane.add(lblBiskraUniversity);
		
	}

	private void plot(int i) {
		Chart2D chart = new Chart2D();
		chart.setBounds(0, 0, 447, 245); 
	    // Axes X
		IAxis<IAxisScalePolicy> xAxis = (IAxis<IAxisScalePolicy>)chart.getAxisX();
		xAxis.setRangePolicy(new RangePolicyFixedViewport(new Range(0, numberOfDays+1)));
		xAxis.setAxisScalePolicy(new AxisScalePolicyManualTicks());
		xAxis.setMinorTickSpacing((int)(numberOfDays/10));  // 
		xAxis.setFormatter(new LabelFormatterNumber(new DecimalFormat("###")));
		xAxis.setTitle("days");
		//axes Y
		IAxis<IAxisScalePolicy> yAxis = (IAxis<IAxisScalePolicy>)chart.getAxisY();
		yAxis.setRangePolicy(new RangePolicyFixedViewport(new Range(0, 1.1)));
		yAxis.setAxisScalePolicy(new AxisScalePolicyManualTicks());
		yAxis.setMinorTickSpacing(0.2);
		yAxis.setFormatter(new LabelFormatterNumber(new DecimalFormat("#0.0")));
		yAxis.setTitle("Reputation");
		//set traces
		
	    ITrace2D trace = new Trace2DSimple(); 
	    trace.setName("Origin");
	    ITrace2D trace2 = new Trace2DSimple(); 
	    trace2.setColor(Color.red);
	    trace2.setName("Assessed");
	    // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
	    chart.addTrace(trace);
	    chart.addTrace(trace2); 
	    // Add all points, as it is static: 
	   // Random random = new Random();
	    for(int k=1;k<=numberOfDays;k++){
	      trace.addPoint(k,originReputation[iterNbr][k-1][i]);
	      trace2.addPoint(k,assessdReputation[iterNbr][k-1][i]);
	    }
	    switch (i)
		{
		case 0 : panel_A.add(chart); break;
		case 1 : panel_B.add(chart); break;
		case 2 : panel_C.add(chart); break;
		case 3 : panel_D.add(chart); break;
		case 4 : panel_E.add(chart); break;
		default: break;
		}
	    {
		
	}   
	}
}

