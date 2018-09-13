package net.dusktech.com.prototipoa;

/**
 * Created by INTEL-COREi7 on 23/01/2018.
 */

public class Questions {
    public String mQuestions[] = {
            "¿Cuales son los elementos que hacen posibles la transmision de datos entre los elementos del computador?",
            "¿En la piramide de los niveles de dispositivo cual es el nivel de complejidad inferior?",
            "¿En que nivel se describe la mayoria de las operaciones basicas del computador?",
            " Al modelo de arquitectura de computadores basado en “programas almacenados” que permite que las instrucciones se mantengan residentes en una memoria listas para ser leídas y ejecutadas cuando sea necesario, se le denomina:",
            "La unidad central de proceso está compuesta por:",
            " A la secuencia de instrucciones entendibles por los ordenadores y que permite realización de las acciones o tareas para las que han sido creadas se les denomina:",
            "Si atendemos a la forma de ejecución, los lenguajes de programación pueden ser:"
    };

    public String mChoices[][] = {
            {"Address Register,Data Register,Instruction Register(AD, DR, IR)", "Bus de direccion, control, datos", "Program Counter, Stack Pointer (PC,SP)","Test1234"
            },
            {"Micromaquina", "Dispositivos electronicos", "Maquina operativa","Test1234"},
            {"Usuario","Logica Digital","Micromaquina","Test1234"},
            {"Arquitectura de programa almacenado","Arquitectura estándar","Arquitectura de Von Neumann","Arquitectura de Babbage"},
            {"La memoria principal y la memoria secundaria","La unidad de control y las interfaces de entrada/salida","La unidad de control, la unidad aritmético-lógica y un conjunto de registros","La unidad aritmético-lógica, la memoria RAM y el microprocesador"},
            {"Algoritmo","Programa","Diagrama de flujo","Organigrama"},
            {"Orientados a objetos o no orientados a objetos","De bajo nivel o de alto nivel","Compilados o interpretados","Imperativos o lenguajes máquina"}
    };

    private String mCorrectAnswers[] = {
            "Address Register,Data Register,Instruction Register(AD, DR, IR)",
            "Micromaquina",
            "Usuario",
            "Arquitectura de programa almacenado",
            "La memoria principal y la memoria secundaria",
            "Algoritmo",
            "Orientados a objetos o no orientados a objetos"
    };


    public String getQuestion(int a) {
        String question = mQuestions[a];
        return question;
    }

    public String getChoice1(int a) {
        String choice0 = mChoices[a][0];
        return choice0;
    }


    public String getChoice2(int a) {
        String choice1 = mChoices[a][1];
        return choice1;
    }

    public String getChoice3(int a) {
        String choice2 = mChoices[a][2];
        return choice2;
    }

    public String getChoice4(int a) {
        String choice3 = mChoices[a][3];
        return choice3;
    }


    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers[a];
        return answer;
    }
}
