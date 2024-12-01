/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fractionoperations;

import fraction.Fraction;
import java.util.Scanner;

/**
 *
 * @author david
 */
public class FractionOperations {

    /**
     * @param args the command line arguments
     */
    private static final String[] units = {
        "cero", "un", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", ""
    };
    

    private static final String[] tens = {
        "", "diez", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa", ""
    };
    
    private static final String[] specialTens = {
        "", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve", ""
    };

    private static final String[] hundreds = {
        "", "cien", "doscientos", "trescientos", "cuatroscientos", "quinientos", "seiscientos", "setescientos", "ochocientos", "novecientos", "" 
    };
    
    private static final String[] denominatorSpecial = {
        "ceroavos", "un", "medio", "tercio", "cuarto", "quinto", "sexto", "septimo", "octavo", "noveno", "decimo"
    };

    public static void main(String[] args) {
        FractionOperations fractionsOperations = new FractionOperations();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Por favor, introduce tu operación en palabras (e.g., 'un medio mas un cuarto'):");
        
        String inputLine = scanner.nextLine().trim().toLowerCase();
        
        // Procesar la entrada y realizar la operación
        String result = fractionsOperations.processInput(inputLine);

        // Mostrar el resultado al usuario
        System.out.println("Resultado: " + result);
    }

    public String processInput(String inputLine) {
        
        // Dividir la entrada en partes para análisis
        String[] parts = inputLine.split(" ");
        int aux = 0;
        
        // Variables para almacenar las fracciones y la operacion
        Fraction fraction1 = new Fraction(); // Inicializar con valores predeterminados
        Fraction fraction2 = new Fraction(); // Inicializar con valores predeterminados
        String operation = "";
        
        try{
            for(int i = 0; i <= parts.length - 1; i++){
                int error = wordToNumber(parts[i]);
                if(error == -1){
                    System.out.println("Palabra erronea: " + parts[i]);
                    aux = error;
                }
                if(i == parts.length - 1 && aux == -1){
                    return "Operación cancelada debido a una palabra erronea"; // Manejo del error
                }
            }
        
        
            if(parts[1].equals("y") && parts[6].equals("y")){
                fraction1.setNumerator((wordToNumber(parts[0])) + wordToNumber(parts[2]));
                fraction1.setDenominator(wordToNumber(parts[3]));
                operation = parts[4];
                fraction2.setNumerator((wordToNumber(parts[5])) + wordToNumber(parts[7]));
                fraction2.setDenominator(wordToNumber(parts[8]));
            }
            else if(parts[1].equals("y")){
                fraction1.setNumerator((wordToNumber(parts[0])) + wordToNumber(parts[2]));
                fraction1.setDenominator(wordToNumber(parts[3]));
                operation = parts[4];
                fraction2.setNumerator((wordToNumber(parts[5])));
                fraction2.setDenominator(wordToNumber(parts[6]));
            }
            else if(parts[4].equals("y")){
                fraction1.setNumerator((wordToNumber(parts[0])));
                fraction1.setDenominator(wordToNumber(parts[1]));
                operation = parts[2];
                fraction2.setNumerator((wordToNumber(parts[3])) + wordToNumber(parts[5]));
                fraction2.setDenominator(wordToNumber(parts[6]));
            }
            else{
                fraction1.setNumerator(wordToNumber(parts[0]));
                fraction1.setDenominator(wordToNumber(parts[1]));
                operation = parts[2];
                fraction2.setNumerator(wordToNumber(parts[3]));
                fraction2.setDenominator(wordToNumber(parts[4]));
            }

            Fraction result = new Fraction();
            switch (operation) {
                case "mas":
                    result = fraction1.add(fraction2);
                    break;
                case "menos":
                    result = fraction1.subtract(fraction2);
                    break;
                case "por":
                    result = fraction1.multiply(fraction2);
                    break;
                case "entre":
                    result = fraction1.divide(fraction2);
                    break;
                default:
                    return "Operación desconocida. Asegúrate de usar 'mas', 'menos', 'por', o 'entre'.";
            }

            // Convertir el resultado a palabras
            String resultInWords = fractionToWords(result);
            return resultInWords;
        } catch (Exception e) { 
            return "Operación cancelada debido a un error.";
        }
    }
    
    private int wordToNumber(String word) {
        for(int i = 0; i <= 10; i++){
            String specialTensDenominator = specialTens[i]+"avo";
            String numberTwenty = "veinti"+units[i];
            String numberTwentyDenominator = "veinti"+units[i]+"avo";
            String compoundDenominator = tens[i]+"i"+units[i]+"avo";
            String tensDenominator = tens[i]+"avo";
            
            if(word.equals(units[i])){
                return i;
            }
            
            if(word.equals(specialTens[i]) || word.equals(specialTensDenominator) || word.equals(specialTensDenominator+"s")){
                return i + 10;
            }
            
            if(word.equals(tens[i]) || word.equals(tensDenominator) || word.equals(tensDenominator+"s")){
                return i*10;
            }
            
            if(word.equals(numberTwenty) || word.equals(numberTwentyDenominator) || word.equals(numberTwentyDenominator+"s")){
                return 20 + i;
            }
            
            if(word.equals(denominatorSpecial[i]) || word.equals(denominatorSpecial[i]+"s")){
                return i;
            }
            
            if(word.equals(compoundDenominator) || word.equals(compoundDenominator+"s")){
                return i*10 + i;
            }
            
            if(word.equals("y") || word.equals("mas") || word.equals("menos") || word.equals("por") || word.equals("entre")){
                return -2;
            }
            
        }
        return -1;
    }
    
    private String fractionToWords(Fraction fraction) {
        String numeratorWords = numberToWordsNumerator(fraction.getNumerator());
        String denominatorWords = numberToWordsDenumerator(fraction.getDenominator());

        // Construir la representación en palabras de la fracción
        return numeratorWords + " " + denominatorWords;
    }
    
    private String numberToWordsNumerator(int number) {
        
        if (number == 0) {
            return units[0];
        }

        if (number < 10) {
            return units[number];
        } else if (number < 20) {
            return specialTens[number - 10];
        } else if (number > 20 && number < 30) {
            return "veinti"+units[number - 20]+"avo";
        } else if (number < 100) {
            return (number % 10 == 0) ? tens[number / 10] : tens[number / 10] + " y " + units[number % 10];
        } else if (number < 1000) {
            return (number == 100) ? "cien" : hundreds[number / 100] + " " + numberToWordsNumerator(number % 100);
        } else if (number < 1000000) {
            int thousands = number / 1000;
            String thousandPart = (thousands == 1) ? "mil" : numberToWordsNumerator(thousands) + " mil";
            int remainder = number % 1000;
            return thousandPart + (remainder > 0 ? " " + numberToWordsNumerator(remainder) : "");
        }
        // Extiende aquí para números más grandes si es necesario.
        return "número demasiado grande";
    }
    
    private String numberToWordsDenumerator(int number) {
        
        String special = isDenominatorSpecial(number);
        if (!special.isEmpty()) {
            return special; // Retorna la representación especial si existe
        }

        // Preparar la base para construir la representación en palabras
        String wordRepresentation = "";

        if (number == 0) {
            return "\nNo se pueden tener ceros en el denominador";
        } else if (number < 10) {
            wordRepresentation = units[number] + "avos";
        } else if (number < 20) {
            wordRepresentation = specialTens[number - 10] + "avos";
        } else if (number >= 20 && number < 30) {
            wordRepresentation = "veinti" + units[number % 20] + "avos";
        } else if (number < 100) {
            wordRepresentation = (number % 10 == 0) ? tens[number / 10] + "avos" : tens[number / 10] + "i" + units[number % 10] + "avos";
        } else if (number == 100) {
            wordRepresentation = "centésimos";
        } else if (number < 1000) {
            wordRepresentation = hundreds[number / 100] + (number % 100 != 0 ? numberToWordsDenumerator(number % 100).replace("avos", "") : "") + "avos";
        } else if (number < 1000000) {
            int thousands = number / 1000;
            String thousandPart = (thousands == 1) ? "mil" : numberToWordsNumerator(thousands) + "mil";
            int remainder = number % 1000;
            wordRepresentation = thousandPart + (remainder > 0 ? numberToWordsDenumerator(remainder).replace("avos", "") : "") + "avos";
        } else {
            return "número demasiado grande";
        }

        // Asegurarse de que "avo" se añade solo una vez al final
        if (!wordRepresentation.endsWith("avos")) {
            wordRepresentation += "avos";
        }

        return wordRepresentation;
    }

    
    private String isDenominatorSpecial(int number) {
        try{
            if (number == 0) {
                return "\nNo se pueden tener ceros en el denominador";
            }
            if (number <= 10) {
                return denominatorSpecial[number];
            }
            return "";
        } catch (Exception e) { 
            return "\nOperación cancelada debido a un error.";
        }
    }
}
