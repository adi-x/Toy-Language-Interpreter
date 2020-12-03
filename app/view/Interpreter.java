package app.view;

import app.controller.Controller;
import app.model.dictionary.IndexedDictionary;
import app.model.dictionary.MyDictionary;
import app.model.expression.*;
import app.model.list.MyList;
import app.model.program_state.ProgramState;
import app.model.stack.MyStack;
import app.model.statement.*;
import app.model.type.BooleanType;
import app.model.type.IntegerType;
import app.model.type.ReferenceType;
import app.model.type.StringType;
import app.model.value.BooleanValue;
import app.model.value.IValue;
import app.model.value.IntegerValue;
import app.model.value.StringValue;
import app.repository.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Interpreter {
    public static void clearFile(String filename) {
        try {
            FileWriter file = new FileWriter(filename);
            BufferedWriter fileBuffer = new BufferedWriter(file);
            PrintWriter filePrinter = new PrintWriter(fileBuffer);
            filePrinter.close();
            fileBuffer.close();
            file.close();
        } catch (Exception e) {
            System.out.println("Could not clear file " + filename + ": " + e.getMessage());
        }
    }
    public static void typeCheck(String Identifier, IStatement statement) throws Exception{
        try {
            statement.typeCheck(new MyDictionary<>());
        } catch (Exception error){
            throw new Exception("TypeCheck Error in " + Identifier + ": " + error.getMessage());
        }
    }
    public static void main(String[] args) throws Exception {

        IStatement example1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        typeCheck("Example 1", example1);
        ProgramState state1 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example1);
        Repository repository1 = new Repository(state1, "log1.txt");
        clearFile("log1.txt");
        Controller service1 = new Controller(repository1);

        IStatement example2 = new CompoundStatement(
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
        typeCheck("Example 2", example2);
        ProgramState state2 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example2);
        Repository repository2 = new Repository(state2, "log2.txt");
        clearFile("log2.txt");
        Controller service2 = new Controller(repository2);

        IStatement example3 = new CompoundStatement(
                new VariableDeclarationStatement("a",new BooleanType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntegerType()),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                                new CompoundStatement(
                                        new ConditionalStatement(
                                                new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(11)), RelationalOperationType.Smaller),
                                                new AssignmentStatement("v",new ValueExpression(new IntegerValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
        typeCheck("Example 3", example3);
        ProgramState state3 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example3);
        Repository repository3 = new Repository(state3, "log3.txt");
        clearFile("log3.txt");
        Controller service3 = new Controller(repository3);

        IStatement example4 = new CompoundStatement(
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
        typeCheck("Example 4", example4);
        ProgramState state4 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example4);
        Repository repository4 = new Repository(state4, "log4.txt");
        clearFile("log4.txt");
        Controller service4 = new Controller(repository4);

        IStatement example5 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntegerType())),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntegerType()))),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );
        typeCheck("Example 5", example5);
        ProgramState state5 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example5);
        Repository repository5 = new Repository(state5, "log5.txt");
        clearFile("log5.txt");
        Controller service5 = new Controller(repository5);

        IStatement example6 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntegerType())),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntegerType()))),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(
                                                        new HeapReadExpression(new HeapReadExpression(new VariableExpression("a"))),
                                                        new ValueExpression(new IntegerValue(5)),
                                                        ArithmeticOperationType.Addition
                                                ))
                                        )
                                )
                        )
                )
        );
        typeCheck("Example 6", example6);
        ProgramState state6 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example6);
        Repository repository6 = new Repository(state6, "log6.txt");
        clearFile("log6.txt");
        Controller service6 = new Controller(repository6);

        IStatement example7 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntegerType())),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new HeapWriteStatement("v", new ValueExpression(new IntegerValue(30))),
                                        new PrintStatement(new ArithmeticExpression(
                                                new HeapReadExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntegerValue(5)),
                                                ArithmeticOperationType.Addition
                                        ))
                                )
                        )
                )
        );
        typeCheck("Example 7", example7);
        ProgramState state7 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example7);
        Repository repository7 = new Repository(state7, "log7.txt");
        clearFile("log7.txt");
        Controller service7 = new Controller(repository7);

        IStatement example8 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntegerType())),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntegerType()))),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(30))),
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );
        typeCheck("Example 8", example8);
        ProgramState state8 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example8);
        Repository repository8 = new Repository(state8, "log8.txt");
        clearFile("log8.txt");
        Controller service8 = new Controller(repository8);

        IStatement example9 = new CompoundStatement(
                new VariableDeclarationStatement("v",new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))),
                        new CompoundStatement(
                                new RepetitiveWhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntegerValue(0)),
                                                RelationalOperationType.Bigger
                                        ),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v", new ArithmeticExpression(
                                                        new VariableExpression("v"),
                                                        new ValueExpression(new IntegerValue(1)),
                                                        ArithmeticOperationType.Subtraction
                                                ))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
        typeCheck("Example 9", example9);
        ProgramState state9 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example9);
        Repository repository9 = new Repository(state9, "log9.txt");
        clearFile("log9.txt");
        Controller service9 = new Controller(repository9);

        IStatement example10 = new CompoundStatement(
                new VariableDeclarationStatement("v",new IntegerType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                                new CompoundStatement(
                                    new HeapAllocationStatement("a", new ValueExpression(new IntegerValue(22))),
                                    new CompoundStatement(
                                            new ForkStatement(new CompoundStatement(
                                                    new HeapWriteStatement("a", new ValueExpression(new IntegerValue(30))),
                                                    new CompoundStatement(
                                                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                                            new CompoundStatement(
                                                                    new PrintStatement(new VariableExpression("v")),
                                                                    new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                                            )
                                                    )
                                            )),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("v")),
                                                    new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                            )
                                    )
                                )
                        )
                )
        );
        typeCheck("Example 10", example10);
        ProgramState state10 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new IndexedDictionary<>(), example10);
        Repository repository10 = new Repository(state10, "log10.txt");
        clearFile("log10.txt");
        Controller service10 = new Controller(repository10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", example1.toString(), service1));
        menu.addCommand(new RunExample("2", example2.toString(), service2));
        menu.addCommand(new RunExample("3", example3.toString(), service3));
        menu.addCommand(new RunExample("4", example4.toString(), service4));
        menu.addCommand(new RunExample("5", example5.toString(), service5));
        menu.addCommand(new RunExample("6", example6.toString(), service6));
        menu.addCommand(new RunExample("7", example7.toString(), service7));
        menu.addCommand(new RunExample("8", example8.toString(), service8));
        menu.addCommand(new RunExample("9", example9.toString(), service9));
        menu.addCommand(new RunExample("10", example10.toString(), service10));
        menu.show();

    }
}
