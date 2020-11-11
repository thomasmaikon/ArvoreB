package Trabalho1;

import java.io.BufferedReader;
import java.io.FileReader;


public class Master{
    public static void main(String[] args) {

       ArvoreB arvore = new ArvoreB(2);
       //inserindo Sites
       inserirSites(arvore);

       /* OBS: Recomenda-se utilizaçã de numeros para facilitar o entendimento
       * A baixo temos o exemplo utilizado no material Fornecido pelo Professor.
       *
       * */

/*    var elemento = new Elemento<Integer, String>(10, "Thominhas");

        arvore.inserirElemento(elemento);
        arvore.inserirElemento(elemento.criarElemento(20,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(30,"Toto"));
        arvore.inserirElemento(elemento.criarElemento(40,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(15,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(35,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(7,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(26,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(18,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(22,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(5,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(42,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(13,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(46,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(27,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(8,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(32,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(38,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(24,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(45,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(25,"Totozinho"));
        arvore.inserirElemento(elemento.criarElemento(25,"t"));
        arvore.inserirElemento(elemento.criarElemento(26,"Tominhasssss"));

        arvore.remover(25);
        arvore.remover(45);
        arvore.remover(24);
*/
        //sobrescrevendo
        System.out.println("Buscando elemento: "+arvore.buscar("www.xvideos.com"));
        System.out.println("Modificando valor com mesma chave" + arvore.inserirElemento(new Elemento("www.xvideos.com", "39.39")));
        System.out.println(arvore.buscar("www.xvideos.com"));

        //Removendo
        System.out.println("Removendo Elemento: " + arvore.remover("www.xvideos.com"));
        System.out.println("Buscando elemento removido: " + arvore.buscar("www.xvideos.com"));

        System.out.println(arvore);

    }

    public static void inserirSites(ArvoreB arvore) {
        System.out.println("Inserindo Elementos a partir de arquivo TXT ...");

        try{
            // colocar o caminho referente a posicao do arquivo no seu computador
            FileReader arq = new FileReader("C:\\sites.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();


            while (linha != null) {
                String separado[] = linha.split( " - ");

                arvore.inserirElemento(new Elemento<>(separado[1],separado[0]));

                linha = lerArq.readLine();
            }
            arq.close();
        }catch (Exception erro){
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    erro.getMessage());
        }
    }
}



