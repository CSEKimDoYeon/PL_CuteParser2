package parser.parse;

import java.io.PrintStream;

import parser.ast.ListNode;
import parser.ast.Node;
import parser.ast.QuoteNode;

public class NodePrinter {
	PrintStream ps;

	public static NodePrinter getPrinter(PrintStream ps) {
		return new NodePrinter(ps);
	}

	private NodePrinter(PrintStream ps) {
		this.ps = ps;
	}

	// ListNode, QuoteNode, Node�� ���� printNode �Լ��� ���� overload �������� �ۼ�
	private void printNode(ListNode listNode) {
		if (listNode == ListNode.EMPTYLIST) {
			ps.print("( ) ");
			return;
		} // ��尡 EMPTYLIST���� ������� ��쿡�� ( ) �� ����Ѵ�.

		if (listNode == ListNode.ENDLIST) {
			return;
		} // ��尡 ENDLIST�� ��쿡�� �����Ѵ�.

		else if (listNode.car() instanceof QuoteNode) {
			// ����Ʈ����� car() �� ���� value�� �� �ؼ� QuoteNode �� ��� 
			ps.print(" ( ");
			printNode((Node) listNode.car()); // ��Ʈ ��� ������ �����ͺ��� ����Լ� ����
			printNode(listNode.cdr());
			ps.print(" ) ");
		} else { // QuoteNode�� �ƴ� ���
			ps.print(" ( ");
			ps.print("[ " + listNode.car() + " ]");
			if (listNode.cdr() != null) { // ù ���� ���� ��尡 null�� �ƴҰ��
				if (listNode.cdr().car() instanceof ListNode) {
					// ù ���� ���� ��尡 null�� �ƴϰ�, �� ��尡 ����Ʈ ����� ���.
					ps.print(" ( "); 
					printNode(listNode.cdr().car()); // ���� ������ ù ���ҷ� ����Լ� ����
					printNode(listNode.cdr().cdr()); // ���� ������� ����Լ� ����.
					ps.print(" ) ");
				} else {
					printNode(listNode.cdr());
					// ��� ����� ���� �ƴ� ��쿡�� cdr�� ����ؼ� ����Լ� ����.
				}
			}
			ps.print(" ) "); // ���������� ��ȣ�� �ݾ��ش�.
		}
	}

	private void printNode(QuoteNode quoteNode) {
		// quoteNode�� �Ű������� ��带 ����ϴ� �޼ҵ�
		if (quoteNode.nodeInside() == null)
			return; // ��͸� �̿��� ���̱� ��Ʈ��尡 ������� �� �����ϴ� ���� ����.
		else {
			if (quoteNode.nodeInside() instanceof ListNode) {
				// ��Ʈ���� ����Ʈ ��带 �������� TRUE�̸�
				ps.print("\'"); 
				printNode((ListNode) quoteNode.nodeInside());
				// ����Ʈ����� printNode�� ����Լ� ����.
			} else {
				ps.print("\'"); 
				ps.print("[ " + quoteNode.nodeInside() + " ]");
				// ����Ʈ ��尡 �ƴ϶�� �ϳ��� ���ڿ��� �ν�, ����Ѵ�.
			}
		}
		// ���� �κ��� �־��� ��� ���Ŀ� �°� �ڵ带 �ۼ��Ͻÿ�.
	}

	private void printNode(Node node) {
		// ��带 �Ű������� ��带 ����ϴ� �޼ҵ�
		if (node == null)
			return;
		else if (node instanceof ListNode) {
			// ó�� value�� ����� ��� ����Ʈ������ ����Ʈ ��� �޼ҵ�� ����Ʈ�Ѵ�.
			printNode((ListNode) node);
		} else if (node instanceof QuoteNode) {
			// ó�� value�� ����� ��� ��Ʈ����� ����Ʈ ��� �޼ҵ�� ����Ʈ�Ѵ�.
			printNode((QuoteNode) node);
		} else 
			ps.print("[ " + node + " ]");
			// �Ѵ� �ƴ� ��쿡�� �׳� ����Ѵ�.
		// ���� �κ��� �־��� ��� ���Ŀ� �°� �ڵ带 �ۼ��Ͻÿ�.
	}

	public void prettyPrint(Node node) {
		printNode(node);
	}
}
