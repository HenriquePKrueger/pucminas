import java.util.*;

public class AdivinharEstrutura {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] dados = new int[1000]; 

        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int topo = 0; 

            boolean pilha = true;
            boolean fila = true;
            boolean prioridade = true;

            for (int i = 0; i < n; i++) {

                int comando = sc.nextInt();
                int x = sc.nextInt();

                if (comando == 1) {

                    dados[topo] = x;
                    topo++;

                }
		else {
                    if (topo == 0) {
                        pilha = fila = prioridade = false;
                    } else {

                        if (x != dados[topo - 1]) {
                            pilha = false;
                        }

                        if (x != dados[0]) {
                            fila = false;
                        }

                        int maiorVal = dados[0];

                        for (int j = 1; j < topo; j++) {
                            if (dados[j] > maiorVal) {
                                maiorVal = dados[j];
                            }

                        }

                        if (x != maiorVal) {
                            prioridade = false;
                        }

                        int posRemover = -1;

                        for (int k = 0; k < topo; k++) {
                            if (dados[k] == x) {
                                posRemover = k;
                                break;
                            }
                        }

                        if (posRemover != -1) {
                            for (int k = posRemover; k < topo - 1; k++) {
                                dados[k] = dados[k + 1];
                            }
                            topo--;
                        }
			else {
                            pilha = fila = prioridade = false;
                        }
                    }
                }
            }

            int soma = (pilha ? 1 : 0) + (fila ? 1 : 0) + (prioridade ? 1 : 0);

            if (soma == 0) {
                System.out.println("impossible");
            }
	    else if (soma > 1) {
                System.out.println("not sure");
            }
	    else if (pilha) {
                System.out.println("stack");
            }
	    else if (fila) {
                System.out.println("queue");
            }
	    else {
                System.out.println("priority queue");
            }
        }

        sc.close();
    }
}
