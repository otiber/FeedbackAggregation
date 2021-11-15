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

public class RecallGUI extends JFrame {

	private JPanel contentPane;
	Simulator sm;
	float [][][][] recall ;
	int numberOfDays =0;
	private JPanel panel_A,panel_B,panel_C,panel_D,panel_E;
	private JPanel panel_1,panel_2,panel_3,panel_4,panel_5;
	private JTextField txt_c1_mean;
	private JTextField txt_c1_mea;
	private JLabel lblMaeMean;
	private JLabel lblClass_4;
	private JTextField txt_c2_mean;
	private JTextField txt_c2_mea;
	private JLabel lblClass_5;
	private JTextField txt_c3_mean;
	private JTextField txt_c3_mea;
	private JLabel lblClass_6;
	private JTextField txt_c4_mean;
	private JTextField txt_c4_mea;
	private JLabel lblClass_7;
	private JTextField txt_c5_mean;
	private JTextField txt_c5_mea;
	private JTextField txt_gl_recall;
	private JTextField txt_gl_mea;
	private JLabel lblGlobalRecallMean;
	private JLabel lblGloablMaeMean;
	int iterNbr;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecallGUI frame = new RecallGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public RecallGUI(float [][][][] rec) {
		this.recall=rec;
		iterNbr = rec.length;
		setTitle("Recall & Mean Absolute Error");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1885, 878);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel_A = new JPanel();
		panel_A.setBackground(new Color(255, 255, 255));
		panel_A.setBounds(10, 30, 455, 250);
		contentPane.add(panel_A);
		panel_A.setLayout(null);
		
	/*	Chart2D chartA = new Chart2D();
		chartA.setBounds(0, 0, 447, 245);
		panel_A.add(chartA);*/
		
		/*Chart2D chartB = new Chart2D();
		chartB.setBounds(0, 0, 447, 245);
		panel_B.add(chartB);*/
		
		panel_C = new JPanel();
		panel_C.setBackground(Color.WHITE);
		panel_C.setBounds(10, 303, 455, 250);
		contentPane.add(panel_C);
		panel_C.setLayout(null);
		
		/*Chart2D chartC = new Chart2D();
		chartC.setBounds(0, 0, 447, 245);
		panel_C.add(chartC);*/
		
		panel_D = new JPanel();
		panel_D.setBackground(Color.WHITE);
		panel_D.setBounds(945, 303, 455, 250);
		contentPane.add(panel_D);
		panel_D.setLayout(null);
		
		/*Chart2D chartD = new Chart2D();
		chartD.setBounds(0, 0, 447, 245);
		panel_D.add(chartD);*/
		
		panel_E = new JPanel();
		panel_E.setBackground(Color.WHITE);
		panel_E.setBounds(10, 575, 455, 250);
		contentPane.add(panel_E);
		panel_E.setLayout(null);
		
	/*	Chart2D chartE = new Chart2D();
		chartE.setBounds(0, 0, 447, 245);
		panel_E.add(chartE);*/
		
		JLabel lblA = new JLabel("Class 2 : Low Performance Web Services");
		lblA.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblA.setBounds(1260, 3, 293, 22);
		contentPane.add(lblA);
		
		JLabel lblClass_3 = new JLabel("Class 1 : High Performance Web Services");
		lblClass_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass_3.setBounds(332, 3, 282, 22);
		contentPane.add(lblClass_3);
		
		JLabel lblClass = new JLabel("Class 3: from High to Low Performance");
		lblClass.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass.setBounds(343, 282, 263, 22);
		contentPane.add(lblClass);
		
		JLabel lblClass_2 = new JLabel("Class 5 : Web services with oscillate Performance");
		lblClass_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass_2.setBounds(320, 553, 309, 22);
		contentPane.add(lblClass_2);
		
		JPanel panel_R = new JPanel();
		panel_R.setBackground(new Color(245, 255, 250));
		panel_R.setBounds(945, 575, 920, 250);
		contentPane.add(panel_R);
		panel_R.setLayout(null);
		
		JLabel lblBiskraUniversity = new JLabel("Biskra University @ 2014");
		lblBiskraUniversity.setBounds(750, 212, 159, 27);
		panel_R.add(lblBiskraUniversity);
		lblBiskraUniversity.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		txt_c1_mean = new JTextField();
		txt_c1_mean.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c1_mean.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c1_mean.setEditable(false);
		txt_c1_mean.setColumns(10);
		txt_c1_mean.setBounds(201, 52, 94, 24);
		panel_R.add(txt_c1_mean);
		
		JLabel lblClasse = new JLabel("Mean Recall");
		lblClasse.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClasse.setBounds(83, 49, 94, 27);
		panel_R.add(lblClasse);
		
		JLabel lblMeam = new JLabel("Class 1");
		lblMeam.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMeam.setBounds(220, 11, 53, 27);
		panel_R.add(lblMeam);
		
		txt_c1_mea = new JTextField();
		txt_c1_mea.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c1_mea.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c1_mea.setEditable(false);
		txt_c1_mea.setColumns(10);
		txt_c1_mea.setBounds(201, 89, 94, 24);
		panel_R.add(txt_c1_mea);
		
		lblMaeMean = new JLabel("MAE Mean");
		lblMaeMean.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMaeMean.setBounds(83, 86, 112, 27);
		panel_R.add(lblMaeMean);
		
		lblClass_4 = new JLabel("Class 2");
		lblClass_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass_4.setBounds(361, 11, 53, 27);
		panel_R.add(lblClass_4);
		
		txt_c2_mean = new JTextField();
		txt_c2_mean.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c2_mean.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c2_mean.setEditable(false);
		txt_c2_mean.setColumns(10);
		txt_c2_mean.setBounds(342, 52, 94, 24);
		panel_R.add(txt_c2_mean);
		
		txt_c2_mea = new JTextField();
		txt_c2_mea.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c2_mea.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c2_mea.setEditable(false);
		txt_c2_mea.setColumns(10);
		txt_c2_mea.setBounds(342, 89, 94, 24);
		panel_R.add(txt_c2_mea);
		
		lblClass_5 = new JLabel("Class 3");
		lblClass_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass_5.setBounds(501, 11, 53, 27);
		panel_R.add(lblClass_5);
		
		txt_c3_mean = new JTextField();
		txt_c3_mean.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c3_mean.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c3_mean.setEditable(false);
		txt_c3_mean.setColumns(10);
		txt_c3_mean.setBounds(482, 52, 94, 24);
		panel_R.add(txt_c3_mean);
		
		txt_c3_mea = new JTextField();
		txt_c3_mea.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c3_mea.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c3_mea.setEditable(false);
		txt_c3_mea.setColumns(10);
		txt_c3_mea.setBounds(482, 89, 94, 24);
		panel_R.add(txt_c3_mea);
		
		lblClass_6 = new JLabel("Class 4");
		lblClass_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass_6.setBounds(637, 11, 53, 27);
		panel_R.add(lblClass_6);
		
		txt_c4_mean = new JTextField();
		txt_c4_mean.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c4_mean.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c4_mean.setEditable(false);
		txt_c4_mean.setColumns(10);
		txt_c4_mean.setBounds(618, 52, 94, 24);
		panel_R.add(txt_c4_mean);
		
		txt_c4_mea = new JTextField();
		txt_c4_mea.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c4_mea.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c4_mea.setEditable(false);
		txt_c4_mea.setColumns(10);
		txt_c4_mea.setBounds(618, 89, 94, 24);
		panel_R.add(txt_c4_mea);
		
		lblClass_7 = new JLabel("Class 5");
		lblClass_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClass_7.setBounds(769, 11, 53, 27);
		panel_R.add(lblClass_7);
		
		txt_c5_mean = new JTextField();
		txt_c5_mean.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c5_mean.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c5_mean.setEditable(false);
		txt_c5_mean.setColumns(10);
		txt_c5_mean.setBounds(750, 52, 94, 24);
		panel_R.add(txt_c5_mean);
		
		txt_c5_mea = new JTextField();
		txt_c5_mea.setHorizontalAlignment(SwingConstants.CENTER);
		txt_c5_mea.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_c5_mea.setEditable(false);
		txt_c5_mea.setColumns(10);
		txt_c5_mea.setBounds(750, 89, 94, 24);
		panel_R.add(txt_c5_mea);
		
		txt_gl_recall = new JTextField();
		txt_gl_recall.setHorizontalAlignment(SwingConstants.CENTER);
		txt_gl_recall.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_gl_recall.setEditable(false);
		txt_gl_recall.setColumns(10);
		txt_gl_recall.setBounds(461, 134, 146, 24);
		panel_R.add(txt_gl_recall);
		
		txt_gl_mea = new JTextField();
		txt_gl_mea.setHorizontalAlignment(SwingConstants.CENTER);
		txt_gl_mea.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_gl_mea.setEditable(false);
		txt_gl_mea.setColumns(10);
		txt_gl_mea.setBounds(461, 171, 146, 24);
		panel_R.add(txt_gl_mea);
		
		lblGlobalRecallMean = new JLabel("Global Recall Mean");
		lblGlobalRecallMean.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGlobalRecallMean.setBounds(318, 134, 133, 27);
		panel_R.add(lblGlobalRecallMean);
		
		lblGloablMaeMean = new JLabel("Gloabl MAE Mean");
		lblGloablMaeMean.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGloablMaeMean.setBounds(318, 171, 133, 27);
		panel_R.add(lblGloablMaeMean);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(47, 79, 79));
		panel_2.setBounds(1410, 30, 455, 250);
		contentPane.add(panel_2);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(47, 79, 79));
		panel_1.setBounds(475, 30, 455, 250);
		contentPane.add(panel_1);
		
		panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(new Color(47, 79, 79));
		panel_4.setBounds(1410, 303, 455, 250);
		contentPane.add(panel_4);
		
		panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBackground(new Color(47, 79, 79));
		panel_5.setBounds(475, 575, 455, 250);
		contentPane.add(panel_5);
		
		
		panel_B = new JPanel();
		panel_B.setBounds(945, 30, 455, 250);
		contentPane.add(panel_B);
		panel_B.setBackground(new Color(255, 255, 255));
		panel_B.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBounds(475, 303, 455, 250);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(47, 79, 79));
		
		JLabel lblClass_1 = new JLabel("Class 4: Low to High Performance");
		lblClass_1.setBounds(1273, 282, 263, 22);
		contentPane.add(lblClass_1);
		lblClass_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			
		numberOfDays= recall[0].length;

		plot(0);plot(1);plot(2);plot(3);plot(4);   // trace recall
		plot(5);plot(6);plot(7);plot(8);plot(9);   // trace Meane Absolute Error
		meanCalculation();  // calculate mean of recall and precision 
		
	}
/**
 * Method for Mean calculation
 */
	private void meanCalculation()
	{
		float glRecallMean=0;
		float glMeaMean=0;

		float clRecallMean;
		float clMeaMean;
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(4); 
		
		// classe 1
		clRecallMean=0;
		clMeaMean=0;
		for(int k=1;k<=numberOfDays;k++)   
		{   clRecallMean= recall[iterNbr-1][k-1][0][0];
			clMeaMean =recall[iterNbr-1][k-1][0][1];
		}
		clRecallMean/=100; txt_c1_mean.setText(clRecallMean*100+"%"); glRecallMean +=clRecallMean;
		clMeaMean /=100;	txt_c1_mea.setText(df.format(clMeaMean)+"");	  glMeaMean+=clMeaMean;
		
		// classe 2
		clRecallMean=0;
		clMeaMean=0;
		for(int k=1;k<=numberOfDays;k++)   
			{   clRecallMean= recall[iterNbr-1][k-1][1][0];
				clMeaMean =recall[iterNbr-1][k-1][1][1];
			}
		clRecallMean/=100; txt_c2_mean.setText(clRecallMean*100+"%"); glRecallMean +=clRecallMean;
		clMeaMean /=100;	txt_c2_mea.setText(df.format(clMeaMean)+"");	  glMeaMean+=clMeaMean;
				
		// classe 3
		clRecallMean=0;
		clMeaMean=0;
		for(int k=1;k<=numberOfDays;k++)   
			{   clRecallMean= recall[iterNbr-1][k-1][2][0];
				clMeaMean =recall[iterNbr-1][k-1][2][1];
			}
		clRecallMean/=100; txt_c3_mean.setText(clRecallMean*100+"%"); glRecallMean +=clRecallMean;
		clMeaMean /=100;	txt_c3_mea.setText(df.format(clMeaMean)+"");	  glMeaMean+=clMeaMean;		
				
		// classe 4
		clRecallMean=0;
		clMeaMean=0;
		for(int k=1;k<=numberOfDays;k++)   
			{   clRecallMean= recall[iterNbr-1][k-1][3][0];
				clMeaMean =recall[iterNbr-1][k-1][3][1];
			}
		clRecallMean/=100; txt_c4_mean.setText(clRecallMean*100+"%"); glRecallMean +=clRecallMean;
		clMeaMean /=100;	txt_c4_mea.setText(df.format(clMeaMean)+"");	  glMeaMean+=clMeaMean;
				
		// classe 5
		clRecallMean=0;
		clMeaMean=0;
		for(int k=1;k<=numberOfDays;k++)   
		{   clRecallMean= recall[iterNbr-1][k-1][4][0];
			clMeaMean =recall[iterNbr-1][k-1][4][1];
		}
		clRecallMean/=100; txt_c5_mean.setText(clRecallMean*100+"%"); glRecallMean +=clRecallMean;
		clMeaMean /=100;	txt_c5_mea.setText(df.format(clMeaMean)+"");	  glMeaMean+=clMeaMean;
		
		//----------- Global
		glRecallMean/=5; txt_gl_recall.setText(glRecallMean*100+"%");
		glMeaMean/=5;	txt_gl_mea.setText(df.format(glMeaMean)+"");
	}
	
	/**
	 * Plot Method
	 */
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
		
		if (i<=4){
		yAxis.setRangePolicy(new RangePolicyFixedViewport(new Range(0, 100.1)));
		yAxis.setAxisScalePolicy(new AxisScalePolicyManualTicks());
		yAxis.setMinorTickSpacing(10);
		yAxis.setFormatter(new LabelFormatterNumber(new DecimalFormat("#0.0")));
		yAxis.setTitle("Recall");
		}
		else
		{
			yAxis.setRangePolicy(new RangePolicyFixedViewport(new Range(0, 1.1)));
			yAxis.setAxisScalePolicy(new AxisScalePolicyManualTicks());
			yAxis.setMinorTickSpacing(0.1);
			yAxis.setFormatter(new LabelFormatterNumber(new DecimalFormat("#0.0")));
			yAxis.setTitle("MAE");
		}
		//set traces
		
	    ITrace2D trace = new Trace2DSimple(); 
	    trace.setName("Origin");
	    ITrace2D trace2 = new Trace2DSimple(); 
	    trace2.setColor(Color.red);
	    trace2.setName("Mean Absolute Error");
	    // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
	    
	    if (i<=4)chart.addTrace(trace);
	    else
	    chart.addTrace(trace2); 
	   
	    if(i<=4)
	    for(int k=1;k<=numberOfDays;k++)
	      trace.addPoint(k,recall[iterNbr-1][k-1][i][0]);
	      else
	    	  for(int k=1;k<=numberOfDays;k++)
	    	  trace2.addPoint(k,recall[iterNbr-1][k-1][i-5][1]); 
	     	    
	    switch (i)
		{
		case 0 : panel_A.add(chart); break;
		case 1 : panel_B.add(chart); break;
		case 2 : panel_C.add(chart); break;
		case 3 : panel_D.add(chart); break;
		case 4 : panel_E.add(chart); break;
		case 5 : panel_1.add(chart); break;
		case 6 : panel_2.add(chart); break;
		case 7 : panel_3.add(chart); break;
		case 8 : panel_4.add(chart); break;
		case 9 : panel_5.add(chart); break;	
		default: break;
		}
	    {
		
	}   
	}
}

