import java.util.Random;

public class GeradorNumerosAleatorios {

	public static  int gerarComReinicio(int semente, int limite) {
		Random gerador = new Random(System.currentTimeMillis() + semente);
		return gerador.nextInt(limite);
	}
	
	private static Random geradorSemReinicio;
	public static  int gerarSemReinicio( int limite) {
		if(geradorSemReinicio == null) {
			geradorSemReinicio = new Random();
		}
		return geradorSemReinicio.nextInt(limite);
	}
}
