import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculadora calc = new Calculadora();
        int controle = 0;
        
        while (controle !=1) {
        System.out.println("");
        System.out.println("");
        System.out.println("========================================");
        System.out.println("       AVALIADOR DE EXPRESSÕES AED      ");
        System.out.println("========================================");
        System.out.print("Digite a expressão infixa: ");
        String expressao = scanner.nextLine();

        
        
        // 1. Validação Sintática
        if (!calc.validaSintaxe(expressao)) {
            System.out.println("\n[ERRO] A expressão possui erros de sintaxe ou parênteses mal balanceados!");
        } else {
            // System.out.println("\n[SUCESSO] Sintaxe válida!");
    
            // 2. Conversões
            String posFixa = calc.infixaParaPosFixa(expressao);
            String preFixa = calc.infixaParaPreFixa(expressao);
    
            // System.out.println("----------------------------------------");
            System.out.println("Expressão Infixa  : " + expressao);
            System.out.println("Expressão Pós-Fixa: " + posFixa);
            System.out.println("Expressão Prefixa : " + preFixa);
    
            // 3. Avaliação do Resultado Numérico (usando a pós-fixa)
            try {
                double resultado = avaliaPosFixa(posFixa);
                System.out.println("Resultado Final   : " + resultado);
            } catch (Exception e) {
                System.out.println("\n[ERRO] Falha ao avaliar o resultado numérico: " + e.getMessage());
            }
            System.out.println("========================================");
            System.out.println("");
            System.out.println("");
        }
        }

    
        scanner.close();
}
    
    // Método auxiliar para calcular o valor da expressão pós-fixa usando a Pilha
    public static double avaliaPosFixa(String posFixa) throws Exception {
        Pilha<Double> pilhaValores = new Pilha<>();
    
        for (int i = 0; i < posFixa.length(); i++) {
            char atual = posFixa.charAt(i);
    
            // Se for operando (dígito de '0' a '9'), empilha convertido para double
            if (atual >= '0' && atual <= '9') {
                pilhaValores.push((double) (atual - '0')); // Transforma o char '5' no número 5.0
            } 
            // Se for operador, desempilha dois operandos e aplica a operação
            else if (atual == '+' || atual == '-' || atual == '*' || atual == '/' || atual == '^') {
                double b = pilhaValores.pop(); // Segundo operando
                double a = pilhaValores.pop(); // Primeiro operando
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