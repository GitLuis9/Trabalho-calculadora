public class Calculadora {

    public boolean validaSintaxe(String expressao) {
    // Cria uma pilha própria para guardar os parênteses (ou caracteres)
    Pilha<Character> pilha = new Pilha<>();
    
    // 1. Validação básica de parênteses balanceados
    for (int i = 0; i < expressao.length(); i++) {
        char atual = expressao.charAt(i);
        
        // Se for parêntese de abertura
        if (atual == '(') {
            pilha.push('(');
        } 
        // Se for parêntese de fechamento
        else if (atual == ')') {
            // Se a pilha estiver vazia, tem um ')' sobrando sem '(' correspondente
            if (pilha.vazia()) {
                return false;
            }
            pilha.pop();
        }
    }
    
    // Se no final a pilha não estiver vazia, faltou fechar algum parêntese
    if (!pilha.vazia()) {
        return false;
    }
    
    // 2. Validações de posições inválidas (ex: operador logo após abertura de parêntese incorreto, 
    // ou fechar parêntese sem operando dentro, como no seu exemplo `(+5)`)
    for (int i = 0; i < expressao.length() - 1; i++) {
        char atual = expressao.charAt(i);
        char proximo = expressao.charAt(i + 1);
        
        // Exemplo de erro: abrir parêntese e colocar um operador logo em seguida (exceto menos unário, 
        // mas como são dígitos de 0 a 9, `(+` é claramente um erro de sintaxe)
        if ( atual == '('  && (proximo == '+' || proximo == '*' || proximo == '/' || proximo == '^')) {
            return false;
        }
        
        // Exemplo de erro: fechar parêntese logo após um operador `(3+ )`
        if (( atual == '+' || atual == '-' || atual == '*' || atual == '/' || atual == '^') && proximo == ')') {
            return false;
        }
    }
    
    return true; // Passou nas validações básicas
}

public String infixaParaPosFixa(String expressaoInfixa) {
        Pilha<Character> pilha = new Pilha<>();
        StringBuilder saida = new StringBuilder();

        for (int i = 0; i < expressaoInfixa.length(); i++) {
            char atual = expressaoInfixa.charAt(i);

            // 1. Se for operando (dígito de '0' a '9'), vai direto para a saída
            if (atual >= '0' && atual <= '9') {
                saida.append(atual);
            }
            // 2. Se for parêntese de abertura, faz push na pilha
            else if (atual == '(') {
                pilha.push(atual);
            }
            // 3. Se for parêntese de fechamento, desempilha até achar o '('
            else if (atual == ')') {
                try {
                    while (!pilha.isEmpty()) {
                        char topo = pilha.pop();
                        if (topo == '(') {
                            break; // Encontrou o parêntese de abertura, para
                        }
                        saida.append(topo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 4. Se for um operador (+, -, *, /, ^)
            else if (atual == '+' || atual == '-' || atual == '*' || atual == '/' || atual == '^') {
                try {
                    while (!pilha.isEmpty()) {
                        char topo = pilha.peek();
                        
                        // Compara a precedência do operador atual com o topo da pilha
                        if (topo != '(' && prioridade(topo) >= prioridade(atual)) {
                            saida.append(pilha.pop());
                        } else {
                            break;
                        }
                    }
                    pilha.push(atual);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 5. Esvazia o que sobrou na pilha para a string de saída
        try {
            while (!pilha.isEmpty()) {
                saida.append(pilha.pop());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saida.toString();
    }

    // Função auxiliar para definir a ordem de precedência dos operadores
    private int prioridade(char operador) {
        if (operador == '^') {
            return 3; // Maior precedência
        } else if (operador == '*' || operador == '/') {
            return 2; // Média precedência
        } else if (operador == '+' || operador == '-') {
            return 1; // Menor precedência
        }
        return 0; // Para parênteses ou outros
    }
}