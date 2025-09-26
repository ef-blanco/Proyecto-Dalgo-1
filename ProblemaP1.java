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
                    final int n = Integer.parseInt(infoProblem[1]);
                    final int[] pc = new int[5];
                    for(int j=2; j<infoProblem.length;j++)
                    {
                        pc[j-2] = Integer.parseInt(infoProblem[j]);
                    }

                    int ct = instancia.creatividadMaxima(k, n, pc);
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
     * @param n cantidad de energía que se tiene disponible para repartir en las k celdas y con el fin
     * de obtener una puntación máxima de creatividad
     * 
     * @param pc arreglo de enteros que indica que cantidad de puntos de creatividad se otorgan en cada
     * dígito
     * 
     * @return ct entero que indica la cantidad máxima de puntos de creatividad alcanzables con k celdas
     * y e de energía
     */
    public int creatividadMaxima(int k, int n, int[] pc)
    {
        int ct = 0;
        int[][] m = new int[k+1][n+1];

        //Código generado a partir de la ecuación de recurrencia
        int e = 0;
        int c = 0;
        while(c<=k)
        {
            if(c==0)
            {
                m[c][e] = -1;
            }
            else if(c==1)
            {
                m[c][e] = puntosCreatividad(e, pc);
            }
            else if(c==2)
            {
                m[c][e] = m[1][e] + m[1][n-e];
            }
            else if(c==3)
            {
                m[c][e] = Math.max(m[c-1][e],m[1][e]+repartirEnergia2Celdas(n-e, m));
            }
            else if(c>3)
            {
                m[c][e] = Math.max(m[c-1][e],m[1][e]+creatividadMaxima(c-1, n-e, pc));
            }

            if(e<n)
            {
                e++;
            }
            else if(e==n)
            {
                e=0;
                c++;
            }
        }

        //Se obtiene el puntaje maximo alcanzable con las k celdas y n de energia, el cual corresponde
        //con el valor máximo en m[k][n]
        int[] ptsK = m[k];
        //Para el caso en el que se tiene una única celda se obtiene el puntaje de e
        if(k==1)
        {
            ct = ptsK[n];
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
    
     /*
      * Metodo que calcula la solución para el problema con c=2
      */
      public int repartirEnergia2Celdas(int e, int[][] m)
      {
        int resp = 0;
        if(e>1)
        {
            int p1 = 0;
            int p2 = e;
            while(p1<=e)
            {
                int pts = m[1][p1]+m[1][p2];
                if(pts>=resp)
                {
                    resp = pts;
                }
                p1++;
                p2--;
            }
        }
        return resp;
      }
}
