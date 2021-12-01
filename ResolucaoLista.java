import java.util.Random;

public class ResolucaoLista {

    private final static int numTestes = 50; 

    public static double[][] gerarTriangularSupAleatoria(int n) {
        Random geradorAleatorio = new Random();
        double[][] triangularSup = new double[n][n];
        double limiteInf = -100.0;
        double limiteSup = 100.0;

        for (int i = 0; i < n; i++) {
            double aleatorio = 0.0;
            while (aleatorio == 0.0) aleatorio = limiteInf + geradorAleatorio.nextDouble() * (limiteSup - limiteInf); 
            triangularSup[i][i] = aleatorio;
        }

        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                double aleatorio = limiteInf + geradorAleatorio.nextDouble() * (limiteSup - limiteInf);
                triangularSup[i][j] = aleatorio;
            }
        }

        return triangularSup;
    }

    public static double[][] gerarTriangularInfAleatoria(int n) {
        Random geradorAleatorio = new Random();
        double[][] triangularInf = new double[n][n];
        double limiteInf = -100.0;
        double limiteSup = 100.0;

        for (int i = 0; i < n; i++) {
            double aleatorio = 0.0;
            while (aleatorio == 0.0) aleatorio = limiteInf + geradorAleatorio.nextDouble() * (limiteSup - limiteInf);
            triangularInf[i][i] = aleatorio;
        }

        for (int j = 0; j < n-1; j++) {
            for (int i = j+1; i < n; i++) {
                double aleatorio = limiteInf + geradorAleatorio.nextDouble() * (limiteSup - limiteInf); 
                triangularInf[i][j] = aleatorio;
            }
        }

        return triangularInf;
    }

    public static double[][] gerarMatrizHilbert(int n) {
        double[][] hilbert = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                hilbert[i][j] = 1.0 / (i + j + 1);
            }
        }

        return hilbert;
    }

    public static double modulo(double num) {
        if (num < 0) return (-1) * num;
        else return num; 
    }

    public static double[][] copia(double[][] matriz) {
        int n = matriz.length;
        double[][] copia = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copia[i][j] = matriz[i][j];
            }
        }

        return copia;
    }

    public static double[][] identidade(int n) {
        double[][] identidade = new double[n][n];
        for (int i = 0; i < n; i++) identidade[i][i] = 1.0;
        return identidade;
    } 

    public static void trocarElementos(int linha1, int linha2, int coluna, double[][] matriz) {
        double aux = matriz[linha1][coluna];
        matriz[linha1][coluna] = matriz[linha2][coluna];
        matriz[linha2][coluna] = aux;
    }

    public static void trocarLinhas(int linha1, int linha2, double[][] matriz, double[][] inversa) {
        int n = matriz.length;

        for (int coluna = 0; coluna < n; coluna++) {
            trocarElementos(linha1, linha2, coluna, matriz);
            trocarElementos(linha1, linha2, coluna, inversa);
        }
    }

    public static void multiplicarLinhaPorEscalar(int linha, double[][] matriz, double[][] inversa) {
        int n = matriz.length;
        double escalar = 1.0/matriz[linha][linha];
       
        for (int j = 0; j < n; j++) {
            matriz[linha][j] *= escalar;
            inversa[linha][j] *= escalar;
        }
    }

    public static void zerarColuna(int linhaPivo, double[][] matriz, double[][] inversa) {
        int n = matriz.length;
        int colunaPivo = linhaPivo;

        for (int i = 0; i < n; i++) {
            if (i != linhaPivo) {
                double multiplicador = matriz[i][colunaPivo];

                for (int j = 0; j < n; j++) {
                    matriz[i][j] -= multiplicador * matriz[linhaPivo][j];
                    inversa[i][j] -= multiplicador * inversa[linhaPivo][j];
                }
            }
        }
    }

    // Calcula a inversa da matriz, assumindo que ela seja invertível.
    public static double[][] calcularInversa(double[][] matriz) {
        int n = matriz.length;
        double[][] copia = copia(matriz);
        double[][] inversa = identidade(n);
        
        for (int i = 0; i < n; i++) {
            int indiceMaximo = i;
            for (int j = i; j < n; j++) {
                if (modulo(copia[j][i]) > modulo(copia[indiceMaximo][i]))
                    indiceMaximo = j;
            }

            trocarLinhas(i, indiceMaximo, copia, inversa);
            
            multiplicarLinhaPorEscalar(i, copia, inversa);

            zerarColuna(i, copia, inversa);
        }

        return inversa;
    }

    // Assume matrizes quadradas.
    public static double[][] multiplicarMatrizes(double[][] matriz1, double[][] matriz2) {
        int n = matriz1.length;
        double[][] resultado = new double[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double soma = 0.0;
                for (int k = 0; k < n; k++)
                    soma += matriz1[i][k] * matriz2[k][j];
                resultado[i][j] = soma;
            }
        }

        return resultado;
    }

    public static boolean verificarInversa(double[][] matriz, double[][] supostaInversa) {
        int n = matriz.length;
        double[][] identidade = identidade(n);
        double[][] multiplicacao = multiplicarMatrizes(matriz, supostaInversa);
        boolean resposta = true;

        for (int i = 0; i < n && resposta; i++) {
            for (int j = 0; j < n && resposta; j++) {
                if (identidade[i][j] != multiplicacao[i][j]) resposta = false;
            }
        }

        return resposta;
    }

    public static void imprimirMatriz(double[][] matriz) {
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%f ", matriz[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        
        // Questão 4: 50 testes para matrizes triangulares superiores aleatórias
        // (de tamanho n = 1, 2, 3, 4, ..., 50):
        for (int i = 1; i <= numTestes; i++) {
            double[][] matrizTeste = gerarTriangularSupAleatoria(i);
            double[][] inversa = calcularInversa(matrizTeste);
            
            System.out.printf("Matriz triangular inferior aleatória de dimensão %d:\n", i);
            imprimirMatriz(matrizTeste);
            System.out.println();

            System.out.println("Sua inversa:");
            imprimirMatriz(inversa);
            System.out.println();

            System.out.println("Multiplicação da matriz e sua inversa:");
            imprimirMatriz(multiplicarMatrizes(matrizTeste, inversa));
            System.out.println();
        }

        // Questão 5: 50 testes para matrizes triangulares inferiores aleatórias
        // (de tamanho n = 1, 2, 3, 4, ..., 50):
        for (int i = 1; i <= numTestes; i++) {
            double[][] matrizTeste = gerarTriangularInfAleatoria(i);
            double[][] inversa = calcularInversa(matrizTeste);
            
            System.out.printf("Matriz triangular inferior aleatória de dimensão %d:\n", i);
            imprimirMatriz(matrizTeste);
            System.out.println();

            System.out.println("Sua inversa:");
            imprimirMatriz(inversa);
            System.out.println();

            System.out.println("Multiplicação da matriz pela sua inversa:");
            imprimirMatriz(multiplicarMatrizes(matrizTeste, inversa));
            System.out.println();
        }

        // Questão 6: 50 testes para matrizes triangulares inferiores aleatórias
        // (de tamanho n = 1, 2, 3, 4, ..., 50):
        for (int i = 1; i <= numTestes; i++) {
            double[][] matrizTeste = gerarMatrizHilbert(i);
            double[][] inversa = calcularInversa(matrizTeste);
            
            System.out.printf("Matriz de Hilbert de dimensão %d:\n", i);
            imprimirMatriz(matrizTeste);
            System.out.println();

            System.out.println("Sua inversa:");
            imprimirMatriz(inversa);
            System.out.println();

            System.out.println("Multiplicação da matriz pela sua inversa:");
            imprimirMatriz(multiplicarMatrizes(matrizTeste, inversa));
            System.out.println();
        }
    }   
}