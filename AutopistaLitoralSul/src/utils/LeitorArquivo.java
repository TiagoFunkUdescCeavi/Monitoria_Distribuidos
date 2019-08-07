package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Cruzamento;
import model.Fabrica;
import model.PedacoMapa;

public class LeitorArquivo {

    private String nomeArquivo;
    private Fabrica fabrica;
    
    private List<PedacoMapa> posicoesIniciais;
    private List<PedacoMapa> posicoesFinais;
    
    private PedacoMapa[][] matriz;
    
    public LeitorArquivo(String nomeArquivo, Fabrica fabrica) {
        this.nomeArquivo = nomeArquivo;
        this.fabrica = fabrica;
        posicoesIniciais = new ArrayList<>();
        posicoesFinais = new ArrayList<>();
    }

    public void carregarMapa() throws FileNotFoundException, IOException {
        int aux;
        int numeroLinhas, numeroColunas;
        int codigos[][];
        String leituraAux[];
        BufferedReader br;

        br = new BufferedReader(new FileReader(new File(nomeArquivo)));
        numeroLinhas = Integer.parseInt(br.readLine().trim());
        numeroColunas = Integer.parseInt(br.readLine().trim());

        matriz = new PedacoMapa[numeroLinhas][numeroColunas];
        codigos = new int[numeroLinhas][numeroColunas];

        for( int i = 0; i < numeroLinhas; i++ ){
            leituraAux = br.readLine().replaceAll("\t", " ").trim().split(" ");
            for( int j = 0; j < numeroColunas; j++ ){
                aux = Integer.parseInt( leituraAux[ j ] );
                codigos[ i ][ j ] = aux;
                matriz[ i ][ j ] = criarPedacoMapa( i, j, aux);
            }
        }
        
        br.close();
//        popularMatriz(vetorAux, matriz);
    }
    
    private PedacoMapa criarPedacoMapa( int i, int j, int codigo ){
        PedacoMapa pm;
        if( codigo == 1 ){
            pm = fabrica.criarEstrada(i, j, "cima");
            if( i == matriz.length-1 ){
                posicoesIniciais.add( pm );
            }
            if( i == 0 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo == 2 ){
            pm = fabrica.criarEstrada(i, j, "direita");
            if( j == 0 ){
                posicoesIniciais.add( pm );
            }
            if( j == matriz.length-1 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo == 3 ){
            pm = fabrica.criarEstrada(i, j, "baixo");
            if( i == 0 ){
                posicoesIniciais.add( pm );
            }
            if( i == matriz.length-1 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo == 4 ){
            pm = fabrica.criarEstrada(i, j, "esquerda");
            if( j == matriz.length-1 ){
                posicoesIniciais.add( pm );
            }
            if( j == 0 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo >= 5 && codigo <= 14 ){
            return fabrica.criarCruzamento(i, j, "");
        }
        return null;
    }

    public List<PedacoMapa> getPosicoesIniciais() {
        return posicoesIniciais;
    }

    public List<PedacoMapa> getPosicoesFinais() {
        return posicoesFinais;
    }

    public PedacoMapa[][] getMatriz() {
        return matriz;
    }

    private void popularMatriz(int[] vetorAux, PedacoMapa[][] matriz) {
        int yInicial, xInicial, xFinal, yFinal;
        PedacoMapa pm;
        
        xInicial = vetorAux[ 0 ];
        yInicial = vetorAux[ 1 ];
        xFinal = vetorAux[ 2 ];
        yFinal = vetorAux[ 3 ];
        
        if ( yInicial == yFinal ) {
            if ( xInicial < xFinal) {
                if( matriz[ xInicial ][ yInicial ] == null ){
                    pm = fabrica.criarCruzamento( xInicial, yInicial, "direita" );
                    matriz[ xInicial ][ yInicial ] = pm;
                    if( xInicial == 0 ){
                        posicoesIniciais.add( (Cruzamento) pm );
                    }
                }
                for (int i = xInicial+1; i <= xFinal-1; i++) {
                    pm = fabrica.criarEstrada( i, yInicial, "direita" );
                    matriz[ i-1 ][ yInicial ].adicionarCaminho( pm );
                    matriz[  i  ][ yInicial ] = pm;
                }
                if( matriz[ xFinal ][ yFinal ] == null ){
                    Cruzamento c = fabrica.criarCruzamento( xFinal, yFinal, "direita" );
                    matriz[ xFinal-1 ][ yFinal ].adicionarCaminho( c );
                    matriz[ xFinal ][ yFinal ] = c;
                    if( xFinal == matriz.length-1 ){
                        posicoesFinais.add( c );
                    }
                }else{
                    matriz[ xFinal-1 ][ yFinal ].adicionarCaminho( matriz[ xFinal ][ yFinal ] );
                }
                /*11111111111111111111111111111111111111111111111111111111111*/
                /*11111111111111111111111111111111111111111111111111111111111*/
                /*11111111111111111111111111111111111111111111111111111111111*/
            } else {
                if( matriz[ xInicial ][ yInicial ] == null ){
                    pm = fabrica.criarCruzamento( xInicial, yInicial, "esquerda" );
                    matriz[ xInicial ][ yInicial ] = pm;
                    if( xInicial == matriz.length-1 ){
                        posicoesIniciais.add( (Cruzamento) pm );
                    }
                }
                for (int i = xInicial-1; i >= xFinal+1; i--) {
                    pm = fabrica.criarEstrada( i, yFinal, "esquerda" );
                    matriz[ i+1 ][ yInicial ].adicionarCaminho( pm );
                    matriz[ i ][ yInicial ] = pm;
                }
                if( matriz[ xFinal ][ yFinal ] == null ){
                    Cruzamento c = fabrica.criarCruzamento( xFinal, yFinal, "esquerda" );
                    matriz[ xFinal+1 ][ yFinal ].adicionarCaminho( c );
                    matriz[ xFinal ][ yFinal ] = c;
                    if( xFinal == 0 ){
                        posicoesFinais.add( c );
                    }
                }else{
                    matriz[ xFinal+1 ][ yFinal ].adicionarCaminho( matriz[ xFinal ][ yFinal ] );
                }
                /*22222222222222222222222222222222222222222222222222222222222*/
                /*22222222222222222222222222222222222222222222222222222222222*/
                /*22222222222222222222222222222222222222222222222222222222222*/
            }
        } else {
            if ( yInicial < yFinal ) {
                if( matriz[ xInicial ][ yInicial ] == null ){
                    pm = fabrica.criarCruzamento( xInicial, yInicial, "baixo" );
                    matriz[ xInicial ][ yInicial ] = pm;
                    if( yInicial == 0 ){
                        posicoesIniciais.add( (Cruzamento) pm);
                    }
                }
                for (int i = yInicial+1; i <= yFinal-1; i++) {
                    pm = fabrica.criarEstrada( xInicial, i, "baixo" );
                    matriz[ xInicial ][ i-1 ].adicionarCaminho( pm );
                    matriz[ xInicial ][ i ] = pm;
                }
                if( matriz[ xFinal ][ yFinal ] == null ){
                    Cruzamento c = fabrica.criarCruzamento( xFinal, yFinal, "baixo" );
                    matriz[ xFinal ][ yFinal-1 ].adicionarCaminho( c );
                    matriz[ xFinal ][ yFinal ] = c;
                    if( yFinal == matriz[0].length-1 ){
                        posicoesFinais.add( c );
                    }
                }else{
                    matriz[ xFinal ][ yFinal-1 ].adicionarCaminho( matriz[ xFinal ][ yFinal ] );
                }
                /*33333333333333333333333333333333333333333333333333333333333*/
                /*33333333333333333333333333333333333333333333333333333333333*/
                /*33333333333333333333333333333333333333333333333333333333333*/
            } else {
                if( matriz[ xInicial ][ yInicial ] == null ){
                    pm = fabrica.criarCruzamento( xInicial, yInicial, "cima" );
                    matriz[ xInicial ][ yInicial ] = pm;
                    if( yInicial == matriz[0].length-1 ){
                        posicoesIniciais.add( (Cruzamento) pm);
                    }
                }
                for (int i = yInicial-1; i >= yFinal+1; i--) {
                    pm = fabrica.criarEstrada( xFinal, i, "cima" );
                    matriz[ xInicial ][ i+1 ].adicionarCaminho( pm );
                    matriz[ xInicial ][ i ] = pm;
                }
                if( matriz[ xFinal ][ yFinal ] == null ){
                    Cruzamento c = fabrica.criarCruzamento( xFinal, yFinal, "cima" );
                    matriz[ xFinal ][ yFinal+1 ].adicionarCaminho( c );
                    matriz[ xFinal ][ yFinal ] = c;
                    if( yFinal == 0 ){
                        posicoesFinais.add( c );
                    }
                }else{
                    matriz[ xFinal ][ yFinal+1 ].adicionarCaminho( matriz[ xFinal ][ yFinal ] );
                }
                /*44444444444444444444444444444444444444444444444444444444444*/
                /*44444444444444444444444444444444444444444444444444444444444*/
                /*44444444444444444444444444444444444444444444444444444444444*/
            }
        }
    }
}
