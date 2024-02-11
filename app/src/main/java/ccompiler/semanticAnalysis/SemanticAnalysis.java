package ccompiler.semanticAnalysis;

import ccompiler.CompilerException;
import ccompiler.parser.AEProgram;
import ccompiler.semanticAnalysis.typechecking.TypeChecker;
import ccompiler.semanticAnalysis.typechecking.TypeHierarchy;

public class SemanticAnalysis {
    private AEProgram program;
    private MethodEnvironment methods;
    private ObjectEnvironment objects;
    private TypeHierarchy hierarchy;
    public SemanticAnalysis(AEProgram program) {
        this.program = program;
        this.methods = new MethodEnvironment();
        this.objects = new ObjectEnvironment();
        this.hierarchy  =new TypeHierarchy();


    }


    public void analyse() throws CompilerException {

    }


    private void collectMethodsAndObjects() throws CompilerException {
        MethodAndObjectCollector collector = new MethodAndObjectCollector(this.methods, this.objects, this.hierarchy);
        this.program.acceptVisitor(collector);
        TypeChecker tc = new TypeChecker(this.methods, this.objects, this.hierarchy);
        this.program.acceptVisitor(tc);

    }
}
