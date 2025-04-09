package tree;
import java.util.ArrayList;

public class GenericTreeNode<E> {
	E data;
	//<some list of children>
	ArrayList<GenericTreeNode<E>> children;
	
	public GenericTreeNode(E theItem) {
		data = theItem;
		children = new ArrayList<>();//constructor/creating object 
	}
	
	public void addChild(GenericTreeNode<E> theItem) {
		children.add(theItem);
	}
	
	public void removeChild(E theItem) {
		for (int i = 0; i < children.size(); i++){
			if (children.get(i).data.equals(theItem)){
				children.remove(i);
				break;
			}
		}
	}
} 
