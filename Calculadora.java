public class Calculadora {

    public boolean validaSintaxe(String expressao) {
        Pilha<Character> pilha = new Pilha<>();
        
        // 1. Validação básica de parênteses balanceados
        for (int i = 0; i < expressao.length(); i++) {
            char atual = expressao.charAt(i);
            
            if (atual == '(') {
                pilha.push('(');
            } 
            else if (atual == ')') {
                if (pilha.isEmpty()) {
                    return false;
                }
                try {
                    pilha.pop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (!pilha.isEmpty()) {
            return false;
        }
        
        // 2. Validações de posições inválidas
        for (int i = 0; i < expressao.length() - 1; i++) {
            char atual = expressao.charAt(i);
            char proximo = expressao.charAt(i + 1);
            
            if (atual == '(' && (proximo == '+' || proximo == '*' || proximo == '/' || proximo == '^')) {
                return false;
            }
            
            if ((atual == '+' || atual == '-' || atual == '*' || atual == '/' || atual == '^') && proximo == ')') {
                return false;
            }
        }
        
        return true;
    }

    public String infixaParaPosFixa(String expressaoInfixa) {
        Pilha<Character> pilha = new Pilha<>();
        StringBuilder saida = new StringBuilder();

        for (int i = 0; i < expressaoInfixa.length(); i++) {
            char atual = expressaoInfixa.charAt(i);

            if (atual >= '0' && atual <= '9') {
                saida.append(atual);
            }
            else if (atual == '(') {
                pilha.push(atual);
            }
            else if (atual == ')') {
                try {
                    while (!pilha.isEmpty()) {
                        char topo = pilha.pop();
                        if (topo == '(') {
                            break;
                        }
                        saida.append(topo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (atual == '+' || atual == '-' || atual == '*' || atual == '/' || atual == '^') {
                try {
                    while (!pilha.isEmpty()) {
                        char topo = pilha.peek();
                        
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

        try {
            while (!pilha.isEmpty()) {
                saida.append(pilha.pop());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saida.toString();
    }

    private int prioridade(char operador) {
        if (operador == '^') {
            return 3;
        } else if (operador == '*' || operador == '/') {
            return 2;
        } else if (operador == '+' || operador == '-') {
            return 1;
        }
        return 0;
    }

    public String infixaParaPreFixa(String expressaoInfixa) {
        StringBuilder invertida = new StringBuilder();
        for (int i = expressaoInfixa.length() - 1; i >= 0; i--) {
            char c = expressaoInfixa.charAt(i);
            if (c == '(') {
                invertida.append(')');
            } else if (c == ')') {
                invertida.append('(');
            } else {
                invertida.append(c);
            }
        }

        Pilha<Character> pilha = new Pilha<>();
        StringBuilder saida = new StringBuilder();

        for (int i = 0; i < invertida.length(); i++) {
            char atual = invertida.charAt(i);

            if (atual >= '0' && atual <= '9') {
                saida.append(atual);
            }
            else if (atual == '(') {
                pilha.push(atual);
            }
            else if (atual == ')') {
                try {
                    while (!pilha.isEmpty()) {
                        char topo = pilha.pop();
                        if (topo == '(') {
                            break;
                        }
                        saida.append(topo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (atual == '+' || atual == '-' || atual == '*' || atual == '/' || atual == '^') {
                try {
                    while (!pilha.isEmpty()) {
                        char topo = pilha.peek();
                        
                        if (topo != '(' && prioridade(topo) > prioridade(atual)) {
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

        try {
            while (!pilha.isEmpty()) {
                saida.append(pilha.pop());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saida.reverse().toString();
    }

    // Avalia a expressão pós-fixa e retorna o resultado numérico
    public double avaliaPosFixa(String posFixa) throws Exception {
        Pilha<Double> pilhaValores = new Pilha<>();

        for (int i = 0; i < posFixa.length(); i++) {
            char atual = posFixa.charAt(i);

            if (atual >= '0' && atual <= '9') {
                pilhaValores.push((double) (atual - '0')); 
            } 
            else if (atual == '+' || atual == '-' || atual == '*' || atual == '/' || atual == '^') {
                double b = pilhaValores.pop(); 
                double a = pilhaValores.pop(); 
                double res = 0;

                switch (atual) {
                    case '+': res = a + b; break;
                    case '-': res = a - b; break;
                    case '*': res = a * b; break;
                    case '/': 
                        if (b == 0) throw new ArithmeticException("Divisão por zero!");
                        res = a / b; 
                        break;
                    case '^': res = Math.pow(a, b); break;
                }
                pilhaValores.push(res);
            }
        }

        return pilhaValores.pop();
    }
}