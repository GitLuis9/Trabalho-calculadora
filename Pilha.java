public class Pilha<T> {

    private Celula<T> topo;
    
    // Construtor para inicializar a pilha vazia
    public Pilha() {
        this.topo = null;
    }
    
    public void push(T item) {
        // 1. Cria uma nova célula na memória
        Celula<T> nova = new Celula<>();
        nova.item = item;
        
        // 2. Faz a nova célula apontar para o antigo topo
        nova.prox = this.topo;
        
        // 3. Atualiza o topo para ser a nova célula
        this.topo = nova;
    }
    
    public T pop() throws Exception {
        // Se a pilha estiver vazia, não tem o que remover (lança um erro)
        if (isEmpty()) {
            throw new Exception("Erro: A pilha está vazia!");
        }
        
        // 1. Guarda o item que está no topo atual para poder retorná-lo
        T itemRetornado = this.topo.item;
        
        // 2. O topo passa a ser o próximo elemento (o de baixo)
        this.topo = this.topo.prox;
        
        // 3. Devolve o item guardado
        return itemRetornado;
    }
    
    public boolean isEmpty() {
        return (this.topo == null);
    }

    public T peek() throws Exception {
    if (isEmpty()) {
        throw new Exception("Erro: A pilha está vazia!");
    }
    return this.topo.item;
    }

}