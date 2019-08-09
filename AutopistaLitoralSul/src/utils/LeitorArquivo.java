package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Fabrica;
import model.PedacoMapa;

public class LeitorArquivo {
    
    private static final int VAZIO = 0;
    private static final int CIMA = 1;
    private static final int DIREITA = 2;
    private static final int BAIXO = 3;
    private static final int ESQUERDA = 4;
    
    private static final int CIMA_DIREITA = 5;
    private static final int CIMA_BAIXO = 6;
    private static final int CIMA_ESQUERDA = 7;
    private static final int DIREITA_BAIXO = 8;
    private static final int DIREITA_ESQUERDA = 9;
    private static final int BAIXO_ESQUERDA = 10;
    
    private static final int CIMA_DIREITA_BAIXO = 11;
    private static final int CIMA_DIREITA_ESQUERDA = 12;
    private static final int CIMA_BAIXO_ESQUERDA = 13;
    private static final int DIREITA_BAIXO_ESQUERDA = 14;

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
                codigos[ j ][ i ] = aux;
                matriz[ j ][ i ] = criarPedacoMapa( j, i, aux);
            }
        }
        
        for( int i = 0; i < numeroLinhas; i++ ){
            for( int j = 0; j < numeroColunas; j++ ){
                criarLinks(j, i, codigos[ j ][ i ]);
            }
        }
        
        br.close();
    }
    
    private PedacoMapa criarPedacoMapa( int j, int i, int codigo ){
        PedacoMapa pm;
        if( codigo == 0 ){
            return null;
        }
        if( codigo == CIMA ){
            pm = fabrica.criarEstrada(j, i, "cima");
            if( i == matriz.length-1 ){
                posicoesIniciais.add( pm );
            }
            if( i == 0 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo == DIREITA ){
            pm = fabrica.criarEstrada(j, i, "direita");
            if( j == 0 ){
                posicoesIniciais.add( pm );
            }
            if( j == matriz.length-1 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo == BAIXO ){
            pm = fabrica.criarEstrada(j, i, "baixo");
            if( i == 0 ){
                posicoesIniciais.add( pm );
            }
            if( i == matriz.length-1 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo == ESQUERDA ){
            pm = fabrica.criarEstrada(j, i, "esquerda");
            if( j == matriz.length-1 ){
                posicoesIniciais.add( pm );
            }
            if( j == 0 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo >= 5 && codigo <= 14 ){
            return fabrica.criarCruzamento(j, i, "");
        }
        return null;
    }

    private void criarLinks( int j, int i, int codigo ){
        if( codigo == 0 ){
            return;
        }
        if( codigo == CIMA && i != 0 ){
            matriz[ j ][ i ].adicionarCaminho( matriz[ j ][ i-1 ] );
            
        }else if( codigo == DIREITA && j != matriz.length-1 ){
            matriz[ j ][ i ].adicionarCaminho( matriz[ j+1 ][ i ] );
            
        }else if( codigo == BAIXO && i != matriz.length-1 ){
            matriz[ j ][ i ].adicionarCaminho( matriz[ j ][ i+1 ] );
            
        }else if( codigo == ESQUERDA && j != 0 ){
            matriz[ j ][ i ].adicionarCaminho( matriz[ j-1 ][ i ] );
            
        } else if( codigo == CIMA_DIREITA ){
            criarLinks(j, i, CIMA);
            criarLinks(j, i, DIREITA);
            
        }else if( codigo == CIMA_BAIXO ){
            criarLinks(j, i, CIMA);
            criarLinks(j, i, BAIXO);
            
        }else if( codigo == CIMA_ESQUERDA ){
            criarLinks(j, i, CIMA);
            criarLinks(j, i, ESQUERDA);
            
        }else if( codigo == DIREITA_BAIXO ){
            criarLinks(j, i, DIREITA);
            criarLinks(j, i, BAIXO);
            
        }else if( codigo == DIREITA_ESQUERDA ){
            criarLinks(j, i, DIREITA);
            criarLinks(j, i, ESQUERDA);
            
        }else if( codigo == BAIXO_ESQUERDA ){
            criarLinks(j, i, BAIXO);
            criarLinks(j, i, ESQUERDA);
            
        }else if( codigo == CIMA_DIREITA_BAIXO ){
            criarLinks(j, i, CIMA);
            criarLinks(j, i, DIREITA);
            criarLinks(j, i, BAIXO);
            
        }else if( codigo == CIMA_DIREITA_ESQUERDA ){
            criarLinks(j, i, CIMA);
            criarLinks(j, i, DIREITA);
            criarLinks(j, i, ESQUERDA);
            
        }else if( codigo == CIMA_BAIXO_ESQUERDA ){
            criarLinks(j, i, CIMA);
            criarLinks(j, i, BAIXO);
            criarLinks(j, i, ESQUERDA);
            
        }else if( codigo == DIREITA_BAIXO_ESQUERDA ){
            criarLinks(j, i, DIREITA);
            criarLinks(j, i, BAIXO);
            criarLinks(j, i, ESQUERDA);
            
        }    
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
    
}
