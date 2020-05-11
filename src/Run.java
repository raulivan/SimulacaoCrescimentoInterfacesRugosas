/**
 * Classe principal
 * @author Raulivan
 *
 */
public class Run {

	/**
	 * Método principal, método de execução
	 * @param args
	 */
	public static void main(String[] args) {
		try {		
			System.out.println("INICIOU...");
			
			DeposicaoAleatoria.iniciarSimulacao();
			
			DeposicaoAleatoriaRelaxacaoSuperficial.iniciarSimulacao();
			
			DeposicaoBalistica.iniciarSimulacao();
			
			System.out.println("FINALIZOU...");
			
		}catch (Exception ex) {
			System.out.println("Ocorreu um erro: " + ex.getMessage());
			System.out.println("Pilha: " + ex.getStackTrace());
		}
	}
}