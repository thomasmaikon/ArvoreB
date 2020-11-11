package Trabalho1;

import java.util.ArrayList;
import java.util.Collections;

public class ArvoreB{
    private Pagina paginaPrincipal;
    private Integer ordem;

    public ArvoreB(Integer ordem){
        setPaginaPrincipal(null);
        setOrdem(ordem);
    }

    public Pagina getPaginaPrincipal() {
        return paginaPrincipal;
    }

    public void setPaginaPrincipal(Pagina paginaPrincipal) {
        this.paginaPrincipal = paginaPrincipal;
    }

    public boolean inserirElemento(Elemento elemento){
        if(paginaPrincipal == null){
            paginaPrincipal = Pagina.criarPagina(elemento,this.getOrdem());
            return true;
        }else {
            if (!paginaPrincipal.listaDeFilhosVazia()) {
                int i = 0;
                if(paginaPrincipal.getListaDeElementos().get(0).compararChaves(elemento.getChave()) == 0){
                    paginaPrincipal.getListaDeElementos().get(0).setValor(elemento.getValor());
                    return true;
                }
                for (Elemento e: paginaPrincipal.getListaDeElementos()){
                    if (e.compararChaves(elemento.getChave())>0){

                        Pagina resultado = inserirElemento1(paginaPrincipal.getListaDeFilhos().get(i), elemento);

                        if(resultado != null){
                            this.setPaginaPrincipal(this.estouroDeFilhos(resultado,paginaPrincipal));
                        }
                       return true;
                    }
                    i = i+1;
                }
                Pagina resultado = inserirElemento1(paginaPrincipal.getListaDeFilhos().get(i), elemento);
                if(resultado != null){
                    this.setPaginaPrincipal(this.estouroDeFilhos(resultado,paginaPrincipal));
                    return true;
                }
            }else{
                boolean estouro = !paginaPrincipal.inserirElementoNaPagina(elemento);
                if(estouro) {

                    this.setPaginaPrincipal(this.estouroRaiz(paginaPrincipal, elemento));
                }
            }
        }
        return false;
    }

    private Pagina inserirElemento1(Pagina p, Elemento elemento){
        for (Elemento e: p.getListaDeElementos()){
            if(e.compararChaves(elemento.getChave()) == 0){
                e.setValor(elemento.getValor());
                return null;
            }
        }
        if(p.getListaDeFilhos().size()!=0){
            for(Elemento e: p.getListaDeElementos()){
                if(e.compararChaves(elemento.getChave())>0){
                    var indice = p.getListaDeElementos().indexOf(e);
                    var filhoReferente = p.getListaDeFilhos().get(indice);
                    return this.inserirElemento1(filhoReferente, elemento);
                }
            }
        }
        boolean estouro = !p.inserirElementoNaPagina(elemento);
        if(estouro){
            return this.estouroRaiz(p, elemento);
        }
        return null;
    }

    public Pagina estouroRaiz(Pagina p, Elemento e){
        Elemento elementoDoMeio = p.getElementoDoMeio(e);
        Pagina novaPagina = Pagina.criarPagina(elementoDoMeio,this.ordem);
        p.inserirElementoNaPagina(e);
        Collections.sort(p.getListaDeElementos());
        Pagina menores = p.getMenores(elementoDoMeio);
        Pagina maiores = p.getMaiores(elementoDoMeio);
        novaPagina.getListaDeFilhos().add(menores);
        novaPagina.getListaDeFilhos().add(maiores);
        Collections.sort(novaPagina.getListaDeElementos());
        return novaPagina;
    }

    public Pagina estouroDeFilhos(Pagina resultado, Pagina raiz){
        Pagina menorDoResultado = resultado.getListaDeFilhos().get(0);
        Pagina maiorDoResultado = resultado.getListaDeFilhos().get(1);
        Elemento elementoDoEstouro = resultado.getListaDeElementos().get(0);
        var verificarSeRaizEstoura = !raiz.inserirElementoNaPagina(elementoDoEstouro);
        if(verificarSeRaizEstoura){
            ArrayList novaListaDeElementosDaRaiz = new ArrayList(raiz.getListaDeElementos());
            novaListaDeElementosDaRaiz.add(elementoDoEstouro);
            Collections.sort(novaListaDeElementosDaRaiz);

            var posicao = novaListaDeElementosDaRaiz.indexOf(elementoDoEstouro);

            if(raiz.getListaDeFilhos().get(posicao) != null ){
                raiz.getListaDeFilhos().remove(posicao);
            }
            raiz.getListaDeFilhos().add(posicao, menorDoResultado);
            raiz.getListaDeFilhos().add(posicao+1, maiorDoResultado); // talvez tenha problema aq tbm

            novaListaDeElementosDaRaiz.remove(elementoDoEstouro);
            raiz.setListaDeElementos(novaListaDeElementosDaRaiz);
            return this.estouroRaiz(raiz, elementoDoEstouro);
        }else {
            var indice = raiz.getListaDeElementos().indexOf(elementoDoEstouro);
            if (raiz.getListaDeFilhos().get(indice) != null) {
                raiz.getListaDeFilhos().remove(indice);
            }
            raiz.getListaDeFilhos().add(indice, menorDoResultado);

            raiz.getListaDeFilhos().add(indice + 1, maiorDoResultado); // talvez tenha problema aq
        }
        Collections.sort(raiz.getListaDeElementos());
        return raiz;
    }

    public Elemento buscar(Comparable chave){
        return this.procurando(paginaPrincipal, chave);
    }
    private Elemento procurando(Pagina p, Comparable chave){
        for (Elemento e: p.getListaDeElementos()){
            if(e.compararChaves(chave) == 0){
                return e;
            }
        }
        if(p.getListaDeFilhos().size()!=0){
            for (Pagina filhos: p.getListaDeFilhos()){
                var resultado = this.procurando(filhos, chave);
                if( resultado != null){
                    return resultado;
                }
            }

        }
        return null;
    }

    public boolean remover(Comparable chave){
        return this.remocaoGenerica(paginaPrincipal,chave);
    }

    private boolean remocaoGenerica(Pagina p,Comparable chave){
        for(Elemento e:p.getListaDeElementos()){
            if(e.compararChaves(chave) == 0){
                return this.remocaoEmPaginaNaoFolha(p, e);
            }
        }

        if(p.getListaDeFilhos().size() !=0){
            for (Pagina filho: p.getListaDeFilhos()){
                var x = this.remocaoGenerica(filho, chave);
               if(x != false){
                   return x;
               }
            }
        }
        return false;
    }

    private boolean remocaoEmPaginaNaoFolha(Pagina p,Elemento e){
        var indice = p.getListaDeElementos().indexOf(e);
        if(p.getListaDeFilhos().size()!=0) {
            var substituto = this.pegarMaiorElemento(p.getListaDeFilhos().get(indice));
            p.getListaDeElementos().remove(e);
            this.remocaoSimples(paginaPrincipal,substituto.getChave());
            p.getListaDeElementos().add(indice, substituto);
            Collections.sort(p.getListaDeElementos());
            for (Pagina filho: paginaPrincipal.getListaDeFilhos()) {
              while (this.balanceamento(filho)){}
            }
            while (this.consertarRaiz());
            return true;
        }
        return this.remocaoEmPaginaFolha(p,e.getChave());
    }

    private boolean remocaoEmPaginaFolha(Pagina p, Comparable chave){
        for (Elemento e: p.getListaDeElementos()){
            if(e.compararChaves(chave) == 0){
                p.getListaDeElementos().remove(e);
                Collections.sort(p.getListaDeElementos());
                for (Pagina filho: paginaPrincipal.getListaDeFilhos()) {
                    while (this.balanceamento(filho)) { }
                }
                while(this.consertarRaiz());
                return true;
            }
        }
        while(this.consertarRaiz());
        return false;
    }

    private Elemento pegarMaiorElemento(Pagina p){
        if(p.getListaDeFilhos().size() !=0){
            var ultimaPagina = p.getListaDeFilhos().size() - 1;
            return this.pegarMaiorElemento(p.getListaDeFilhos().get(ultimaPagina));
        }
        var ultimaPosicao = p.getListaDeElementos().size()-1;
        var elemento = p.getListaDeElementos().get(ultimaPosicao);
        return elemento;
    }

    private boolean balanceamento(Pagina p){
       Pagina paginaComFilhoQuebrado = this.procurarPaiDaPaginaQuebrada(p);
       if(paginaComFilhoQuebrado == null) {return false;}
       Pagina paginaFilhoQuebrada = this.pegarPaginaQuebrada(paginaComFilhoQuebrado);
       if(paginaFilhoQuebrada == null){return false;}
       Integer indice =0;
       for (Pagina f: paginaComFilhoQuebrado.getListaDeFilhos()){
           for (Elemento e: f.getListaDeElementos()){
               if (e.compararChaves(paginaFilhoQuebrada.getListaDeElementos().get(0).getChave())==0){
                   if((indice - 1) >= 0) {
                       Elemento elementoQuebrado = paginaComFilhoQuebrado.getListaDeElementos().get(indice-1);
                       var tamanhoDoAjudante = paginaComFilhoQuebrado.getListaDeFilhos().get(indice - 1).getListaDeElementos().size();
                       if (tamanhoDoAjudante > this.getOrdem()) {
                           Elemento maiorElemento = this.pegarMaiorElemento(paginaComFilhoQuebrado.getListaDeFilhos().get(indice - 1));
                           this.remocaoSimples(paginaPrincipal, maiorElemento.getChave());
                           paginaComFilhoQuebrado.getListaDeElementos().remove(elementoQuebrado);
                           paginaComFilhoQuebrado.inserirElementoNaPagina(maiorElemento);
                           paginaFilhoQuebrada.inserirElementoNaPagina(elementoQuebrado);
                           return this.balanceamento(p);
                       }else{
                           paginaComFilhoQuebrado.getListaDeFilhos().get(indice - 1).inserirElementoNaPagina(elementoQuebrado);
                           paginaComFilhoQuebrado.getListaDeElementos().remove(elementoQuebrado);
                           Collections.sort(paginaComFilhoQuebrado.getListaDeElementos());
                           for (Elemento e2: paginaFilhoQuebrada.getListaDeElementos()) {
                               paginaComFilhoQuebrado.getListaDeFilhos().get(indice - 1).inserirElementoNaPagina(e2);
                           }
                           paginaComFilhoQuebrado.getListaDeFilhos().remove(paginaFilhoQuebrada);
                           return this.balanceamento(p);
                       }
                   }else{
                       Elemento elementoQuebrado = paginaComFilhoQuebrado.getListaDeElementos().get(indice);
                       var tamanhoDoAjudante = paginaComFilhoQuebrado.getListaDeFilhos().get(indice + 1).getListaDeElementos().size();
                       if (tamanhoDoAjudante > this.getOrdem()) {
                           Elemento menorElemento = this.pegarMenorElemento(paginaComFilhoQuebrado.getListaDeFilhos().get(indice + 1));

                           this.remocaoSimples(paginaPrincipal, menorElemento.getChave());
                           paginaComFilhoQuebrado.getListaDeElementos().remove(elementoQuebrado);
                           paginaComFilhoQuebrado.inserirElementoNaPagina(menorElemento);
                           paginaFilhoQuebrada.inserirElementoNaPagina(elementoQuebrado);
                           return this.balanceamento(p);
                       }else{
                           paginaFilhoQuebrada.getListaDeElementos().add(elementoQuebrado);
                           paginaComFilhoQuebrado.getListaDeElementos().remove(elementoQuebrado);
                           ArrayList<Elemento> elementos = paginaFilhoQuebrada.getListaDeElementos();
                           for (Elemento e1: elementos) {
                               paginaComFilhoQuebrado.getListaDeFilhos().get(indice + 1).inserirElementoNaPagina(e1);
                           }
                           paginaComFilhoQuebrado.getListaDeFilhos().get(indice).limpar();
                           paginaComFilhoQuebrado.getListaDeFilhos().remove(indice);
                           Collections.sort(paginaComFilhoQuebrado.getListaDeElementos());
                           return this.balanceamento(p);
                       }
                   }
               }
           }
           indice++;
       }
       return false;
    }

    private Pagina procurarPaiDaPaginaQuebrada(Pagina p){
        if(paginaPrincipal.getListaDeElementos().size() < this.getOrdem()){
            for (Pagina filho: paginaPrincipal.getListaDeFilhos()){
                if(filho.getListaDeElementos().size() < this.getOrdem()){

                    return paginaPrincipal;
                }
            }
        }
        if(p.getListaDeElementos().size() < this.getOrdem()){
            return p;
        }
        if(p.getListaDeFilhos().size()!=0){
            for (Pagina filho:p.getListaDeFilhos()){
                if(this.procurarPaiDaPaginaQuebrada(filho) != null){
                    return p;
                }
            }
        }
        return null;
    }

    private Pagina pegarPaginaQuebrada(Pagina p){

        if(p.getListaDeElementos().size() < this.getOrdem()){
            return p;
        }
        if(p.getListaDeFilhos().size()!=0){
            for (Pagina filho:p.getListaDeFilhos()){
                var f = this.pegarPaginaQuebrada(filho);
                if(f != null){
                    return f;
                }
            }
        }
        return null;
    }

    private Elemento pegarMenorElemento(Pagina p){
        if(p.getListaDeFilhos().size() !=0){
            var primeiraPagina = 0;
            return this.pegarMenorElemento(p.getListaDeFilhos().get(primeiraPagina));
        }
        var primeiraPagina = 0;
        var elemento = p.getListaDeElementos().get(primeiraPagina);
        return elemento;
    }

    private boolean remocaoSimples(Pagina p,Comparable chave){
        for (Elemento e:p.getListaDeElementos()){
            if(e.compararChaves(chave) == 0){
                p.getListaDeElementos().remove(e);
                return true;
            }
        }
        if(p.getListaDeFilhos().size()!=0){
            for (Pagina filho: p.getListaDeFilhos()){
                if(this.remocaoSimples(filho, chave)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean consertarRaiz(){
        Pagina raiz = paginaPrincipal;
        Pagina filhoDesbalanceado = null;
        for(Pagina filho: raiz.getListaDeFilhos()){
            if(filho.getListaDeElementos().size() < this.getOrdem()){
              filhoDesbalanceado = filho;
            }
        }
        if(filhoDesbalanceado == null){
            return false;
        }

        int posicaoFilhoDesbalanceado = raiz.getListaDeFilhos().indexOf(filhoDesbalanceado);
        if(posicaoFilhoDesbalanceado == 0){
            Pagina ajudante = raiz.getListaDeFilhos().get(posicaoFilhoDesbalanceado+1);
            if(ajudante.getListaDeElementos().size() <= this.getOrdem()){
                filhoDesbalanceado.getListaDeElementos().add(raiz.getListaDeElementos().get(posicaoFilhoDesbalanceado));

                for (Elemento elementosDoAjudante: ajudante.getListaDeElementos()) {
                    filhoDesbalanceado.getListaDeElementos().add(elementosDoAjudante);
                }
                for (Pagina paginaFilhoDoAjudante: ajudante.getListaDeFilhos()){
                    filhoDesbalanceado.getListaDeFilhos().add(paginaFilhoDoAjudante);
                }
                Collections.sort(filhoDesbalanceado.getListaDeElementos());
                this.setPaginaPrincipal(filhoDesbalanceado);
            }else{
                Elemento menorElementoDoAjudante = this.pegarMenorElemento(ajudante);
                filhoDesbalanceado.inserirElementoNaPagina(raiz.getListaDeElementos().get(posicaoFilhoDesbalanceado));
                raiz.getListaDeElementos().remove(posicaoFilhoDesbalanceado);
                this.remocaoSimples(raiz,menorElementoDoAjudante.getChave());
                raiz.inserirElementoNaPagina(menorElementoDoAjudante);
                return this.consertarRaiz();
            }
        }else{
            Pagina ajudante = raiz.getListaDeFilhos().get(posicaoFilhoDesbalanceado-1);
            if(ajudante.getListaDeElementos().size() <= this.getOrdem()){
                    ajudante.inserirElementoNaPagina(raiz.getListaDeElementos().get(posicaoFilhoDesbalanceado - 1));
                    System.out.println("FIlho desbalanceado: " + ajudante);
                    this.remocaoSimples(raiz, raiz.getListaDeElementos().get(posicaoFilhoDesbalanceado - 1).getChave());
                    for (Elemento elementosDoFilho: filhoDesbalanceado.getListaDeElementos()){
                        ajudante.inserirElementoNaPagina(elementosDoFilho);
                    }

                    System.out.println(" Ajudante: " + ajudante);
                    for (Pagina filhoQuebrado: filhoDesbalanceado.getListaDeFilhos()){
                        ajudante.getListaDeFilhos().add(filhoQuebrado);
                    }
                    raiz.getListaDeFilhos().remove(filhoDesbalanceado);
                    System.out.println("Raiz: " + raiz);
                return this.consertarRaiz();
            }else{
                Elemento maiorElementoAjudante = this.pegarMaiorElemento(ajudante);
                filhoDesbalanceado.inserirElementoNaPagina(raiz.getListaDeElementos().get(posicaoFilhoDesbalanceado-1));
                raiz.getListaDeElementos().remove(posicaoFilhoDesbalanceado-1);
                this.remocaoSimples(raiz,maiorElementoAjudante.getChave());
                raiz.inserirElementoNaPagina(maiorElementoAjudante);
                return this.consertarRaiz();
            }
        }

        return this.consertarRaiz();
    }

    @Override
    public String toString() {
        return "ArvoreB{" +
                "paginaPrincipal=" + paginaPrincipal +
                '}';
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
}
