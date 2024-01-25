package ccompiler.parser;

import ccompiler.parser.expression.AEDivide;
import ccompiler.parser.expression.AEEnclosedExpression;
import ccompiler.parser.expression.AEExpression;
import ccompiler.parser.expression.AEIfElse;
import ccompiler.parser.expression.AEMinus;
import ccompiler.parser.expression.AEMultiply;
import ccompiler.parser.expression.AEPlus;
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

    private Graph<AElement, LabelEdge> graph = new SimpleGraph<>(LabelEdge.class);

    public ASTPrinter() {
    }

    @Override
    public void visitProgram(AEProgram program) {
        graph.addVertex(program);
        for (AElement e : program.getClasses()) {
            graph.addVertex(e);
            graph.addEdge(program, e, new LabelEdge(""));
        }
    }

    @Override
    public void visitClass(AEClass c) {
        graph.addVertex(c);
        for (AEFeature f : c.getFeatures()) {
            graph.addVertex(f);
            graph.addEdge(c, f, new LabelEdge(""));
        }
    }

    @Override
    public void visitFunction(AEFunction function) {
        graph.addVertex(function);
        for (AEFormal formal : function.getFormals()) {
            graph.addVertex(formal);
            graph.addEdge(function, formal, new LabelEdge("argument"));
        }
        AEExpression expression = function.getExpression();
        graph.addVertex(expression);
        graph.addEdge(function, expression, new LabelEdge("body"));

    }

    @Override
    public void visitAttribute(AEAttribute attribute) {
        graph.addVertex(attribute);
        AEExpression expression = attribute.getExpression();
        if (expression != null) {
            graph.addVertex(expression);
            graph.addEdge(attribute, expression, new LabelEdge("<-"));

        }
    }

    public void exportToDOT(File file) {
        DOTExporter<AElement, LabelEdge> exporter = new DOTExporter<>(v -> String.valueOf(v.hashCode()));

        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.toString()));
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
	public void visitIfElse(AEIfElse ifElse) {
        graph.addVertex(ifElse);
        AEExpression cond = ifElse.getCondExpression();
        graph.addVertex(cond);
        graph.addEdge(ifElse, cond, new LabelEdge("cond"));
        AEExpression then = ifElse.getThenExpression();
        graph.addVertex(then);
        graph.addEdge(ifElse, then, new LabelEdge("then"));
        AEExpression e = ifElse.getElseExpression();
        graph.addVertex(e);
        graph.addEdge(ifElse, e, new LabelEdge("else"));
	}

	@Override
	public void visitEnclosedExpression(AEEnclosedExpression enclosedExpression) {
        graph.addVertex(enclosedExpression);
        AEExpression expression = enclosedExpression.getExpression();
        graph.addVertex(expression);
        graph.addEdge(enclosedExpression, expression, new LabelEdge(""));
	
    }

	@Override
	public void visitPlus(AEPlus plus) {
        graph.addVertex(plus);
        AEExpression leftSide = plus.getLeftSide();
        AEExpression rightSide = plus.getRightSide();
        graph.addVertex(leftSide);
        graph.addVertex(rightSide);
        graph.addEdge(plus, leftSide, new LabelEdge(""));
        graph.addEdge(plus, rightSide, new LabelEdge(""));
	}

	@Override
	public void visitMinus(AEMinus minus) {
        graph.addVertex(minus);
        AEExpression leftSide = minus.getLeftSide();
        AEExpression rightSide = minus.getRightSide();
        graph.addVertex(leftSide);
        graph.addVertex(rightSide);
        graph.addEdge(minus, leftSide, new LabelEdge(""));
        graph.addEdge(minus, rightSide, new LabelEdge(""));
	}

	@Override
	public void visitMultiply(AEMultiply multiply) {
        graph.addVertex(multiply);
        AEExpression leftSide = multiply.getLeftSide();
        AEExpression rightSide = multiply.getRightSide();
        graph.addVertex(leftSide);
        graph.addVertex(rightSide);
        graph.addEdge(multiply, leftSide, new LabelEdge(""));
        graph.addEdge(multiply, rightSide, new LabelEdge(""));
	}

	@Override
	public void visitDivide(AEDivide divide) {
        graph.addVertex(divide);
        AEExpression leftSide = divide.getLeftSide();
        AEExpression rightSide = divide.getRightSide();
        graph.addVertex(leftSide);
        graph.addVertex(rightSide);
        graph.addEdge(divide, leftSide, new LabelEdge(""));
        graph.addEdge(divide, rightSide, new LabelEdge(""));
	}

}
