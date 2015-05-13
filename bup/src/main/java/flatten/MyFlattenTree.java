package flatten;


import java.util.List;

public class MyFlattenTree<T> implements FlattenTree<T> {
/*
 * Implement a method that, given a tree as a parameter, will return an inorder traversal of that tree.

Your implementation should throw an IllegalArgumentException if the tree is null.

Your implementation must implement the FlattenTree interface
would result in the list [1,5,4,9,6]. 
  /|\
 1 | 6
  /|\
 5 4 9
 * (non-Javadoc)
 * @see flatten.FlattenTree#flattenInOrder(flatten.Tree)
 */
	public List<T> flattenInOrder(Tree<T> tree) {
		if(tree==null)
			throw new IllegalArgumentException();
		new flatten.Triple<>(l, m, r)
		return null;
	}

}
