package ccompiler.parser;

import ccompiler.CompilerException;
import ccompiler.parser.expression.AEAssignment;
import ccompiler.parser.expression.AEDivide;
import ccompiler.parser.expression.AEEnclosedExpression;
import ccompiler.parser.expression.AEEquals;
import ccompiler.parser.expression.AEExpression;
import ccompiler.parser.expression.AEExpressionBlock;
import ccompiler.parser.expression.AEFalse;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.parser.expression.AEIfElse;
import ccompiler.parser.expression.AEIsVoid;
import ccompiler.parser.expression.AELet;
import ccompiler.parser.expression.AEMinus;
import ccompiler.parser.expression.AEMultiply;
import ccompiler.parser.expression.AENew;
import ccompiler.parser.expression.AENot;
import ccompiler.parser.expression.AEPlus;
import ccompiler.parser.expression.AESmaller;
import ccompiler.parser.expression.AETrue;
import ccompiler.parser.expression.AEWhile;
import ccompiler.parser.feature.AEAttribute;
import ccompiler.parser.feature.AEFeature;
import ccompiler.parser.feature.AEFunction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

public class ASTPrinter implements ASTVisitor {

    private class LabelEdge extends DefaultEdge {
        private String label;

        public LabelEdge(String label) {
            super();
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }

    }

    private Graph<GraphElement, LabelEdge> graph = new SimpleGraph<>(LabelEdge.class);

    public ASTPrinter() {
    }

    @Override
    public void visitProgram(AEProgram program) throws CompilerException {
        graph.addVertex(program);
        for (AElement e : program.getClasses()) {
            graph.addVertex(e);
            graph.addEdge(program, e, new LabelEdge(""));
            e.acceptVisitor(this);
        }

    }

    @Override
    public void visitClass(AEClass c) throws CompilerException {
        graph.addVertex(c);
        for (AEFeature f : c.getFeatures()) {
            graph.addVertex(f);
            graph.addEdge(c, f, new LabelEdge(""));
            f.acceptVisitor(this);
        }
    }

    @Override
    public void visitFunction(AEFunction function) throws CompilerException {
        graph.addVertex(function);
        for (AEFormal formal : function.getFormals()) {
            graph.addVertex(formal);
            graph.addEdge(function, formal, new LabelEdge("argument"));
            formal.acceptVisitor(this);
        }
        AEExpression expression = function.getExpression();
        graph.addVertex(expression);
        graph.addEdge(function, expression, new LabelEdge("body"));
        expression.acceptVisitor(this);

    }

    @Override
    public void visitAttribute(AEAttribute attribute) throws CompilerException {
        graph.addVertex(attribute);
        AEExpression expression = attribute.getExpression();
        if (expression != null) {
            graph.addVertex(expression);
            graph.addEdge(attribute, expression, new LabelEdge("<-"));
            expression.acceptVisitor(this);

        }
    }

    public void exportToDOT(File file) {
        DOTExporter<GraphElement, LabelEdge> exporter = new DOTExporter<>(v -> String.valueOf(v.hashCode()));

        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getGraphRepresentation()));
            return map;
        });
        exporter.setEdgeAttributeProvider((e) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(e.getLabel()));
            return map;
        });
        try {
            Writer writer = new FileWriter(file);
            exporter.exportGraph(this.graph, writer);
        } catch (IOException e) {
            System.out.println("could not print the file.\nReason: " + e.getMessage());
        }
        ;

    }

    @Override
    public void visitFormal(AEFormal formal) {
        graph.addVertex(formal);
    }

    @Override
    public void visitIfElse(AEIfElse ifElse) throws CompilerException {
        graph.addVertex(ifElse);
        AEExpression cond = ifElse.getCondExpression();
        graph.addVertex(cond);
        graph.addEdge(ifElse, cond, new LabelEdge("cond"));
        cond.acceptVisitor(this);
        AEExpression then = ifElse.getThenExpression();
        graph.addVertex(then);
        graph.addEdge(ifElse, then, new LabelEdge("then"));
        then.acceptVisitor(this);
        AEExpression e = ifElse.getElseExpression();
        graph.addVertex(e);
        graph.addEdge(ifElse, e, new LabelEdge("else"));
        e.acceptVisitor(this);
    }

    @Override
    public void visitEnclosedExpression(AEEnclosedExpression enclosedExpression) throws CompilerException {
        graph.addVertex(enclosedExpression);
        AEExpression expression = enclosedExpression.getExpression();
        graph.addVertex(expression);
        graph.addEdge(enclosedExpression, expression, new LabelEdge(""));
        expression.acceptVisitor(this);

    }

    @Override
    public void visitPlus(AEPlus plus) throws CompilerException {
        graph.addVertex(plus);
        AEExpression leftSide = plus.getLeftSide();
        AEExpression rightSide = plus.getRightSide();
        graph.addVertex(leftSide);
        graph.addVertex(rightSide);
        graph.addEdge(plus, leftSide, new LabelEdge(""));
        graph.addEdge(plus, rightSide, new LabelEdge(""));
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
    }

    @Override
    public void visitMinus(AEMinus minus) throws CompilerException {
        graph.addVertex(minus);
        AEExpression leftSide = minus.getLeftSide();
        AEExpression rightSide = minus.getRightSide();
        graph.addVertex(leftSide);
        graph.addVertex(rightSide);
        graph.addEdge(minus, leftSide, new LabelEdge(""));
        graph.addEdge(minus, rightSide, new LabelEdge(""));
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
    }

    @Override
    public void visitMultiply(AEMultiply multiply) throws CompilerException {
        graph.addVertex(multiply);
        AEExpression leftSide = multiply.getLeftSide();
        AEExpression rightSide = multiply.getRightSide();
        graph.addVertex(leftSide);
        graph.addVertex(rightSide);
        graph.addEdge(multiply, leftSide, new LabelEdge(""));
        graph.addEdge(multiply, rightSide, new LabelEdge(""));
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
    }

    @Override
    public void visitDivide(AEDivide divide) throws CompilerException {
        graph.addVertex(divide);
        AEExpression leftSide = divide.getLeftSide();
        AEExpression rightSide = divide.getRightSide();
        graph.addVertex(leftSide);
        graph.addVertex(rightSide);
        graph.addEdge(divide, leftSide, new LabelEdge(""));
        graph.addEdge(divide, rightSide, new LabelEdge(""));
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
    }

    @Override
    public void visitWhile(AEWhile whileE) throws CompilerException {
        
        graph.addVertex(whileE);
        AEExpression cond = whileE.getCondExpression();
        AEExpression body = whileE.getBodyExpression();
        graph.addVertex(cond);
        graph.addVertex(body);
        graph.addEdge(whileE, cond, new LabelEdge("cond"));
        graph.addEdge(whileE, body, new LabelEdge("body"));
        cond.acceptVisitor(this);
        body.acceptVisitor(this);

    }

    @Override
    public void visitExpressionBlock(AEExpressionBlock expressionBlock) throws CompilerException {
        graph.addVertex(expressionBlock);
        for (AEExpression expr : expressionBlock.getExpressions()) {
            graph.addVertex(expr);
            graph.addEdge(expressionBlock, expr, new LabelEdge(""));
            expr.acceptVisitor(this);
        }

    }

    @Override
    public void visitEquals(AEEquals equals) throws CompilerException {
        graph.addVertex(equals);
        AEExpression leftSide = equals.getLeftSide();
        AEExpression rightSide = equals.getRightSide();
        graph.addVertex(leftSide);
        graph.addVertex(rightSide);
        graph.addEdge(equals, leftSide, new LabelEdge(""));
        graph.addEdge(equals, rightSide, new LabelEdge(""));
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
    }

    @Override
    public void visitLet(AELet let) throws CompilerException {
        graph.addVertex(let);
        for (AEAttribute attr : let.getAttributes()) {
            graph.addVertex(attr);
            graph.addEdge(let, attr, new LabelEdge("attribute"));
            attr.acceptVisitor(this);
        }
        AEExpression expr = let.getExpression();
        graph.addVertex(expr);
        graph.addEdge(let, expr, new LabelEdge("expr"));
        expr.acceptVisitor(this);
    }

    @Override
    public void visitVoid(AEIsVoid isVoid) throws CompilerException {
        graph.addVertex(isVoid);
        AEExpression expr = isVoid.getExpression();
        graph.addVertex(expr);
        graph.addEdge(isVoid, expr, new LabelEdge(""));
        expr.acceptVisitor(this);
        
    }

    @Override
    public void visitTrue(AETrue aeTrue) {
        graph.addVertex(aeTrue);

    }

    @Override
    public void visitFalse(AEFalse aeFalse) {
        graph.addVertex(aeFalse);
    }

    @Override
    public void visitAssignment(AEAssignment aeAssignment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitAssignment'");
    }

    @Override
    public void visitIdentifier(AEIdentifier identifier) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitIdentifier'");
    }

    @Override
    public void visitSmaller(AESmaller aeSmaller) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitSmaller'");
    }

    @Override
    public void visitNew(AENew aeNew) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitNew'");
    }

	@Override
	public void visitNot(AENot eaNot) throws CompilerException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visitNot'");
	}

}
