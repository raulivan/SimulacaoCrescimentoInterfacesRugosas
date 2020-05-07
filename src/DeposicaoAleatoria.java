import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

/**
 * Classe de simulação de crescimento de interface 
 * usando o modelo de DEPOSIÇÃO ALEATÓRIA
 * @author Raulivan
 *
 */
public class DeposicaoAleatoria {

	/**
	 * Método que inicia a simulação
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void iniciarSimulacao() throws FileNotFoundException, IOException, Exception {	
			System.out.println("Iniciou simulação de Deposição Aleatória...");
			init();
			System.out.println("Finalizou simulação de Deposição Aleatória...");
	}
	
	/**
	 * Inciar o algoritmo de simulação
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	private static void init() throws FileNotFoundException, IOException, Exception {
		System.out.println("Deposição Aleatória");
		int l1 = 200, l2= 400, l3 = 800, l4 = 1600;
		int tmax = (int)Math.pow(10, 4);
		
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
		
		plotarGrafico("Deposição Aleatoria",wt_l1, wt_l2,wt_l3,wt_l4);
	}
	
	/**
	 * Sorteio do sitio
	 * @param limite Valor máximo do número a ser gerado
	 * @param semente Valor de variação
	 * @return
	 */
	private static int getSitio(int limite, int semente) {
		int sitio = 0;
		Random gerador = new Random(System.currentTimeMillis() + semente);
		sitio = gerador.nextInt(limite);
		
		return sitio;
	}
	
	/**
	 * Calcular a Rugosidade do substrato w(L,t)
	 * @param tmax Tempo máximo
	 * @param substrato Substrato
	 * @return Calculo da rugosidade
	 */
	private static double[] calculaRugosidadeSubstrato(int tmax,int[] substrato) {
		double[] wt = new double[tmax];//Tempo máximo
		
		for(int t=0;t < tmax; t++) {//Tempo
			for(int cont=0; cont < substrato.length; cont++) {//Particulas do Substrato
				int i = getSitio(substrato.length,t);//Sorteia uma posição
				substrato[i]++;//deposita a particula
			}
			wt[t] = calcRugosidade(substrato,t);
		}
		
		return wt;
	}
	
	/**
	 * Calculo da rugosidade
	 * @param L Substrato
	 * @param t Tempo
	 * @return Resultado do calculo
	 */
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
	
	/**
	 * Calcula a Altura média de crescimento
	 * @param L Substrato
	 * @param t Tempo
	 * @return Resultado do calculo
	 */
	private static double calcAlturaMedia(int[] L, int t) {
		double h = 0;
		double somah=0;
		for(int l: L)
			somah += l;
		
		double divisao = (1d/L.length);
		
		h = divisao*somah;

		return h;
	}
	
	
	/**
	 * Plotar o gráfico da simulação
	 * @param titulo Título do gráfico
	 * @param w1 Rugosidade 200
	 * @param w2 Rugosidade 400
	 * @param w3 Rugosidade 800
	 * @param w4 Rugosidade 1600
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	private static void plotarGrafico(String titulo, double[] w1, double[] w2, double[] w3, double[] w4 ) throws FileNotFoundException, IOException, Exception {
		System.out.println("Plotando o Gráfico");
		
		Grafico grafico = new Grafico(titulo, "Iteração t", "Rugosidade w(L,t)");
		grafico.setDataSet(w1, w2, w3, w4);
		grafico.plotarLog( "grafico_da");
	}
}
