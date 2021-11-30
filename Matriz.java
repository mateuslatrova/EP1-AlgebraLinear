public class Matriz {

    private double[][] elementos;
    private int n;

    public Matriz(int n) {
        this.n = n;
        elementos = new double[n][n];
    }

    public Matriz(double[][] elementos) {
        this.n = elementos.length;
        this.elementos = elementos;
    }

    // Calcula a inversa desta matriz, assumindo que ela seja invertível.
    public Matriz inversa() {
        Matriz copia = new Matriz(elementos);
        Matriz identidade = Matriz.identidade(n);
        
        for (int i = 0; i < n; i++) {
            
        }

        return new Matriz(1);
    }

    public static Matriz identidade(int n) {
        double[][] identidade = new double[n][n];
        for (int i = 0; i < n; i++) identidade[i][i] = 1;
        return new Matriz(identidade);
    } 

    public int tamanho() {
        return this.n;
    }

    public static void main(String[] args) {
        
    }
}