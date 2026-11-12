import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculadora calc = new Calculadora();
        
        // Loop infinito para testes rápidos e fluidos na apresentação
        while (true) {
            System.out.println("");
            System.out.println("");
            System.out.println("========================================");
            System.out.println("       AVALIADOR DE EXPRESSÕES AED      ");
            System.out.println("========================================");
            System.out.print("Digite a expressão infixa: ");
            String expressao = scanner.nextLine();

            // Caso queira sair digitando 'sair' ou deixar vazio, opcional, 
            // mas o Ctrl + C encerra o programa a qualquer momento.
            if (expressao.equalsIgnoreCase("sair")) {
                break;
            }
            
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
        
                // 3. Avaliação do Resultado Numérico (usando a pós-fixa na Calculadora)
                try {
                    double resultado = calc.avaliaPosFixa(posFixa);
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
}