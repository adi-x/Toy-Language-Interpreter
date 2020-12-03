package app.view;

import app.controller.Controller;
import app.model.dictionary.IDictionary;
import app.model.dictionary.MyDictionary;
import app.model.expression.ArithmeticExpression;
import app.model.expression.ArithmeticOperationType;
import app.model.expression.ValueExpression;
import app.model.expression.VariableExpression;
import app.model.list.IList;
import app.model.list.MyList;
import app.model.program_state.ProgramState;
import app.model.stack.IStack;
import app.model.stack.MyStack;
import app.model.statement.*;
import app.model.type.BooleanType;
import app.model.type.IntegerType;
import app.model.type.StringType;
import app.model.value.IValue;
import app.model.value.IntegerValue;
import app.model.value.StringValue;
import app.repository.IRepository;
import app.repository.Repository;

import java.io.BufferedReader;
import java.util.Scanner;

public class View {
    Controller controller;
    public View(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("Options:");
            System.out.println("  1. Integer v; v = 2; print(v);");
            System.out.println("  2. Integer a; Integer b; a = 2 + 3 * 5; b = a + 1; print(b);");
            System.out.println("  3. Example 3: Boolean a; Integer v; a = true; (If a Then v=2 Else v=3); print(v);");
            System.out.println("  4. Example 4: File open, read, close and strings");
            System.out.println("  5. Stop \n");
            System.out.println("Enter option number: ");
            String option = scan.nextLine();
            int optionNumber = Integer.parseInt(option);
            switch (optionNumber) {
                case 1:
                    Example1();
                    break;
                case 2:
                    Example2();
                    break;
                case 3:
                    Example3();
                    break;
                case 4:
                    Example4();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void Example1() {
        //int v; v=2; Print(v) is represented as:
        IStatement mainStatement = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntegerType()),
            new CompoundStatement(
                new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                new PrintStatement(new VariableExpression("v"))
            )
        );
        runStatement(mainStatement);
    }

    private void Example2() {
        // int a;int b; a=2+3*5;b=a+1;Print(b) is represented as:
        IStatement mainStatement = new CompoundStatement(
            new VariableDeclarationStatement("a", new IntegerType()),
            new CompoundStatement(
                new VariableDeclarationStatement("b", new IntegerType()),
                new CompoundStatement(
                    new AssignmentStatement("a",
                        new ArithmeticExpression(
                            new ValueExpression(new IntegerValue(2)),
                            new ArithmeticExpression(
                                new ValueExpression(new IntegerValue(3)),
                                new ValueExpression(new IntegerValue(5)),
                                ArithmeticOperationType.Multiplication
                            ),
                            ArithmeticOperationType.Addition
                        )
                    ),
                    new CompoundStatement(
                        new AssignmentStatement("b",
                            new ArithmeticExpression(
                                new VariableExpression("a"),
                                new ValueExpression(new IntegerValue(1)),
                                ArithmeticOperationType.Addition
                            )
                        ),
                        new PrintStatement(new VariableExpression("b"))
                    )
                )
            )
        );
        runStatement(mainStatement);
    }

    private void Example3() {
        // bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStatement mainStatement = new CompoundStatement(
            new VariableDeclarationStatement("a",new BooleanType()),
            new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                    new AssignmentStatement("a", new ValueExpression(new IntegerValue(10))),
                    new CompoundStatement(
                        new ConditionalStatement(
                            new VariableExpression("a"),
                            new AssignmentStatement("v",new ValueExpression(new IntegerValue(2))),
                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                        ),
                        new PrintStatement(new VariableExpression("v"))
                    )
                )
            )
        );
        runStatement(mainStatement);
    }
    private void Example4() {
        // string varf; x
        // varf="test.in"; x
        // openRFile(varf); x
        // int varc;
        // readFile(varf,varc);print(varc);
        // readFile(varf,varc);print(varc)
        // closeRFile(varf)
        IStatement mainStatement = new CompoundStatement(
            new VariableDeclarationStatement("varf",new StringType()),
            new CompoundStatement(
                new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                new CompoundStatement(
                    new OpenReadFileStatement(new VariableExpression("varf")),
                    new CompoundStatement(
                        new VariableDeclarationStatement("varc",new IntegerType()),
                        new CompoundStatement(
                            new ReadFileStatement(new VariableExpression("varf"), "varc"),
                            new CompoundStatement(
                                new PrintStatement(new VariableExpression("varc")),
                                new CompoundStatement(
                                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                    new CompoundStatement(
                                        new PrintStatement(new VariableExpression("varc")),
                                        new CloseReadFileStatement(new VariableExpression("varf"))
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );
        runStatement(mainStatement);
    }

    private void runStatement(IStatement mainStatement) {
        IStack <IStatement> executionStack = new MyStack<>();
        IDictionary <String, IValue> symbolTable = new MyDictionary<>();
        IDictionary <String, BufferedReader> fileTable = new MyDictionary<>();
        IDictionary <Integer, IValue> heapTable = new MyDictionary<>();
        IList<IValue> output = new MyList<>();
        ProgramState state = new ProgramState(executionStack, symbolTable, output, fileTable, heapTable, mainStatement);
        IRepository repository = new Repository(state, "log.txt");
        controller.setRepository(repository);
        controller.setDisplay(true);
        try {
            controller.run();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
