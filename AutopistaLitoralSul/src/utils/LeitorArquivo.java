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
    private static final int ESTRADA_CIMA = 1;
    private static final int ESTRADA_DIREITA = 2;
    private static final int ESTRADA_BAIXO = 3;
    private static final int ESTRADA_ESQUERDA = 4;
    
    private static final int CRUZAMENTO_CIMA = 5;
    private static final int CRUZAMENTO_DIREITA = 6;
    private static final int CRUZAMENTO_BAIXO = 7;
    private static final int CRUZAMENTO_ESQUERDA = 8;
    
    private static final int CRUZAMENTO_CIMA_DIREITA = 9;
    private static final int CRUZAMENTO_CIMA_ESQUERDA = 10;
    private static final int CRUZAMENTO_DIREITA_BAIXO = 11;
    private static final int CRUZAMENTO_BAIXO_ESQUERDA = 12;
    private static final int CRUZAMENTO_CIMA_BAIXO = 13;
    private static final int CRUZAMENTO_DIREITA_ESQUERDA = 14;
    private static final int CRUZAMENTO_CIMA_DIREITA_BAIXO = 15;
    private static final int CRUZAMENTO_CIMA_DIREITA_ESQUERDA = 16;
    private static final int CRUZAMENTO_CIMA_BAIXO_ESQUERDA = 17;
    private static final int CRUZAMENTO_DIREITA_BAIXO_ESQUERDA = 18;

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
        
        for( int i = 0; i < numeroLinhas; i++ ){
            for( int j = 0; j < numeroColunas; j++ ){
                criarLinks(i, j, codigos[ i ][ j ]);
            }
        }
        
        br.close();
    }
    
    private PedacoMapa criarPedacoMapa( int i, int j, int codigo ){
        PedacoMapa pm;
        if( codigo == 0 ){
            return null;
        }
        if( codigo == ESTRADA_CIMA ){
            pm = fabrica.criarEstrada(i, j, "cima");
            if( i == matriz.length-1 ){
                posicoesIniciais.add( pm );
            }
            if( i == 0 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo == ESTRADA_DIREITA ){
            pm = fabrica.criarEstrada(i, j, "direita");
            if( j == 0 ){
                posicoesIniciais.add( pm );
            }
            if( j == matriz.length-1 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo == ESTRADA_BAIXO ){
            pm = fabrica.criarEstrada(i, j, "baixo");
            if( i == 0 ){
                posicoesIniciais.add( pm );
            }
            if( i == matriz.length-1 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo == ESTRADA_ESQUERDA ){
            pm = fabrica.criarEstrada(i, j, "esquerda");
            if( j == matriz.length-1 ){
                posicoesIniciais.add( pm );
            }
            if( j == 0 ){
                posicoesFinais.add( pm );
            }
            return pm;
            //////////////////////////////////////
        }else if( codigo >= 5 && codigo <= 18 ){
            return fabrica.criarCruzamento(i, j, "");
        }
        return null;
    }

    private void criarLinks( int i, int j, int codigo ){
        if( codigo == 0 ){
            return;
        }
        if( codigo == ESTRADA_CIMA && i != 0 ){
            matriz[ i ][ j ].adicionarCaminho( matriz[ i-1 ][ j ] );
            
        }else if( codigo == ESTRADA_DIREITA && j != matriz[i].length-1 ){
            matriz[ i ][ j ].adicionarCaminho( matriz[ i ][ j+1 ] );
            
        }else if( codigo == ESTRADA_BAIXO && i != matriz.length-1 ){
            matriz[ i ][ j ].adicionarCaminho( matriz[ i+1 ][ j ] );
            
        }else if( codigo == ESTRADA_ESQUERDA && j != 0 ){
            matriz[ i ][ j ].adicionarCaminho( matriz[ i ][ j-1 ] );
            
        } else if( codigo == CRUZAMENTO_CIMA ){
            criarLinks(i, j, ESTRADA_CIMA);
            
        }else if( codigo == CRUZAMENTO_DIREITA ){
            criarLinks(i, j, ESTRADA_DIREITA);
            
        }else if( codigo == CRUZAMENTO_BAIXO ){
            criarLinks(i, j, ESTRADA_BAIXO);
            
        }else if( codigo == CRUZAMENTO_ESQUERDA ){
            criarLinks(i, j, ESTRADA_ESQUERDA);
            
        }else if( codigo == CRUZAMENTO_CIMA_DIREITA ){
            criarLinks(i, j, ESTRADA_CIMA);
            criarLinks(i, j, ESTRADA_DIREITA);
            
        }else if( codigo == CRUZAMENTO_CIMA_BAIXO ){
            criarLinks(i, j, ESTRADA_CIMA);
            criarLinks(i, j, ESTRADA_BAIXO);
            
        }else if( codigo == CRUZAMENTO_CIMA_ESQUERDA ){
            criarLinks(i, j, ESTRADA_CIMA);
            criarLinks(i, j, ESTRADA_ESQUERDA);
            
        }else if( codigo == CRUZAMENTO_DIREITA_BAIXO ){
            criarLinks(i, j, ESTRADA_DIREITA);
            criarLinks(i, j, ESTRADA_BAIXO);
            
        }else if( codigo == CRUZAMENTO_DIREITA_ESQUERDA ){
            criarLinks(i, j, ESTRADA_DIREITA);
            criarLinks(i, j, ESTRADA_ESQUERDA);
            
        }else if( codigo == CRUZAMENTO_BAIXO_ESQUERDA ){
            criarLinks(i, j, ESTRADA_BAIXO);
            criarLinks(i, j, ESTRADA_ESQUERDA);
            
        }else if( codigo == CRUZAMENTO_CIMA_DIREITA_BAIXO ){
            criarLinks(i, j, ESTRADA_CIMA);
            criarLinks(i, j, ESTRADA_DIREITA);
            criarLinks(i, j, ESTRADA_BAIXO);
            
        }else if( codigo == CRUZAMENTO_CIMA_DIREITA_ESQUERDA ){
            criarLinks(i, j, ESTRADA_CIMA);
            criarLinks(i, j, ESTRADA_DIREITA);
            criarLinks(i, j, ESTRADA_ESQUERDA);
            
        }else if( codigo == CRUZAMENTO_CIMA_BAIXO_ESQUERDA ){
            criarLinks(i, j, ESTRADA_CIMA);
            criarLinks(i, j, ESTRADA_BAIXO);
            criarLinks(i, j, ESTRADA_ESQUERDA);
            
        }else if( codigo == CRUZAMENTO_DIREITA_BAIXO_ESQUERDA ){
            criarLinks(i, j, ESTRADA_DIREITA);
            criarLinks(i, j, ESTRADA_BAIXO);
            criarLinks(i, j, ESTRADA_ESQUERDA);
            
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
