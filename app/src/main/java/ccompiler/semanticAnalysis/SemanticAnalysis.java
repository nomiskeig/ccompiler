package ccompiler.semanticAnalysis;

import ccompiler.CompilerException;
import ccompiler.parser.AEProgram;

public class SemanticAnalysis {
    private AEProgram program;
    private MethodEnvironment methods;
    private ObjectEnvironment objects;
    public SemanticAnalysis(AEProgram program) {
        this.program = program;
        this.methods = new MethodEnvironment();
        this.objects = new ObjectEnvironment();


    }


    public void analyse() throws CompilerException {

    }


    private void collectMethodsAndObjects() throws CompilerException {
        MethodAndObjectCollector collector = new MethodAndObjectCollector(this.methods, this.objects);
        this.program.acceptVisitor(collector);

    }
}
