import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * Programa que resuelve el problema planteado para el proyecto 1 de Dalgo
 * 
 * @author: Emmanuel Felipe Blanco Corredor
 * Código: 202312743
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
        int ct = 0;
        int[][] c = new int[k+1][e+1];

        //Código generado a partir de la ecuación de recurrencia
        int n = 0;
        int p = 0;
        int[][] subProblems = new int[k+1][e+1];
        while(n<=k)
        {
            if(n==0)
            {
                c[n][p] = -1;
                subProblems[n][p] =-1;
            }
            else if(n==1)
            {
                c[n][p] = puntosCreatividad(p, pc);
                subProblems[n][p] = c[n][p];
            }
            else if(n==2)
            {
                int op2 = c[1][p] + c[1][e-p];
                c[n][p] = Math.max(c[n-1][p],op2);
            }
            else
            {
                int subProblem = -1;
                if (subProblems[n-1][e-p]!=0)
                {
                    subProblem = subProblems[n-1][e-p];
                }
                else
                {
                    subProblem = creatividadMaxima(n-1, e-p, pc);
                    subProblems[n-1][e-p] = subProblem;
                }

                int opcion2 = subProblem + c[1][p];

                c[n][p] = Math.max(c[n-1][p],opcion2);
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

        //Se obtiene el puntaje maximo alcanzable con las k celdas y e de energia, el cual corresponde
        //con el valor máximo en la fila k de nuestra matriz c
        int[] ptsK = c[k];
        //Para el caso en el que se tiene una única celda se obtiene el puntaje de e
        if(k==1)
        {
            ct = ptsK[e];
        }
        else
        {
            for(int pts:ptsK)
            {
                if(pts>=ct)
                {
                    ct = pts;
                }
            }
        }
        

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
