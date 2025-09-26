import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * Programa que resuelve el problema planteado para el proyecto 1 de Dalgo
 * 
 * @author: Emmanuel Felipe Blanco Corredor - 202312743
 * @author: Laura Sofia Sarmiento – 202113056 
 * 
 */

public class ProblemaP1 
{
    public static void main(String[] args) throws Exception
    {
        ProblemaP1 instancia = new ProblemaP1();
        try(InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);)
            {
                String linea = br.readLine();
                int casos = Integer.parseInt(linea);
                linea = br.readLine();

                for(int i=0; i<casos && linea!=null && linea.length()>0 && !"0".equals(linea);i++)
                {
                    //Se obtienen las entradas del problema para cada caso
                    final String[] infoProblem = linea.split(" ");
                    final int k = Integer.parseInt(infoProblem[0]);
                    final int e = Integer.parseInt(infoProblem[1]);
                    final int[] pc = new int[5];
                    for(int j=2; j<infoProblem.length;j++)
                    {
                        pc[j-2] = Integer.parseInt(infoProblem[j]);
                    }

                    int ct = instancia.creatividadMaxima(k, e, pc);
                    System.out.println(((Integer)ct).toString());
                    linea = br.readLine();
                }
            }
    }


    /**
     * Método que utiliza la ecuación de recurrencia para calcular la respuesta a una instancia
     * del problema obtenido en el main
     * 
     * @param k número de celdas que se planea usar para obtener la mayor puntación de creatividad
     * posible
     * 
     * @param e cantidad de energía que se tiene disponible para repartir en las k celdas y con el fin
     * de obtener una puntación máxima de creatividad
     * 
     * @param pc arreglo de enteros que indica que cantidad de puntos de creatividad se otorgan en cada
     * dígito
     * 
     * @return ct entero que indica la cantidad máxima de puntos de creatividad alcanzables con k celdas
     * y e de energía
     */
    public int creatividadMaxima(int k, int e, int[] pc)
    {
        int ct;
        //Código generado a partir de la ecuación de recurrencia

        int[] puntaje = new int[e + 1];//guarda los puntajes de creatividad para cada cantidad de energia

        for (int x = 0; x <= e; x++) {
            puntaje[x] = puntosCreatividad(x, pc);
        }

        int[][] subProblems = new int[k+1][e+1];
        final int NEG_INF = -1000000;

        // Casos base: 0 celdas
        subProblems[0][0] = 0;// E=0, puntaje 0

        for (int x = 1; x <= e; x++) { // En el grafo de necesidades llena la primera fila (menos el 0,0)
            subProblems[0][x] = NEG_INF; // si se quiere repartir energia en 0 celdas: imposible
        }

        int n = 1;
        int p = 0;
        while(n<=k)
        {
            if(n==1) {
                subProblems[n][p] = puntaje[p];
            }
            else {
                int best = NEG_INF;
                for (int x = 0; x <= p; x++) {
                    int prev = subProblems[n - 1][p - x];
                    if (prev == NEG_INF){
                        continue;
                    } // imposible
                    int cand = prev + puntaje[x];
                    if (cand > best) {
                        best = cand;
                    subProblems[n][p] = best;
                    }
                }
            }

            if(p<e)
            {
                p++;
            }
            else if(p==e)
            {
                p=0;
                n++;
            }
        }

        ct = subProblems[k][e];

        return ct;
    }

    /*
     * Método que calcula los puntos de creatividad de un número
     */

     public int puntosCreatividad(int num, int[]pc)
     {
        int pts = 0;
        int[] digitos = new int[5];
        int i = 0;
        while(num>0)
        {
            digitos[i] = num%10;
            num = num/10;
            i++;
        }
        for(int pos=0; pos<5; pos++)
        {
            int d = digitos[pos];
            if(d==3)
            {
                pts = pts+pc[pos];
            }
            else if(d==6)
            {
                pts = pts+(2*pc[pos]);
            }
            else if(d==9)
            {
                pts = pts+(3*pc[pos]);
            }
        }
        return pts;
     }
}
