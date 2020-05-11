import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe se simulação o modelo de Deposição Aleatoria com Relaxação Superficial
 * @author Raulivan
 *
 */
public class DeposicaoAleatoriaRelaxacaoSuperficial {

	public static void iniciarSimulacao() throws FileNotFoundException, IOException, Exception {	
		System.out.println("Iniciou simulação - DARS...");
		init();
		System.out.println("Finalizou simulação - DARS...");
	}
	
	private static void init() throws FileNotFoundException, IOException, Exception {
		int l1 = 200, l2= 400, l3 = 800, l4 = 1600;
		int tmax = (int)Math.pow(10, 5);
		
		int[] substrato_l1 = new int[l1];
		double[] wt_l1 = new double[tmax];
		
		int[] substrato_l2 = new int[l2];
		double[] wt_l2 = new double[tmax];
		
		int[] substrato_l3 = new int[l3];
		double[] wt_l3 = new double[tmax];
		
		int[] substrato_l4 = new int[l4];
		double[] wt_l4 = new double[tmax];
		
		wt_l1 = calculaRugosidadeSubstrato(tmax, substrato_l1);
		wt_l2 = calculaRugosidadeSubstrato(tmax, substrato_l2);
		wt_l3 = calculaRugosidadeSubstrato(tmax, substrato_l3);
		wt_l4 = calculaRugosidadeSubstrato(tmax, substrato_l4);
		
		plotarGrafico("Deposição Aleatória com Relaxação Superficial",wt_l1, wt_l2,wt_l3,wt_l4);
	}
	
	private static double[] calculaRugosidadeSubstrato(int tmax,int[] substrato) {
		double[] wt = new double[tmax];//Tempo máximo
		int INT_MAX = (int)Math.pow(2,31) -1;
		
		for(int t=0;t < tmax; t++) {//Tempo
			for(int cont=0; cont < substrato.length; cont++) {//Particulas do Substrato
				int i = getSitio(substrato.length,t);//Sorteia uma posição
				
				int h_sorteado = -1,h_antes = -1,h_depois = -1;
				
				h_sorteado = substrato[i];
				
				if(i > 0)
					h_antes = substrato[i-1];
				else
					h_antes = INT_MAX;
				
				if(i < (substrato.length-1))
					h_depois = substrato[i+1];
				else
					h_depois = INT_MAX;
				
				if(h_antes < h_sorteado && h_depois > h_sorteado) {
					substrato[i-1]++;//deposita a particula
				}else if(h_antes > h_sorteado && h_depois < h_sorteado) {
					substrato[i+1]++;//deposita a particula
				}else
					substrato[i]++;//deposita a particula
			}
			wt[t] = calcRugosidade(substrato,t);
		}
		
		return wt;
	}
	
	private static int getSitio(int limite, int semente) {
		int sitio = 0;
		sitio = GeradorNumerosAleatorios.gerarComReinicio(semente, limite);
		//sitio = GeradorNumerosAleatorios.gerarSemReinicio(limite);
		return sitio;
	}
	
	private static double calcRugosidade(int L[], int t) {
		double w=0;
		double delta = 0;
		double alturaMedia = calcAlturaMedia(L,t);
		
		for(int i: L)
			delta += Math.pow((i - alturaMedia),2);
		
		double indice = (1d/L.length);
		
		w = indice * delta;
		
		return w;
	}
	
	private static double calcAlturaMedia(int[] L, int t) {
		double h = 0;
		double somah=0;
		for(int l: L)
			somah += l;
		
		double divisao = (1d/L.length);
		
		h = divisao*somah;

		return h;
	}
	private static void plotarGrafico(String titulo, double[] w1, double[] w2, double[] w3, double[] w4 ) throws FileNotFoundException, IOException, Exception {
		System.out.println("Plotando o Gráfico");
		
		Grafico grafico = new Grafico(titulo, "Iteração t", "Rugosidade w(L,t)");
		grafico.setDataSet(w1, w2, w3, w4);
		grafico.plotarLog( "grafico_DARS");
	}
}
