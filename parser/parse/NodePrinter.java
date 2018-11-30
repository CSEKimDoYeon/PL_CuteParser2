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

	// ListNode, QuoteNode, Node에 대한 printNode 함수를 각각 overload 형식으로 작성
	private void printNode(ListNode listNode) {
		if (listNode == ListNode.EMPTYLIST) {
			ps.print("( ) ");
			return;
		} // 노드가 EMPTYLIST여서 비어있을 경우에는 ( ) 만 출력한다.

		if (listNode == ListNode.ENDLIST) {
			return;
		} // 노드가 ENDLIST일 경우에는 종료한다.

		else if (listNode.car() instanceof QuoteNode) {
			// 리스트노드의 car() 즉 다음 value랑 비교 해서 QuoteNode 일 경우 
			ps.print(" ( ");
			printNode((Node) listNode.car()); // 쿼트 노드 제외한 다음것부터 재귀함수 실행
			printNode(listNode.cdr());
			ps.print(" ) ");
		} else { // QuoteNode가 아닐 경우
			ps.print(" ( ");
			ps.print("[ " + listNode.car() + " ]");
			if (listNode.cdr() != null) { // 첫 원소 다음 노드가 null이 아닐경우
				if (listNode.cdr().car() instanceof ListNode) {
					// 첫 원소 다음 노드가 null이 아니고, 그 노드가 리스트 노드일 경우.
					ps.print(" ( "); 
					printNode(listNode.cdr().car()); // 다음 원소의 첫 원소로 재귀함수 실행
					printNode(listNode.cdr().cdr()); // 같은 방식으로 재귀함수 실행.
					ps.print(" ) ");
				} else {
					printNode(listNode.cdr());
					// 모든 경우의 수가 아닐 경우에는 cdr만 사용해서 재귀함수 실행.
				}
			}
			ps.print(" ) "); // 마지막으로 괄호를 닫아준다.
		}
	}

	private void printNode(QuoteNode quoteNode) {
		// quoteNode를 매개변수로 노드를 출력하는 메소드
		if (quoteNode.nodeInside() == null)
			return; // 재귀를 이용할 것이기 쿼트노드가 비어있을 때 종료하는 것을 정의.
		else {
			if (quoteNode.nodeInside() instanceof ListNode) {
				// 쿼트노드와 리스트 노드를 비교했을때 TRUE이면
				ps.print("\'"); 
				printNode((ListNode) quoteNode.nodeInside());
				// 리스트노드의 printNode로 재귀함수 실행.
			} else {
				ps.print("\'"); 
				ps.print("[ " + quoteNode.nodeInside() + " ]");
				// 리스트 노드가 아니라면 하나의 문자열로 인식, 출력한다.
			}
		}
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오.
	}

	private void printNode(Node node) {
		// 노드를 매개변수로 노드를 출력하는 메소드
		if (node == null)
			return;
		else if (node instanceof ListNode) {
			// 처음 value가 노드일 경우 리스트도드의 프린트 노드 메소드로 프린트한다.
			printNode((ListNode) node);
		} else if (node instanceof QuoteNode) {
			// 처음 value가 노드일 경우 쿼트노드의 프린트 노드 메소드로 프린트한다.
			printNode((QuoteNode) node);
		} else 
			ps.print("[ " + node + " ]");
			// 둘다 아닐 경우에는 그냥 출력한다.
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오.
	}

	public void prettyPrint(Node node) {
		printNode(node);
	}
}
