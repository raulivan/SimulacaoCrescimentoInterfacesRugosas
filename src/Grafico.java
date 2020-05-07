import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Classe reponsável para plotagem do gráfico
 * @author Raulivan
 *
 */
public class Grafico {

	private String titulo;
	private String labelHorizontalX;
	private String labelVerticialY;
	
	private double[] w_substrato_l1;
	private double[] w_substrato_l2;
	private double[] w_substrato_l3;
	private double[] w_substrato_l4;
	
	/**
	 * Construtor
	 * @param titulo Título do gráfico
	 * @param labelHorizontalX Rótulo do eixo X
	 * @param labelVerticialY Rótulo do eixo Y
	 */
	public Grafico(String titulo, String labelHorizontalX, String labelVerticialY) {
		this.titulo = titulo;
		this.labelHorizontalX = labelHorizontalX;
		this.labelVerticialY = labelVerticialY;
	}
	
	/**
	 * Fonte de dados para plotar o gráfico
	 * @param w1 Serie 1
	 * @param w2 Serie 2
	 * @param w3 Serie 3
	 * @param w4 Serie 4
	 */
	public void setDataSet(double[] w1,double[] w2,double[] w3,double[] w4 ){
		this.w_substrato_l1 = w1;
		this.w_substrato_l2 = w2;
		this.w_substrato_l3 = w3;
		this.w_substrato_l4 = w4;
	}
	
	/**
	 * Plotar o gráfico em escala aritmetica
	 * @param nome_iamgem Nome da imagem que será gerada com o gráfico
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public void plotar(String nome_iamgem) throws FileNotFoundException, IOException, Exception {
		//Gerando o DataSet do Gráfico
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		
		for(int p = 0; p < w_substrato_l1.length; p++) {
			ds.addValue(w_substrato_l1[p], "L200", String.valueOf(p));
		}
		
		for(int p = 0; p < w_substrato_l2.length; p++) {
			ds.addValue(w_substrato_l2[p], "L400", String.valueOf(p));
		}
		
		for(int p = 0; p < w_substrato_l3.length; p++) {
			ds.addValue(w_substrato_l3[p], "L800", String.valueOf(p));
		}
		
		for(int p = 0; p < w_substrato_l4.length; p++) {
			ds.addValue(w_substrato_l4[p], "L1600", String.valueOf(p));
		}
	    
		JFreeChart grafico = ChartFactory.createLineChart(titulo, 
				labelHorizontalX,  labelVerticialY, ds, 
				PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot categoryPlot = grafico.getCategoryPlot();
        CategoryItemRenderer renderer = categoryPlot.getRenderer();
        renderer.setSeriesPaint( 0, Color.RED );
        renderer.setSeriesPaint( 1, Color.BLUE );
        renderer.setSeriesPaint( 2, Color.GREEN );
        renderer.setSeriesPaint( 3, Color.YELLOW );

		OutputStream arquivo = new FileOutputStream(nome_iamgem + ".png");
		ChartUtils.writeChartAsPNG(arquivo, grafico, 550, 400);
		arquivo.close();
	}
	
	/**
	 * Plotar o gráfico em escala Logaritmica
	 * @param nome_iamgem Nome da imagem que será gerada com o gráfico
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public void plotarLog(String nome_iamgem) throws FileNotFoundException, IOException, Exception {
		XYSeriesCollection seriesCollection = new XYSeriesCollection();
		
		XYSeries series1 = new XYSeries("L200");
		for(int p = 0; p < w_substrato_l1.length; p++) 
	       series1.add(p,w_substrato_l1[p]);
		seriesCollection.addSeries(series1);
	     
	    XYSeries series2 = new XYSeries("L400");
	    for(int p = 0; p < w_substrato_l2.length; p++) 
	    	series2.add(p,w_substrato_l2[p]);
	    seriesCollection.addSeries(series2);
	    
	    XYSeries series3 = new XYSeries("L800");
	    for(int p = 0; p < w_substrato_l3.length; p++) 
	    	series3.add(p,w_substrato_l3[p]);
	    seriesCollection.addSeries(series3);
	    
	    XYSeries series4 = new XYSeries("L1600");
	    for(int p = 0; p < w_substrato_l4.length; p++) 
	    	series4.add( p,w_substrato_l4[p]);
	    seriesCollection.addSeries(series4);
		     
	    LogarithmicAxis xAxis = new LogarithmicAxis(labelHorizontalX);
        xAxis.setAllowNegativesFlag(true);
        
        LogarithmicAxis yAxis = new LogarithmicAxis(labelVerticialY);
        yAxis.setAllowNegativesFlag(true);
        
       
        XYPlot plot = new XYPlot(seriesCollection,
            xAxis, yAxis, new XYLineAndShapeRenderer(true, false));
        
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint( 0, Color.RED );
        renderer.setSeriesPaint( 1, Color.BLUE );
        renderer.setSeriesPaint( 2, Color.GREEN );
        renderer.setSeriesPaint( 3, Color.YELLOW );
        
        LegendItemCollection legendas = new LegendItemCollection();
        
    	LegendItem legenda1 = new LegendItem("Substrato 200");
    	legenda1.setSeriesIndex(0);
    	legenda1.setFillPaint(Color.RED);
    	legenda1.setLabelPaint(Color.BLACK);
    	legendas.add(legenda1);
    	
    	LegendItem legenda2 = new LegendItem("Substrato 400");
    	legenda2.setSeriesIndex(1);
    	legenda2.setFillPaint(Color.BLUE);
    	legenda2.setLabelPaint(Color.BLACK);
    	legendas.add(legenda2);
    	
    	LegendItem legenda3 = new LegendItem("Substrato 800");
    	legenda3.setSeriesIndex(2);
    	legenda3.setFillPaint(Color.GREEN);
    	legenda3.setLabelPaint(Color.BLACK);
    	legendas.add(legenda3);
    	
    	LegendItem legenda4 = new LegendItem("Substrato 1600");
    	legenda4.setSeriesIndex(3);
    	legenda4.setFillPaint(Color.YELLOW);
    	legenda4.setLabelPaint(Color.BLACK);
    	legendas.add(legenda4);
    	
    	
    	plot.setFixedLegendItems(legendas);
    	
        JFreeChart grafico = new JFreeChart(
            titulo, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        
		OutputStream arquivo = new FileOutputStream(nome_iamgem + ".png");
		ChartUtils.writeChartAsPNG(arquivo, grafico, 550, 400);
		arquivo.close();
	}
}
