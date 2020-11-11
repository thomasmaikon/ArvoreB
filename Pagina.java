package Trabalho1;

import java.util.ArrayList;
import java.util.Collections;

public class Pagina{
    private static Integer ordem;
    private ArrayList<Elemento> listaDeElementos;
    private ArrayList<Pagina> listaDeFilhos;

    public Pagina(ArrayList listaDeElementos, Integer ordem){
        setOrdem(ordem);
        setListaDeElementos(listaDeElementos);
        listaDeFilhos = new ArrayList<>(this.numFilhos());
    }
    public Pagina(Elemento novoElemento, Integer ordem){
        setOrdem(ordem);
        listaDeElementos = new ArrayList<>(getOrdem()*2);
        listaDeElementos.add(novoElemento);
        listaDeFilhos = new ArrayList<>(this.numFilhos());
    }
    private Integer numFilhos(){
        return (ordem*2)+1;
    }

    public boolean inserirElementoNaPagina(Elemento novoElemento){
        if(listaDeElementos.size() < getOrdem()*2){
            listaDeElementos.add(novoElemento);
            Collections.sort(listaDeElementos);
            return true;
        }
        Collections.sort(listaDeElementos);
        return false;
    }

    public static Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        Pagina.ordem = ordem;
    }

    public ArrayList<Elemento> getListaDeElementos() {
        return listaDeElementos;
    }

    public void setListaDeElementos(ArrayList<Elemento> listaDeElementos) {
        this.listaDeElementos = listaDeElementos;
    }

    public ArrayList<Pagina> getListaDeFilhos() {
        return listaDeFilhos;
    }

    public void setListaDeFilhos(ArrayList<Pagina> listaDeFilhos) {
        this.listaDeFilhos = listaDeFilhos;
    }

    public static Pagina criarPagina(Elemento elemento, Integer ordem){
        return new Pagina(elemento, ordem);
    }

    public static Pagina criarPaginaComArray(ArrayList lista){
        return new Pagina(lista, getOrdem());
    }

    @Override
    public String toString() {
        return "Pagina{" +
                " " + listaDeElementos +
                ", Filhos" + listaDeFilhos +
                '}';
    }

    public Elemento getElementoDoMeio(Elemento e) {

        ArrayList<Elemento> novoArray = new ArrayList(this.getListaDeElementos());
        novoArray.add(e);
        Collections.sort(novoArray);
        var elementoDoMeio = novoArray.get(getOrdem());
        this.getListaDeElementos().remove(elementoDoMeio);
        return elementoDoMeio;
    }

    public Pagina getMenores(Elemento elementoDoMeio){
        Integer posicao = 0;
        for (Elemento e1: this.getListaDeElementos()) {
            if(e1.compararChaves(elementoDoMeio.getChave()) > 0 ){
                break;
            }
            posicao++;
        }
        var menores =  new ArrayList<>(this.getListaDeElementos().subList(0, posicao));
        var novaPaginaMenores = Pagina.criarPaginaComArray(menores);
        if(this.getListaDeFilhos().size()!=0){
            for (Pagina filho:this.getListaDeFilhos()){
                    if(filho.getListaDeElementos().get(0).compararChaves(elementoDoMeio.getChave())>0){
                        break;
                    }else{
                        novaPaginaMenores.getListaDeFilhos().add(filho);
                    }
            }
        }
        Collections.sort(novaPaginaMenores.getListaDeElementos());
        return novaPaginaMenores;

    }

    public Pagina getMaiores(Elemento elementoDoMeio) {
        Integer posicao = 0;
        for (Elemento e1: this.getListaDeElementos()) {
            if(e1.compararChaves(elementoDoMeio.getChave()) > 0 ){
                break;
            }
            posicao++;
        }
        var maiores =  new ArrayList<>(this.getListaDeElementos().subList(posicao, this.getListaDeElementos().size()));
        var novaPaginaMaiores = Pagina.criarPaginaComArray(maiores);
        if(this.getListaDeFilhos().size()!=0){
            for (Pagina filho:this.getListaDeFilhos()){
                if(filho.getListaDeElementos().get(0).compararChaves(elementoDoMeio.getChave())>0){
                    novaPaginaMaiores.getListaDeFilhos().add(filho);
                }
            }
        }
        Collections.sort(novaPaginaMaiores.getListaDeElementos());
        return novaPaginaMaiores;
    }

    public void limpar(){
        this.getListaDeFilhos().clear();
        this.getListaDeElementos().clear();
    }

    public boolean listaDeFilhosVazia(){
        if(this.getListaDeFilhos().size() == 0){
            return true;
        }
        return false;
    }

}
