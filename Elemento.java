package Trabalho1;

public class Elemento<C extends Comparable<C>, V> implements Comparable<Elemento> {
    private C chave;
    private V valor;

    public Elemento(C chave, V valor){
        setValor(valor);
        setChave(chave);
    }

    public Integer compararChaves(C chave){
        return this.chave.compareTo(chave);
    }

    public C getChave() { return chave; }

    public void setChave(C chave) {
        if(chave == null){
            throw new IllegalArgumentException();
        }
        this.chave = chave;
    }

    public V getValor() { return valor; }

    public void setValor(V valor) {
        if(valor == null) {
            throw new IllegalArgumentException();
        }
        this.valor = valor;
    }

    public Elemento criarElemento(C chave, V valor){
        return new Elemento<C, V>(chave,valor);
    }

    @Override
    public String toString() {
        return "Elemento{" +
                " " + chave +
                " " + valor +
                '}';
    }

    @Override
    public int compareTo(Elemento o) {
        return this.compararChaves((C) o.getChave());
    }
}
